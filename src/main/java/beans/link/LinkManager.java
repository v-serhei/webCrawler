package beans.link;

import java.util.List;

public interface LinkManager extends Runnable{
    boolean getWorkStatus ();

    int getPageLimit ();

    int getVisitedLinksCount ();

    void addLinksToQueue(List<Link> linkList);

    void addVisitedLinkToStorage(Link link);

    void crawlLink();

    void stopNow();
}
