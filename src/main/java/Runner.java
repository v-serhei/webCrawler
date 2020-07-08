import beans.crawler.Crawler;
import beans.crawler.DefaultCrawlerSettings;
import beans.crawler.SimpleCrawler;
import beans.crawlerBuilder.SimpleCrawlerBuilder;

import java.util.concurrent.TimeUnit;

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
            crawler.startCrawl();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //todo переделать запуск (подождать пока стартанут потоки!)
            try {
                System.out.println("status = " + ((SimpleCrawler) crawler).getWorkStatus());
                if (((SimpleCrawler) crawler).getWorkStatus()) {
                    System.out.println("try to shutdown pools");
                    if (((SimpleCrawler) crawler).getLinkManager().getLinkExtractorsPool().isShutdown()) {
                        crawler.stopCrawl();
                    } else {
                        System.out.println("ждемсс");
                        ((SimpleCrawler) crawler).getLinkManager().getLinkExtractorsPool().awaitTermination(5, TimeUnit.SECONDS);
                        crawler.stopCrawl();
                    }


                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        System.out.println("-------------------------------------end program");
    }
}
