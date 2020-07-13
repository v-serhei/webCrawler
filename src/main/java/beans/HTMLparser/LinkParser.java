package beans.HTMLparser;

import beans.link.Link;

import java.io.File;
import java.util.List;

/**
 * Base interface of simple HTML parser.  Provides method for parsing web page.
 * Parser gets values of {@code <a>} HTML tags that contains URL address from downloaded files and generates
 * list of {@link Link} objects. This list is consumed by the {@link beans.link.LinkManager}
 * for crawling process
 * <p>
 * Method returns list of {@link Link} objects
 *
 * @author Verbitsky Segey
 * @version 1.0
 * @see Link
 * @see beans.link.LinkManager
 */


public interface LinkParser {
    List<Link> parseLink(File file, Link parentLink);
}
