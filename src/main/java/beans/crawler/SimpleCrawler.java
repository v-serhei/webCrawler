package beans.crawler;

import java.net.URL;

public class SimpleCrawler implements Crawler {
    private URL seedUri;
    private int pageLimit;
    private int depthLink;
    private boolean parallelMode;
    //private


    public SimpleCrawler(URL seedUri, int pageLimit, int depthLink, boolean parallelMode) {
        this.seedUri = seedUri;
        this.pageLimit = pageLimit;
        this.depthLink = depthLink;
        this.parallelMode = parallelMode;
    }

    public void startCrawl() {
        System.out.println("start crawling. stub");
        crawlPage();
    }

    public void stopCrawl() {
        System.out.println("stop crawling. stub");
    }

    @Override
    public void crawlPage() {
        System.out.println("CrawlPAge, stub");
    }
}
