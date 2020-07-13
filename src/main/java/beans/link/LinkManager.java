package beans.link;

import java.util.List;

/**
 * Base interface of Link Manager objects
 * Provides control of crawling process
 * The interface extends {@link Runnable} interface
 * that allows to Manager to run crawling process
 * in separated thread
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see Runnable
 */

public interface LinkManager extends Runnable {
    boolean getWorkStatus();

    int getPageLimit();

    void addLinksToQueue(List<Link> linkList);

    void addVisitedLinkToStorage(Link link);

    void crawlLink();

    void stopNow();
}
