public interface Tradeable {
    String getSymbol();

    double getCurrentPriceValue();

    boolean isAvailableForTrading();

    default String getTradingInfo() {
        return "Tradeable: " + getSymbol()
                + " at $" + getCurrentPriceValue()
                + " (" + (isAvailableForTrading() ? "Available" : "Unavailable") + ")";
    }
}