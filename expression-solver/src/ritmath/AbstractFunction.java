package ritmath;


/**
 * The abstractFunction that holds functionality for numChildren,
 * isConstant, integral, and setChildren
 *
 * @author:  Eliot Nagy (epn2643)
 */
public abstract class AbstractFunction implements MathFunction {

    /** The terms of the expression */
    private MathFunction[] terms;

    /**
     * The constructor for AbstractFunction setting the terms of the expression
     * @param children The terms of the expression
     */
    protected AbstractFunction(MathFunction... children){
        this.terms = children;
    }

    /**
     * Returns the terms in the expression
     * @return The size of the expression
     */
    protected int numChildren(){
        return terms.length;
    }

    /**
     * Checks to see if all the terms in an expression are constant
     * @return The boolean representing constant (true) or not constant (false)
     */
    public boolean isConstant(){
        for (MathFunction term : terms) {
            if (!term.isConstant()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the instance's terms
     * @return The terms of the instance
     */
    public MathFunction[] getTerms(){
        return terms;
    }

    /**
     * Returns the term at the index
     * @param c The index
     * @return The term at the index
     */
    public MathFunction get(int c){
        return terms[c];
    }

    /**
     * Updates the children for the instance of a class
     * @param children The new children value
     */
    protected void setChildren(MathFunction... children){
        this.terms = children;
    }

    /**
     * The string method that all classes that extend AbstractFunction
     * must implement
     * @return A string
     */
    public abstract String toString();

    /**
     * Calculates the integral using the trapezoidal method
     * @param a The lower bound
     * @param b The upper bound
     * @param n The amount of trapezoids to use
     * @return The calculated integral value
     */
    public double integral(double a, double b, int n){
        double inside = 0;
        double h = (b-a)/n;
        for(int i = 1; i < n; ++i){
            inside += evaluate(a+(h*i));
        }
        return (h/2) * (evaluate(a) + (2*inside) + evaluate(b));
    }
}
