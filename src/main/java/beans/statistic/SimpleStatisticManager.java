package beans.statistic;

import utils.FilesUtil;
import utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleStatisticManager implements StatisticManager {
    private CopyOnWriteArrayList<StatisticResult> fullStats;
    private BlockingQueue<String> filesQueue;
    private List<String> searchWords;
    private ExecutorService fileParserPool;
    private AtomicInteger filesRemaining;
    private boolean collect;


    public SimpleStatisticManager(List<String> searchWords) {
        this.searchWords = searchWords;
        fileParserPool = Executors.newFixedThreadPool(2);
        filesQueue = new LinkedBlockingQueue<>();
        fullStats = new CopyOnWriteArrayList<>();
        collect = false;
        filesRemaining = new AtomicInteger(0);
    }


    @Override
    public CopyOnWriteArrayList<StatisticResult> getFullStats() {
        return fullStats;
    }


    @Override
    public void collectStatistic() {
        if (!collect){
            collect = true;
            fillFilesQueue();
            filesRemaining.set(filesQueue.size());
            while (!filesQueue.isEmpty()) {
                fileParserPool.execute(new SimpleStatisticCollector(filesQueue.poll(), searchWords, this));
            }
            while (true) {
                if (filesRemaining.get() > 0) {
                    System.out.println("Осталось обработать файлов: " + filesRemaining.get());
                    Helper.sleepCurrentThread(1000);
                }else {
                    break;
                }
            }
            Helper.sleepCurrentThread(3000);
            fileParserPool.shutdown();
            Helper.sleepCurrentThread(3000);
            collect = false;
        } else {
            System.out.println("Statistics are still being collected");
        }
    }

    private List <StatisticResult> getTopTenResult() {
        ArrayList<StatisticResult> tmp = new ArrayList<>(fullStats);
        ArrayList<StatisticResult> res = new ArrayList<>(10);
        tmp.sort((o1, o2) -> o2.getTotalHits() - o1.getTotalHits());
        for (int i = 0; i < 9; i++) {
            res.add(tmp.get(i));
        }
        return res;
    }

    @Override
    public void addStatisticResult(StatisticResult result) {
        fullStats.add(result);
        filesRemaining.decrementAndGet();
    }

    private void fillFilesQueue() {
        filesQueue.addAll(FilesUtil.getDownloadedFilesList());
    }

    @Override
    public void saveStatisticToCSV(String fileName) {
        if (!fullStats.isEmpty()) {
            System.out.println("записали стату в файл");
        }
       /*
        File f = new File(StringUtil.STATISTIC_FOLDER.concat(fileName));
        if (!f.exists()) {
            try {
                f.createNewFile();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        System.out.println("Stub save to file "+ fileName);
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
        for (StatisticResult result : getTopTenResult()) {
            System.out.println(result);
        }
    }
}
