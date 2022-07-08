/**
 * Contains all the methods and fields related to a jam config
 *
 * @author: Suzayet Hoque,sh7354@rit.edu
 */

package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class JamConfig implements Configuration {
    private Character[][] board;
    public static char BLANK = '.';
    public static char X_CAR = 'X';

    private int ROWS;
    private int COLS;

    public int numofCars;

    private HashMap<Character, Boolean> cardirection;
    // if its true, the car goes up and down
    // if its false, the car goes left and right


    /**
     * The constructor for this class that sets the base values
     */
    public JamConfig(String filename) {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // read first line: rows cols
            String[] fields = in.readLine().split("\\s+");
            ROWS = Integer.parseInt(fields[0]);
            COLS = Integer.parseInt(fields[1]);

            numofCars = Integer.parseInt(in.readLine());

            board = new Character[ROWS][COLS];

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    board[i][j] = BLANK;
                }
            }

            cardirection = new HashMap<>();

            String line;
            while ((line = in.readLine()) != null) {
                String[] car = line.split("\\s+");
                char name = (car[0].charAt(0));
                int startrow = Integer.parseInt(car[1]);


                int startcol = Integer.parseInt(car[2]);


                int endrow = Integer.parseInt(car[3]);


                int endcol = Integer.parseInt(car[4]);


                for (int i = startrow; i < endrow + 1; i++) {
                    for (int j = startcol; j < endcol + 1; j++) {
                        board[i][j] = name;
                    }
                }
                if (startrow == endrow) {
                    cardirection.put(name, false);
                }
                if (startcol == endcol) {
                    cardirection.put(name, true);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JamConfig(JamConfig other, int oldrow, int oldcol, int newrow, int newcol, char car) {

        this.ROWS = other.ROWS;
        this.COLS = other.COLS;

        this.board = new Character[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                this.board[i][j] = other.board[i][j];
            }
        }

        this.cardirection = other.cardirection;


        this.board[oldrow][oldcol] = BLANK;
        this.board[newrow][newcol] = car;


    }

    /**
     * Checks to see if the game is over and returns true if it is
     *
     * @return The boolean representing whether the game is over or not
     */
    @Override
    public boolean isSolution() {
        boolean solution = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == X_CAR) {
                    if (j == COLS - 1) {
                        solution = true;
                        break;
                    }
                }
            }
        }


        return solution;
    }

    /**
     * Gets all the next possible configs (neighbors) for the configs following the rules of the game
     *
     * @return All the possible next configs
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                char cursor = board[i][j];
                if (cursor == BLANK) {
                    continue;
                }
                boolean direction = cardirection.get(cursor);
                if (direction) { //gets up and down neighbors
                    if (i == 0) { //checks for the first row
                        int newrow = i;
                        char temp = board[newrow][j];
                        while (temp == cursor) {
                            newrow++; //checking down neighbors
                            if (newrow >= ROWS) {
                                newrow--;
                                break;
                            }
                            temp = board[newrow][j];
                        }
                        if (temp == BLANK) {
                            neighbors.add(new JamConfig(this, i, j, newrow, j, cursor));
                        }
                    } else if (i == ROWS - 1) { //Checks for the last row and goes upwardss
                        int newrow = i;
                        char temp = board[newrow][j];
                        while (temp == cursor) {
                            newrow--; //checking down neighbors
                            if (newrow <= 0) {
                                newrow++;
                                break;
                            }
                            temp = board[newrow][j];
                        }
                        if (temp == BLANK) {
                            neighbors.add(new JamConfig(this, i, j, newrow, j, cursor));
                        }
                    } else {
                        if (BLANK == board[i - 1][j]) { //checks if the one above is blank
                            int bottom = i;
                            for (int start = bottom + 1; start < ROWS; start++) { // Gets the end of the car that is at the bottom
                                if (cursor != board[start][j]) {
                                    break;
                                }
                                bottom++;
                            }
                            neighbors.add(new JamConfig(this, bottom, j, i - 1, j, cursor));

                        } else if (BLANK == board[i + 1][j]) { //checks if the one below is blank
                            int top = i;
                            for (int start = top - 1; start >= 0; start--) { //Gets the end of the car that is at the top
                                if (cursor != board[start][j]) {
                                    break;
                                }
                                top--;
                            }
                            neighbors.add(new JamConfig(this, top, j, i + 1, j, cursor));

                        }
                    }
                } else {
                    if (j == 0) { //checks for the first col
                        int newcol = j;
                        char temp = board[i][newcol]; // checks left and right
                        while (temp == cursor) {
                            newcol++; //checking right neighbors
                            if (newcol >= COLS) {
                                newcol--;
                                break;
                            }
                            temp = board[i][newcol];
                        }
                        if (temp == BLANK) {
                            neighbors.add(new JamConfig(this, i, j, i, newcol, cursor));
                        }
                    } else if (j == COLS - 1) { //Checks for the last row and goes leftwards
                        int newcol = j;
                        char temp = board[i][newcol];
                        while (temp == cursor) {
                            newcol--; //checking left neighbors
                            if (newcol <= 0) {
                                newcol++;
                                break;
                            }
                            temp = board[i][newcol];
                        }
                        if (temp == BLANK) {
                            neighbors.add(new JamConfig(this, i, j, i, newcol, cursor));
                        }
                    } else {
                        if (BLANK == board[i][j - 1]) {// checks if left space is blank to move car left
                            int replaceright = j;
                            // checks left and right
                            for (int start = j + 1; start < COLS; start++) {
                                if (cursor != board[i][start]) {
                                    break;
                                }
                                replaceright++;
                            }


                            neighbors.add(new JamConfig(this, i, replaceright, i, j - 1, cursor));

                        } else if (BLANK == board[i][j + 1]) { //checks if the right space is blank to move car right
                            int replaceleft = j;

                            for (int start = j - 1; start >= 0; start--) {
                                if (cursor != board[i][start]) {
                                    break;
                                }
                                replaceleft--;
                            }
                            neighbors.add(new JamConfig(this, i, replaceleft, i, j + 1, cursor));

                        }
                    }
                }

            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        System.out.println();
        StringBuilder returner = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                returner.append(board[i][j]);
                returner.append(" ");
            }
            returner.append("\n");
        }
        return returner.toString();
    }

    /**
     * Compares two objects to see if they are equal
     *
     * @param other The other object to compare to
     * @return A boolean (true if equal)
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof JamConfig) {
            return Arrays.deepEquals(this.board, ((JamConfig) other).board);
        }

        return false;
    }

    /**
     * Gets the hashCode value for the config
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }


    public int getROWS() {
        return this.ROWS;
    }

    public int getCOLS() {
        return this.COLS;
    }


    public Character[][] getBoard() {
        return this.board;
    }


    public JamConfig JamConfig(int i, int j) {
        char cursor = board[i][j];

        boolean direction = cardirection.get(cursor);
        if (direction) { //gets up and down neighbors
            if (i == 0) { //checks for the first row
                int newrow = i;
                char temp = board[newrow][j];
                while (temp == cursor) {
                    newrow++; //checking down neighbors
                    if (newrow >= ROWS) {
                        newrow--;
                        break;
                    }
                    temp = board[newrow][j];
                }
                if (temp == BLANK) {
                    return (new JamConfig(this, i, j, newrow, j, cursor));
                }
            } else if (i == ROWS - 1) { //Checks for the last row and goes upwardss
                int newrow = i;
                char temp = board[newrow][j];
                while (temp == cursor) {
                    newrow--; //checking down neighbors
                    if (newrow <= 0) {
                        newrow++;
                        break;
                    }
                    temp = board[newrow][j];
                }
                if (temp == BLANK) {
                    return (new JamConfig(this, i, j, newrow, j, cursor));
                }
            } else {
                if (BLANK == board[i - 1][j]) { //checks if the one above is blank
                    int bottom = i;
                    for (int start = bottom + 1; start < ROWS; start++) { // Gets the end of the car that is at the bottom
                        if (cursor != board[start][j]) {
                            break;
                        }
                        bottom++;
                    }
                    return (new JamConfig(this, bottom, j, i - 1, j, cursor));

                } else if (BLANK == board[i + 1][j]) { //checks if the one below is blank
                    int top = i;
                    for (int start = top - 1; start >= 0; start--) { //Gets the end of the car that is at the top
                        if (cursor != board[start][j]) {
                            break;
                        }
                        top--;
                    }
                    return (new JamConfig(this, top, j, i + 1, j, cursor));

                }
            }
        } else {
            if (j == 0) { //checks for the first col
                int newcol = j;
                char temp = board[i][newcol]; // checks left and right
                while (temp == cursor) {
                    newcol++; //checking right neighbors
                    if (newcol >= COLS) {
                        newcol--;
                        break;
                    }
                    temp = board[i][newcol];
                }
                if (temp == BLANK) {
                    return (new JamConfig(this, i, j, i, newcol, cursor));
                }
            } else if (j == COLS - 1) { //Checks for the last row and goes leftwards
                int newcol = j;
                char temp = board[i][newcol];
                while (temp == cursor) {
                    newcol--; //checking left neighbors
                    if (newcol <= 0) {
                        newcol++;
                        break;
                    }
                    temp = board[i][newcol];
                }
                if (temp == BLANK) {
                    return (new JamConfig(this, i, j, i, newcol, cursor));
                }
            } else {
                if (BLANK == board[i][j - 1]) {// checks if left space is blank to move car left
                    int replaceright = j;
                    // checks left and right
                    for (int start = j + 1; start < COLS; start++) {
                        if (cursor != board[i][start]) {
                            break;
                        }
                        replaceright++;
                    }


                    return new JamConfig(this, i, replaceright, i, j - 1, cursor);

                } else if (BLANK == board[i][j + 1]) { //checks if the right space is blank to move car right
                    int replaceleft = j;

                    for (int start = j - 1; start >= 0; start--) {
                        if (cursor != board[i][start]) {
                            break;
                        }
                        replaceleft--;
                    }
                    return new JamConfig(this, i, replaceleft, i, j + 1, cursor);

                }
            }

        }

        return null;
    }
}