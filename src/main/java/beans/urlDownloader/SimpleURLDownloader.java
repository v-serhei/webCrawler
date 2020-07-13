package beans.urlDownloader;

import beans.link.Link;
import utils.StringUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleURLDownloader implements URLDownloader {
    private URL url;
    private HttpURLConnection connection;

    @Override
    public File downloadHTML(Link link) {
        System.out.println("Обрабатываем " + link.getLinkValue());
        //Sleep thread for delay between downloads
        delay(link);
        int status;
        try {
            url = new URL(link.getLinkValue());

            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestProperty("Accept", "text/html, text/*");
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            status = connection.getResponseCode();
        } catch (IOException e) {
            System.out.println("не получилось скачать страницу: "+ link.getLinkValue());
           // e.printStackTrace();
            return null;
        }

        File  downloadFile= null;
        if (status == HttpURLConnection.HTTP_OK)  {
            downloadFile= createFileForDownload(link);
            if (downloadFile.exists()) {
                try (BufferedReader reader = new BufferedReader
                        (new InputStreamReader(connection.getInputStream()));
                     BufferedWriter writer = new BufferedWriter
                             (new FileWriter(downloadFile))) {
                    writer.write(link.getLinkValue());
                    writer.write("\n");
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
                    System.out.println("не получилось скачать страницу: "+ link.getLinkValue());
                   // e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
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

    private void delay(Link link) {
        File folder = new File(StringUtil.getFolderNameFromUrl(link));
        if (folder.exists()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }
}
