package beans.HTMLparser;

import beans.link.Link;

import java.io.File;
import java.util.List;

public interface Extractor {
    List<Link> extractLinks(File file, Link parentLink);
}
