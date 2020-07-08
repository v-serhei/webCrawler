import beans.crawler.Crawler;
import beans.crawler.CrawlerStrings;
import beans.crawlerBuilder.BasicCrawlerBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class Runner {
    public static void main(String[] args) {
        BasicCrawlerBuilder cBuilder = new BasicCrawlerBuilder();
        try {
            cBuilder.setStartURL(new URL(CrawlerStrings.START_URL));
        } catch (MalformedURLException e) {
            //TODO del this
            System.out.println("error to set start URL");
            e.printStackTrace();
        }

        cBuilder.setDepthLink(8);
        cBuilder.setVisitedPagesLimit(10000);
        cBuilder.setParallelMode(false);


        Crawler crawler =  cBuilder.buildSimpleCrawler();
        crawler.startCrawl();
        crawler.stopCrawl();

    }
}
