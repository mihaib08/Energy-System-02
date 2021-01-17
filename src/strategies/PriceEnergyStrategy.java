package strategies;

import entities.Producers;
import entity.Producer;

import java.util.ArrayList;
import java.util.List;

public class PriceEnergyStrategy implements EnergyStrategy {
    @Override
    public List<Producer> getEnergyProviders(int neededEnergy) {
        List<Producer> distProducers = new ArrayList<>();
        Producers producers = Producers.getInstance();
        List<Producer> priceProducers = producers.getPriceProducers();
        int aux = 0;

        for (Producer p : priceProducers) {
            int maxDistributors = p.getMaxDistributors();
            int currDistributors = p.getCurrDistributors();
            if (currDistributors < maxDistributors) {
                aux += p.getEnergyPerDistributor();
                distProducers.add(p);

                p.setCurrDistributors(currDistributors + 1);
            }
            /*
             * the required amount of energy was obtained
             *    > return the list of producers
             */
            if (aux >= neededEnergy) {
                return distProducers;
            }
        }
        return null;
    }
}