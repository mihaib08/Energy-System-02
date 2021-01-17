package entity;

import common.Constants;
import input.InConsumer;
import input.InDistributor;
import input.InProducer;

/**
 * Factory class for creating a particular Entity object
 *     ++ Singleton
 */
public final class EntityFactory {
    private static final EntityFactory FACTORY = new EntityFactory();

    /** Constructor - Private */
    private EntityFactory() { }

    public static EntityFactory getInstance() {
        return FACTORY;
    }

    /**
     * Generate the object corresponding to the given arguments
     */
    public Entity createEntity(final String entityType,
                               final Object inputData) {
        switch (entityType) {
            /* Downcast inputData depending on the class type */
            case Constants.CONSUMER -> {
                InConsumer inputConsumer = (InConsumer) inputData;
                return new Consumer(inputConsumer);
            }
            case Constants.DISTRIBUTOR -> {
                InDistributor inputDistributor = (InDistributor) inputData;
                return new Distributor(inputDistributor);
            }
            case Constants.PRODUCER -> {
                InProducer inputProducer = (InProducer) inputData;
                return new Producer(inputProducer);
            }
            default -> {
            }
        }
        return null;
    }
}
