package beans.crawlerBuilder;

import beans.crawler.Crawler;
import beans.crawler.SimpleCrawler;

import java.util.List;

/**
 * This is class is a simple implementation of {@link beans.crawlerBuilder.CrawlerBuilder}
 * Provide of the simple building process of {@link Crawler} object using builder template
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see <a href="https://en.wikipedia.org/wiki/Builder_pattern" > Pattern buider </a>
 */

public class SimpleCrawlerBuilder implements CrawlerBuilder {
    /**
     * Stores start URL address for scanning
     */
    private String seed;
    /**
     * Stores the numbers of pages to scan
     */
    private int visitedPagesLimit;
    /**
     * Stores the depth value of visited pages.
     * Link depth is the number of clicks required to reach a
     * given page from the home page
     */
    private int depthLink;
    /**
     * Flag of using multithreading mode
     * True - enable
     * false - disable
     */
    private boolean parallelMode;
    /**
     * Stores list of searched words
     */
    private List<String> searchWords;

    /**
     * Default builder constructor
     */
    public SimpleCrawlerBuilder() {
    }

    /**
     * sets the list of searched words
     *
     * @param words - contains searching words
     */
    @Override
    public void setSearchWords(List<String> words) {
        this.searchWords = words;
    }

    /**
     * Method create a {@link beans.crawler.SimpleCrawler} instance
     *
     * @return {@link SimpleCrawler} instance
     */
    public Crawler buildSimpleCrawler() {
        if (seed == null) {
            return null;
        }
        return new SimpleCrawler(seed, visitedPagesLimit, depthLink, parallelMode, searchWords);
    }

    /**
     * Sets the start page to {@link beans.crawler.Crawler}
     *
     * @param seed - url link, for example: {@code https://en.wikipedia.org}
     */
    @Override
    public void setStartURL(String seed) {
        this.seed = seed;
    }

    /**
     * Sets the max number of processed pages
     *
     * @param pagesLimit - contains number of pages limit
     */
    @Override
    public void setVisitedPagesLimit(int pagesLimit) {
        this.visitedPagesLimit = pagesLimit;
    }


    /**
     * Sets the depth of visited pages.
     *
     * @param depth - contains value of depth
     */
    @Override
    public void setDepthLink(int depth) {
        this.depthLink = depth;
    }

    /**
     * Sets the multithreading mode
     *
     * @param mode - defines the need for multithreading.
     *             True - enable
     *             False - disable
     */
    @Override
    public void setParallelMode(boolean mode) {
        this.parallelMode = mode;
    }

}
