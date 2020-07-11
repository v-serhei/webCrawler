package beans.crawlerBuilder;

import java.util.List;

public interface CrawlerBuilder {
    void setStartURL(String seed);

    void setVisitedPagesLimit(int pagesLimit);

    void setDepthLink(int depth);

    void setParallelMode(boolean mode);

    void setSearchWords(List<String> words);

}
