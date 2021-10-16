package input;

import java.util.List;

/**
 * Singleton Class
 *     --> parses the given input database
 */
public final class InputParser {
    private static final InputParser INPUT_PARSER = new InputParser();

    private int numberOfTurns;
    private InitialData initialData;
    private List<Update> monthlyUpdates;

    /** Constructor - Private */

    private InputParser() { }

    public static InputParser getInstance() {
        return INPUT_PARSER;
    }

    @Override
    public String toString() {
        return "InputParser{"
                + "numberOfTurns=" + numberOfTurns
                + ", initialData=" + initialData
                + ", monthlyUpdates=" + monthlyUpdates
                + '}';
    }

    /** Getters + Setters */

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public List<Update> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}

