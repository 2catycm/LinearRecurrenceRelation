package src;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.linear.*;

public class LinearAlgebraSolver extends HomogeneousLinearRecurrenceRelation {

    private final RealVector initialVector;
    public LinearAlgebraSolver(double[] coefficients, double[] initialValues) {
        super(coefficients, initialValues);
        initialVector = new ArrayRealVector(initialValues, true);
    }

    /**
     * @return the general term formula(closed form) of the sequence.
     */
    @Override
    public UnivariateFunction getGeneralTermFormula() {
        final var doubles = new double[order][order];
        System.arraycopy(coefficients, 0, doubles[0], 0, order); //copy to avoid possible change by matrix operations.
        for (int i = 1; i < doubles.length; i++) {
            doubles[i][i - 1] = 1;
        }
        final var matrixA = new Array2DRowRealMatrix(doubles, false);
        final var svd = new SingularValueDecomposition(matrixA);
        final var matrixU = svd.getU();
        final var matrixVT = svd.getVT();
        final var matrixLambda = new DiagonalMatrix(svd.getSingularValues());
        if (debugMode) {
            final var I = MatrixUtils.createRealIdentityMatrix(order);
            if (!matrixVT.multiply(matrixU).equals(I))
                throw new AssertionError("SVD wrong.");
        }
        return new UnivariateFunction() {
            @Override
            public double value(double n) {
                if (debugMode && Math.abs(n - (int) n) > 1e-5)
                    throw new AssertionError("general term formula only computes sequence.");
                final var APowerN = matrixU.multiply(matrixLambda.power(order)).multiply(matrixVT);
                RealVector vectorN = APowerN.operate(initialVector);
                return vectorN.getEntry(order-1);
            }
        };
    }
}
