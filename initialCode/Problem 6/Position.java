public class Position {
    private final Instrument instrument;
    private int quantity;
    private double averageCostBasis;

    public Position(Instrument instrument, int quantity, double averageCostBasis) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.averageCostBasis = averageCostBasis;
    }

    public double marketValue() {
        return quantity * instrument.getCurrentPriceValue();
    }

    public double unrealizedPnL() {
        return marketValue() - quantity * averageCostBasis;
    }

    public void addQuantity(int qty, double costBasis) {
        double totalCost = this.quantity * this.averageCostBasis + qty * costBasis;
        this.quantity += qty;
        this.averageCostBasis = totalCost / this.quantity;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAverageCostBasis() {
        return averageCostBasis;
    }

    @Override
    public String toString() {
        return "Position[instrument=" + instrument.getSymbol()
                + ", quantity=" + quantity
                + ", averageCostBasis=" + averageCostBasis + "]";
    }
}