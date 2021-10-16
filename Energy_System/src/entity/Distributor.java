package entity;

import input.InDistributor;
import strategies.EnergyStrategy;

public final class Distributor extends Entity {
    /** current budget of the distributor */
    private long budget;

    private int contractLength;

    /** current infrastructure cost */
    private int infrastructureCost;

    /** current production cost */
    private long productionCost;

    private int energyNeededKW;

    private String producerStrategy;

    private EnergyStrategy strategy;

    private boolean bankrupt;

    /**
     * Check if one of the distributor's producers is updated
     */
    private boolean changedProducer;

    public Distributor(final InDistributor inDistributor) {
        super(inDistributor.getId());
        contractLength = inDistributor.getContractLength();
        budget = inDistributor.getInitialBudget();
        infrastructureCost = inDistributor.getInitialInfrastructureCost();
        productionCost = 0;
        bankrupt = false;
        energyNeededKW = inDistributor.getEnergyNeededKW();
        producerStrategy = inDistributor.getProducerStrategy();
        changedProducer = true;
    }

    @Override
    public String toString() {
        return "Distributor{"
                + "ID = " + super.getId()
                + ", budget=" + budget
                + ", contractLength=" + contractLength
                + ", infrastructureCost=" + infrastructureCost
                + ", productionCost=" + productionCost
                + ", bankrupt=" + bankrupt
                + '}';
    }

    /** Getters + Setters */

    public long getBudget() {
        return budget;
    }

    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public long getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public EnergyStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(EnergyStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isChangedProducer() {
        return changedProducer;
    }

    public void setChangedProducer(boolean changedProducer) {
        this.changedProducer = changedProducer;
    }
}
