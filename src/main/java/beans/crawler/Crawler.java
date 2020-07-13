package beans.crawler;

/**
 *
 * Base interface of Web Crawler
 * Provides methods for control web crawling process
 *
 *
 * @author Verbitsky Segey
 * @version 1.0
 */

public interface Crawler {
    //Start crawling process
    void startCrawl();

    //Stop crawling process
    void stopCrawl();

    //Stop crawling process when get an error
    void stopCrawlWithCrash();

    /**
     * @return boolean value "true" if
     * an error occurred during crawling processing
     * This will end the crawling process
     */

    boolean getErrorStatus();

    /**
     * Display site statistic, that contains some information
     * about the number of processed sites, the number of words found,
     *
     * @see beans.statistic.StatisticResult
     * @see beans.statistic.StatisticManager
     */

    void showStatistic();

    /**
     * Save statistic to file
     * @param fileName - contains the specific file name
     *                 for creating the statistic file
     *
     * @see beans.statistic.StatisticResult
     * @see beans.statistic.StatisticManager
     */

    void saveStatistic(String fileName);
}
