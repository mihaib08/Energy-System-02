package entities;

import common.Constants;
import entity.Producer;
import output.MonthlyStatus;
import output.OutProducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Producers Database
 * Manages the operations of all producers
 */
public final class Producers {
    /**
     * Singleton instance
     */
    private static final Producers PRODUCERS = new Producers();

    /**
     * HashMap id (key) - producer (value)
     */
    private HashMap<Integer, Producer> producers;

    /**
     * List of producers having renewable energy
     *   -- to be (re)sorted by GREEN strategy after each update
     */
    private List<Producer> greenProducers;

    /**
     * List of producers sorted by their priceKW
     */
    private List<Producer> priceProducers;

    /**
     * List of producers sorted by energyPerDistributor
     */
    private List<Producer> quantityProducers;

    /** Add a producer to the simulation */
    public void addProducer(final Producer producer) {
        producers.put(producer.getId(), producer);

        checkGreen(producer);
        priceProducers.add(producer);
        quantityProducers.add(producer);
    }

    /**
     * (Re)initialise the fields
     */
    public void initialiseEntries() {
        producers = new HashMap<>();
        greenProducers = new ArrayList<>();
        priceProducers = new ArrayList<>();
        quantityProducers = new ArrayList<>();
    }

    /**
     * Check if the given producer
     * can be added to the greenProducers list
     */
    private void checkGreen(final Producer producer) {
        String energy = producer.getEnergyType();
        if (energy.equals(Constants.WIND) || energy.equals(Constants.HYDRO)
                || energy.equals(Constants.SOLAR)) {
            greenProducers.add(producer);
        }
    }

    /** Constructor - Private */
    private Producers() { }

    public static Producers getInstance() {
        return PRODUCERS;
    }

    /**
     * Update the strategy lists by sorting them
     * after each new monthly update
     */
    public void updateStrategyLists() {
        greenProducers = greenProducers.stream()
                .sorted(Comparator.comparing(Producer::getId))
                .sorted(Comparator.comparing(Producer::getEnergyPerDistributor).reversed())
                .sorted(Comparator.comparing(Producer::getPriceKW))
                .collect(Collectors.toList());

        priceProducers = priceProducers.stream()
                .sorted(Comparator.comparing(Producer::getId))
                .sorted(Comparator.comparing(Producer::getEnergyPerDistributor).reversed())
                .sorted(Comparator.comparing(Producer::getPriceKW))
                .collect(Collectors.toList());

        quantityProducers = quantityProducers.stream()
                .sorted(Comparator.comparing(Producer::getId))
                .sorted(Comparator.comparing(Producer::getEnergyPerDistributor).reversed())
                .collect(Collectors.toList());

    }

    /**
     * Update the current producer
     */
    public void updateChanges(int id, int newEnergy, Distributors distributors) {
        Producer p = producers.get(id);
        p.setEnergyPerDistributor(newEnergy);

        /* notify the distributors */
        p.notifyDistributors(distributors);
    }

    /**
     * Add the distributor id - id - to the current list of its producers
     */
    public void updateProducers(int id, List<Producer> resProducers) {
        for (Producer p : resProducers) {
            List<MonthlyStatus> ids = p.getDistributorIds();

            MonthlyStatus status = ids.get(ids.size() - 1);
            List<Integer> distributorsIds = status.getDistributorsIds();
            distributorsIds.add(id);
//            status.setDistributorsIds(distributorsIds);

//            int sz = ids.size() - 1;
//            ids.set(sz, status);
//            p.setDistributorIds(ids);
        }
    }

    /**
     * eliminate the distributor - id -
     * from its current list of producers
     */
    public void eliminateDistributor(int id, List<Producer> currProducers) {
        for (Producer p : currProducers) {
            int curr = p.getCurrNoDistributors();
            curr--;
            p.setCurrDistributors(curr);

            /* get the last status */
            List<MonthlyStatus> stats = p.getDistributorIds();
            MonthlyStatus status = stats.get(stats.size() - 1);

            /* remove the current distributor */
            List<Integer> dIds = status.getDistributorsIds();
            dIds.remove((Integer) id);

            status.setDistributorsIds(dIds);

            int sz = stats.size() - 1;
            stats.set(sz, status);
            p.setDistributorIds(stats);
        }
    }

    /**
     * Create the correspondent monthly distributors' lists
     */
    public void createMonthlyList(int currMonth) {
        if (currMonth == 0) {
            for (int id : producers.keySet()) {
                Producer p = producers.get(id);

                MonthlyStatus status = new MonthlyStatus(currMonth, new ArrayList<>());
                List<MonthlyStatus> dIds = p.getDistributorIds();
                dIds.add(status);
                p.setDistributorIds(dIds);
            }
        } else {
            for (int id : producers.keySet()) {
                Producer p = producers.get(id);

                List<MonthlyStatus> dIds = p.getDistributorIds();
                MonthlyStatus currStatus = dIds.get(dIds.size() - 1);

                MonthlyStatus status = new MonthlyStatus(currStatus);
                dIds.add(status);
//                p.setDistributorIds(dIds);
            }
        }
    }

    /** Get the list of producers to be printed */
    public List<OutProducer> getOutProducers(int noTurns) {
        List<OutProducer> outProducers = new ArrayList<>();

        for (int id : producers.keySet()) {
            Producer p = producers.get(id);

            List<MonthlyStatus> stats = p.getDistributorIds();
            List<MonthlyStatus> resStats = new ArrayList<>();

            for (int i = 1; i < stats.size(); ++i) {
                MonthlyStatus m = stats.get(i);
                List<Integer> dIds = m.getDistributorsIds();

                Collections.sort(dIds);
                MonthlyStatus newStatus = new MonthlyStatus(m.getMonth(), dIds);
                resStats.add(newStatus);
            }

            OutProducer newProducer = new OutProducer(id, p.getMaxDistributors(), p.getPriceKW(),
                    p.getEnergyType(), p.getEnergyPerDistributor(), resStats);

            outProducers.add(newProducer);
        }
        return outProducers;
    }

    /** Getters + Setters */

    public HashMap<Integer, Producer> getProducers() {
        return producers;
    }

    public List<Producer> getGreenProducers() {
        return greenProducers;
    }

    public List<Producer> getPriceProducers() {
        return priceProducers;
    }

    public List<Producer> getQuantityProducers() {
        return quantityProducers;
    }
}
