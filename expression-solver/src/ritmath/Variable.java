package ritmath;

/**
 * The variable class which contains all the methods
 * and fields needed
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Variable extends AbstractFunction{

    /**
     * The constructor for variable that doesn't do much
     */
    protected Variable(){
    }

    /**
     * Checks to see if the variable is constant which it never is
     * @return returns false always
     */
    @Override
    public boolean isConstant(){
        return false;
    }


    /**
     * Calculates the value of x which will always be the value passed in
     * @param x The value for x
     * @return The value of x
     */
    public double evaluate(double x){
        return x;
    }


    /**
     * Calculates the derivative of the variable which is always 1
     * @return The value as a constant
     */
    public MathFunction derivative(){
        return new Constant(1.0);
    }

    /**
     * The to string method which returns the variable as a string
     * @return The nice string output
     */
    @Override
    public String toString() {
        return "x";
    }

    /**
     * Calculates the integral for the variable
     * @param lower The lower bound of the integral
     * @param upper The upper bound of the integral
     * @param accuracy The amount of rectangles to be used, not used
     * @return The calculated integral value
     */
    @Override
    public double integral(double lower, double upper, int accuracy) {
        return (Math.pow(upper, 2) * 1/2) - (Math.pow(lower, 2) * 1/2);

    }
}
