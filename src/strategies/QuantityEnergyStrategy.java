package strategies;

import entities.Producers;
import entity.Producer;

import java.util.ArrayList;
import java.util.List;

public final class QuantityEnergyStrategy implements EnergyStrategy {
    @Override
    public List<Producer> getEnergyProducers(int energyNeeded, Producers producers) {
        List<Producer> resProducers = new ArrayList<>();

        List<Producer> quantityProducers = producers.getQuantityProducers();

        int currEnergy = 0;

        for (Producer p : quantityProducers) {
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

        return null;
    }
}
