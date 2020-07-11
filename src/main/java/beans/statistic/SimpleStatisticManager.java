package beans.statistic;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class SimpleStatisticManager implements StatisticManager {
    private CopyOnWriteArrayList<StatisticResult> fullStats;
    private BlockingQueue<String> filesQueue;
    private List<String> searchWords;
    private ExecutorService fileParserPool;
    private int statCount;


    public SimpleStatisticManager(List<String> searchWords) {
        this.searchWords = searchWords;
        fileParserPool = Executors.newFixedThreadPool(2);
        filesQueue = new LinkedBlockingQueue<>();
        fullStats = new CopyOnWriteArrayList<>();
    }


    @Override
    public CopyOnWriteArrayList<StatisticResult> getFullStats() {
        return fullStats;
    }


    @Override
    public void showStatistic() {
        fillFilesQueue();
        statCount = filesQueue.size();
        while (!filesQueue.isEmpty()) {
            fileParserPool.execute(new SimpleStatisticCollector(filesQueue.poll(), searchWords, this));
        }
        /*
        while (true) {
            if (fullStats.size() < statCount) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }

    private List <StatisticResult> getTopTenResult() {
        ArrayList<StatisticResult> tmp = new ArrayList<>(fullStats);
        ArrayList<StatisticResult> res = new ArrayList<>(10);
        tmp.sort(Comparator.comparingInt(StatisticResult::getTotalHits));
        for (int i = 0; i < 9; i++) {
            res.add(tmp.get(i));
        }
        return res;
    }

    @Override
    public void addStatisticResult(StatisticResult result) {

    }

    private void fillFilesQueue() {
        filesQueue.addAll(FilesUtil.getDownloadedFilesList());
    }

}
