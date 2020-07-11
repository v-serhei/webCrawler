package beans.statistic;

import java.util.Map;

public class StatisticResult {
    private String link;
    private Map<String, Integer> statistic;
    private int totalHits;

    public StatisticResult(String link, Map<String, Integer> statistic, int totalHits) {
        this.link = link;
        this.statistic = statistic;
        this.totalHits = totalHits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(link).append("\n");
        sb.append("Numbers are: ").append("\n");
        for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
            sb
                    .append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append("\n");
        }
        sb.append("Total hits: ").append(totalHits);
        return sb.toString();
    }
}
