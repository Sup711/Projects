package testing.junit_part2;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ritmath.FunctionFactory;
import ritmath.MathFunction;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder( MethodOrderer.MethodName.class )
public class Part2BigExprTests {

    private static final MathFunction X = FunctionFactory.x();

    @Test
    public void t0SpecialCases() {
        MathFunction emptySum = FunctionFactory.sum();
        for ( double x: new double[]{ -10, 0, 10, 99 } ) {
            assertEquals( 0.0, emptySum.evaluate( x ) );
        }
        MathFunction emptyProduct = FunctionFactory.product();
        for ( double x: new double[]{ -10, 0, 10, 99 } ) {
            assertEquals( 1.0, emptyProduct.evaluate( x ) );
        }
        MathFunction almostEmptySum = FunctionFactory.sum(
                FunctionFactory.constant( 3.0 ),
                X,
                FunctionFactory.constant( -3.0 )
        );
        assertEquals( "x", almostEmptySum.toString() );
        MathFunction almostEmptyProd = FunctionFactory.product(
                FunctionFactory.constant( 8.0 ),
                X,
                FunctionFactory.constant( 0.125 )
        );
        assertEquals( "x", almostEmptyProd.toString() );
    }

    @Test
    public void t1SumCombo() {
        final MathFunction p1 = FunctionFactory.product(X, FunctionFactory.constant( 10 ), FunctionFactory.cosine( X ));
        final MathFunction p2 = FunctionFactory.product(
                FunctionFactory.constant( 1 ),
                FunctionFactory.constant( 30 ),
                FunctionFactory.constant( 11 )
        );
        final MathFunction p3 = FunctionFactory.product(
                FunctionFactory.constant( 2 ),
                FunctionFactory.constant( 4 ),
                X,
                FunctionFactory.constant( 6 ),
                FunctionFactory.sine( X ),
                FunctionFactory.constant( 8 ),
                FunctionFactory.constant( 10 )
        );
        final MathFunction p4 = FunctionFactory.product();
        final MathFunction sumOfProds =
                FunctionFactory.sum( p1, p2, p3, p4 );

        // toString
        assertEquals( "( x * cos( x ) * 10.0 )", p1.toString() );
        assertEquals( "330.0", p2.toString() );
        assertEquals( "( x * sin( x ) * 3840.0 )", p3.toString() );
        assertEquals( "1.0", p4.toString() );
        assertEquals(
            "( ( x * cos( x ) * 10.0 ) + ( x * sin( x ) * 3840.0 ) + 331.0 )",
            sumOfProds.toString()
        );

        // evaluate
        assertEquals( 81.61641236267839, p1.evaluate( 20 ), .00001 );
        assertEquals( 330.0, p2.evaluate( 20 ) );
        assertEquals( 70114.1952558818, p3.evaluate( 20 ), .01 );
        assertEquals( 1.0, p4.evaluate( 20 ) );
        assertEquals( 70526.81166824448, sumOfProds.evaluate( 20 ), .01 );

        // derivative
        assertEquals(
            "( ( x * ( ( sin( x ) * -1.0 ) * 10.0 ) ) + ( cos( x ) * 10.0 ) )",
            p1.derivative().toString()
        );
        assertEquals(
            "0.0",
            p2.derivative().toString()
        );
        assertEquals(
            "( ( x * ( cos( x ) * 3840.0 ) ) + ( sin( x ) * 3840.0 ) )",
            p3.derivative().toString()
        );
        assertEquals(
            "0.0",
            p4.derivative().toString()
        );
        assertEquals(
            "( ( ( x * ( ( sin( x ) * -1.0 ) * 10.0 ) ) + ( cos( x ) * 10.0 ) ) + ( ( x * ( cos( x ) * 3840.0 ) ) + ( sin( x ) * 3840.0 ) ) )",
            sumOfProds.derivative().toString()
        );


        // integral
        assertEquals( 0.0, p1.integral( -100.0, 100.0, 1000 ), 1e-6 );
        assertEquals( 66000, p2.integral( -100.0, 100.0, 1000 ), 1e-14 );
        assertEquals( -663953.7615615956, p3.integral( -100.0, 100.0, 1000 ), 0.1 );
        assertEquals( 200.0, p4.integral( -100.0, 100.0, 1000 ), 1e-6 );
        assertEquals( -597753.7615615941, sumOfProds.integral( -100.0, 100.0, 1000 ), 0.1 );
    }

    @Test
    public void t2ProductCombo() {
        final MathFunction s1 = FunctionFactory.sum(
                X,
                FunctionFactory.constant( 10 ),
                FunctionFactory.cosine( X )
        );
        final MathFunction s2 = FunctionFactory.sum(
                FunctionFactory.constant( 1 ),
                FunctionFactory.constant( 30 ),
                FunctionFactory.constant( 11 )
        );
        final MathFunction s3 = FunctionFactory.sum(
                FunctionFactory.constant( 2 ),
                FunctionFactory.constant( 4 ),
                X,
                FunctionFactory.constant( 6 ),
                FunctionFactory.sine( X ),
                FunctionFactory.constant( 8 ),
                FunctionFactory.constant( 10 )
        );
        final MathFunction s4 = FunctionFactory.sum();
        final MathFunction prodOfSums =
                FunctionFactory.product( s1, s2, s3, s4 );

        // toString
        assertEquals( "( x + cos( x ) + 10.0 )", s1.toString() );
        assertEquals( "42.0", s2.toString() );
        assertEquals( "( x + sin( x ) + 30.0 )", s3.toString() );
        assertEquals( "0.0", s4.toString() );
        assertEquals(
            "0.0",
            prodOfSums.toString()
        );

        // evaluate
        assertEquals( 30.408082061813392, s1.evaluate( 20 ), .00001 );
        assertEquals( 42.0, s2.evaluate( 20 ) );
        assertEquals( 50.91294525072763, s3.evaluate( 20 ), .01 );
        assertEquals( 0.0, s4.evaluate( 20 ) );
        assertEquals( 0, prodOfSums.evaluate( 20 ), .01 );

        // derivative
        assertEquals(
            "( ( sin( x ) * -1.0 ) + 1.0 )",
            s1.derivative().toString()
        );
        assertEquals(
            "0.0",
            s2.derivative().toString()
        );
        assertEquals(
            "( cos( x ) + 1.0 )",
            s3.derivative().toString()
        );
        assertEquals(
            "0.0",
            s4.derivative().toString()
        );
        assertEquals(
            "0.0",
            prodOfSums.derivative().toString()
        );


        // integral
        assertEquals( 1998.9906467413823, s1.integral( -100.0, 100.0, 1000 ), 1e-3 );
        assertEquals( 8400.0, s2.integral( -100.0, 100.0, 1000 ), 1e-3);
        assertEquals( 6000.0, s3.integral( -100.0, 100.0, 1000 ), 1e-4 );
        assertEquals( 0.0, s4.integral( -100.0, 100.0, 1000 ), 1e-6 );
        assertEquals( 0.0, prodOfSums.integral( -100.0, 100.0, 1000 ), 1e-6 );
    }
}
