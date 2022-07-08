package testing.simple;

import ritmath.FunctionFactory;
import ritmath.MathFunction;

/**
 * Put any manual tests you create in here.
 *
 * @author YOUR NAME HERE
 */
public class SimpleTests {

    public static void main( String[] args ) {
        MathFunction two = FunctionFactory.constant( 2 );
        MathFunction twoX = FunctionFactory.product( two, FunctionFactory.x() );
        MathFunction sinTwoX = FunctionFactory.sine( twoX );
        MathFunction twoXSinTwoX = FunctionFactory.product(
                two, FunctionFactory.x(), sinTwoX
        );

        System.out.println( "Function: " + twoXSinTwoX );
        System.out.println( "Value at 7.5: " + twoXSinTwoX.evaluate( 7.5 ) );
        System.out.println( "Derivative: " + twoXSinTwoX.derivative() );
        System.out.println(
                "Integral from 1 to 5: " + twoXSinTwoX.integral( 1, 5, 1000 )
        );
    }
}
