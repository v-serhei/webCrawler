package beans.crawlerBuilder;

import java.net.URL;

public interface CrawlerBuilder {
    void setStartURL(URL seed);

    void setVisitedPagesLimit(int pagesLimit);

    void setDepthLink(int depth);

    void setParallelMode (boolean mode);
}
