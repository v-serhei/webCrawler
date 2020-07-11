package beans.urlDownloader;

import beans.link.Link;
import utils.StringUtil;

import java.io.*;
import java.net.URL;

public class SimpleURLDownloader implements URLDownloader {
    private URL url;

    @Override
    public File downloadHTML(Link urlAddress) {
        //Sleep thread for delay between downloads
        delay(urlAddress);
        File downloadFile = createFileForDownload(urlAddress);
        if (downloadFile.exists()) {
            try (BufferedReader reader = new BufferedReader
                    (new InputStreamReader(url.openConnection().getInputStream()));
                 BufferedWriter writer = new BufferedWriter
                         (new FileWriter(downloadFile))) {
                writer.write(urlAddress.getLinkValue());
                while (reader.ready()) {
                    writer.write(reader.readLine());
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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
