package beans.statistic;

import java.util.concurrent.CopyOnWriteArrayList;

public interface StatisticManager {
    void collectStatistic();

    void addStatisticResult(StatisticResult result);

    void saveStatisticToCSV(String filePath);

    void printTopTenResults();

    CopyOnWriteArrayList<StatisticResult> getFullStats();


}
