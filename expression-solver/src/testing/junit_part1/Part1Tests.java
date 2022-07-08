package testing.junit_part1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.opentest4j.AssertionFailedError;
import ritmath.FunctionFactory;
import ritmath.MathFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestMethodOrder( MethodOrderer.MethodName.class )
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
public class Part1Tests {

    // Explanations for errors
    public static final String EXP_CLASS = "Wrong class for function";
    public static final String EXP_EVAL = "Wrong value from evaluation";
    public static final String EXP_STRING = "Wrong toString output";
    public static final String EXP_CONSTANT = "Wrong boolean isConstant answer";
    public static final String EXP_DERIV = "Wrong derivative result (toString)";

    public static final String TYPE_NAME_CONST = "ritmath.Constant";
    public static final String TYPE_NAME_VAR = "ritmath.Variable";
    public static final String TYPE_NAME_SUM = "ritmath.Sum";

    private MathFunction c1, c_5;
    private MathFunction v1, v2;
    private MathFunction s1, s2, s3, s4, s5, s6, s7;

    private void testType( String expected, MathFunction f ) {
        assertEquals( expected, f.getClass().getName(), EXP_CLASS );
    }

    private void initVars() {
        v1 = FunctionFactory.x();
        v2 = FunctionFactory.x();
    }

    private void initConsts() {
        c1 = FunctionFactory.constant( 1 );
        c_5 = FunctionFactory.constant( -5 );
    }

    public void allInit() {
        initConsts();
        initVars();
        s1 = FunctionFactory.sum( v1, v1 );
        s2 = FunctionFactory.sum( c1 );
        s3 = FunctionFactory.sum( v1 );
        s4 = FunctionFactory.sum( c1, v1 );
        s5 = FunctionFactory.sum( v1, c1 );
        s6 = FunctionFactory.sum( c_5, v1 );
        s7 = FunctionFactory.sum( v1, c_5 );
    }

    @Test
    public void t1Constants() {
        initConsts();

        for ( MathFunction f: new MathFunction[]{ c1, c_5 } ) {
            testType( TYPE_NAME_CONST, f );
        }

        assertEquals( "1.0", c1.toString(), EXP_STRING );
        assertEquals( "-5.0", c_5.toString(), EXP_STRING );

        assertEquals( 1, c1.evaluate( 0 ), EXP_EVAL );
        assertEquals( 1, c1.evaluate( 5 ), EXP_EVAL );
        assertEquals( -5.0, c_5.evaluate( 99 ), EXP_EVAL );
        assertEquals( 1.0, c1.evaluate( 99 ), EXP_EVAL );

        assertEquals( "0.0", c1.derivative().toString(), EXP_DERIV );
        assertEquals( "0.0", c_5.derivative().toString(), EXP_DERIV );
    }

    @Test
    public void t2aVariable() {
        initVars();

        for ( MathFunction f: new MathFunction[]{ v1, v2 } ) {
            testType( TYPE_NAME_VAR, f );
        }

        assertEquals( "x", v1.toString(), EXP_STRING );
        assertEquals( "x", v2.toString(), EXP_STRING );

        assertSame( v1, v2, "Check for multiple instances of Variable class" );

        for ( double d: new double[]{
                -5, -4.875, 0.0, 1, 1.0009765625, 6.125e23
        } ) {
            assertEquals( d, v1.evaluate( d ), EXP_EVAL );
            assertEquals( d, v2.evaluate( d ), EXP_EVAL );
        }

        assertEquals( "1.0", v1.derivative().toString(), EXP_DERIV );
    }

    @Test
    public void t3Part1Sums() {

        allInit();

        testType( TYPE_NAME_CONST, s2 );
        for ( MathFunction f: new MathFunction[]{ s1, s3, s4, s5, s6, s7 } ) {
            testType( TYPE_NAME_SUM, f );
        }

        // s1
        assertEquals( "( x + x )", s1.toString() );
        try {
            assertEquals( "2.0", s1.derivative().toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 1.0 + 1.0 )", s1.derivative().toString() );
            // Part 1
        }

        // s2
        assertEquals( "1.0", s2.toString() );
        assertEquals( "0.0", s2.derivative().toString() );

        // s3
        assertEquals( "x", s3.toString() );
        assertEquals( "1.0", s3.derivative().toString() );

        // s4
        try {
            assertEquals( "( x + 1.0 )", s4.toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 1.0 + x )", s4.toString() ); // Part 1
        }
        try {
            assertEquals( "1.0", s4.derivative().toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 0.0 + 1.0 )", s4.derivative().toString() );
            // Part 1
        }

        // s5
        assertEquals( "( x + 1.0 )", s5.toString() );
        try {
            assertEquals( "1.0", s5.derivative().toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 1.0 + 0.0 )", s5.derivative().toString() );
            // Part 1
        }

        // s6
        try {
            assertEquals( "( x + -5.0 )", s6.toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( -5.0 + x )", s6.toString() ); // Part 1
        }
        try {
            assertEquals( "1.0", s6.derivative().toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 0.0 + 1.0 )", s6.derivative().toString() );
            // Part 1
        }

        // s7
        assertEquals( "( x + -5.0 )", s7.toString() );
        try {
            assertEquals( "1.0", s7.derivative().toString() ); // Part 2
        }
        catch( AssertionFailedError a ) {
            assertEquals( "( 1.0 + 0.0 )", s7.derivative().toString() );
            // Part 1
        }

        assertEquals( 2.0, s1.evaluate( 1.0 ) );
        assertEquals( -10.0, s1.evaluate( -5.0 ) );
        assertEquals( 1.0, s2.evaluate( 1.0 ) );
        assertEquals( 1.0, s2.evaluate( -5.0 ) );
        assertEquals( 1.0, s3.evaluate( 1.0 ) );
        assertEquals( -5.0, s3.evaluate( -5.0 ) );
        assertEquals( 2.0, s4.evaluate( 1.0 ) );
        assertEquals( -4.0, s4.evaluate( -5.0 ) );
        assertEquals( 2.0, s5.evaluate( 1.0 ) );
        assertEquals( -4.0, s5.evaluate( -5.0 ) );
        assertEquals( -4.0, s6.evaluate( 1.0 ) );
        assertEquals( -10.0, s6.evaluate( -5.0 ) );
    }

}
