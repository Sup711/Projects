package ritmath;

/**
 * The functionFactory class that creates instances of all
 * the supporting classes when called
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class FunctionFactory{

    /** The one variable that will be used throughout the entire expression */
    static private final MathFunction var = new Variable();

    /**
     * The constructor for function factor that creates the
     * one instance of variable that will be used for each expression
     */
    FunctionFactory(){
    }

    /**
     * Creates an instance of constant
     * @param value The value of the constant
     * @return The instance of constant
     */
    static public MathFunction constant(double value){
        return new Constant(value);
    }

    /**
     * Creates an instance of cosine.  If the arguments passed in
     * are constant a constant is created
     * @param arg The arguments to be passed into cosine
     * @return The new cosine MathFunction
     */
    static public MathFunction cosine(MathFunction arg){
        if (arg.isConstant()){
            return new Constant(Math.cos(arg.evaluate(0)));
        }
        return new Cosine(arg);
    }

    /**
     * Creates an instance of product.  If the arguments passed in
     * are constant a constant is created
     * @param args The arguments to be passed into product
     * @return The new product MathFunction
     */
    static public MathFunction product(MathFunction... args){
        double total = 1;
        for (MathFunction arg : args) {
            if (arg.isConstant()){
                total *= arg.evaluate(1);
            }
            else {
                return new Product(args);
            }
        }
        return new Constant(total);
    }

    /**
     * Creates an instance of sine.  If the arguments passed in
     * are constant a constant is created
     * @param arg The arguments to be passed into sine
     * @return The new sine MathFunction
     */
    static public MathFunction sine(MathFunction arg){
        if (arg.isConstant()){
            return new Constant(Math.sin(arg.evaluate(0)));
        }
        return new Sine(arg);
    }

    /**
     * Creates an instance of sum.  If one value is passed in, and
     * it is a constant, a constant instance is created instead
     * @param args The values inside the sum
     * @return The instance of sum
     */
    static public MathFunction sum(MathFunction... args) {
        double total = 0;
        for (MathFunction arg : args) {
            if (arg.isConstant()){
                total += arg.evaluate(0);
            }
            else {
                return new Sum(args);
            }
        }
        return new Constant(total);
    }

    /**
     * Basically a getter for the variable instance
     * @return The already instantiated variable
     */
    static public MathFunction x(){
        return var;
    }

}
