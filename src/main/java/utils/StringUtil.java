package utils;

import java.io.File;
import java.net.URL;

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

    public static String getLinkValue(URL link) {
        String value;

        //TODO del this
        value = "";

        return value;
    }

    public static String getBaseDomain (String url) {
        String domain;

        //TODO del this
        domain = "";

        return domain;
    }

    public static String getFolderNameFromUrl (String url) {
        return ROOT_DOWNLOADS_FOLDER.concat(getBaseDomain(url));
    }

}
