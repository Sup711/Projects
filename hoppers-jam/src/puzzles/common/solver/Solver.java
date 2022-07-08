/**
 * Contains the common solver
 *
 * @author: Eliot Nagy (epn2643)
 */

package puzzles.common.solver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solver {

    /**
     * The boring constructor
     */
    public Solver(){}

    /**
     * The Common solver method
     * @param origConfig The config that needs to be solved
     */
    public void solve(Configuration origConfig){

        List<Configuration> queue = new LinkedList<>();
        queue.add(origConfig);
        Map<Configuration, Configuration> pred = new HashMap<>();
        pred.put(origConfig, origConfig);
        Configuration end = null;

        int totalConfig = 1;
        int totalUnique = 1;

        // Finds its way to the last config from the first one
        while (!queue.isEmpty()){
            Configuration current = queue.remove(0);

            if (current.isSolution()){
                end = current;
                break;
            }

            for (Configuration nbr : current.getNeighbors()){
                totalConfig += 1;
                if (!pred.containsKey(nbr)){
                    totalUnique += 1;
                    pred.put(nbr, current);
                    queue.add(nbr);
                }
            }
        }

        // Constructs the path
        List<Configuration> path = new LinkedList<>();
        if (pred.containsKey(end)){
            Configuration curr = end;
            while (!origConfig.equals(curr)){
                path.add(0, curr);
                curr = pred.get(curr);
            }
            path.add(0, origConfig);
        }

        // Prints out the path and configs information
        System.out.println("Total Configs: " + totalConfig);
        System.out.println("Total Unique Configs: " + totalUnique);
        if (path.size() == 0){
            System.out.println("No Path Found");
        }
        else{
            for (int i = 0; i < path.size(); i++) {
                System.out.println("Step " + i + ": ");
                System.out.println(path.get(i).toString());
            }
        }
    }
}
