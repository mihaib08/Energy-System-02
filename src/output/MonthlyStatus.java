package output;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyStatus {
    private final int month;
    private List<Integer> distributorsIds;

    public MonthlyStatus(MonthlyStatus status) {
        month = status.getMonth() + 1;

        distributorsIds = new ArrayList<>();
        distributorsIds.addAll(status.getDistributorsIds());
    }

    public MonthlyStatus(final int currMonth, List<Integer> ids) {
        month = currMonth;
        distributorsIds = ids;
    }

    @Override
    public String toString() {
        return "MonthlyStatus{"
                + "month=" + month
                + ", distributorsIds=" + distributorsIds
                + '}';
    }

    /** Getters + Setters */

    public int getMonth() {
        return month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
