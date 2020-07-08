package beans.link;

import java.util.LinkedList;
import java.util.List;

public class SimpleLinkExtractor implements Extractor, Runnable {

    @Override
    public void run() {
        //проверять соответствует ли домен найденой линки домену линки, которую нам передали
        //если нет - установить предел для линки = 0
        //если да - установить предел как link.getCurrentDepth()+1;
    }

    @Override
    public List<Link> extract (Link link){
        List <Link> links = new LinkedList<>();



        return links;
    }

}
