package strategies;

import common.Constants;

public final class EnergyStrategyFactory {
    private static final EnergyStrategyFactory STRATEGY = new EnergyStrategyFactory();

    private EnergyStrategyFactory() { }

    public static EnergyStrategyFactory getInstance() {
        return STRATEGY;
    }

    /**
     * Create the correspondent strategy of a distributor
     */
    public EnergyStrategy createStrategy(String energyStrategy) {
        switch (energyStrategy) {
            case Constants.GREEN -> {
                return new GreenEnergyStrategy();
            }
            case Constants.PRICE -> {
                return new PriceEnergyStrategy();
            }
            case Constants.QUANTITY -> {
                return new QuantityEnergyStrategy();
            }
            default -> {
            }
        }
        return null;
    }
}
