public interface Tradeable {
    String getSymbol();

    double getCurrentPriceValue();

    boolean isAvailableForTrading();

    // <symbol> @ <price with 2 decimal places> [AVAILABLE/UNAVAILABLE]
    default String getTradingInfo() {
        return getSymbol()
                + " @ "
                + String.format("%.2f", getCurrentPriceValue())
                + " ["
                + (isAvailableForTrading() ? "AVAILABLE" : "UNAVAILABLE")
                + "]";
    }
}