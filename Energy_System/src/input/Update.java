package input;

import java.util.List;

/**
 * Keep track of each monthly update data
 */
public final class Update {
    private List<InConsumer> newConsumers;
    private List<DistributorChange> distributorChanges;
    private List<ProducerChange> producerChanges;

    @Override
    public String toString() {
        return "Update{"
                + "newConsumers=" + newConsumers
                + ", distributorChanges=" + distributorChanges
                + ", producerChanges=" + producerChanges
                + '}';
    }

    /** Getters + Setters */

    public List<InConsumer> getNewConsumers() {
        return newConsumers;
    }

    public List<DistributorChange> getDistributorChanges() {
        return distributorChanges;
    }

    public List<ProducerChange> getProducerChanges() {
        return producerChanges;
    }
}
