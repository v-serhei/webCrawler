package beans.link;

import java.util.Objects;

/**
 * The class that describes the link object.
 * Encapsulates the full value of the HTML link,
 * the depth relative to the base link,
 * the type of protocol for connecting to the site
 * and the root domain of the site.
 *
 * @author Verbitsky Sergey
 */

public class Link {
    /**
     * Stores URL address of site
     */
    private String linkValue;
    /**
     * Stores base domain name of the site
     * example:
     * for URL address: {@code https://en.wikipedia.org/wiki/Wikipedia:Contents}
     * domain name: {@code en.wikipedia.org}
     */
    private String baseDomain;
    /**
     * Stores the protocol type of the site (https)
     */
    private String protocolValue;
    /**
     * Stores the depth of link
     */
    private int linkDepth;

    /**
     * Constructs the object, and sets values:
     *
     * @param protocolHTTP - base HTTP protocol
     * @param baseDomain   - base domain name
     * @param linkValue    - full URL address
     * @param currentDepth - link depth. Link depth is the
     *                     number of clicks required to reach a
     *                     given page from the home page
     */
    public Link(String protocolHTTP, String baseDomain, String linkValue, int currentDepth) {
        this.linkValue = linkValue;
        this.baseDomain = baseDomain;
        this.linkDepth = currentDepth;
        this.protocolValue = protocolHTTP;
    }

    /**
     * Return full URL address
     *
     * @return string value of URL address
     */
    public String getLinkValue() {
        return linkValue;
    }

    /**
     * Return base domain name value
     *
     * @return string value of base domain name
     */
    public String getBaseDomain() {
        return baseDomain;
    }

    /**
     * Return depth value
     *
     * @return int value of link depth
     */
    public int getLinkDepth() {
        return linkDepth;
    }

    /**
     * Return HTTP protocol type
     *
     * @return string value of protocol
     */
    public String getProtocolValue() {
        return protocolValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link)) return false;
        Link link = (Link) o;
        return Objects.equals(linkValue, link.linkValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkValue);
    }
}
