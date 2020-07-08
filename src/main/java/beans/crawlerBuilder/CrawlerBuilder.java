package beans.crawlerBuilder;

public interface CrawlerBuilder {
    void setStartURL(String seed);

    void setVisitedPagesLimit(int pagesLimit);

    void setDepthLink(int depth);

    void setParallelMode (boolean mode);

}
