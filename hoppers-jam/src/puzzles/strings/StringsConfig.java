/**
 * Contains all the methods and fields related to a string config
 *
 * @author: Eliot Nagy (epn2643)
 */

package puzzles.strings;
import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class StringsConfig implements Configuration {

    /** Stores the starting string */
    private final String first;
    /** Stores the string you're looking for*/
    private final String last;

    /**
     * The constructor for StringConfig that sets the values
     * @param first The starting string
     * @param last The string you're looking for
     */
    public StringsConfig(String first, String last){
        this.first = first;
        this.last = last;
    }

    /**
     * Checks if the current solution is valid by comparing the start and end value
     * @return A boolean (true if valid, false if not)
     */
    @Override
    public boolean isSolution() {
        return this.first.equals(this.last);
    }

    /**
     * Generates all the next configs based on the current config
     * @return All the next configs
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();

        for (int i = 0; i < this.first.length(); i++) {
            char temp = first.charAt(i);

            if (i == 0){
                // Loops A around
                if (temp == 'A'){
                    neighbors.add(new StringsConfig("B" + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig("Z" + first.substring(i+1), this.last));
                }
                // Loops B around
                else if (temp == 'Z'){
                    neighbors.add(new StringsConfig("A" + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig("Y" + first.substring(i+1), this.last));
                }
                // All others cases
                else {
                    neighbors.add(new StringsConfig((char) (temp+1) + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig((char) (temp-1) + first.substring(i+1), this.last));
                }
            }

            else {
                // Loops A around
                if (temp == 'A'){
                    neighbors.add(new StringsConfig(first.substring(0, i) + "B" + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig(first.substring(0, i) + "Z" + first.substring(i+1), this.last));
                }
                // Loops B around
                else if (temp == 'Z'){
                    neighbors.add(new StringsConfig(first.substring(0, i) + "A" + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig(first.substring(0, i) + "Y" + first.substring(i+1), this.last));

                }
                // All others cases
                else {
                    neighbors.add(new StringsConfig(first.substring(0, i) + (char) (temp+1) + first.substring(i+1), this.last));
                    neighbors.add(new StringsConfig(first.substring(0, i) + (char) (temp-1) + first.substring(i+1), this.last));
                }
            }
        }
        return neighbors;
    }

    /**
     * Gets the hashCode value for the config
     * @return the hash code
     */
    @Override
    public int hashCode(){
        int code = 0;
        for (int i = 0; i < this.first.length(); i++) {
            code += ((int) this.first.charAt(i));
        }
        return code;
    }

    /**
     * The toString for the config which returns the first value
     * @return The string
     */
    @Override
    public String toString(){
        return first;
    }

    /**
     * Compares two objects to see if they are equal
     * @param other The other object to compare to
     * @return A boolean (true if equal)
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof StringsConfig otherConfig){
            return this.first.equals(otherConfig.first) && this.last.equals(otherConfig.last);
        }
        return false;
    }

}
