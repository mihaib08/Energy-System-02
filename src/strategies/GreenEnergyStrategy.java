package strategies;

import entities.Producers;
import entity.Producer;

import java.util.ArrayList;
import java.util.List;

public class GreenEnergyStrategy implements EnergyStrategy {
    @Override
    public List<Producer> getEnergyProviders(int neededEnergy) {
        List<Producer> distProducers = new ArrayList<>();
        Producers producers = Producers.getInstance();
        List<Producer> greenProducers = producers.getGreenProducers();

        int aux = 0;
        for (Producer p : greenProducers) {
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
        /*
         * the required amount isn't completed
         *    --> more energy is needed
         */
        List<Producer> priceProducers = producers.getPriceProducers();
        for (Producer p : priceProducers) {
            int maxDistributors = p.getMaxDistributors();
            int currDistributors = p.getCurrDistributors();
            if (currDistributors < maxDistributors && !distProducers.contains(p)) {
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