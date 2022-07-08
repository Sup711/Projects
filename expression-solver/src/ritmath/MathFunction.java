package ritmath;

/**
 * The interface that holds what each class that
 * implements it should have
 *
 * @author: Eliot Nagy (epn2643)
 */
public interface MathFunction {

    /**
     * The derivative of a function
     * @return MathFunction
     */
    MathFunction derivative();

    /**
     * Evaluates the function of the expression
     * @param x The value to evaluate at
     * @return The calculated value
     */
    double evaluate(double x);

    /**
     * Calculates the integral using the trapezoidal method
     * @param lower The lower bound
     * @param upper The upper bound
     * @param accuracy The amount of trapezoids to use
     */
    double integral(double lower, double upper, int accuracy);

    /**
     * Checks to see if a class is constant or not
     * @return Boolean
     */
    boolean isConstant();

}
