package beans.urlDownloader;

import beans.link.Link;
import utils.StringUtil;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleURLDownloader implements URLDownloader {
    private URL url;

    @Override
    public File downloadHTML(Link urlAddress) {
        //Sleep thread for delay between downloads
        delay(urlAddress);

        try {
            url = new URL(urlAddress.getLinkValue());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        File downloadFile = createFileForDownload(urlAddress.getLinkValue());
        if (downloadFile.exists()) {
            try (BufferedReader reader = new BufferedReader
                    (new InputStreamReader(url.openConnection().getInputStream()));
                 BufferedWriter writer = new BufferedWriter
                         (new FileWriter(downloadFile))) {

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

    private File createFileForDownload(String urlAddress) {
        String folderPath = StringUtil.getFolderNameFromUrl(urlAddress);
        String filePath = folderPath.concat(File.separator).concat(urlAddress);

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

    private void delay(Link link) {
        File folder = new File (link.getLinkValue());
        if (folder.exists()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
