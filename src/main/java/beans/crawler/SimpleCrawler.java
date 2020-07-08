package beans.crawler;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import utils.LinkParser;

public class SimpleCrawler implements Crawler {
    private boolean status;

    private String seedUrl;
    private int pageLimit;
    private int depthLink;
    private boolean parallelMode;
    private LinkManager linkManager;

    //private

    public SimpleCrawler(String seedUri, int pageLimit, int depthLink, boolean parallelMode) {
        this.seedUrl = seedUri;
        this.pageLimit = pageLimit;
        this.depthLink = depthLink;
        this.parallelMode = parallelMode;
        linkManager = new SimpleLinkManager(pageLimit, depthLink, parallelMode);
    }

    @Override
    public void startCrawl() {
        if (!status) {
            status = !status;
            // if page limit = 0 - there is no pages to crawl
            if (pageLimit > 0) {
                System.out.println("start crawling. stub");
                linkManager.processLink(new Link(seedUrl, LinkParser.getBaseDomain(seedUrl), 0));

             } else {
                System.out.println("Page limit is 0, please set page limit before start crawler");
                stopCrawl();
            }
        } else {
            System.out.println("Already running");
        }

    }

    @Override
    public void stopCrawl() {
        if (status) {
            status = !status;
            System.out.println("stop crawling. stub");
        } else {
            System.out.println("Not started yet");
        }
    }

    public boolean getWorkStatus() {
        return status;

    }

    public LinkManager getLinkManager() {
        return linkManager;
    }
}
