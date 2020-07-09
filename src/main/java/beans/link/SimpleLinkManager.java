package beans.link;


import beans.crawler.DefaultCrawlerSettings;
import beans.HTMLparser.SimpleHTMLParser;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleLinkManager implements LinkManager {
    private ExecutorService linkExtractorsPool;
    private ExecutorService pageParsersPool;
    private int pageLimit;
    private int depthLink;

    private AtomicInteger visitedPageCount;

    //TODO make thread safe
    private CopyOnWriteArraySet<Link> linksStorage = new CopyOnWriteArraySet<>();

    public SimpleLinkManager(int pageLimit, int depthLink, boolean parallelMode) {
        this.pageLimit = pageLimit;
        this.depthLink = depthLink;

        int threadCount;
        if (parallelMode) {
            threadCount = DefaultCrawlerSettings.DEFAULT_THREAD_COUNT;
        } else {
            threadCount = 1;
        }
        visitedPageCount = new AtomicInteger(0);
        linkExtractorsPool = Executors.newFixedThreadPool(threadCount);
        pageParsersPool = Executors.newFixedThreadPool(threadCount);

    }

    @Override
    public void crawlLink(Link link) {
        //запускать потоки на выполнение поиска ссылок и граба страниц в файлы
        linkExtractorsPool.execute(new SimpleHTMLParser(this, link));
    }

    public ExecutorService getLinkExtractorsPool() {
        return linkExtractorsPool;
    }

    public ExecutorService getPageParsersPool() {
        return pageParsersPool;
    }


    public int addLinks(List<Link> linkList) {
        if (linkList.size() > 0) {
            linksStorage.addAll(linkList);
            return linkList.size();
        }
        return 0;
    }

}
