package beans.link;


import beans.HTMLparser.SimpleHTMLParser;
import beans.crawler.DefaultCrawlerSettings;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleLinkManager implements LinkManager {

    private ExecutorService linkProcessorPool;
    private int pageLimit;
    private int depthLinkLimit;
    private AtomicInteger visitedPageCount;
    private BlockingQueue<Link> linkQueue;
    private CopyOnWriteArraySet<Link> visitedLinkStorage;


    public SimpleLinkManager(Link startLink, int pageLimit, int depthLinkLimit, boolean parallelMode) {
        this.pageLimit = pageLimit;
        this.depthLinkLimit = depthLinkLimit;

        int threadCount;
        if (parallelMode) {
            threadCount = DefaultCrawlerSettings.DEFAULT_THREAD_COUNT;
        } else {
            threadCount = 1;
        }

        visitedPageCount = new AtomicInteger(0);
        linkProcessorPool = Executors.newFixedThreadPool(threadCount);
        linkQueue = new LinkedBlockingQueue<>(500);
        linkQueue.add(startLink);
        visitedLinkStorage = new CopyOnWriteArraySet<>();
    }

    @Override
    public void crawlLink() {
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

        linkProcessorPool.execute(new SimpleHTMLParser(this, link));
        visitedLinkStorage.add(link);
        visitedPageCount.getAndIncrement();

        //recursion call
        crawlLink();
    }

    @Override
    public int getVisitedPageCount() {
        return visitedPageCount.get();
    }

    @Override
    public boolean continueWork() {
        if (visitedPageCount.get() < pageLimit) {
            crawlLink();
            return true;
        } else {
            linkProcessorPool.shutdown();
            try {
                linkProcessorPool.awaitTermination(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void stopNow() {
        if (!linkProcessorPool.isShutdown()) {
            linkProcessorPool.shutdownNow();
            try {
                linkProcessorPool.awaitTermination(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addLinksToQueue(List<Link> linkList) {
        if (linkList == null) {
            return;
        }
        if (linkList.size() > 0) {
            linkQueue.addAll(linkList);
        }
    }

}
