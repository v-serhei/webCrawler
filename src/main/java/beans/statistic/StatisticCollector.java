package beans.statistic;

/**
 * Base interface of Statistic collector.
 * Provides method for collecting {@link StatisticResult}
 * objects from test/html files
 * The interface extends {@link Runnable} interface
 * that allows to {@link StatisticManager} instance to run
 * collecting process in separated thread
 * Method returns  {@link StatisticResult} object
 *
 * @author Verbitsky Segey
 * @version 1.0
 * @see StatisticResult
 * @see Runnable
 */
public interface StatisticCollector extends Runnable {
    StatisticResult getStatisticFromFile();
}
