import java.util.*;

public class Portfolio implements Observable<String> {
    private final String portfolioId;
    private final String ownerName;
    private final List<Position> positions;
    private final List<Observer<String>> observers;

    public Portfolio(String portfolioId, String ownerName) {
        this.portfolioId = portfolioId;
        this.ownerName = ownerName;
        this.positions = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addPosition(Instrument inst, int qty, double costBasis) {
        for (Position position : positions) {
            if (position.getInstrument().getSymbol().equals(inst.getSymbol())) {
                position.addQuantity(qty, costBasis);
                notifyObservers("ADDED: " + inst.getSymbol() + " x" + qty);
                return;
            }
        }

        positions.add(new Position(inst, qty, costBasis));
        notifyObservers("ADDED: " + inst.getSymbol() + " x" + qty);
    }

    public void removePosition(String symbol) throws PositionNotFoundException {
        Iterator<Position> iterator = positions.iterator();
        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (position.getInstrument().getSymbol().equals(symbol)) {
                iterator.remove();
                notifyObservers("REMOVED: " + symbol);
                return;
            }
        }
        throw new PositionNotFoundException("Position not found: " + symbol);
    }

    public double totalMarketValue() {
        double sum = 0.0;
        for (Position position : positions) {
            sum += position.marketValue();
        }
        return sum;
    }

    public double totalUnrealizedPnL() {
        double sum = 0.0;
        for (Position position : positions) {
            sum += position.unrealizedPnL();
        }
        return sum;
    }

    public Position getPosition(String symbol) throws PositionNotFoundException {
        for (Position position : positions) {
            if (position.getInstrument().getSymbol().equals(symbol)) {
                return position;
            }
        }
        throw new PositionNotFoundException("Position not found: " + symbol);
    }

    public List<Position> getPositionsSortedByValue() {
        List<Position> sorted = new ArrayList<>(positions);
        sorted.sort((a, b) -> Double.compare(b.marketValue(), a.marketValue()));
        return sorted;
    }

    public Map<String, Double> allocationByAssetClass() {
        Map<String, Double> rawValues = new HashMap<>();
        double total = totalMarketValue();

        if (total == 0.0) {
            return rawValues;
        }

        for (Position position : positions) {
            String assetClass = position.getInstrument().assetClass();
            rawValues.put(assetClass,
                    rawValues.getOrDefault(assetClass, 0.0) + position.marketValue());
        }

        Map<String, Double> allocation = new HashMap<>();
        for (Map.Entry<String, Double> entry : rawValues.entrySet()) {
            allocation.put(entry.getKey(), entry.getValue() * 100.0 / total);
        }

        return allocation;
    }

    public void revalueAll(PricingStrategy strategy) {
        for (Position position : positions) {
            Instrument instrument = position.getInstrument();
            double fairValue = strategy.calculateFairValue(instrument);
            instrument.updatePrice(fairValue);
        }
        notifyObservers("REVALUED: " + strategy.strategyName());
    }

    @Override
    public void addObserver(Observer<String> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer<String> observer : observers) {
            observer.onEvent(event);
        }
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}