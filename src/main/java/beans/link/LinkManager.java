package beans.link;

import java.util.List;

public interface LinkManager {
    boolean continueWork();

    int getVisitedPageCount();

    void addLinksToQueue(List<Link> linkList);

    void crawlLink();

    void stopNow();
}
