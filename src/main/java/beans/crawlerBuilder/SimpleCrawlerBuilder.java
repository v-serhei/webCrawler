package beans.crawlerBuilder;

import beans.crawler.Crawler;
import beans.crawler.SimpleCrawler;

import java.util.List;

public class SimpleCrawlerBuilder implements CrawlerBuilder {

    private String seed;
    private int visitedPagesLimit;
    private int depthLink;
    private boolean parallelMode;
    private List <String> searchWords;

    @Override
    public void setSearchWords(List<String> words) {
       this.searchWords = words;
    }

    public SimpleCrawlerBuilder() {
    }

    public Crawler buildSimpleCrawler() {
        if (seed == null) {
            return null;
        }
        return new SimpleCrawler(seed, visitedPagesLimit, depthLink, parallelMode, searchWords);
    }

    @Override
    public void setStartURL(String seed) {
        this.seed = seed;
    }
    @Override
    public void setVisitedPagesLimit(int pagesLimit) {
        this.visitedPagesLimit = pagesLimit;
    }
    @Override
    public void setDepthLink(int depth) {
        this.depthLink = depth;
    }
    @Override
    public void setParallelMode(boolean mode) {
        this.parallelMode = mode;
    }

}
