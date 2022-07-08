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

public class SolverReturn {

    /**
     * The boring constructor
     */
    public SolverReturn(){}

    /**
     * The Common solver method
     * @param origConfig The config that needs to be solved
     */
    public List<Configuration> solve(Configuration origConfig){

        List<Configuration> queue = new LinkedList<>();
        queue.add(origConfig);
        Map<Configuration, Configuration> pred = new HashMap<>();
        pred.put(origConfig, origConfig);
        Configuration end = null;

        // Finds its way to the last config from the first one
        while (!queue.isEmpty()){
            Configuration current = queue.remove(0);

            if (current.isSolution()){
                end = current;
                break;
            }

            for (Configuration nbr : current.getNeighbors()){
                if (!pred.containsKey(nbr)){
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
        return path;
    }
}
