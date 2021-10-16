package output;

import java.util.List;

/**
 * Singleton Class
 *     --> parses the expected output
 */
public final class OutputParser {
    private static final OutputParser OUTPUT_PARSER = new OutputParser();

    private List<OutConsumer> consumers;
    private List<OutDistributor> distributors;
    private List<OutProducer> energyProducers;

    /** Constructor - Private */

    private OutputParser() { }

    /**
     * Get the necessary fields to be printed
     */
    public static OutputParser getInstance(final List<OutConsumer> outConsumers,
                                           final List<OutDistributor> outDistributors,
                                           final List<OutProducer> outProducers) {
        OUTPUT_PARSER.consumers = outConsumers;
        OUTPUT_PARSER.distributors = outDistributors;
        OUTPUT_PARSER.energyProducers = outProducers;

        return OUTPUT_PARSER;
    }

    @Override
    public String toString() {
        return "OutputParser{"
                + "consumers=" + consumers
                + ", distributors=" + distributors
                + ", energyProducers=" + energyProducers
                + '}';
    }

    /** Getters + Setters */

    public List<OutConsumer> getConsumers() {
        return consumers;
    }

    private void setConsumers(final List<OutConsumer> consumers) {
        this.consumers = consumers;
    }

    public List<OutDistributor> getDistributors() {
        return distributors;
    }

    private void setDistributors(final List<OutDistributor> distributors) {
        this.distributors = distributors;
    }

    public List<OutProducer> getEnergyProducers() {
        return energyProducers;
    }

    private void setEnergyProducers(List<OutProducer> energyProducers) {
        this.energyProducers = energyProducers;
    }
}
