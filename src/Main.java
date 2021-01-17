import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entities.Consumers;
import entities.Distributors;
import entities.Entities;
import entities.Producers;
import entity.EntityFactory;
import entity.Producer;
import input.InitialData;
import input.InputParser;
import input.Update;
import output.OutConsumer;
import output.OutDistributor;
import output.OutProducer;
import output.OutputParser;

import java.io.File;
import java.util.List;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        String inFile = args[0];
        String outFile = args[1];

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        InputParser input = mapper.readValue(new File(inFile), InputParser.class);

        int noTurns = input.getNumberOfTurns();

        InitialData data = input.getInitialData();
        EntityFactory entity = EntityFactory.getInstance();

        /* get the databases' instances */
        Consumers consumers = Consumers.getInstance();
        Distributors distributors = Distributors.getInstance();
        Producers producers = Producers.getInstance();

        Entities entities = Entities.getInstance();
        /* (re)initialise the classes' fields */
        entities.initialiseEntries(data, entity);


        producers.initialiseDistributorLists(noTurns);
        distributors.applyStrategies(0);
        distributors.updateProductionCost();
        entities.simulateMonth(consumers, distributors);

        for (int i = 1; i <= noTurns; ++i) {
            /*
             * copy the distributors' list for each producers
             * from the previous one
             */
            producers.copyDistributors(i);

            /* check if all distributors went bankrupt */
            if (distributors.checkAllBankrupt()) {
                break;
            }

            /* eliminate the bankrupt consumers */
            consumers.eliminateBankruptConsumers(distributors);

            Update u = input.getMonthlyUpdates().get(i - 1);
            entities.getUpdates(u, entity, distributors, consumers, producers);
            /* simulate the current month */
            entities.simulateMonth(consumers, distributors);

            producers.updateStrategyLists();

            distributors.applyStrategies(i);
            distributors.updateProductionCost();
        }
        /* eliminate the bankrupt consumers */
        consumers.eliminateBankruptConsumers(distributors);

        List<OutConsumer> resConsumers = consumers.getOutConsumers(distributors);
        List<OutDistributor> resDistributors = distributors.getOutDistributors();
        List<OutProducer> resProducers = producers.getOutProducers(noTurns);

        OutputParser output = OutputParser.getInstance(resConsumers, resDistributors, resProducers);
        mapper.writeValue(new File(outFile), output);
    }
}