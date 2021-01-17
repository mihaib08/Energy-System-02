package output;

public final class OutConsumer {
    private final int id;
    private final boolean isBankrupt;
    private final long budget;

    /** Constructor(s) */

    public OutConsumer(final int outId, final boolean bankrupt,
                       final long finalBudget) {
        id = outId;
        isBankrupt = bankrupt;
        budget = finalBudget;
    }

    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", isBankrupt=" + isBankrupt
                + ", budget=" + budget
                + '}';
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public long getBudget() {
        return budget;
    }
}
