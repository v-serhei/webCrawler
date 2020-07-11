package beans.link;

import java.util.Objects;

public class Link {
    private String linkValue;
    private String baseDomain;
    private String protocolValue;
    private int linkDepth;

    public Link(String protocolHTTP, String baseDomain, String linkValue, int currentDepth) {
        this.linkValue = linkValue;
        this.baseDomain = baseDomain;
        this.linkDepth = currentDepth;
        this.protocolValue = protocolHTTP;
    }

    public String getLinkValue() {
        return linkValue;
    }

    public String getBaseDomain() {
        return baseDomain;
    }

    public int getLinkDepth() {
        return linkDepth;
    }

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
