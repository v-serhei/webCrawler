package beans.HTMLparser;

import beans.link.Link;

import java.io.File;
import java.util.List;

public interface LinkParser {
    List<Link> parseLink(File file, Link parentLink);
}
