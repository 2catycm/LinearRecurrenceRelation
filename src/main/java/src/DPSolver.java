package src;

import org.apache.commons.math3.analysis.UnivariateFunction;

import java.util.ArrayList;

public class DPSolver extends HomogeneousLinearRecurrenceRelation {
    public DPSolver(double[] coefficients, double[] initialValues) {
        super(coefficients, initialValues);
        prev = new ArrayList<>(order);
        for (int i = 0; i < order; i++) {
            prev.add(initialValues[order - i - 1]);
        }
    }

    @Override
    public UnivariateFunction getGeneralTermFormula() {
        return n -> compute((int) n);
    }

    private double compute(int n) {
        if (n < prev.size())
            return prev.get(n);
        for (int i = prev.size(); i <= n; i++) {
            double sum = 0;
            for (int j = 0; j < order; j++) {
                sum += coefficients[j] * prev.get(i - j - 1);
            }
            prev.add(sum);
        }
        return prev.get(n);
    }

    private ArrayList<Double> prev;
}
