import java.util.*;

public class RiskAnalyzer<T extends Instrument> {
    private final List<T> instruments = new ArrayList<>();

    public void add(T instrument) {
        instruments.add(instrument);
    }

    public double averageRisk() {
        if (instruments.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (T instrument : instruments) {
            sum += instrument.riskScore();
        }
        return sum / instruments.size();
    }

    public T highestRisk() {
        if (instruments.isEmpty()) {
            return null;
        }

        T highest = instruments.get(0);
        for (T instrument : instruments) {
            if (instrument.riskScore() > highest.riskScore()) {
                highest = instrument;
            }
        }
        return highest;
    }

    public T lowestRisk() {
        if (instruments.isEmpty()) {
            return null;
        }

        T lowest = instruments.get(0);
        for (T instrument : instruments) {
            if (instrument.riskScore() < lowest.riskScore()) {
                lowest = instrument;
            }
        }
        return lowest;
    }

    public List<T> getAboveRiskThreshold(double threshold) {
        List<T> result = new ArrayList<>();
        for (T instrument : instruments) {
            if (instrument.riskScore() > threshold) {
                result.add(instrument);
            }
        }
        return result;
    }
}