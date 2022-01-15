package src;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.*;

import java.util.Arrays;

public class DiscreteMathSolver extends HomogeneousLinearRecurrenceRelation{
    public DiscreteMathSolver(double[] coefficients, double[] initialValues) {
        super(coefficients, initialValues);
    }

    public PolynomialFunction getCharacteristicEquation(){
        double[] d = new double[order+1];
        System.arraycopy(coefficients, 0, d, 0, order);
        d[order] = -1;
        for (int i = 0; i < order / 2; i++) {
            double temp = d[i];
            d[i] = d[order-i-1];
            d[order-i-1] = d[i];
        }
        return new PolynomialFunction(d);
    }
    @Override
    public UnivariateFunction getGeneralTermFormula() {
        final var characteristicEquation = getCharacteristicEquation();
        LaguerreSolver laguerreSolver = new LaguerreSolver();
        final var roots = laguerreSolver.solveAllComplex(characteristicEquation.getCoefficients(), 0);
        if (debugMode && Arrays.stream(roots).anyMatch(c->c.getImaginary()>1e5))
            throw new AssertionError("has complex roots.");
        final var realRoots = Arrays.stream(roots).mapToDouble(Complex::getReal).toArray();
        var row = new double[order];
        Arrays.fill(row, 1);
        final var valuesMatrixA = new double[order][];
        for (int i = 0; i < order; i++) {
            valuesMatrixA[i] = row;
            row = termWiseProduct(row, realRoots);
        }
        final var matrixA = new Array2DRowRealMatrix(valuesMatrixA);
        DecompositionSolver solver = new LUDecomposition(matrixA).getSolver();
        RealVector vectorB = new ArrayRealVector(reversedVector(initialValues));
        RealVector solution = solver.solve(vectorB);
        return n -> {
            if (debugMode && Math.abs(n - (int) n) > 1e-5)
                throw new AssertionError("general term formula only computes sequence.");
            double[] powerNOfRoots = realRoots.clone();
            for (int i = 0; i < order; i++) {
                powerNOfRoots[i] = Math.pow(powerNOfRoots[i], n);
            }
            final var powerVector = new ArrayRealVector(powerNOfRoots, false);
            return solution.dotProduct(powerVector);
        };
    }
    private double[] reversedVector(double[] initial){
        double[] result = new double[initial.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = initial[initial.length-i-1];
        }
        return result;
    }
    private double[] termWiseProduct(double[] muliplicand, double[] multiplier) {
        assert (muliplicand.length<=multiplier.length);
        final var result = new double[muliplicand.length];
        for (int i = 0; i < muliplicand.length; i++) {
            result[i] = muliplicand[i]*multiplier[i];
        }
        return result;
    }
}
