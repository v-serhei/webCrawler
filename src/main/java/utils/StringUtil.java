package utils;

import java.io.File;
import java.util.List;

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

    private StringUtil () {
    }

    public static String getBaseDomain (String url) {
        //TODO release this

        return null;
    }

    public static String getFolderNameFromUrl (String url) {
        return ROOT_DOWNLOADS_FOLDER.concat(getBaseDomain(url));
    }

    public static List<String> getLinkFromLine(String readLine) {
        //TODO release this

        return null;
    }
}
