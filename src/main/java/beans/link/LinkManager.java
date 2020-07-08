package beans.link;

import java.util.LinkedList;
import java.util.List;

public enum LinkManager {
    GET;
    //TODO make thread safe
    private LinkedList<Link> linksStorage = new LinkedList<>();

    public Link getLink () {
        return linksStorage.pollFirst();
    }

    public void addLinks (List<Link> linkList) {
        if (linkList.size() > 0) {
            linksStorage.addAll(linkList);
        }
    }
}
