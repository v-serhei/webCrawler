package beans.statistic;

import java.util.List;

public class SimpleStatisticCollector implements StatisticCollector {

    private String filePath;
    private StatisticManager statisticManager;
    List<String> searchWords;

    public SimpleStatisticCollector(String path, List<String> words, StatisticManager sManager) {
        filePath = path;
        statisticManager = sManager;
        searchWords = words;
    }

    @Override
    public StatisticResult getStatisticFromFile() {
        StatisticResult sr = new StatisticResult();

        return null;
    }

    @Override
    public void run() {

    }
}
