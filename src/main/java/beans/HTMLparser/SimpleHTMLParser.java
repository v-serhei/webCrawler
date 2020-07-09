package beans.HTMLparser;

import beans.link.Link;
import beans.link.SimpleLinkManager;
import beans.urlDownloader.SimpleURLDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleHTMLParser implements Extractor, Runnable {
    private Link link;
    private SimpleLinkManager linkManager;
    private SimpleURLDownloader htmlDownloader;

    public SimpleHTMLParser(SimpleLinkManager linkManager, Link link) {
        this.link = link;
        this.linkManager = linkManager;
        htmlDownloader = new SimpleURLDownloader();
    }

    @Override
    public void run() {
        extractLinks(htmlDownloader.downloadHTML(link), link);
    }

    @Override
    public List<Link> extractLinks(File file, Link parentLinl) {
        List <Link> links = new ArrayList<>(128);
        //проверять соответствует ли домен найденой линки домену линки, которую нам передали
        //если нет - установить предел для линки = 0
        //если да - установить предел как link.getCurrentDepth()+1;


        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            while (bf.ready()) {

            }
        }catch (IOException ex) {

        }


        return null;
    }
}
