package strategies;

import entities.Producers;
import entity.Producer;

import java.util.ArrayList;
import java.util.List;

public final class GreenEnergyStrategy implements EnergyStrategy {
    @Override
    public List<Producer> getEnergyProducers(int energyNeeded, Producers producers) {
        List<Producer> resProducers = new ArrayList<>();

        List<Producer> greenProducers = producers.getGreenProducers();
        List<Producer> priceProducers = producers.getPriceProducers();

        int currEnergy = 0;

        for (Producer p : greenProducers) {
            int maxDistributors = p.getMaxDistributors();
            int currDistributors = p.getCurrNoDistributors();

            if (currDistributors < maxDistributors) {
                resProducers.add(p);

                currDistributors++;
                p.setCurrDistributors(currDistributors);

                currEnergy += p.getEnergyPerDistributor();
                if (currEnergy >= energyNeeded) {
                    return resProducers;
                }
            }
        }

        /*
         * it gets here --> greenProducers are not enough
         *   -- use priceProducers
         */
        for (Producer p : priceProducers) {
            if (!resProducers.contains(p)) {
                int maxDistributors = p.getMaxDistributors();
                int currDistributors = p.getCurrNoDistributors();

                if (currDistributors < maxDistributors) {
                    resProducers.add(p);

                    currDistributors++;
                    p.setCurrDistributors(currDistributors);

                    currEnergy += p.getEnergyPerDistributor();
                    if (currEnergy >= energyNeeded) {
                        return resProducers;
                    }
                }
            }
        }

        return null;
    }
}
