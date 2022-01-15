package src;

public abstract class HomogeneousLinearRecurrenceRelation implements LinearRecurrenceRelation{
    protected static final boolean debugMode = true;
    protected final double[] coefficients; //constants
    protected final double[] initialValues;
    protected final int order;
    /**
     * Suppose an = c1*an-1 + c2*an-2 + ... + ck*an-k,
     * then we claim that an is a homogeneous linear recurrence relation with constant coefficients.
     * And the HomogeneousLinearRecurrenceRelation object for an can be created by
     * coefficients = {c1, c2, c3, ... , ck}.
     * @param coefficients
     * @param initialValues
     */
    public HomogeneousLinearRecurrenceRelation(double[] coefficients, double[] initialValues) {
        if (debugMode&&coefficients.length!=initialValues.length){
            throw new AssertionError("coefficients and initialValues does not match. ");
        }
        order = coefficients.length;
        this.coefficients = coefficients;
        this.initialValues = initialValues;
    }

    public double[] getCoefficients() {
        return coefficients;
    }
    public int getOrder(){
        return order;
    }
}
