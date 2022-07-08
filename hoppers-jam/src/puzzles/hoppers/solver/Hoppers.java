package puzzles.hoppers.solver;

import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

public class Hoppers {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        HoppersConfig hp = new HoppersConfig(args[0]);
        System.out.println("Filename: " + args[0]);
        System.out.println(hp);
        System.out.println();
        Solver solve = new Solver();
        solve.solve(hp);
    }
}
