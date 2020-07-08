package beans.link;

import java.util.concurrent.ExecutorService;

public interface LinkManager {
    void crawlLink(Link link);
    ExecutorService getLinkExtractorsPool();
    ExecutorService getPageParsersPool() ;
}
