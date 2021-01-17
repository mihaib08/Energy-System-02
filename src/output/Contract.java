package output;

public final class Contract {
    private final int consumerId;
    private final long price;
    private final int remainedContractMonths;

    public Contract(final int id, final long finalPrice, final int remainedMonths) {
        consumerId = id;
        price = finalPrice;
        remainedContractMonths = remainedMonths;
    }

    @Override
    public String toString() {
        return "Contract{"
                + "consumerId=" + consumerId
                + ", price=" + price
                + ", remainedContractMonths=" + remainedContractMonths
                + '}';
    }

    /** Getters + Setters */

    public int getConsumerId() {
        return consumerId;
    }

    public long getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
