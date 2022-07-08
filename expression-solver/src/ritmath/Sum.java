package ritmath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * The sum class which holds all the necessary fields and
 * methods for sum
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Sum extends AbstractFunction{

    /**
     * The constructor for Sum which takes in terms
     * and stores it
     * @param terms the terms to be stored and operated on
     */
    public Sum(MathFunction... terms){
        super(terms);
        normalize();
    }


    /**
     * Simplifies down the constants in the expression to make is to there is
     * only one constant term
     */
    public void normalize(){
        ArrayList<MathFunction> copyArray = new ArrayList<>();
        Collections.addAll(copyArray, getTerms());
        MathFunction[] terms = getTerms();
        double normalized = 0;
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].isConstant()){
                copyArray.set(i, null);
                normalized += terms[i].evaluate(0);
            }
        }
        copyArray.removeIf(Objects::isNull);
        MathFunction[] newTerms;
        if (normalized == 0){
            newTerms = new MathFunction[copyArray.size()];

            for (int i = 0; i < copyArray.size(); i++) {
                newTerms[i] = copyArray.get(i);
            }
        }
        else{
            newTerms = new MathFunction[copyArray.size() + 1];
            copyArray.add(new Constant(normalized));

            for (int i = 0; i < copyArray.size(); i++) {
                newTerms[i] = copyArray.get(i);
            }
        }
        setChildren(newTerms);
    }


    /**
     * Evaluates the sum by iterating through and calling
     * evaluate on every term
     * @param x The value of x
     * @return The total evaluated value
     */
    public double evaluate(double x){
        double total = 0.0;
        for (MathFunction term : getTerms()) {
            total += term.evaluate(x);
        }
        return total;
    }


    /**
     * Calculated the derivative of the sum because the derivative of a sum
     * is the sum of a derivative
     * @return The derivative as a sum
     */
    public MathFunction derivative(){
        MathFunction[] terms = getTerms();
        MathFunction[] derivative = new MathFunction[terms.length];
        for (int i = 0; i < terms.length; i++) {
            derivative[i] = terms[i].derivative();
        }
        return new Sum(derivative);
    }


    /**
     * Calculates the definite integral of a sum
     * @param a The lower bound
     * @param b The upper bound
     * @param n The amount of trapezoids to use
     * @return The definite integral value
     */
    public double integral(double a, double b, int n){
        double integralEval = 0;
        for (MathFunction term : getTerms()) {
            integralEval += term.integral(a, b, n);
        }
        return integralEval;
    }


    /**
     * The to string for sum which makes it look nice
     * @return The output string
     */
    @Override
    public String toString() {
        MathFunction[] terms = getTerms();
        StringBuilder output = new StringBuilder(terms[0].toString());
        for (int i = 1; i < terms.length; i++) {
            output.append(" + ").append(terms[i].toString());
        }
        if (terms.length == 1){
            return output.toString();
        }
        else {
            return "( " + output + " )";
        }
    }
}
