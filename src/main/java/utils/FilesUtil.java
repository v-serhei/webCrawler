package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesUtil {

    public static List<String> getDownloadedFilesList () {
        List <String> list = new ArrayList<>(1024);
        File rf = new File (StringUtil.DOWNLOADS_FOLDER);
        File [] folders = rf.listFiles();
        for (File folder : folders) {
            File [] files = folder.listFiles();
            for (File file : files) {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

}
