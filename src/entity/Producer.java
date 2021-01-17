package entity;

import entities.Distributors;
import input.InProducer;
import output.MonthlyStatus;

import java.util.ArrayList;
import java.util.List;

public final class Producer extends Entity implements ProducerObservable {
    private String energyType;

    private int maxDistributors;

    private double priceKW;

    private int energyPerDistributor;

    /** the current no. of subscribed distributors */
    private int currNoDistributors;

    /**
     * List of monthly stats - distributorsIds
     */
    private List<MonthlyStatus> distributorIds;

    public Producer(final InProducer inProducer) {
        super(inProducer.getId());
        energyType = inProducer.getEnergyType();
        maxDistributors = inProducer.getMaxDistributors();
        priceKW = inProducer.getPriceKW();
        energyPerDistributor = inProducer.getEnergyPerDistributor();
        distributorIds = new ArrayList<>();
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

    @Override
    public void notifyDistributors(Distributors distributors) {
        distributors.update(this);
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

    public List<MonthlyStatus> getDistributorIds() {
        return distributorIds;
    }

    public void setDistributorIds(List<MonthlyStatus> distributorIds) {
        this.distributorIds = distributorIds;
    }
}
