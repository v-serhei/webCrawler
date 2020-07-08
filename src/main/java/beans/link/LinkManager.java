package beans.link;

import java.util.concurrent.ExecutorService;

public interface LinkManager {
    void processLink(Link link);
    ExecutorService getLinkExtractorsPool();
    ExecutorService getPageParsersPool() ;
}
