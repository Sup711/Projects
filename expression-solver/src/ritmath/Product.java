package ritmath;

import java.util.*;

/**
 * The product class which holds all the necessary fields and
 * methods for product
 *
 * @author:  Eliot Nagy (epn2643)
 */
public class Product extends AbstractFunction{


    /**
     * The constructor for product
     * @param factors the expression to be used in the expression
     */
    public Product(MathFunction... factors){
        super(factors);
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
        double normalized = 1;
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].isConstant()){
                copyArray.set(i, null);
                normalized *= terms[i].evaluate(1);
            }
        }
        copyArray.removeIf(Objects::isNull);
        if (normalized == 0){
            setChildren(new Constant(0));
        }

        else if (normalized == 1){
            MathFunction[] newTerms = new MathFunction[copyArray.size()];
            for (int i = 0; i < copyArray.size(); i++) {
                newTerms[i] = copyArray.get(i);
            }
            setChildren(newTerms);
        }

        else {
            MathFunction[] newTerms = new MathFunction[copyArray.size()+1];
            copyArray.add(new Constant(normalized));
            for (int i = 0; i < copyArray.size(); i++) {
                newTerms[i] = copyArray.get(i);
            }
            setChildren(newTerms);
        }
    }

    /**
     * Calculates the derivative for the product using the product rule
     * for derivatives, recursively
     * @return An updated MathFunction with the calculated derivative
     */
    public MathFunction derivative(){
        MathFunction[] terms = getTerms();
        if (terms.length == 1){
            return terms[0].derivative();
        }
        else {
            return new Sum(
                   new Product(terms[0], new Product(Arrays.copyOfRange(terms, 1, terms.length)).derivative()),
                   new Product(terms[0].derivative(), new Product(Arrays.copyOfRange(terms, 1, terms.length))));
        }
    }

    /**
     * Evaluates the value of the product
     * @param x The value to evaluate at
     * @return The calculated value of the expression
     */
    public double evaluate(double x){
        double total = 1;
        for (MathFunction term: getTerms()) {
            total *= term.evaluate(x);
        }
        return total;
    }


    /**
     * The to string for the product
     * @return returns a nicely formatted string
     */
    @Override
    public String toString(){
        MathFunction[] terms = getTerms();
        StringBuilder output = new StringBuilder(terms[0].toString());
        for (int i = 1; i < terms.length; i++) {
            output.append(" * ").append(terms[i].toString());
        }
        if (terms.length == 1){
            return output.toString();
        }
        else {
            return "( " + output + " )";
        }
    }
}
