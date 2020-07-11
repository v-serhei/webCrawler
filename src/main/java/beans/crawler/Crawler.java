package beans.crawler;

public interface Crawler {
    void startCrawl();

    void stopCrawl();
    void stopCrawlWithCrash ();
    boolean getErrorStatus ();
    void getStatistic();
}
