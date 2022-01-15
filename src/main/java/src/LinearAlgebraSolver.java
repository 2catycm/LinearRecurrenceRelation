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
        final var valuesMatrixA = new double[order][order];
        System.arraycopy(coefficients, 0, valuesMatrixA[0], 0, order); //copy to avoid possible change by matrix operations.
        for (int i = 1; i < valuesMatrixA.length; i++) {
            valuesMatrixA[i][i - 1] = 1;
        }
        final var matrixA = new Array2DRowRealMatrix(valuesMatrixA, false);
        final var ed = new EigenDecomposition(matrixA);
        final var eigenvalues = ed.getRealEigenvalues();
        if (debugMode && eigenvalues.length!=order)
            throw new AssertionError("real eigen values not enough. ");
        final var matrixLambda = new DiagonalMatrix(eigenvalues);

        final var valuesMatrixS = new double[order][];
        for (int i = 0; i < order; i++) {
            valuesMatrixS[i] = ed.getEigenvector(i).toArray();
        }
        final var matrixS = new Array2DRowRealMatrix(valuesMatrixS).transpose();
        final var matrixSI = MatrixUtils.inverse(matrixS);
//        if (debugMode){
//            final var I = MatrixUtils.createRealIdentityMatrix(order);
//            if (!matrixS.multiply(matrixSI).equals(I))
//                throw new AssertionError("not accurate.");
//        }
        return n -> {
            if (debugMode && Math.abs(n - (int) n) > 1e-5)
                throw new AssertionError("general term formula only computes sequence.");
            final var APowerN = matrixS.multiply(matrixLambda.power((int)n)).multiply(matrixSI);
            RealVector vectorN = APowerN.operate(initialVector);
            return vectorN.getEntry(order-1);
        };
    }
}
