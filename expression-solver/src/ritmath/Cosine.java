package ritmath;

/**
 * The cosine class which contains all the methods
 * and fields needed
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Cosine extends AbstractFunction {

    private final MathFunction term;

    /**
     * The constructor for cosine
     *
     * @param arg The terms to be used in calculations
     */
    public Cosine(MathFunction arg) {
        this.term = arg;
    }

    /**
     * Calculates the derivative for the cosine which is sin(terms) * d/dx terms * -1
     *
     * @return The new product that contains the derivative
     */
    public MathFunction derivative() {
        return new Product(new Sine(term), term.derivative(), new Constant(-1));
    }

    /**
     * Calculates the value at the x-value
     *
     * @param x The value to evaluate at
     * @return The calculated value
     */
    public double evaluate(double x) {
        return Math.cos(term.evaluate(x));
    }

    /**
     * Figures out if all the terms in the expression are constant and
     * returns true if they are
     *
     * @return The isConstant boolean
     */
    @Override
    public boolean isConstant() {
        return term.isConstant();
    }

    /**
     * The to string for the cosine
     *
     * @return returns a nicely formatted string
     */
    @Override
    public String toString() {
        return "cos( " + term.toString() + " )";
    }
}

