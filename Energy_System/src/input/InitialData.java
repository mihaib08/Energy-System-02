package input;

import java.util.List;

public final class InitialData {
    private List<InConsumer> consumers;
    private List<InDistributor> distributors;
    private List<InProducer> producers;

    @Override
    public String toString() {
        return "InitialData{"
                + "consumers=" + consumers
                + ", distributors=" + distributors
                + '}';
    }

    /** Getters + Setters */

    public List<InConsumer> getConsumers() {
        return consumers;
    }

    public List<InDistributor> getDistributors() {
        return distributors;
    }

    public List<InProducer> getProducers() {
        return producers;
    }
}
