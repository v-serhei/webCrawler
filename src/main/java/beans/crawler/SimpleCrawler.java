package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import beans.statistic.SimpleStatisticManager;
import beans.statistic.StatisticManager;
import utils.StringUtil;

import java.util.List;


/**
 *
 * This class is a simple implementation of the {@link Crawler} interface.
 * Implements all basic methods of {@link Crawler} interface for present an
 * example of the simple crawling process.
 *
 *  To crawl pages, the class object must be provided with an implementation:
 *  {@link LinkManager} - controls the process of scanning web pages:
 *  connecting, downloading, saving the result of scanning
 *
 *  {@link StatisticManager} - controls the process of collecting
 *  statistics from downloaded pages
 *
 *  To create a class object, use constructor or create this
 *  using class builder {@link beans.crawlerBuilder.CrawlerBuilder}
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see LinkManager
 * @see StatisticManager
 * @see beans.crawlerBuilder.CrawlerBuilder
 * @see DefaultCrawlerSettings
 *
 *
 * */



public class SimpleCrawler implements Crawler {
    private boolean status;
    private boolean errorStatus;
    private int pageLimit;
    private LinkManager linkManager;
    private StatisticManager statisticManager;
    private Thread lmThread;
    private List<String> searchWords;


    /**
     * Construct a simple crawler object
     *
     * @param depthLinkLimit  - sets the depth of visited pages.
     *                        Link depth is the number of clicks
     *                        required to reach a given page from the home page
     *
     * @param pageLimit - sets the count of scanning pages
     * @param parallelMode - sets the use of multithreading when scanning pages.
     *                     Value "true" - multithreading is enable
     *                     (uses the number of threads specified in {@link DefaultCrawlerSettings}
     *                     Value "false" - using one thread.
     *
     *                     Note:
     *                     When using multithreaded mode, the scan threads may take
     *                     some time to shut down
     *
     *                     This param does not affect the use of multithreading
     *                     by statistics manager. Statistic manager use
     *
     *                     {@link LinkManager} always starts in a separate thread.
     *
     * @param searchWords - contains a list if searching words
     * @param seedUrl - sets the start page for scanning
     *
     * */
    public SimpleCrawler(String seedUrl, int pageLimit, int depthLinkLimit, boolean parallelMode, List <String> searchWords) {
        this.pageLimit = pageLimit;

        linkManager = new SimpleLinkManager(
                new Link("https://", StringUtil.getDomainFromURL(seedUrl), seedUrl, 0),
                pageLimit,
                depthLinkLimit,
                parallelMode,
                this
        );
        this.searchWords = searchWords;

        errorStatus = false;

        lmThread = new Thread(linkManager);
        statisticManager = new SimpleStatisticManager(searchWords);

    }

    /**
     * Start scanning process and awaits the result of the {@link LinkManager}'s completion
     *
     */
    @Override
    public void startCrawl() {

        if (!status) {
            status = true;
            // if page limit = 0 - there is no pages to crawl
            if (pageLimit > 0) {
                lmThread.start();
                while (true) {
                    if (errorStatus) {
                        break;
                    }
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!linkManager.getWorkStatus()) {
                        break;
                    }
                }
            } else {
                System.out.println("Page limit is 0, please set page limit before start crawler");
                status = false;
            }
        } else {
            System.out.println("Already running");
        }
        try {
            Thread.sleep(3000);
            lmThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * Forcibly stops the process without awaits
     * the result of the {@link LinkManager}'s completion.
     *
     * Sends a forced stop command to the {@link LinkManager}
     *
     *
     */
    @Override
    public void stopCrawl() {
        if (status) {
            status = false;
            linkManager.stopNow();
            System.out.println("Stop crawling");
        }
    }

    /**
     * Display statistic of scanning
     * */
    @Override
    public void showStatistic() {
        if (!linkManager.getWorkStatus()) {
            lmThread.interrupt();
        }
        statisticManager.collectStatistic();
        statisticManager.printTopTenResults();
    }

    /**
     * Save statistic to specific file
     * @param fileName - contains file name for the statistic file
     * */
    @Override
    public void saveStatistic(String fileName) {
        statisticManager.saveStatisticToCSV(fileName);
    }

    /**
     * Method called by LinkManager when crawling process failed
     *
     * */
    @Override
    public void stopCrawlWithCrash() {
        status = false;
        errorStatus = true;
        System.out.println("Stop crawler with error");
    }

    /**
     * Method called by {@code Runner} to get crawling process status
     * @return boolean value "True" if process was crashed
     *
     * */
    @Override
    public boolean getErrorStatus() {
        return errorStatus;
    }
}
