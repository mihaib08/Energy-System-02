package output;

import java.util.List;

public final class OutDistributor {
    private final int id;
    private final int energyNeededKW;
    private final long contractCost;
    private final long budget;
    private final String producerStrategy;
    private final boolean isBankrupt;
    private final List<Contract> contracts;

    public OutDistributor(final int dId, final int energyNeeded, final long contract,
                          final long finalBudget, final String strategy,
                          final boolean bankrupt, final List<Contract> finalContracts) {
        id = dId;
        budget = finalBudget;
        isBankrupt = bankrupt;
        contracts = finalContracts;
        energyNeededKW = energyNeeded;
        contractCost = contract;
        producerStrategy = strategy;
    }

    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + id
                + ", budget=" + budget
                + ", isBankrupt=" + isBankrupt
                + ", contracts=" + contracts
                + '}';
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }

    public long getBudget() {
        return budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public long getContractCost() {
        return contractCost;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }
}
