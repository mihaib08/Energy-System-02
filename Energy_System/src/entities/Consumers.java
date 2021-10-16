package entities;

import entity.Consumer;
import entity.Distributor;
import output.Contract;
import output.OutConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Consumers Database
 * Manages the operations of all consumers
 */
public final class Consumers {
    /**
     * Singleton instance
     */
    private static final Consumers CONSUMERS = new Consumers();

    /**
     * HashMap id (key) - consumer (value)
     */
    private HashMap<Integer, Consumer> consumers;

    /** Add a consumer to the simulation */
    public void addConsumer(final Consumer consumer) {
        consumers.put(consumer.getId(), consumer);
    }

    /** Constructor - Private */
    private Consumers() { }

    public static Consumers getInstance() {
        return CONSUMERS;
    }

    /**
     * Subscribe consumer - c -
     *     to distributor - d -
     *
     * @param distributors - distributors' database
     */
    private void subscribeConsumer(final Consumer c, final Distributor d,
                                   final Distributors distributors) {
        /* get the necessary payment */
        long price = distributors.getContractPrice(d.getId());
        c.setPayment(price);

        c.setCurrDistributorId(d.getId());
        c.setContractLength(d.getContractLength());
        /* increase the no. consumers for the current distributor */
        distributors.increaseNoConsumers(d.getId());
    }

    /**
     * Pick a distributor for each consumer
     *
     * @param distributors - distributors' database
     */
    void pickDistributor(final Distributors distributors) {
        for (int id : consumers.keySet()) {
            Consumer c = consumers.get(id);
            /*
             * check if the consumer
             * can pick another distributor
             */
            if (c.getBankState() != 2 && c.getContractLength() == 0) {
                Distributor d = distributors.getCheapestDistributor();

                /* decrease the no. consumers for the previous distributor */
                if (c.getCurrDistributorId() != -1) {
                    distributors.decreaseNoConsumers(c.getCurrDistributorId());
                }

                subscribeConsumer(c, d, distributors);
            } else if (c.getBankState() != 2) {
                /* check if the current distributor is bankrupt */
                int dId = c.getCurrDistributorId();
                if (distributors.checkBankruptcy(dId)) {
                    /* can choose another distributor */
                    Distributor d = distributors.getCheapestDistributor();

                    subscribeConsumer(c, d, distributors);

                    /* erase any possible debt */
                    c.setBankState(0);
                }
            }
        }
    }

    /**
     * Simulate the current month's payment for each consumer
     *
     * @param distributors - distributors' database
     */
    void payMonthlyInstallment(final Distributors distributors) {
        for (int id : consumers.keySet()) {
            Consumer c = consumers.get(id);

            /* check for the consumer to not be bankrupt */
            if (c.getBankState() != 2) {
                c.payInstallment(distributors);

                /* add the revenue to each correspondent distributor */
                if (c.getBankState() == 0) {
                    distributors.addRevenue(c.getCurrDistributorId(), c.getPayment());
                }
            }
        }
    }

    /**
     * Eliminate the bankrupt consumers from their previous distributors
     *     --> to be done for each new month
     */
    public void eliminateBankruptConsumers(final Distributors distributors) {
        for (int id : consumers.keySet()) {
            Consumer c = consumers.get(id);

            /* check if the user is - newly - bankrupt */
            if (c.getBankState() == 2 && c.getCurrDistributorId() != -1) {
                int dId = c.getCurrDistributorId();
                distributors.decreaseNoConsumers(dId);
                c.setCurrDistributorId(-1);
            }
        }
    }

    /**
     * Get the list of consumer's specifications to be printed
     */
    public List<OutConsumer> getOutConsumers(final Distributors distributors) {
        List<OutConsumer> outConsumers = new ArrayList<>();
        for (int id : consumers.keySet()) {
            Consumer c = consumers.get(id);

            boolean isBankrupt = false;
            if (c.getBankState() == 2) {
                isBankrupt = true;
            } else {
                /* check for the distributor to not be bankrupt */
                if (!distributors.checkBankruptcy(c.getCurrDistributorId())) {
                    /* generate the current consumer's contract */
                    Contract contract = new Contract(c.getId(), c.getPayment(),
                            c.getContractLength());
                    /* add the contract to the correspondent distributor's list */
                    distributors.addContract(c.getCurrDistributorId(), contract);
                }
            }

            OutConsumer outConsumer = new OutConsumer(c.getId(), isBankrupt, c.getBudget());
            outConsumers.add(outConsumer);
        }
        return outConsumers;
    }

    /**
     * (Re)initialise the fields
     */
    void initialiseEntries() {
        consumers = new HashMap<>();
    }
}
