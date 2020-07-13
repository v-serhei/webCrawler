package beans.statistic;

import java.util.concurrent.CopyOnWriteArraySet;

public interface StatisticManager {
    void collectStatistic();

    void addStatisticResult(StatisticResult result, String fileName);

    void saveStatisticToCSV(String filePath);

    void printTopTenResults();

    //CopyOnWriteArrayList<StatisticResult> getFullStats();
    CopyOnWriteArraySet<StatisticResult> getFullStats();


}
