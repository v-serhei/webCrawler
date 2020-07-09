package beans.link;

import java.util.Objects;

public class Link {
    private String linkValue;
    private String baseDomain;
    private int currentDepth;

    public Link(String linkValue, String baseDomain, int currentDepth) {
        this.linkValue = linkValue;
        this.baseDomain = baseDomain;
        this.currentDepth = currentDepth;
    }

    public String getLinkValue() {
        return linkValue;
    }

    public String getBaseDomain() {
        return baseDomain;
    }

    public int getCurrentDepth() {
        return currentDepth;
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
