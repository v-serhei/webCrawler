package beans.urlDownloader;

import beans.link.Link;
import utils.StringUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleURLDownloader implements URLDownloader {
    private URL url;

    @Override
    public File downloadHTML(Link link) {
        //Sleep thread for delay between downloads
        delay(link);
        try {
            url = new URL(link.getLinkValue());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File downloadFile = createFileForDownload(link);
        if (downloadFile.exists()) {
            try (BufferedReader reader = new BufferedReader
                    (new InputStreamReader(url.openConnection().getInputStream()));
                 BufferedWriter writer = new BufferedWriter
                         (new FileWriter(downloadFile))) {
                writer.write(link.getLinkValue());
                if (reader !=null) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.write("\n");
                    }
                } else {
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return downloadFile;
    }

    private File createFileForDownload(Link urlAddress) {
        String folderPath = StringUtil.getFolderNameFromUrl(urlAddress);
        String filePath = folderPath.concat(File.separator)
                .concat(StringUtil.getUniqueFileName());

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    //если папка есть, значит закачки с сайта уже были и надо сделать небольшую паузу
    private void delay(Link link) {

        File folder = new File(StringUtil.getFolderNameFromUrl(link));
        if (folder.exists()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }
}
