package beans.crawlerBuilder;

import java.util.List;

/**
 *
 * Basic builder template interface
 * for creating an {@link beans.crawler.Crawler} object
 *
 *
 *
 * @author Verbitsky Segey
 * @version 1.0
 *
 * @see beans.crawler.Crawler
 */

public interface CrawlerBuilder {
    /**
     * Sets the start page for scanning
     * */
    void setStartURL(String seed);

    /**
     * Sets the count of scanning pages
     * */
    void setVisitedPagesLimit(int pagesLimit);

    /**
     *  Sets the depth of visited pages.
     *  Link depth is the number of clicks required to reach a
     *  given page from the home page
     *
     * */
    void setDepthLink(int depth);

    /**sets the use of multithreading when scanning pages.
     *
     * @param mode - defines the need for multithreading.
     *             True - enable
     *             false - disable
     */
    void setParallelMode(boolean mode);

    /**
     * sets the list of searched words
     * @param words - contains searching words
     *
     */
    void setSearchWords(List<String> words);

}
