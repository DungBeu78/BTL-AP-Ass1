public class Option extends Derivative {
    private final double strikePrice;
    private final boolean isCall;
    private final int expiryDays;

    public Option(String symbol, String name, double currentPrice, double strikePrice, boolean isCall, int expiryDays) {
        super(symbol, name, currentPrice);
        this.strikePrice = strikePrice;
        this.isCall = isCall;
        this.expiryDays = expiryDays;
    }

    @Override
    public double riskScore() {
        return 8.5;
    }

    @Override
    public void accept(InstrumentVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isInTheMoney(double spotPrice) {
        return isCall ? spotPrice > strikePrice : spotPrice < strikePrice;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public boolean isCall() {
        return isCall;
    }

    public int getExpiryDays() {
        return expiryDays;
    }
}