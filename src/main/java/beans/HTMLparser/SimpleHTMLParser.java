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
        List<String> stringLinks = new ArrayList<>(128);
        //проверять соответствует ли домен найденой линки домену линки, которую нам передали
        //если нет - установить предел для линки = 0
        //если да - установить предел как link.getCurrentDepth()+1;

        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            while (bf.ready()) {
                stringLinks.addAll(StringUtil.getLinkFromLine(bf.readLine()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!stringLinks.isEmpty()) {
            for (String stringLink : stringLinks) {
                String linkDomain = StringUtil.getBaseDomain(stringLink);
                int linkDepth = 0;
                if (parentLink.getBaseDomain().equals(linkDomain)) {
                    linkDepth = parentLink.getCurrentDepth()+1;
                }

                links.add (new Link (stringLink, linkDomain, linkDepth));
            }
        }
        return links;
    }
}
