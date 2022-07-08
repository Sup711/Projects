package puzzles.jam.solver;

import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

/**
 * Main class to call to solve Jam game
 * @author Suzayet Hoque, sh7354@rit.edu
 */


public class Jam {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        }
        JamConfig jc = new JamConfig(args[0]);
        System.out.println(jc);
        Solver solve = new Solver();
        solve.solve(jc);
    }
}