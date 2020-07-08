package beans.crawler;

import java.net.URL;

public interface Crawler {
    void startCrawl();

    void stopCrawl();

    void crawlPage(URL page);
}
