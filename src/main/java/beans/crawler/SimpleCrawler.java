package beans.crawler;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCrawler implements Crawler {
    private boolean status;
    private URL seedUri;
    private int pageLimit;
    private int depthLink;
    private boolean parallelMode;
    private AtomicInteger parsedPagesCount;
    //private

    {
        status = false;
        parsedPagesCount = new AtomicInteger(0);
    }

    public SimpleCrawler(URL seedUri, int pageLimit, int depthLink, boolean parallelMode) {
        this.seedUri = seedUri;
        this.pageLimit = pageLimit;
        this.depthLink = depthLink;
        this.parallelMode = parallelMode;
    }

    public void startCrawl() {
        if (!status) {
            status = !status;
            parsedPagesCount.set(0);
            System.out.println("start crawling. stub");
            crawlPage(seedUri);
        } else {
            System.out.println("Already running");
        }

    }

    public void stopCrawl() {
        if (status) {
            status = !status;
            System.out.println("stop crawling. stub");
        } else {
            System.out.println("Not started yet");
        }

    }

    @Override
    public void crawlPage(URL page) {

        System.out.println("CrawlPAge, stub");
    }
}
