package entity;

import input.InProducer;

import java.util.HashMap;
import java.util.List;

public final class Producer extends Entity {
    private String energyType;

    private int maxDistributors;

    private double priceKW;

    private int energyPerDistributor;

    /**
     * Hashmap month (key) - distributorIds
     *    > keep track of the distributors
     *      that are being provided by the current producer
     */
    private HashMap<Integer, List<Integer>> distributorIds;

    /** the current no. of subscribed distributors */
    private int currNoDistributors;


    public Producer(final InProducer inProducer) {
        super(inProducer.getId());
        energyType = inProducer.getEnergyType();
        maxDistributors = inProducer.getMaxDistributors();
        priceKW = inProducer.getPriceKW();
        energyPerDistributor = inProducer.getEnergyPerDistributor();
        distributorIds = new HashMap<>();
        currNoDistributors = 0;
    }

    @Override
    public String toString() {
        return "Producer{"
                + "id= " + super.getId()
                + ", energyType='" + energyType + '\''
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }

    /** Getters + Setters */

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getCurrNoDistributors() {
        return currNoDistributors;
    }

    public void setCurrDistributors(int currDistributors) {
        this.currNoDistributors = currDistributors;
    }

    public HashMap<Integer, List<Integer>> getDistributorIds() {
        return distributorIds;
    }

    public void setDistributorIds(HashMap<Integer, List<Integer>> distributorIds) {
        this.distributorIds = distributorIds;
    }
}