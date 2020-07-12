package beans.crawler;

public interface Crawler {
    void startCrawl();

    void stopCrawl();

    void stopCrawlWithCrash();

    boolean getErrorStatus();

    void showStatistic();

    void saveStatistic(String fileName);
}
