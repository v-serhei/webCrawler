package beans.crawlerBuilder;

import beans.crawler.Crawler;
import beans.crawler.SimpleCrawler;

import java.net.URL;

public class BasicCrawlerBuilder implements CrawlerBuilder {

    private URL seed;
    private int visitedPagesLimit;
    private int depthLink;
    private boolean parallelMode;


    public Crawler buildSimpleCrawler() {
        return new SimpleCrawler(seed, visitedPagesLimit, depthLink, parallelMode);
    }

    public void setStartURL(URL seed) {
        this.seed = seed;
    }

    public void setVisitedPagesLimit(int pagesLimit) {
        this.visitedPagesLimit = pagesLimit;
    }

    public void setDepthLink(int depth) {
        this.depthLink = depth;
    }

    public void setParallelMode(boolean mode) {
        this.parallelMode = mode;
    }
}
