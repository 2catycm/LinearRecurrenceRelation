package sample;

import org.apache.commons.math3.analysis.UnivariateFunction;
import src.DPSolver;
import src.DiscreteMathSolver;
import src.HomogeneousLinearRecurrenceRelation;
import src.LinearAlgebraSolver;

public class FibonacciNumber {
    public static void main(String[] args) {
        final HomogeneousLinearRecurrenceRelation solver1 = new LinearAlgebraSolver(new double[]{1, 1}, new double[]{1, 0});
        final HomogeneousLinearRecurrenceRelation solver2 = new DiscreteMathSolver(new double[]{1, 1}, new double[]{1, 0});
        final HomogeneousLinearRecurrenceRelation solver3 = new DPSolver(new double[]{1, 1}, new double[]{1, 0});
        final var generalTermFormula1 = solver1.getGeneralTermFormula();
        final var generalTermFormula2 = solver2.getGeneralTermFormula();
        final var generalTermFormula3 = solver3.getGeneralTermFormula();
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.round(generalTermFormula1.value(i)));
            System.out.println(Math.round(generalTermFormula2.value(i)));
            System.out.println(Math.round(generalTermFormula3.value(i)));
        }
    }
}
