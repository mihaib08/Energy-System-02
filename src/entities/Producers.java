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
     * Update the monthly changes of the producers
     *    -- notify the Observers = Distributors
     */
    public void updateChanges(final int id, final int energy, final Distributors distributors) {
        Producer p = producers.get(id);
        p.setEnergyPerDistributor(energy);
        distributors.updateProducers(p);
    }

    void initialiseEntries() {
        producers = new HashMap<>();
        greenProducers = new ArrayList<>();
        priceProducers = new ArrayList<>();
        quantityProducers = new ArrayList<>();
    }

    /**
     * initialise the monthly distributors lists for each producer
     */
    public void initialiseDistributorLists(int noMonths) {
        for (int id : producers.keySet()) {
            Producer p = producers.get(id);
            HashMap<Integer, List<Integer>> monthlyDist = p.getDistributorIds();
            for (int i = 0; i <= noMonths; ++i) {
                List<Integer> dIds = new ArrayList<>();
                monthlyDist.put(i, dIds);
            }
            p.setDistributorIds(monthlyDist);
        }
    }

    /**
     * Eliminate a given distributor from the producers' lists
     */
    public void eliminateDistributor(int id, int currMonth) {
        for (int pId : producers.keySet()) {
            Producer p = producers.get(pId);
            HashMap<Integer, List<Integer>> dIds = p.getDistributorIds();
            List<Integer> ids = dIds.get(currMonth);

            ids.remove((Integer) id);
            dIds.put(currMonth, ids);
            p.setDistributorIds(dIds);

            int nrDist = p.getCurrNoDistributors();
            p.setCurrDistributors(nrDist - 1);
        }
    }

    /**
     * Make a copy of the previous month's distributor list
     * to the current month
     */
    public void copyDistributors(int currMonth) {
        for (int id : producers.keySet()) {
            Producer p = producers.get(id);
            HashMap<Integer, List<Integer>> dIds = p.getDistributorIds();

            List<Integer> prevIds = dIds.get(currMonth - 1);
            List<Integer> currIds = dIds.get(currMonth);
            currIds.addAll(prevIds);

            dIds.put(currMonth, currIds);
            p.setDistributorIds(dIds);
        }
    }

    private List<MonthlyStatus> getMonthlyStats(Producer p, int noMonths) {
        List<MonthlyStatus> stats = new ArrayList<>();

        HashMap<Integer, List<Integer>> dIds = p.getDistributorIds();
        for (int i = 1; i <= noMonths; ++i) {
            List<Integer> currIds = dIds.get(i);
            Collections.sort(currIds);
            MonthlyStatus monthlyStatus = new MonthlyStatus(i, currIds);
            stats.add(monthlyStatus);
        }
        return stats;
    }

    /**
     * Get the list of each producer's characteristics to be printed
     */
    public List<OutProducer> getOutProducers(int noMonths) {
        List<OutProducer> outProducers = new ArrayList<>();

        for (int id : producers.keySet()) {
            Producer p = producers.get(id);
            List<MonthlyStatus> stats = getMonthlyStats(p, noMonths);

            OutProducer outProducer = new OutProducer(id, p.getMaxDistributors(),
                    p.getPriceKW(),p.getEnergyType(), p.getEnergyPerDistributor(), stats);
            outProducers.add(outProducer);
        }
        return outProducers;
    }

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