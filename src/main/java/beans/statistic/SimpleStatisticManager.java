package beans.statistic;

import com.opencsv.CSVWriter;
import utils.FilesUtil;
import utils.Helper;
import utils.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleStatisticManager implements StatisticManager {
    private CopyOnWriteArraySet<StatisticResult> fullStats;
    private BlockingQueue<String> filesQueue;
    private CopyOnWriteArrayList<String> processedFiles;
    private List<String> searchWords;
    private ExecutorService fileParserPool;
    private AtomicInteger filesRemaining;
    private boolean collect;


    public SimpleStatisticManager(List<String> searchWords) {
        this.searchWords = searchWords;
        fileParserPool = Executors.newFixedThreadPool(16);
        filesQueue = new LinkedBlockingQueue<>();
        processedFiles = new CopyOnWriteArrayList<>();
        fullStats = new CopyOnWriteArraySet<>();
        collect = false;
        filesRemaining = new AtomicInteger(0);
    }


    @Override
    public CopyOnWriteArraySet<StatisticResult> getFullStats() {
        return fullStats;
    }


    @Override
    public void collectStatistic() {
        if (!collect) {
            collect = true;
            fillFilesQueue();
            filesRemaining.set(filesQueue.size());
            while (!filesQueue.isEmpty()) {
                String fileName = filesQueue.poll();

                if (processedFiles.contains(fileName)) {
                    System.out.println("File already processed "+fileName);
                    continue;
                }
                fileParserPool.execute(new SimpleStatisticCollector(fileName, searchWords, this));
            }
            while (true) {
                if (filesRemaining.get() > 0) {
                    System.out.println("Files remaining: " + filesRemaining.get());
                    Helper.sleepCurrentThread(1000);
                } else {
                    break;
                }
            }
            Helper.sleepCurrentThread(10000);
            fileParserPool.shutdown();
            collect = false;

        } else {
            System.out.println("Statistics are still being collected");
        }
    }

    private List<StatisticResult> getTopTenResult() {
        if (fullStats.isEmpty()) {
            return null;
        }
        ArrayList<StatisticResult> tmp = new ArrayList<>(fullStats);
        ArrayList<StatisticResult> res = new ArrayList<>(10);
        tmp.sort((o1, o2) -> o2.getTotalHits() - o1.getTotalHits());
        for (int i = 0; i < 9; i++) {
            res.add(tmp.get(i));
        }
        return res;
    }

    @Override
    public void addStatisticResult(StatisticResult result, String fileName) {
        fullStats.add(result);
        processedFiles.add(fileName);
        filesRemaining.decrementAndGet();

    }

    private void fillFilesQueue() {
        filesQueue.addAll(FilesUtil.getDownloadedFilesList());
    }

    @Override
    public void saveStatisticToCSV(String fileName) {
        saveFullStatistic(fileName);
        saveTopTenStatistic(fileName);
    }

    private void saveFullStatistic(String fileName) {
        if (!fullStats.isEmpty()) {
            List<String[]> fullStatistic = new ArrayList<>(1024);
            String fStatPath = StringUtil.STATISTIC_FOLDER.concat(fileName);
            StringBuilder header = generateCSVHeader();
            if(header == null) {
                return;
            }

            for (StatisticResult item : fullStats) {
                fullStatistic.add(getArrayFromStatisticResult(item));
            }

            try (
                    Writer writer = Files.newBufferedWriter(Paths.get(fStatPath));
                    CSVWriter csvWriter = new CSVWriter(writer,
                            ';',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END)
            ) {
                csvWriter.writeNext(header.toString().split(StringUtil.STATISTIC_DELIMITER));
                csvWriter.writeAll(fullStatistic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveTopTenStatistic(String fileName) {
        if (!fullStats.isEmpty()) {
            List<String[]> topTenStatistic = new ArrayList<>(10);
            String tStatPath = StringUtil.STATISTIC_FOLDER.concat("TopTen").concat(fileName);
            StringBuilder header = generateCSVHeader();
            if(header == null) {
                return;
            }

            for (StatisticResult item : getTopTenResult()) {
                topTenStatistic.add(getArrayFromStatisticResult(item));
            }

            try (
                    Writer writer = Files.newBufferedWriter(Paths.get(tStatPath));
                    CSVWriter csvWriter = new CSVWriter(writer,
                            ';',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END)
            ) {
                csvWriter.writeNext(header.toString().split(StringUtil.STATISTIC_DELIMITER));
                csvWriter.writeAll(topTenStatistic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getArrayFromStatisticResult(StatisticResult item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getLink().replaceAll(";", "")).append(StringUtil.STATISTIC_DELIMITER);
        for (Map.Entry<String, Integer> entry : item.getStatisticMap().entrySet()) {
            sb.append(entry.getValue()).append(StringUtil.STATISTIC_DELIMITER);
        }
        sb.append(item.getTotalHits());
        return sb.toString().split(StringUtil.STATISTIC_DELIMITER);
    }

    private StringBuilder generateCSVHeader() {
        if (fullStats.isEmpty()) {
            return null;
        }
        StringBuilder header = new StringBuilder();
        header.append("Link value").append(StringUtil.STATISTIC_DELIMITER);
        StatisticResult sr = null;
        for (StatisticResult item : fullStats) {
            sr = item;
            break;
        }

        for (Map.Entry<String, Integer> entry : sr.getStatisticMap().entrySet()) {
            header.append("Word: ").append(entry.getKey()).append(StringUtil.STATISTIC_DELIMITER);
        }
        header.append("Total hits");
        return header;
    }

    @Override
    public void printTopTenResults() {
        while (true) {
            if (collect) {
                Helper.sleepCurrentThread(3000);
            } else {
                break;
            }
        }
        System.out.println("\n\nTop 10 pages that contain the maximum number of searched words:\n");
        List <StatisticResult> top = getTopTenResult();

        if (top.isEmpty()) {
            System.out.println("No statistic files");
            return;
        }
        for (StatisticResult result : getTopTenResult()) {
            System.out.println(result);
        }

    }
}
