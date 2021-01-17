package input;

public final class InConsumer {
    private int id;
    private int initialBudget;
    private int monthlyIncome;

    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", initialBudget=" + initialBudget
                + ", monthlyIncome=" + monthlyIncome
                + '}';
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }
}
