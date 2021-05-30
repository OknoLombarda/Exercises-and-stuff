package me.math.pi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Pi {

    public static PiCalculationResult calculate(int precision) {
        var requiredPrecision = new MathContext(precision + 1, RoundingMode.HALF_DOWN);
        var mathContext = new MathContext(precision + 10);
        var four = new BigDecimal(4);
        var two = new BigDecimal(2);

        var denominator = BigDecimal.ONE;

        var result = four;
        boolean negate = true;

        long requiredIterationCount = getRequiredIterationCount(precision);
        long iterations = 0;
        while (iterations < requiredIterationCount) {
            denominator = denominator.add(two);

            if (negate) {
                result = result.add(four.negate().divide(denominator, mathContext));
            } else {
                result = result.add(four.divide(denominator, mathContext));
            }

            negate = !negate;
            iterations++;
        }

        return new PiCalculationResult(result.round(requiredPrecision), iterations);
    }

    private static long getRequiredIterationCount(int precision) {
        return (long) (2L * Math.pow(10, precision) - (double) 1 / 2);
    }

    private Pi() {}
}
