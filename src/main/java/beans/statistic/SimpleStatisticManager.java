package beans.statistic;

import java.util.List;

public class SimpleStatisticManager implements StatisticManager {

    private List <String> searchWords;


    public SimpleStatisticManager(List<String> searchWords) {
        this.searchWords = searchWords;

    }

    @Override
    public void getStatistic() {

    }
}
