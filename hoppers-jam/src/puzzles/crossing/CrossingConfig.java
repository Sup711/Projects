/**
 * Contains all the methods and fields related to a crossing config
 *
 * @author: Eliot Nagy (epn2643)
 */

package puzzles.crossing;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class CrossingConfig implements Configuration {

    /** Represents the boat on the left side */
    public static final String LEFT_BOAT = "LEFT";
    /** Represents the boat on the right side */
    public static final String RIGHT_BOAT = "RIGHT";

    /** Stores which side the boat is on */
    private final String boatSide;
    /** Stores how many pups are on the left side */
    private final int pupLeft;
    /** Stores how many pups are on the right side */
    private final int pupRight;
    /** Stores how many wolves are on the left side */
    private final int wolfLeft;
    /** Stores how many wolves are on the wight side */
    private final int wolfRight;

    /**
     * The constructor which sets the values for the class
     * @param side The side the boat is on
     * @param pupLeft The number of pups on the left side
     * @param wolfLeft The number of wolves on the left side
     * @param pupRight The number of pups on the right side
     * @param wolfRight The number of pups on the right side
     */
    public CrossingConfig(String side, int pupLeft, int wolfLeft, int pupRight, int wolfRight){
        this.boatSide = side;
        this.pupLeft = pupLeft;
        this.wolfLeft = wolfLeft;
        this.pupRight = pupRight;
        this.wolfRight = wolfRight;
    }

    /**
     * Checks if it is the solution is valid (all animals are on the right)
     * @return A boolean (true if is valid)
     */
    @Override
    public boolean isSolution() {
        return pupLeft + wolfLeft == 0;
    }

    /**
     * Gets all the neighbors for the Crossing configs following the rules of the game
     * @return All the possible next configs
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();

        // All the next configs if the boat is on the left side
        if (boatSide.equals(LEFT_BOAT)){
            // One wolf moves over
            if (wolfLeft >= 1){
                neighbors.add(new CrossingConfig(RIGHT_BOAT, pupLeft, wolfLeft-1, pupRight, wolfRight+1));
            }
            // One pup moves over
            if (pupLeft >= 1){
                neighbors.add(new CrossingConfig(RIGHT_BOAT, pupLeft-1, wolfLeft, pupRight+1, wolfRight));
            }
            // Two pups move over
            if (pupLeft >= 2){
                neighbors.add(new CrossingConfig(RIGHT_BOAT, pupLeft-2, wolfLeft, pupRight+2, wolfRight));
            }
        }

        // All the next configs if the boat is on the right side
        else {
            // One wolf moves over
            if (wolfRight >= 1){
                neighbors.add(new CrossingConfig(LEFT_BOAT, pupLeft, wolfLeft+1, pupRight, wolfRight-1));
            }
            // One pup moves over
            if (pupRight >= 1){
                neighbors.add(new CrossingConfig(LEFT_BOAT, pupLeft+1, wolfLeft, pupRight-1, wolfRight));
            }
            // Two pups move over
            if (pupRight >= 2){
                neighbors.add(new CrossingConfig(LEFT_BOAT, pupLeft + 2, wolfLeft, pupRight - 2, wolfRight));
            }
        }
        return neighbors;
    }

    /**
     * The toString for the config which returns pups, wolves, and boats location
     * @return The string
     */
    @Override
    public String toString(){
        if (boatSide.equals(LEFT_BOAT)){
            return "(BOAT) left=[" + pupLeft + ", " + wolfLeft + "], right=[" + pupRight + ", " + wolfRight + "]";
        }
        else {
            return "       left=[" + pupLeft + ", " + wolfLeft + "], right=[" + pupRight + ", " + wolfRight + "] (BOAT)";
        }
    }

    /**
     * Compares two objects to see if they are equal
     * @param other The other object to compare to
     * @return A boolean (true if equal)
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof CrossingConfig otherConfig){
            return  (this.boatSide.equals(otherConfig.boatSide)) && (this.pupLeft == otherConfig.pupLeft) && (this.wolfLeft == otherConfig.wolfLeft) &&
                    (this.pupRight == otherConfig.pupRight) && (this.wolfRight == otherConfig.wolfRight);
        }
        return false;
    }

    /**
     * Gets the hashCode value for the config
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return boatSide.hashCode() + pupLeft + wolfLeft + pupRight + pupRight;
    }

}
