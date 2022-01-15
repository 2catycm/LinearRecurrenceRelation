package sample;

import org.apache.commons.math3.analysis.UnivariateFunction;
import src.DiscreteMathSolver;
import src.HomogeneousLinearRecurrenceRelation;
import src.LinearAlgebraSolver;

public class FibonacciNumber {
    public static void main(String[] args) {
        final HomogeneousLinearRecurrenceRelation solver1 = new LinearAlgebraSolver(new double[]{1, 1}, new double[]{1, 0});
        final HomogeneousLinearRecurrenceRelation solver2 = new DiscreteMathSolver(new double[]{1, 1}, new double[]{1, 0});
        final var generalTermFormula1 = solver1.getGeneralTermFormula();
        final var generalTermFormula2 = solver2.getGeneralTermFormula();
        for (int i = 0; i < 100; i++) {
            System.out.println((long)generalTermFormula1.value(i));
            System.out.println((long)generalTermFormula2.value(i));
        }
    }
}
