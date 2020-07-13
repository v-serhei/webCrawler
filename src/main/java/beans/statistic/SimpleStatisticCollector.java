package beans.statistic;

import utils.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimpleStatisticCollector implements StatisticCollector {

    private String filePath;
    private StatisticManager statisticManager;
    List<String> searchWords;

    public SimpleStatisticCollector(String path, List<String> words, StatisticManager sManager) {
        super();
        filePath = path;
        statisticManager = sManager;
        searchWords = words;
    }

    @Override
    public StatisticResult getStatisticFromFile() {
        StatisticResult statRes = new StatisticResult();
        for (String word : searchWords) {
            statRes.getStatisticMap().put(word, 0);
        }
        StringBuilder sb = new StringBuilder();
        StatisticResult sr = new StatisticResult();
        File f = new File(filePath);
        if (f.exists()) {
            //System.out.println("Читаю файл: "+f.getAbsolutePath());
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                //first line = link
                statRes.setLink(reader.readLine());
                while (reader.ready()) {
                    sb.append(reader.readLine());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for (String word : searchWords) {
            int wordCount = StringUtil.getWordCountFromLine(sb.toString(), word);
            statRes.getStatisticMap().compute(word, (key, value) -> value += wordCount);
        }
        int total = 0;
        for (Map.Entry<String, Integer> entry : statRes.getStatisticMap().entrySet()) {
            total += entry.getValue();
        }
        statRes.setTotalHits(total);
        return statRes;
    }

    @Override
    public void run() {
        statisticManager.addStatisticResult(getStatisticFromFile(), filePath);
    }
}
