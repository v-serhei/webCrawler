package beans.statistic;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Base interface of Statistic Manager objects.
 * Provides control of statistic collecting process,
 * printing and saving statistic in CSV format
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see StatisticResult
 * @see StatisticCollector
 * @see <a href="https://ru.wikipedia.org/wiki/CSV"> CSV </a>
 */
public interface StatisticManager {
    void collectStatistic();

    void addStatisticResult(StatisticResult result, String fileName);

    void saveStatisticToCSV(String filePath);

    void printTopTenResults();

    CopyOnWriteArraySet<StatisticResult> getFullStats();


}
