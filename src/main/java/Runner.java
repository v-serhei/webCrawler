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
        cBuilder.setParallelMode(true); //defaul 8 threads, thread count can be changed in DefaultCrawlerSettings interface
        */
        cBuilder.setDepthLink(2);
        cBuilder.setVisitedPagesLimit(10);
        cBuilder.setParallelMode(false);

        Crawler crawler =  cBuilder.buildSimpleCrawler();

        if(crawler != null) {
            System.out.println("Start crawling\n\n");
            crawler.startCrawl();
            if (crawler.getErrorStatus()) {
                System.out.println("Crawler has been crashed");
            }
            //crawler.getStatistic();
        }
        System.out.println("\n\nEnd program");
    }
}
