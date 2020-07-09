package beans.link;


import beans.HTMLparser.SimpleHTMLParser;
import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleLinkManager implements LinkManager {

    private ExecutorService linkProcessorPool;
    private int pageLimit;
    private int depthLink;
    private Crawler crawler;
    private boolean working;

    private AtomicInteger visitedPageCount;
    private BlockingQueue<Link> linkQueue;
    private CopyOnWriteArraySet<Link> visitedLinkStorage;


    public SimpleLinkManager(Link startLink, int pageLimit, int depthLink, boolean parallelMode, Crawler crawler) {
        this.pageLimit = pageLimit;
        this.depthLink = depthLink;
        //TODO del if not used
        this.crawler = crawler;

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
        working = true;
        Link link = linkQueue.poll();

        if (link != null) {
            if (!visitedLinkStorage.contains(link)) {

                linkProcessorPool.execute(new SimpleHTMLParser(this, link));

                visitedLinkStorage.add(link);

                visitedPageCount.getAndIncrement();

            }
        }
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
