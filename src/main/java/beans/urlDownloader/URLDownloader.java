package beans.urlDownloader;

import beans.link.Link;

import java.io.File;

/**
 * Base interface for URL downloader
 * Provide method to download web page to text/HTML file
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see java.net.URL
 * @see File
 */

public interface URLDownloader {
    File downloadHTML(Link url);
}
