package beans.crawler;

import java.net.URI;

public class SimpleCrawler implements Crawler {
    private URI seedUri;
    private int pageLimit;
    private int depthLink;
    private boolean parallelMode;
    //private

    private SimpleCrawler() {
    }

    public void startCrawl() {
        System.out.println("start crawling. stub");
    }

    public void stopCrawl() {
        System.out.println("stop crawling. stub");
    }

}
