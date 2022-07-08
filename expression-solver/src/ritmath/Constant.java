package ritmath;

/**
 * The is the constant class for the overall program.
 * It contains all the fields and methods for the constants
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Constant extends AbstractFunction{

    /** The value that the constant is storing */
    public Double value;

    /**
     * The constructor for constant that stores the value
     * @param value the value to be stored
     */
    public Constant(double value) {
        this.value = value;
    }

    /**
     * Evaluates the constant, which is just the value
     * @param x Not used but passed in so a separate method will not need
     *          to be called later
     * @return the evaluated value
     */
    public double evaluate(double x) {
        return value;
    }

    /**
     * Tells the program whether this class stores a constant
     * @return always true because constants are constant
     */
    @Override
    public boolean isConstant() {
        return true;
    }


    /**
     * Calculates the derivative
     * @return Always zero because the derivative of a constant is 0
     */
    public MathFunction derivative(){
        return new Constant(0);
    }

    /**
     * Calculates the integral for the constant
     * @param lower The lower bound of the integral
     * @param upper The upper bound of the integral
     * @param accuracy The amount of rectangles to be used, not used
     * @return The calculated integral value
     */
    @Override
    public double integral(double lower, double upper, int accuracy) {
        return value * (upper - lower);
    }

    /**
     * The toString method which returns a fancy string
     * @return the fancy string
     */
    @Override
    public String toString(){
        return value.toString();
    }

}
