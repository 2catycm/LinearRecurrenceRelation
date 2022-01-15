package sample;

import org.apache.commons.math3.analysis.UnivariateFunction;
import src.HomogeneousLinearRecurrenceRelation;
import src.LinearAlgebraSolver;

public class FibonacciNumber {
    public static void main(String[] args) {
        final HomogeneousLinearRecurrenceRelation solver = new LinearAlgebraSolver(new double[]{1, 1}, new double[]{1, 0});
        final var generalTermFormula = solver.getGeneralTermFormula();
        for (int i = 0; i < 100; i++) {
            System.out.println((long)generalTermFormula.value(i));
        }
    }
}
