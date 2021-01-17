package input;

public final class DistributorChange {
    private int id;
    private int infrastructureCost;

    @Override
    public String toString() {
        return "DistributorChange{"
                + "id=" + id
                + ", infrastructureCost=" + infrastructureCost
                + '}';
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }
}
