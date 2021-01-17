package output;

import java.util.List;

public final class OutProducer {
    private final int id;
    private final int maxDistributors;
    private final double priceKW;
    private final String energyType;
    private final int energyPerDistributor;
    private final List<MonthlyStatus> monthlyStats;

    public OutProducer(final int pId, final int noDistributors, final double price,
                       final String energy, final int distributorEnergy,
                       final List<MonthlyStatus> stats) {
        id = pId;
        maxDistributors = noDistributors;
        priceKW = price;
        energyType = energy;
        energyPerDistributor = distributorEnergy;
        monthlyStats = stats;
    }

    @Override
    public String toString() {
        return "OutProducer{"
                + "id=" + id
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyType='" + energyType + '\''
                + ", energyPerDistributor=" + energyPerDistributor
                + ", monthlyStats=" + monthlyStats
                + '}';
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<MonthlyStatus> getMonthlyStats() {
        return monthlyStats;
    }
}
