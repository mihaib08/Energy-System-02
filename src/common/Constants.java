package common;

/**
 * Useful common constants
 */
public final class Constants {
    private Constants() { }

    public static final String CONSUMER = "consumer";
    public static final String DISTRIBUTOR = "distributor";
    public static final String PRODUCER = "producer";

    /* EnergyType */
    public static final String WIND = "WIND";
    public static final String SOLAR = "SOLAR";
    public static final String HYDRO = "HYDRO";
    public static final String COAL = "COAL";
    public static final String NUCLEAR = "NUCLEAR";

    /* EnergyStrategy */
    public static final String GREEN = "GREEN";
    public static final String PRICE = "PRICE";
    public static final String QUANTITY = "QUANTITY";

    public static final double PRODUCTION_PERCENT = 0.2;
}
