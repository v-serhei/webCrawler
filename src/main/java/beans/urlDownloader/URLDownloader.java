package beans.urlDownloader;

import beans.link.Link;
import java.io.File;

public interface URLDownloader {
    File downloadHTML (Link url);
}
