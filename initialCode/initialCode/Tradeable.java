public interface Tradeable {
    String getSymbol();

    double getCurrentPriceValue();

    boolean isAvailableForTrading();

    /**
     * 
     * Example: "Tradeable: AAPL at $150.0 (Available)".
     *
     */
    default String getTradingInfo() {
        return "Tradeable: " + getSymbol()
                + " at $" + getCurrentPriceValue()
                + " (" + (isAvailableForTrading() ? "Available" : "Unavailable") + ")";
    }
}
