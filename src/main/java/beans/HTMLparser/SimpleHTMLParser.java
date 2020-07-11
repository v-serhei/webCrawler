package beans.HTMLparser;

import beans.link.Link;
import beans.link.LinkManager;
import beans.link.SimpleLinkManager;
import beans.urlDownloader.SimpleURLDownloader;
import beans.urlDownloader.URLDownloader;
import utils.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleHTMLParser implements LinkParser, Runnable {
    private Link link;
    private LinkManager linkManager;
    private URLDownloader htmlDownloader;

    public SimpleHTMLParser(SimpleLinkManager linkManager, Link link) {
        this.link = link;
        this.linkManager = linkManager;
        htmlDownloader = new SimpleURLDownloader();
    }

    @Override
    public void run() {
        linkManager.addLinksToQueue(parseLink(htmlDownloader.downloadHTML(link), link));
    }

    @Override
    public List<Link> parseLink(File file, Link parentLink) {
        List<Link> links = new ArrayList<>(128);
            if (file!=null) {
                List<String> stringLinks = new ArrayList<>(128);
                try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
                    while (bf.ready()) {
                        links.addAll(StringUtil.getLinksFromLine(bf.readLine(), parentLink));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        return links;
    }
}
