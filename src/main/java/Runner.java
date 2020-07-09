import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;
import beans.crawlerBuilder.SimpleCrawlerBuilder;

public class Runner {
    public static void main(String[] args) {
        System.out.println("-------------------------------------start program");

        SimpleCrawlerBuilder cBuilder = new SimpleCrawlerBuilder();

        cBuilder.setStartURL(DefaultCrawlerSettings.START_URL);
        cBuilder.setDepthLink(8);
        cBuilder.setVisitedPagesLimit(10000);
        cBuilder.setParallelMode(false);
        Crawler crawler =  cBuilder.buildSimpleCrawler();

        if(crawler != null) {
            System.out.println("-------------------------------------start crawling");
            crawler.startCrawl();
            crawler.getStatistic();
        }


        System.out.println("-------------------------------------end program");
    }
}
