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

public class SimpleHTMLParser extends Thread implements LinkParser {
    private Link link;
    private LinkManager linkManager;
    private URLDownloader htmlDownloader;

    public SimpleHTMLParser(SimpleLinkManager linkManager, Link link) {
        super(link.getLinkValue());
        this.link = link;
        this.linkManager = linkManager;
        htmlDownloader = new SimpleURLDownloader();
    }

    @Override
    public void run() {
        if (link == null) {
            return;
        }
        if (((SimpleLinkManager) linkManager).getVisitedLinkStorage().size() < linkManager.getPageLimit()) {
            List<Link> linkList = parseLink(htmlDownloader.downloadHTML(link), link);
            if (!linkList.isEmpty()) {
                linkManager.addVisitedLinkToStorage(link);
                linkManager.addLinksToQueue(linkList);
            }
        }
    }

    @Override
    public List<Link> parseLink(File file, Link parentLink) {
        List<Link> links = new ArrayList<>(128);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String in;
                while ((in = reader.readLine()) != null) {
                    links.addAll(StringUtil.getLinksFromLine(in, parentLink));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return links;
    }
}
