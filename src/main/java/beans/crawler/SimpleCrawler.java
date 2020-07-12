package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import beans.statistic.SimpleStatisticManager;
import beans.statistic.StatisticManager;
import utils.Helper;
import utils.StringUtil;

import java.util.List;

public class SimpleCrawler implements Crawler {
    private boolean status;
    private boolean errorStatus;
    private int pageLimit;
    private LinkManager linkManager;
    private StatisticManager statisticManager;
    private Thread lmThread;
    private List<String> searchWords;

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

    @Override
    public void stopCrawl() {
        if (status) {
            status = false;
            linkManager.stopNow();
            System.out.println("Stop crawling");
        }
    }

    @Override
    public void showStatistic() {
        statisticManager.collectStatistic();
        statisticManager.printTopTenResults();
    }

    @Override
    public void saveStatistic(String fileName) {
        statisticManager.saveStatisticToCSV(fileName);
    }

    @Override
    public void stopCrawlWithCrash() {
        status = false;
        errorStatus = true;
        System.out.println("Stop crawler with error");
    }

    @Override
    public boolean getErrorStatus() {
        return errorStatus;
    }
}
