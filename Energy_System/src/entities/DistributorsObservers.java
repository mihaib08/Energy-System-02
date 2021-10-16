package entities;

import entity.Producer;

public interface DistributorsObservers {
    /**
     * Update the distributors about the change of (Producer) p
     */
    void update(Producer p);
}
