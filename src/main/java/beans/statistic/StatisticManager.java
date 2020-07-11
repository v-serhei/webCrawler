package beans.statistic;

import java.util.concurrent.CopyOnWriteArrayList;

public interface StatisticManager {
    void showStatistic();
    CopyOnWriteArrayList<StatisticResult> getFullStats();
    void addStatisticResult (StatisticResult result);

}
