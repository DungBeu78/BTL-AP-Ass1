import java.time.LocalDateTime;

public abstract class Instrument implements Tradeable, Priceable {
    private final String symbol;
    private String name;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    public Instrument(String symbol, String name, double currentPrice) {
        if (currentPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    public abstract double riskScore();

    public abstract String assetClass();

    public abstract void accept(InstrumentVisitor visitor);

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.currentPrice = newPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getCurrentPriceValue() {
        return currentPrice;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public boolean isAvailableForTrading() {
        return true;
    }

    @Override
    public double getPriceChange(double previousPrice) {
        return currentPrice - previousPrice;
    }

    @Override
    public double getPriceChangePercent(double previousPrice) {
        return ((currentPrice - previousPrice) / previousPrice) * 100.0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[symbol=" + symbol
                + ", price=" + currentPrice
                + ", risk=" + riskScore() + "]";
    }
}