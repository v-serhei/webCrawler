package utils;

import beans.link.Link;
import beans.link.SimpleLinkManager;
import beans.urlDownloader.SimpleURLDownloader;

import java.io.File;

public class SimpleHTMLParser implements Runnable {
    private Link link;
    private SimpleLinkManager linkManager;

    public SimpleHTMLParser(SimpleLinkManager linkManager, Link link) {
        this.link = link;
        this.linkManager = linkManager;
    }

    @Override
    public void run() {
        parseHTMLFile(new SimpleURLDownloader().downloadHTML(link));
    }

    private void parseHTMLFile(File downloadHTML) {

    }
}
