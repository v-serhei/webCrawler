package beans.link;


import beans.HTMLparser.SimpleHTMLParser;
import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleLinkManager implements LinkManager {

    private final Crawler crawler;
    private boolean workStatus;
    private int pageLimit;
    private int depthLinkLimit;
    private AtomicInteger visitedPageCount;
    private BlockingQueue<Link> linkQueue;
    private CopyOnWriteArraySet<Link> visitedLinkStorage;
    private ExecutorService linkProcessorPool;
    private Link startLink;


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
                (parallelMode ? "ON":"OFF"),
                threadCount);
    }


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


    private void stopProcess() {
        linkProcessorPool.shutdownNow();
        workStatus = false;
        System.out.println("Visited pages count: " + visitedPageCount.get());
        System.out.println("Stop crawl process");
        synchronized (crawler) {
            crawler.notifyAll();
        }
    }



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
