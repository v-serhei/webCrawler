package beans.link;

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

}
