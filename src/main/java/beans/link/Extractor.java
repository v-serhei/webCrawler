package beans.link;

import java.net.URL;
import java.util.List;

public interface Extractor {
    List<Link> extract (URL link);
}
