import java.time.LocalDateTime;

public abstract class Instrument implements Tradeable, Priceable {
    private final String symbol;
    private String name;
    private double currentPrice;
    private LocalDateTime lastUpdated;

    public Instrument(String symbol, String name, double currentPrice) {
        if (currentPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    public abstract double riskScore();

    public abstract String assetClass();

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        this.currentPrice = newPrice;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }

    public double getCurrentPriceValue() {
        return this.currentPrice;
    }

    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[symbol=" + this.symbol
                + ", price=" + this.currentPrice
                + ", risk=" + this.riskScore() + "]";
    }

    // Tradeable and Priceable method implementations
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
}
