package ritmath;

/**
 * The sine class which holds all the necessary fields and
 * methods for sine
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Sine extends AbstractFunction{

    /** The term for the sine */
    private final MathFunction term;

    /**
     * The constructor for sine
     * @param arg The terms to be used in calculations
     */
    public Sine(MathFunction arg){
        this.term = arg;
    }

    /**
     * Calculates the derivative for the sine which is cos(terms) * d/dx terms
     * @return The new product that contains the derivative
     */
    public MathFunction derivative(){
        return new Product(new Cosine(term), term.derivative());
    }

    /**
     * Calculates the value at the x-value
     * @param x The value to evaluate at
     * @return The calculated value
     */
    public double evaluate(double x){
        return Math.sin(term.evaluate(x));
    }

    /**
     * Figures out if all the terms in the expression are constant and
     * returns true if they are
     * @return The isConstant boolean
     */
    @Override
    public boolean isConstant() {
        return term.isConstant();
    }

    /**
     * The to string for the sine
     * @return returns a nicely formatted string
     */
    @Override
    public String toString(){
        return "sin( " + term.toString() + " )";
    }

}
