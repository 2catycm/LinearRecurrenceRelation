package sample;

import org.apache.commons.math3.analysis.UnivariateFunction;
import src.LinearAlgebraSolver;

public class FibonacciNumber {
    public static void main(String[] args) {
        final var linearAlgebraSolver = new LinearAlgebraSolver(new double[]{1, 1}, new double[]{0, 1});
        final var generalTermFormula = linearAlgebraSolver.getGeneralTermFormula();
        for (int i = 0; i < 100; i++) {
            System.out.println(generalTermFormula.value(i));
        }
    }
}
