package me.math.pi;

import java.math.BigDecimal;
import java.util.Objects;

public class PiCalculationResult {
    private final BigDecimal pi;
    private final long iterationCount;

    public PiCalculationResult(BigDecimal pi, long iterationCount) {
        this.pi = pi;
        this.iterationCount = iterationCount;
    }

    public BigDecimal getPi() {
        return pi;
    }

    public long getIterationCount() {
        return iterationCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiCalculationResult that = (PiCalculationResult) o;
        return iterationCount == that.iterationCount && pi.equals(that.pi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pi, iterationCount);
    }

    @Override
    public String toString() {
        return "PiCalculationResult{" +
                "pi=" + pi +
                ", iterationCount=" + iterationCount +
                '}';
    }
}
