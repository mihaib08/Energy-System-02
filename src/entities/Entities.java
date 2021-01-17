package entities;

import common.Constants;
import entity.Consumer;
import entity.Distributor;
import entity.Entity;
import entity.EntityFactory;
import entity.Producer;
import input.DistributorChange;
import input.InConsumer;
import input.InDistributor;
import input.InProducer;
import input.InitialData;
import input.ProducerChange;
import input.Update;

/**
 * Manages the entities' operations
 */
public final class Entities {
    /**
     * Singleton instance
     */
    private static final Entities ENTITIES = new Entities();

    /** Constructor - Private */
    private Entities() { }

    public static Entities getInstance() {
        return ENTITIES;
    }

    private void initialiseEntries() {
        Consumers consumers = Consumers.getInstance();
        Distributors distributors = Distributors.getInstance();
        Producers producers = Producers.getInstance();

        consumers.initialiseEntries();
        distributors.initialiseEntries();
        producers.initialiseEntries();
    }

    /**
     * Initialise the entities' fields
     */
    public void initialiseEntries(final InitialData data,
                                  final EntityFactory entity) {
        initialiseEntries();

        /*
         * Get the initial data for the Consumer and Distributor objects
         */
        Distributors distributors = Distributors.getInstance();
        Consumers consumers = Consumers.getInstance();
        Producers producers = Producers.getInstance();

        for (InConsumer inConsumer : data.getConsumers()) {
            Entity c = entity.createEntity(Constants.CONSUMER, inConsumer);
            consumers.addConsumer((Consumer) c);
        }
        for (InDistributor inDistributor : data.getDistributors()) {
            Entity d = entity.createEntity(Constants.DISTRIBUTOR, inDistributor);
            distributors.addDistributor((Distributor) d);
        }
        for (InProducer inProducer : data.getProducers()) {
            Entity p = entity.createEntity(Constants.PRODUCER, inProducer);
            producers.addProducer((Producer) p);
        }
        producers.updateStrategyLists();
    }

    /**
     * Parse the monthly updates for each entity
     */
    public void getUpdates(final Update update,
                           final EntityFactory entity,
                           final Distributors distributors,
                           final Consumers consumers,
                           final Producers producers) {
        for (InConsumer inConsumer : update.getNewConsumers()) {
            /* add the new consumers to the simulation */
            Consumer c = (Consumer) entity.createEntity(Constants.CONSUMER, inConsumer);
            consumers.addConsumer(c);
        }

        for (DistributorChange dChange : update.getDistributorChanges()) {
            distributors.updateCosts(dChange.getId(), dChange.getInfrastructureCost());
        }

        for (ProducerChange pChange : update.getProducerChanges()) {
            producers.updateChanges(pChange.getId(), pChange.getEnergyPerDistributor(), distributors);
        }
    }

    /**
     * Simulate the current month's transactions flow
     */
    public void simulateMonth(final Consumers consumers,
                              final Distributors distributors) {
        distributors.updatePrices();
        consumers.pickDistributor(distributors);
        consumers.payMonthlyInstallment(distributors);
        distributors.processPayments();
    }
}