package strategies;

import entities.Producers;
import entity.Producer;

import java.util.List;

/**
 * Define the operations that can be done
 * having a general strategy
 */
public interface EnergyStrategy {
    /**
     * get a list of available producers depending on the strategy
     */
    List<Producer> getEnergyProducers(int energyNeeded, Producers producers);
}
