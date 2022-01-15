package src;

import org.apache.commons.math3.analysis.UnivariateFunction;

public interface LinearRecurrenceRelation {
    UnivariateFunction getGeneralTermFormula();
}
