package beans.crawler;


//в дальнейшем настройки можно брать из файла базовых настроек
public interface DefaultCrawlerSettings {
    String START_URL = "https://en.wikipedia.org/wiki/Elon_Musk";
    int DEFAULT_THREAD_COUNT = 2;
}
