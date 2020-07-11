import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;
import beans.crawlerBuilder.SimpleCrawlerBuilder;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Start program \n");

        SimpleCrawlerBuilder cBuilder = new SimpleCrawlerBuilder();

        List<String> searchWords = new ArrayList<>(4);
        searchWords.add("Tesla");
        searchWords.add("Musk");
        searchWords.add("Gigafactory");
        searchWords.add("Elon Mask");

        cBuilder.setStartURL(DefaultCrawlerSettings.START_URL);
       /*
        cBuilder.setDepthLink(8);
        cBuilder.setVisitedPagesLimit(10000);
        cBuilder.setParallelMode(true); //default 4 threads, thread count can be changed in DefaultCrawlerSettings interface
        */
        cBuilder.setDepthLink(2);
        cBuilder.setVisitedPagesLimit(10);
        cBuilder.setParallelMode(true);
        cBuilder.setSearchWords(searchWords);

        Crawler crawler =  cBuilder.buildSimpleCrawler();

        if(crawler != null) {
            crawler.startCrawl();
            if (crawler.getErrorStatus()) {
                System.out.println("Crawler has been crashed");
            }
        }
        crawler.showStatistic();
        System.out.println("\n\nEnd program");
    }
}
