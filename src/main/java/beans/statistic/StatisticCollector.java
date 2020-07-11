package beans.statistic;

public interface StatisticCollector extends Runnable{
    StatisticResult getStatisticFromFile ();
}
