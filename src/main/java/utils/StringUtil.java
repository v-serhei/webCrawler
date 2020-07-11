package utils;

import beans.link.Link;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    static {
        ROOT_DOWNLOADS_FOLDER = "src"
                .concat(File.separator)
                .concat("main")
                .concat(File.separator)
                .concat("resources")
                .concat(File.separator)
                .concat("downloads")
                .concat(File.separator);
    }

    private static final String ROOT_DOWNLOADS_FOLDER;

    private StringUtil() {
    }

    public static String getDomainFromURL(String url) {
        Pattern domainPattern = Pattern.compile(RegularExpressions.PATTERN_BASE_DOMAIN_PART);
        Matcher matcher = domainPattern.matcher(url.concat("/"));
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }


    public static String getFolderNameFromUrl(String url) {
        return ROOT_DOWNLOADS_FOLDER.concat(getDomainFromURL(url));
    }

    public static List<Link> getLinksFromLine(String line, Link parentLink) {
        Pattern tagPattern = Pattern.compile(RegularExpressions.PATTERN_A_TAG_PART);
        List<String> strLinks = new ArrayList<>();
        Matcher tagMatcher;
        Matcher invisibleLinkMatcher;
        tagMatcher = tagPattern.matcher(line);

        while (tagMatcher.find()) {
            invisibleLinkMatcher = Pattern.compile(
                    RegularExpressions.PATTERN_INVISIBLE_LINK_PROPERTY)
                    .matcher(tagMatcher.group());
            //if link haven't a property "display:none - add to links list
            if (!invisibleLinkMatcher.find()) {
                if (!tagMatcher.group(5).startsWith("#"))
                    strLinks.add(getLinkValueFromTag(tagMatcher.group(5), parentLink));
            }
        }

        int linkDepth = 0;
        List<Link> resultList = new ArrayList<>();

        for (String linkValue : strLinks) {
            if (getDomainFromURL(linkValue).equals(parentLink.getBaseDomain())) {
                linkDepth = parentLink.getLinkDepth() + 1;
            }
            resultList.add(new Link(
                    RegularExpressions.HTTP_BASE_PROTOCOL.concat(RegularExpressions.HTTP_PROTOCOL_DELIMITER),
                    getDomainFromURL(linkValue),
                    linkValue,
                    linkDepth)
            );
        }
        return resultList;
    }

    public static String getLinkValueFromTag(String link, Link parentLink) {
        if (link.startsWith("//")) {
            return RegularExpressions.HTTP_BASE_PROTOCOL_DOTS
                    .concat(link);
        }

        if (link.startsWith("/")) {
            return parentLink.getProtocolValue()
                    .concat(parentLink.getBaseDomain())
                    .concat(link);
        }

        return RegularExpressions.HTTP_BASE_PROTOCOL
                .concat(RegularExpressions.HTTP_PROTOCOL_DELIMITER)
                .concat(link);
    }
}
