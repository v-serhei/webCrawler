package beans.link;


import beans.HTMLparser.SimpleHTMLParser;
import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements {@link beans.link.LinkManager} interface.
 * It provides management of pages crawling process. Manager stores
 * URL addresses of downloaded pages and provides it for generating
 * the statistic.
 * <p>
 * Interface {@link LinkManager} extends Runnable interface. This allows to Manager
 * to run crawling process in separated thread
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */


public class SimpleLinkManager implements LinkManager {

    /**
     * Stores link to the {@link beans.crawler.Crawler} implementation instance
     */
    private final Crawler crawler;
    /**
     * Stores the status of work process:
     * value True - crawling is processing
     */
    private boolean workStatus;
    /**
     * Stores the numbers of pages to scan
     */
    private int pageLimit;
    /**
     * Stores the depth value of visited pages.
     * Link depth is the number of clicks required to reach a
     * given page from the home page
     */
    private int depthLinkLimit;
    /**
     * Stores the processed pages count
     * Used thread safe type {@link AtomicInteger}
     */
    private AtomicInteger visitedPageCount;
    /**
     * Stores the queue of pages to scan
     * Used thread safe type {@link BlockingQueue}
     */
    private BlockingQueue<Link> linkQueue;
    /**
     * Stores processed pages
     * Used thread safe type {@link CopyOnWriteArraySet}
     * The set interface stores unique values,
     * which eliminates the possibility of reprocessing pages
     */
    private CopyOnWriteArraySet<Link> visitedLinkStorage;
    /**
     * Store the link to the thread pool service
     * that provide multithreading process of scan
     * Used FixedThreadPool implementation
     */

    private ExecutorService linkProcessorPool;
    private Link startLink;


    /**
     * Constructs the {@link SimpleLinkManager} object and sets settings for the scanning process
     */
    public SimpleLinkManager(Link startLink, int pageLimit, int depthLinkLimit, boolean parallelMode, Crawler crawler) {
        this.pageLimit = pageLimit;
        this.depthLinkLimit = depthLinkLimit;

        int threadCount;
        if (parallelMode) {
            threadCount = DefaultCrawlerSettings.DEFAULT_THREAD_COUNT;
        } else {
            threadCount = 1;
        }
        this.startLink = startLink;

        visitedPageCount = new AtomicInteger(0);
        linkProcessorPool = Executors.newFixedThreadPool(threadCount);
        linkQueue = new LinkedBlockingQueue<>();
        linkQueue.add(startLink);
        visitedLinkStorage = new CopyOnWriteArraySet<>();
        this.crawler = crawler;

        System.out.printf("\nSeed link: %s\nPages limit: %d\nDepth limit: %d\nMultithreading mode: %s, threadCount: %d\n\n",
                startLink.getLinkValue(),
                pageLimit,
                depthLinkLimit,
                (parallelMode ? "ON" : "OFF"),
                threadCount);
    }


    /**
     * Implementation of crawl process
     */
    @Override
    public void crawlLink() {
        workStatus = true;
        Link link = linkQueue.poll();
        if (link == null) {
            return;
        }
        if (link.getLinkDepth() == depthLinkLimit) {
            return;
        }
        if (visitedLinkStorage.contains(link)) {
            return;
        }
        if (visitedLinkStorage.size() >= pageLimit) {
            return;
        }
        linkProcessorPool.execute(new SimpleHTMLParser(this, link));
    }

    @Override
    public void addVisitedLinkToStorage(Link link) {
        visitedLinkStorage.add(link);
        visitedPageCount.getAndIncrement();

    }

    @Override
    public boolean getWorkStatus() {
        return workStatus;
    }

    @Override
    public int getPageLimit() {
        return pageLimit;
    }

    public CopyOnWriteArraySet<Link> getVisitedLinkStorage() {
        return visitedLinkStorage;
    }

    @Override
    public void run() {
        System.out.println("Start crawl process");

        while (visitedPageCount.get() < pageLimit) {
            crawlLink();
            if (!workStatus) {
                stopNow();
                return;
            }
        }
        stopProcess();
    }


    /**
     * shutdown crawling process.
     * Called when the limit of processed pages is reached
     */
    private void stopProcess() {
        linkProcessorPool.shutdownNow();
        workStatus = false;
        System.out.println("Visited pages count: " + visitedPageCount.get());
        System.out.println("Stop crawl process");
        synchronized (crawler) {
            crawler.notifyAll();
        }
    }


    /**
     * forced process shutdown
     */
    @Override
    public void stopNow() {
        try {
            linkProcessorPool.shutdownNow();
            linkProcessorPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            linkProcessorPool.shutdownNow();
        }
        crawler.stopCrawlWithCrash();
        workStatus = false;
        synchronized (crawler) {
            crawler.notifyAll();
        }
    }

    public void addLinksToQueue(List<Link> linkList) {
        if (linkList.isEmpty()) {
            if (visitedPageCount.get() <= 1) {
                stopNow();
            }
            return;
        }
        if (linkList.size() > 0) {
            linkQueue.addAll(linkList);
        }
    }
}
