/**
 * The driving class that is used to set up the solver
 *
 * @author: Eliot Nagy (epn2643)
 */

package puzzles.strings;
import puzzles.common.solver.Solver;

/**
 * The main method that sets up and then solves the puzzle
 */
public class Strings {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        }
        else {
            StringsConfig temp = new StringsConfig(args[0], args[1]);
            System.out.println("Start: " + args[0] + ", End: " + args[1]);
            Solver solve = new Solver();
            solve.solve(temp);
        }
    }
}
