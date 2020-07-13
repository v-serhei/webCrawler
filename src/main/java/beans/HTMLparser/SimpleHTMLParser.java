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

/**
 * This class is a simple implementation of {@link LinkParser} interface that
 * Provides processing of HTML files. Extracts values of links from HTML files,
 * collect it to list of {@link Link} objects and provide it to consumer.
 * This class extends java Thread class. Instance of this class can be
 * in separate thread to provide multithreaded processing
 *
 * @author Verbitsky Segey
 * @version 1.0
 * @see Link
 * @see LinkManager
 */


public class SimpleHTMLParser extends Thread implements LinkParser {
    /**
     * Stores {@link Link} object, that contains URL address.
     * Parser uses {@link URLDownloader} implementation for download HTML page to file.
     * File is analyzed with methods of {@link StringUtil} static class
     * for extracting values of <A > HTML tags and collecting it in list of {@link Link} objects.
     */
    private Link link;

    /**
     * Stores the link of {@link LinkManager} instance that consumes
     * result list of {@link List} objects. The result list is provided to the {@link LinkManager}
     * by calling {@code LinkManager.addLinksToQueue} method".
     */
    private LinkManager linkManager;
    /**
     * Stores the link of {@link URLDownloader} instance.
     * Used for download HTML page for parsing
     */
    private URLDownloader htmlDownloader;

    /**
     * Constructor of SimpleHTMLParser class
     *
     * @param linkManager - contains instance of {@link LinkManager} instance
     * @param link        - contains {@link Link} object, that contains URL address for parsing
     */
    public SimpleHTMLParser(SimpleLinkManager linkManager, Link link) {
        super(link.getLinkValue());
        this.link = link;
        this.linkManager = linkManager;
        htmlDownloader = new SimpleURLDownloader();
    }

    /**
     * Implementation of run method, that begin a parsing process.
     * Before downloading a page, parser checks if the limit of processed pages has been reached.
     * After downloading and parsing the page, it adds the found links to the queue for processing
     */
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


    /**
     * Implementation of {@link beans.HTMLparser.LinkParser} interface method.
     *
     * @param file       - contains the link to file, that needs to be parsed
     * @param parentLink - contains the parent {@link Link} object.
     *                   It used to define and set the depth of links was found
     * @return list of {@link Link} objects, that was found while parsing parent URL address
     */
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
