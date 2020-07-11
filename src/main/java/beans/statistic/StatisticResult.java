package beans.statistic;

import java.util.Map;

public class StatisticResult {
    private String link;
    private Map<String, Integer> statistic;
    private int totalHits;

    public StatisticResult() {
    }

    public StatisticResult(String link, Map<String, Integer> statistic, int totalHits) {
        this.link = link;
        this.statistic = statistic;
        this.totalHits = totalHits;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, Integer> getStatisticMap() {
        return statistic;
    }

    public void setStatisticMap(Map<String, Integer> statistic) {
        this.statistic = statistic;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
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
