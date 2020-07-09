package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import utils.StringUtil;

public class SimpleCrawler implements Crawler {
    private boolean status;

    private String seedURL;
    private int pageLimit;
    private LinkManager linkManager;


    //private

    public SimpleCrawler(String seedUri, int pageLimit, int depthLink, boolean parallelMode) {
        this.seedURL = seedUri;
        this.pageLimit = pageLimit;

        linkManager = new SimpleLinkManager(
                new Link(seedURL, StringUtil.getBaseDomain(seedURL), 0),
                pageLimit,
                depthLink,
                parallelMode,
                this
        );
    }

    @Override
    public void startCrawl() {
        if (!status) {
            status = true;
            // if page limit = 0 - there is no pages to crawl
            if (pageLimit > 0) {
                System.out.println("start crawling");
                linkManager.crawlLink();
                while (linkManager.getVisitedPageCount() < pageLimit) {
                    if (!linkManager.continueWork()) {
                        status = false;
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
    }

    @Override
    public void stopCrawl() {
        if (status) {
            status = false;
            linkManager.stopNow();
            System.out.println("stop crawling");
        } else {
            System.out.println("Not started yet");
        }
    }

    @Override
    public void getStatistic() {
        //TODO доделать статистику
        System.out.println("Статистика");
    }

    public boolean getWorkStatus() {
        return status;

    }

    public LinkManager getLinkManager() {
        return linkManager;
    }
}
