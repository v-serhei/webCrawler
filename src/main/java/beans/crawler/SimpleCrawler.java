package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import utils.StringUtil;

public class SimpleCrawler implements Crawler {
    private boolean status;
    private boolean errorStatus;
    private int pageLimit;
    private LinkManager linkManager;
    private Thread lmThread;


    public SimpleCrawler(String seedUrl, int pageLimit, int depthLinkLimit, boolean parallelMode) {
        this.pageLimit = pageLimit;

        linkManager = new SimpleLinkManager(
                new Link("https://", StringUtil.getDomainFromURL(seedUrl), seedUrl, 0),
                pageLimit,
                depthLinkLimit,
                parallelMode,
                this
        );
        errorStatus=false;
        lmThread = new Thread(linkManager);
    }

    @Override
    public void startCrawl() {

        if (!status) {
            status = true;
            // if page limit = 0 - there is no pages to crawl
            if (pageLimit > 0) {
                System.out.println("start crawling");
                lmThread.start();
                while (true) {
                    if (errorStatus) {
                        return;
                    }
                    synchronized (this) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!linkManager.getWorkStatus()) {
                        return;
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
            System.out.println("stop crawling");
        }
    }

    @Override
    public void getStatistic() {
        //TODO доделать статистику
        stopCrawl();
        System.out.println("Статистика");
    }

    @Override
    public void stopCrawlWithCrash() {
        status = false;
        errorStatus = true;
        System.out.println("Stop With error");
    }

    @Override
    public boolean getErrorStatus() {
        return errorStatus;
    }
}
