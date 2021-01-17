package entity;

import entities.Distributors;

public interface ProducerObservable {
    /**
     * Notify the distributors about an update of producers
     */
    void notifyDistributors(Distributors distributors);
}
