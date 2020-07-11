import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;
import beans.crawlerBuilder.SimpleCrawlerBuilder;

public class Runner {
    public static void main(String[] args) {
        System.out.println("Start program \n");

        SimpleCrawlerBuilder cBuilder = new SimpleCrawlerBuilder();

        cBuilder.setStartURL(DefaultCrawlerSettings.START_URL);
       /*
        cBuilder.setDepthLink(8);
        cBuilder.setVisitedPagesLimit(10000);
        cBuilder.setParallelMode(true); //default 4 threads, thread count can be changed in DefaultCrawlerSettings interface
        */
        cBuilder.setDepthLink(2);
        cBuilder.setVisitedPagesLimit(10);
        cBuilder.setParallelMode(true);

        Crawler crawler =  cBuilder.buildSimpleCrawler();

        if(crawler != null) {
            crawler.startCrawl();
            if (crawler.getErrorStatus()) {
                System.out.println("Crawler has been crashed");
            }
        }
        System.out.println("\n\nEnd program");
    }
}
