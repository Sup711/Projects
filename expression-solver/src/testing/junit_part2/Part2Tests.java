package testing.junit_part2;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ritmath.FunctionFactory;
import ritmath.MathFunction;

import java.util.*;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the final submission
 *
 * @author RIT CS
 */
@TestMethodOrder( MethodOrderer.MethodName.class )
public class Part2Tests {

    private MathFunction C_2, C0, C3, X, S1C, S2C, S0, SXC, S2XC, SX5C, S2S;
    private MathFunction PC, P2C, PXC, P2X0, P2XC, PX5C;
    private MathFunction SnC, SnX, SnX2, CsX, CsSP, CsSn, Sn2X;

    private final Map< String, MathFunction > funcs = new HashMap<>();
    private final Map< String, Map< Double, Double > > vals = new HashMap<>();

    private final List< String > funcsToTest = new LinkedList<>();

    private final double[] xVals = new double[]{ -3, -1, 0, 1, 10, 100 };

    /**
     * Check constant, variable, and Sum again, but now check for
     * normalization as well.
     */
    private void initPart1() {

        C_2 = FunctionFactory.constant( -2 );
        C0 = FunctionFactory.constant( 0 );
        C3 = FunctionFactory.constant( 3 );
        X = FunctionFactory.x();
        S1C = FunctionFactory.sum( C3 );
        S2C = FunctionFactory.sum( C_2, C3 );
        S0 = FunctionFactory.sum( C0 );
        SXC = FunctionFactory.sum( X, C3 );
        S2XC = FunctionFactory.sum( X, C0, X );
        SX5C =
                FunctionFactory.sum( C_2, C3, C_2, C3, X, C_2 );
        S2S = FunctionFactory.sum( SXC, S2XC );
        MathFunction Sn2X =
                FunctionFactory.sine( FunctionFactory.sum( X, X ) );

        funcsToTest.add( "C_2" );
        funcsToTest.add( "C0" );
        funcsToTest.add( "C3" );
        funcsToTest.add( "X" );
        funcsToTest.add( "S1C" );
        funcsToTest.add( "S2C" );
        funcsToTest.add( "S0" );
        funcsToTest.add( "SXC" );
        funcsToTest.add( "S2XC" );
        funcsToTest.add( "SX5C" );
        funcsToTest.add( "S2S" );

        funcs.put( "C_2", C_2 );
        funcs.put( "C0", C0 );
        funcs.put( "C3", C3 );
        funcs.put( "X", X );
        funcs.put( "S1C", S1C );
        funcs.put( "S2C", S2C );
        funcs.put( "S0", S0 );
        funcs.put( "SXC", SXC );
        funcs.put( "S2XC", S2XC );
        funcs.put( "SX5C", SX5C );
        funcs.put( "S2S", S2S );

        vals.put( "S1C", new HashMap<>() );
        vals.get( "S1C" ).put( -3.0000000000, 3.0000000000e+00 );
        vals.get( "S1C" ).put( -1.0000000000, 3.0000000000e+00 );
        vals.get( "S1C" ).put( 0.0000000000, 3.0000000000e+00 );
        vals.get( "S1C" ).put( 1.0000000000, 3.0000000000e+00 );
        vals.get( "S1C" ).put( 10.0000000000, 3.0000000000e+00 );
        vals.get( "S1C" ).put( 100.0000000000, 3.0000000000e+00 );
        vals.put( "S2C", new HashMap<>() );
        vals.get( "S2C" ).put( -3.0000000000, 1.0000000000e+00 );
        vals.get( "S2C" ).put( -1.0000000000, 1.0000000000e+00 );
        vals.get( "S2C" ).put( 0.0000000000, 1.0000000000e+00 );
        vals.get( "S2C" ).put( 1.0000000000, 1.0000000000e+00 );
        vals.get( "S2C" ).put( 10.0000000000, 1.0000000000e+00 );
        vals.get( "S2C" ).put( 100.0000000000, 1.0000000000e+00 );
        vals.put( "S2XC", new HashMap<>() );
        vals.get( "S2XC" ).put( -3.0000000000, -6.0000000000e+00 );
        vals.get( "S2XC" ).put( -1.0000000000, -2.0000000000e+00 );
        vals.get( "S2XC" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "S2XC" ).put( 1.0000000000, 2.0000000000e+00 );
        vals.get( "S2XC" ).put( 10.0000000000, 2.0000000000e+01 );
        vals.get( "S2XC" ).put( 100.0000000000, 2.0000000000e+02 );
        vals.put( "C0", new HashMap<>() );
        vals.get( "C0" ).put( -3.0000000000, 0.0000000000e+00 );
        vals.get( "C0" ).put( -1.0000000000, 0.0000000000e+00 );
        vals.get( "C0" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "C0" ).put( 1.0000000000, 0.0000000000e+00 );
        vals.get( "C0" ).put( 10.0000000000, 0.0000000000e+00 );
        vals.get( "C0" ).put( 100.0000000000, 0.0000000000e+00 );
        vals.put( "C3", new HashMap<>() );
        vals.get( "C3" ).put( -3.0000000000, 3.0000000000e+00 );
        vals.get( "C3" ).put( -1.0000000000, 3.0000000000e+00 );
        vals.get( "C3" ).put( 0.0000000000, 3.0000000000e+00 );
        vals.get( "C3" ).put( 1.0000000000, 3.0000000000e+00 );
        vals.get( "C3" ).put( 10.0000000000, 3.0000000000e+00 );
        vals.get( "C3" ).put( 100.0000000000, 3.0000000000e+00 );
        vals.put( "SX5C", new HashMap<>() );
        vals.get( "SX5C" ).put( -3.0000000000, -3.0000000000e+00 );
        vals.get( "SX5C" ).put( -1.0000000000, -1.0000000000e+00 );
        vals.get( "SX5C" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "SX5C" ).put( 1.0000000000, 1.0000000000e+00 );
        vals.get( "SX5C" ).put( 10.0000000000, 1.0000000000e+01 );
        vals.get( "SX5C" ).put( 100.0000000000, 1.0000000000e+02 );
        vals.put( "S2S", new HashMap<>() );
        vals.get( "S2S" ).put( -3.0000000000, -6.0000000000e+00 );
        vals.get( "S2S" ).put( -1.0000000000, 0.0000000000e+00 );
        vals.get( "S2S" ).put( 0.0000000000, 3.0000000000e+00 );
        vals.get( "S2S" ).put( 1.0000000000, 6.0000000000e+00 );
        vals.get( "S2S" ).put( 10.0000000000, 3.3000000000e+01 );
        vals.get( "S2S" ).put( 100.0000000000, 3.0300000000e+02 );
        vals.put( "C_2", new HashMap<>() );
        vals.get( "C_2" ).put( -3.0000000000, -2.0000000000e+00 );
        vals.get( "C_2" ).put( -1.0000000000, -2.0000000000e+00 );
        vals.get( "C_2" ).put( 0.0000000000, -2.0000000000e+00 );
        vals.get( "C_2" ).put( 1.0000000000, -2.0000000000e+00 );
        vals.get( "C_2" ).put( 10.0000000000, -2.0000000000e+00 );
        vals.get( "C_2" ).put( 100.0000000000, -2.0000000000e+00 );
        vals.put( "X", new HashMap<>() );
        vals.get( "X" ).put( -3.0000000000, -3.0000000000e+00 );
        vals.get( "X" ).put( -1.0000000000, -1.0000000000e+00 );
        vals.get( "X" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "X" ).put( 1.0000000000, 1.0000000000e+00 );
        vals.get( "X" ).put( 10.0000000000, 1.0000000000e+01 );
        vals.get( "X" ).put( 100.0000000000, 1.0000000000e+02 );
        vals.put( "S0", new HashMap<>() );
        vals.get( "S0" ).put( -3.0000000000, 0.0000000000e+00 );
        vals.get( "S0" ).put( -1.0000000000, 0.0000000000e+00 );
        vals.get( "S0" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "S0" ).put( 1.0000000000, 0.0000000000e+00 );
        vals.get( "S0" ).put( 10.0000000000, 0.0000000000e+00 );
        vals.get( "S0" ).put( 100.0000000000, 0.0000000000e+00 );
        vals.put( "SXC", new HashMap<>() );
        vals.get( "SXC" ).put( -3.0000000000, 0.0000000000e+00 );
        vals.get( "SXC" ).put( -1.0000000000, 2.0000000000e+00 );
        vals.get( "SXC" ).put( 0.0000000000, 3.0000000000e+00 );
        vals.get( "SXC" ).put( 1.0000000000, 4.0000000000e+00 );
        vals.get( "SXC" ).put( 10.0000000000, 1.3000000000e+01 );
        vals.get( "SXC" ).put( 100.0000000000, 1.0300000000e+02 );
    }

    /**
     * Check the Product class.
     */
    private void initProducts() {
        funcsToTest.add( "PC" );
        funcsToTest.add( "P2C" );
        funcsToTest.add( "PXC" );
        funcsToTest.add( "P2X0" );
        funcsToTest.add( "P2XC" );
        funcsToTest.add( "PX5C" );

        PC = FunctionFactory.product( C3 );
        P2C = FunctionFactory.product(FunctionFactory.constant( 1.0 / 2.0 ), X, FunctionFactory.constant( 2.0 )
        );
        PXC = FunctionFactory.product( X, C3 );
        P2X0 = FunctionFactory.product( X, C0, X );
        P2XC = FunctionFactory.product( X, C3, X );
        PX5C =
                FunctionFactory.product( C_2, C3, C3, X, C_2 );

        funcs.put( "PC", PC );
        funcs.put( "P2C", P2C );
        funcs.put( "PXC", PXC );
        funcs.put( "P2X0", P2X0 );
        funcs.put( "P2XC", P2XC );
        funcs.put( "PX5C", PX5C );

        vals.put( "P2C", new HashMap<>() );
        vals.get( "P2C" ).put( -3.0000000000, -3.0000000000e+00 );
        vals.get( "P2C" ).put( -1.0000000000, -1.0000000000e+00 );
        vals.get( "P2C" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "P2C" ).put( 1.0000000000, 1.0000000000e+00 );
        vals.get( "P2C" ).put( 10.0000000000, 1.0000000000e+01 );
        vals.get( "P2C" ).put( 100.0000000000, 1.0000000000e+02 );
        vals.put( "P2XC", new HashMap<>() );
        vals.get( "P2XC" ).put( -3.0000000000, 2.7000000000e+01 );
        vals.get( "P2XC" ).put( -1.0000000000, 3.0000000000e+00 );
        vals.get( "P2XC" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "P2XC" ).put( 1.0000000000, 3.0000000000e+00 );
        vals.get( "P2XC" ).put( 10.0000000000, 3.0000000000e+02 );
        vals.get( "P2XC" ).put( 100.0000000000, 30000.0000000000 );
        vals.put( "PC", new HashMap<>() );
        vals.get( "PC" ).put( -3.0000000000, 3.0000000000e+00 );
        vals.get( "PC" ).put( -1.0000000000, 3.0000000000e+00 );
        vals.get( "PC" ).put( 0.0000000000, 3.0000000000e+00 );
        vals.get( "PC" ).put( 1.0000000000, 3.0000000000e+00 );
        vals.get( "PC" ).put( 10.0000000000, 3.0000000000e+00 );
        vals.get( "PC" ).put( 100.0000000000, 3.0000000000e+00 );
        vals.put( "PX5C", new HashMap<>() );
        vals.get( "PX5C" ).put( -3.0000000000, -1.0800000000e+02 );
        vals.get( "PX5C" ).put( -1.0000000000, -3.6000000000e+01 );
        vals.get( "PX5C" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "PX5C" ).put( 1.0000000000, 3.6000000000e+01 );
        vals.get( "PX5C" ).put( 10.0000000000, 3.6000000000e+02 );
        vals.get( "PX5C" ).put( 100.0000000000, 3600.0000000000 );
        vals.put( "PXC", new HashMap<>() );
        vals.get( "PXC" ).put( -3.0000000000, -9.0000000000e+00 );
        vals.get( "PXC" ).put( -1.0000000000, -3.0000000000e+00 );
        vals.get( "PXC" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "PXC" ).put( 1.0000000000, 3.0000000000e+00 );
        vals.get( "PXC" ).put( 10.0000000000, 3.0000000000e+01 );
        vals.get( "PXC" ).put( 100.0000000000, 3.0000000000e+02 );
        vals.put( "P2X0", new HashMap<>() );
        vals.get( "P2X0" ).put( -3.0000000000, 0.0000000000e+00 );
        vals.get( "P2X0" ).put( -1.0000000000, 0.0000000000e+00 );
        vals.get( "P2X0" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "P2X0" ).put( 1.0000000000, 0.0000000000e+00 );
        vals.get( "P2X0" ).put( 10.0000000000, 0.0000000000e+00 );
        vals.get( "P2X0" ).put( 100.0000000000, 0.0000000000e+00 );
    }

    /**
     * Check sine and cosine.
     */
    private void initTrig() {
        funcsToTest.add( "SnC" );
        funcsToTest.add( "SnX" );
        funcsToTest.add( "SnX2" );
        funcsToTest.add( "CsX" );
        funcsToTest.add( "CsSP" );
        funcsToTest.add( "CsSn" );
        funcsToTest.add( "Sn2X" );

        SnC =
                FunctionFactory.sine( FunctionFactory.constant( Math.PI ) );
        SnX = FunctionFactory.sine( X );
        Sn2X = FunctionFactory.sine( FunctionFactory.sum( X, X ) );
        SnX2 =
                FunctionFactory.sine( FunctionFactory.product( X, X ) );
        CsX = FunctionFactory.cosine( X );
        CsSP = FunctionFactory.cosine(
                FunctionFactory.sum( FunctionFactory.product( C_2, X ), C3 )
        );
        CsSn = FunctionFactory.cosine( SnX );

        funcs.put( "SnC", SnC );
        funcs.put( "SnX", SnX );
        funcs.put( "Sn2X", Sn2X );
        funcs.put( "SnX2", SnX2 );
        funcs.put( "CsX", CsX );
        funcs.put( "CsSP", CsSP );
        funcs.put( "CsSn", CsSn );

        vals.put( "Sn2X", new HashMap<>() );
        vals.get( "Sn2X" ).put( -3.0000000000, 2.7941549820e-01 );
        vals.get( "Sn2X" ).put( -1.0000000000, -9.0929742683e-01 );
        vals.get( "Sn2X" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "Sn2X" ).put( 1.0000000000, 9.0929742683e-01 );
        vals.get( "Sn2X" ).put( 10.0000000000, 9.1294525073e-01 );
        vals.get( "Sn2X" ).put( 100.0000000000, -8.7329729721e-01 );
        vals.put( "SnC", new HashMap<>() );
        vals.get( "SnC" ).put( -3.0000000000, 1.2246467991e-16 );
        vals.get( "SnC" ).put( -1.0000000000, 1.2246467991e-16 );
        vals.get( "SnC" ).put( 0.0000000000, 1.2246467991e-16 );
        vals.get( "SnC" ).put( 1.0000000000, 1.2246467991e-16 );
        vals.get( "SnC" ).put( 10.0000000000, 1.2246467991e-16 );
        vals.get( "SnC" ).put( 100.0000000000, 1.2246467991e-16 );
        vals.put( "CsX", new HashMap<>() );
        vals.get( "CsX" ).put( -3.0000000000, -9.8999249660e-01 );
        vals.get( "CsX" ).put( -1.0000000000, 5.4030230587e-01 );
        vals.get( "CsX" ).put( 0.0000000000, 1.0000000000e+00 );
        vals.get( "CsX" ).put( 1.0000000000, 5.4030230587e-01 );
        vals.get( "CsX" ).put( 10.0000000000, -8.3907152908e-01 );
        vals.get( "CsX" ).put( 100.0000000000, 8.6231887229e-01 );
        vals.put( "CsSn", new HashMap<>() );
        vals.get( "CsSn" ).put( -3.0000000000, 9.9005908576e-01 );
        vals.get( "CsSn" ).put( -1.0000000000, 6.6636674539e-01 );
        vals.get( "CsSn" ).put( 0.0000000000, 1.0000000000e+00 );
        vals.get( "CsSn" ).put( 1.0000000000, 6.6636674539e-01 );
        vals.get( "CsSn" ).put( 10.0000000000, 8.5563435482e-01 );
        vals.get( "CsSn" ).put( 100.0000000000, 8.7451295121e-01 );
        vals.put( "CsSP", new HashMap<>() );
        vals.get( "CsSP" ).put( -3.0000000000, -9.1113026188e-01 );
        vals.get( "CsSP" ).put( -1.0000000000, 2.8366218546e-01 );
        vals.get( "CsSP" ).put( 0.0000000000, -9.8999249660e-01 );
        vals.get( "CsSP" ).put( 1.0000000000, 5.4030230587e-01 );
        vals.get( "CsSP" ).put( 10.0000000000, -2.7516333805e-01 );
        vals.get( "CsSP" ).put( 100.0000000000, -6.0555186431e-01 );
        vals.put( "SnX2", new HashMap<>() );
        vals.get( "SnX2" ).put( -3.0000000000, 4.1211848524e-01 );
        vals.get( "SnX2" ).put( -1.0000000000, 8.4147098481e-01 );
        vals.get( "SnX2" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "SnX2" ).put( 1.0000000000, 8.4147098481e-01 );
        vals.get( "SnX2" ).put( 10.0000000000, -5.0636564111e-01 );
        vals.get( "SnX2" ).put( 100.0000000000, -3.0561438889e-01 );
        vals.put( "SnX", new HashMap<>() );
        vals.get( "SnX" ).put( -3.0000000000, -1.4112000806e-01 );
        vals.get( "SnX" ).put( -1.0000000000, -8.4147098481e-01 );
        vals.get( "SnX" ).put( 0.0000000000, 0.0000000000e+00 );
        vals.get( "SnX" ).put( 1.0000000000, 8.4147098481e-01 );
        vals.get( "SnX" ).put( 10.0000000000, -5.4402111089e-01 );
        vals.get( "SnX" ).put( 100.0000000000, -5.0636564111e-01 );
    }

    private final Map< String, String > strings = new HashMap<>();

    /**
     * MathFunctions' toString() results
     */
    private void initStrings() {
        strings.put( "P2C", "x" );
        strings.put( "S1C", "3.0" );
        strings.put( "S2C", "1.0" );
        strings.put( "Sn2X", "sin( ( x + x ) )" );
        strings.put( "P2XC", "( x * x * 3.0 )" );
        strings.put( "SnC", "1.2246467991473532E-16" );
        strings.put( "CsX", "cos( x )" );
        strings.put( "CsSn", "cos( sin( x ) )" );
        strings.put( "S2XC", "( x + x )" );
        strings.put( "C0", "0.0" );
        strings.put( "CsSP", "cos( ( ( x * -2.0 ) + 3.0 ) )" );
        strings.put( "C3", "3.0" );
        strings.put( "SnX2", "sin( ( x * x ) )" );
        strings.put( "PC", "3.0" );
        strings.put( "PX5C", "( x * 36.0 )" );
        strings.put( "SX5C", "x" );
        strings.put( "S2S", "( ( x + 3.0 ) + ( x + x ) )" );
        strings.put( "C_2", "-2.0" );
        strings.put( "X", "x" );
        strings.put( "PXC", "( x * 3.0 )" );
        strings.put( "SnX", "sin( x )" );
        strings.put( "S0", "0.0" );
        strings.put( "SXC", "( x + 3.0 )" );
        strings.put( "P2X0", "0.0" );
    }

    private final Map< String, String > derivs = new HashMap<>();

    private void initDerivs() {
        derivs.put( "P2C", "1.0" );
        derivs.put( "S1C", "0.0" );
        derivs.put( "S2C", "0.0" );
        derivs.put( "Sn2X", "( cos( ( x + x ) ) * 2.0 )" );
        derivs.put( "P2XC", "( ( x * 3.0 ) + ( x * 3.0 ) )" );
        derivs.put( "SnC", "0.0" );
        derivs.put( "CsX", "( sin( x ) * -1.0 )" );
        derivs.put( "CsSn", "( sin( sin( x ) ) * cos( x ) * -1.0 )" );
        derivs.put( "S2XC", "2.0" );
        derivs.put( "C0", "0.0" );
        derivs.put( "CsSP", "( sin( ( ( x * -2.0 ) + 3.0 ) ) * 2.0 )" );
        derivs.put( "C3", "0.0" );
        derivs.put( "SnX2", "( cos( ( x * x ) ) * ( x + x ) )" );
        derivs.put( "PC", "0.0" );
        derivs.put( "PX5C", "36.0" );
        derivs.put( "SX5C", "1.0" );
        derivs.put( "S2S", "3.0" );
        derivs.put( "C_2", "0.0" );
        derivs.put( "X", "1.0" );
        derivs.put( "PXC", "3.0" );
        derivs.put( "SnX", "cos( x )" );
        derivs.put( "S0", "0.0" );
        derivs.put( "SXC", "1.0" );
        derivs.put( "P2X0", "0.0" );
    }

    private final Map< String, LinkedList< Double > > integs = new HashMap<>();

    private void initIntegs() {
        integs.put( "P2C", new LinkedList<>() );
        integs.get( "P2C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "P2C" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "P2C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "P2C" ).add( -0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "S1C", new LinkedList<>() );
        integs.get( "S1C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "S1C" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "S1C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "S1C" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "S2C", new LinkedList<>() );
        integs.get( "S2C" ).add( 4.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "S2C" ).add( 6.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "S2C" ).add( 4.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "S2C" ).add( 6.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "Sn2X", new LinkedList<>() );
        integs.get( "Sn2X" ).add( 0.1643002545 ); // 1.000000 to 5.000000 for 5
        integs.get( "Sn2X" ).add( -0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "Sn2X" ).add( 0.2114612185 ); // 1.000000 to 5.000000 for 1000
        integs.get( "Sn2X" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "P2XC", new LinkedList<>() );
        integs.get( "P2XC" ).add( 125.2800000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "P2XC" ).add( 58.3200000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "P2XC" ).add( 124.0000320000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "P2XC" ).add( 54.0001080000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "SnC", new LinkedList<>() );
        integs.get( "SnC" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "SnC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "SnC" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "SnC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "CsX", new LinkedList<>() );
        integs.get( "CsX" ).add( -1.7033340931 ); // 1.000000 to 5.000000 for 5
        integs.get( "CsX" ).add( 0.2475294526 ); // -3.000000 to 3.000000 for 5
        integs.get( "CsX" ).add( -1.8003928589 ); // 1.000000 to 5.000000 for 1000
        integs.get( "CsX" ).add( 0.2822391694 ); // -3.000000 to 3.000000 for 1000
        integs.put( "CsSn", new LinkedList<>() );
        integs.get( "CsSn" ).add( 2.9309591866 ); // 1.000000 to 5.000000 for 5
        integs.get( "CsSn" ).add( 4.5646344325 ); // -3.000000 to 3.000000 for 5
        integs.get( "CsSn" ).add( 2.8958629580 ); // 1.000000 to 5.000000 for 1000
        integs.get( "CsSn" ).add( 4.5256359027 ); // -3.000000 to 3.000000 for 1000
        integs.put( "S2XC", new LinkedList<>() );
        integs.get( "S2XC" ).add( 24.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "S2XC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "S2XC" ).add( 24.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "S2XC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "C0", new LinkedList<>() );
        integs.get( "C0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "C0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "C0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "C0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "CsSP", new LinkedList<>() );
        integs.get( "CsSP" ).add( 0.5821295534 ); // 1.000000 to 5.000000 for 5
        integs.get( "CsSP" ).add( 0.1290526939 ); // -3.000000 to 3.000000 for 5
        integs.get( "CsSP" ).add( 0.7492247959 ); // 1.000000 to 5.000000 for 1000
        integs.get( "CsSP" ).add( 0.2766159272 ); // -3.000000 to 3.000000 for 1000
        integs.put( "C3", new LinkedList<>() );
        integs.get( "C3" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "C3" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "C3" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "C3" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "SnX2", new LinkedList<>() );
        integs.get( "SnX2" ).add( -0.8520740598 ); // 1.000000 to 5.000000 for 5
        integs.get( "SnX2" ).add( 1.1042037172 ); // -3.000000 to 3.000000 for 5
        integs.get( "SnX2" ).add( 0.2176607550 ); // 1.000000 to 5.000000 for 1000
        integs.get( "SnX2" ).add( 1.5470922524 ); // -3.000000 to 3.000000 for 1000
        integs.put( "PC", new LinkedList<>() );
        integs.get( "PC" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "PC" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "PC" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "PC" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "PX5C", new LinkedList<>() );
        integs.get( "PX5C" ).add( 432.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "PX5C" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "PX5C" ).add( 432.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "PX5C" ).add( -0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "SX5C", new LinkedList<>() );
        integs.get( "SX5C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "SX5C" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "SX5C" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "SX5C" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "S2S", new LinkedList<>() );
        integs.get( "S2S" ).add( 48.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "S2S" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "S2S" ).add( 48.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "S2S" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "C_2", new LinkedList<>() );
        integs.get( "C_2" ).add( -8.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "C_2" ).add( -12.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "C_2" ).add( -8.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "C_2" ).add( -12.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "X", new LinkedList<>() );
        integs.get( "X" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "X" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "X" ).add( 12.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "X" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "PXC", new LinkedList<>() );
        integs.get( "PXC" ).add( 36.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "PXC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "PXC" ).add( 36.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "PXC" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "SnX", new LinkedList<>() );
        integs.get( "SnX" ).add( 0.2428043867 ); // 1.000000 to 5.000000 for 5
        integs.get( "SnX" ).add( -0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "SnX" ).add( 0.2566397782 ); // 1.000000 to 5.000000 for 1000
        integs.get( "SnX" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "S0", new LinkedList<>() );
        integs.get( "S0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "S0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "S0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "S0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "SXC", new LinkedList<>() );
        integs.get( "SXC" ).add( 24.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "SXC" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "SXC" ).add( 24.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "SXC" ).add( 18.0000000000 ); // -3.000000 to 3.000000 for 1000
        integs.put( "P2X0", new LinkedList<>() );
        integs.get( "P2X0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 5
        integs.get( "P2X0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 5
        integs.get( "P2X0" ).add( 0.0000000000 ); // 1.000000 to 5.000000 for 1000
        integs.get( "P2X0" ).add( 0.0000000000 ); // -3.000000 to 3.000000 for 1000
    }

    private double errorTolerance( Double actual ) {
        return ( actual == 0.0 )?
                0.00001 : abs( 0.00001 * actual );
    }

    /**
     * Function that uses the data structures initialized in the init...
     * methods to see if the evaluation methods of the classes work
     * correctly.
     */
    private void testEvaluate() {
        for ( String funcName : funcsToTest ) {
            String message = funcName + ", applied to ";
            for ( double x : xVals ) {
                String arg = Double.toString( x );
                final Double actual = vals.get( funcName ).get( x );
                assertEquals(
                        actual,
                        funcs.get( funcName ).evaluate( x ),
                        errorTolerance( actual ), // tolerated error
                        message + arg
                );
            }
        }
    }

    private static final double[] bounds = { 1, 5, -3, 3 };

    private static final int[] cuts = { 5, 1000 };

    private void testIntegral() {
        for ( String funcName : funcsToTest ) {
            String message = funcName + ", integrated over ";
            Iterator< Double > actuals = integs.get( funcName ).iterator();
            for ( int cut : cuts ) {
                for ( int i = 0; i < bounds.length; i += 2 ) {
                    final Double actual = actuals.next();
                    final double lower = bounds[ i ];
                    final double upper = bounds[ i + 1 ];
                    assertEquals(
                            actual,
                            funcs.get( funcName )
                                 .integral( lower, upper, cut ),
                            errorTolerance( actual ), // tolerated error
                            message + lower + " to " + upper + ", " +
                            cut + " steps"
                    );
                }
            }
        }
    }

    @Test
    public void t1Part1() {
        initPart1();
        testEvaluate();
    }

    @Test
    public void t2Strings() {
        initPart1();
        initProducts();
        initTrig();
        initStrings();
        for ( Map.Entry< String, MathFunction > e : funcs.entrySet() ) {
            MathFunction f = e.getValue();
            final String fName = e.getKey();
            String expectedString = strings.get( fName );
            assertEquals(
                    expectedString, f.toString(), "toString() for " + fName
            );
        }
    }

    @Test
    public void t3Product() {
        initPart1();
        initProducts();
        testEvaluate();
    }

    @Test
    public void t4Trig() {
        initPart1();
        initProducts();
        initTrig();
        testEvaluate();
    }

    @Test
    public void t5Derivatives() {
        initPart1();
        initProducts();
        initTrig();
        initDerivs();
        for ( Map.Entry< String, MathFunction > e : funcs.entrySet() ) {
            MathFunction f = e.getValue();
            String expectedDerivString = derivs.get( e.getKey() );
            assertEquals(
                    expectedDerivString, f.derivative().toString(),
                    "derivative of " + f
            );
        }
    }

    @Test
    public void t6Integrals() {
        initPart1();
        initProducts();
        initTrig();
        initIntegs();
        testIntegral();
    }
}
