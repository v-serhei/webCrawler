package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import utils.StringUtil;

public class SimpleCrawler implements Crawler {
    private boolean status;
    private int pageLimit;
    private LinkManager linkManager;


    //private

    public SimpleCrawler(String seedUri, int pageLimit, int depthLinkLimit, boolean parallelMode) {
        this.pageLimit = pageLimit;

        linkManager = new SimpleLinkManager(
                new Link(seedUri, StringUtil.getBaseDomain(seedUri), 0),
                pageLimit,
                depthLinkLimit,
                parallelMode
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
        stopCrawl();
        System.out.println("Статистика");
    }

}
