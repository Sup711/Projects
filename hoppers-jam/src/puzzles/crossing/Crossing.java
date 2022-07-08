/**
 * The driving class that is used to set up the solver
 *
 * @author: Eliot Nagy (epn2643)
 */

package puzzles.crossing;
import puzzles.common.solver.Solver;

/**
 * The main method that sets up and then solves the puzzle
 */
public class Crossing {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        }
        else {
            System.out.println("Pups: " + args[0] + ", Wolves: " + args[1]);
            CrossingConfig temp = new CrossingConfig(CrossingConfig.LEFT_BOAT, Integer.parseInt(args[0]), Integer.parseInt(args[1]), 0, 0);
            Solver solve = new Solver();
            solve.solve(temp);
        }
    }
}
