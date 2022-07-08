package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Contains the hopper config
 *
 * @author: Eliot Nagy (epn2643)
 */
public class HoppersConfig implements Configuration{

        /** The board that holds the pieces */
        private char[][] board;
        /** The number of columns*/
        private int numCols;
        /** The number of rows */
        private int numRows;
        /** The illegal character */
        public static final char ILLEGAL = '*';
        /** The open space character */
        public static final char OPEN = '.';
        /** The red frog character*/
        public static final char REDFROG = 'R';
        /** The green frog character */
        public static final char GREENFROG = 'G';

        /**
         * The constructor for this class that sets the base values
         */
        public HoppersConfig(String fileName){
            try(BufferedReader in = new BufferedReader(new FileReader(fileName))){
                String[] splitLine = in.readLine().split("\\s+");
                this.numRows = Integer.parseInt(splitLine[0]);
                this.numCols = Integer.parseInt(splitLine[1]);
                board = new char[numRows][numCols];

                String line;
                int row = 0;
                while ((line = in.readLine()) != null) {
                    if (line.equals("")){
                        break;
                    }
                    splitLine = line.split("\\s+");
                    for (int i = 0; i < numCols; i++) {
                        board[row][i] = splitLine[i].charAt(0);
                    }
                    ++row;
                }
            }
            catch (IOException ioe){
                ioe.printStackTrace();
            }
        }

        /**
         * The copy constructor for making a new config
         * @param other the config you are modifying
         * @param position where you are moving the piece to
         * @param i the current position of the piece
         * @param j the current position of the piece
         */
        public HoppersConfig(HoppersConfig other, String position, int i, int j){
            this.numCols = other.numCols;
            this.numRows = other.numRows;
            this.board = new char[other.numRows][other.numCols];

            for (int k = 0; k < numRows; k++) {
                System.arraycopy(other.board[k], 0, this.board[k], 0, numCols);
            }

            switch (position) {
                case "Left" -> {
                    board[i][j - 4] = board[i][j];
                    board[i][j - 2] = OPEN;
                    board[i][j] = OPEN;
                }
                case "Right" -> {
                    board[i][j + 4] = board[i][j];
                    board[i][j + 2] = OPEN;
                    board[i][j] = OPEN;
                }
                case "Up" -> {
                    board[i - 4][j] = board[i][j];
                    board[i - 2][j] = OPEN;
                    board[i][j] = OPEN;
                }
                case "Down" -> {
                    board[i + 4][j] = board[i][j];
                    board[i + 2][j] = OPEN;
                    board[i][j] = OPEN;
                }
                case "UpLeft" -> {
                    board[i - 2][j - 2] = board[i][j];
                    board[i - 1][j - 1] = OPEN;
                    board[i][j] = OPEN;
                }
                case "UpRight" -> {
                    board[i - 2][j + 2] = board[i][j];
                    board[i - 1][j + 1] = OPEN;
                    board[i][j] = OPEN;
                }
                case "DownLeft" -> {
                    board[i + 2][j - 2] = board[i][j];
                    board[i + 1][j - 1] = OPEN;
                    board[i][j] = OPEN;
                }
                // Down Right
                default -> {
                    board[i + 2][j + 2] = board[i][j];
                    board[i + 1][j + 1] = OPEN;
                    board[i][j] = OPEN;
                }
            }
        }

        /**
         * Checks to see if the game is over and returns true if it is
         * @return The boolean representing whether the game is over or not
         */
        @Override
        public boolean isSolution() {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (board[i][j] == GREENFROG){
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * Gets all the next possible configs (neighbors) for the configs following the rules of the game
         * @return All the possible next configs
         */
        @Override
        public Collection<Configuration> getNeighbors() {
            List<Configuration> successors = new ArrayList<>();

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (board[i][j] == REDFROG || board[i][j] == GREENFROG) {
                        if (i % 2 == 0){
                            // Checks left
                            if (j - 4 >= 0 && board[i][j-2] == GREENFROG && board[i][j-4] == OPEN){
                                successors.add(new HoppersConfig(this, "Left", i, j));
                            }
                            // Checks right
                            if (j + 4 < numCols && board[i][j+2] == GREENFROG && board[i][j+4] == OPEN){
                                successors.add(new HoppersConfig(this, "Right", i, j));
                            }
                            // Checks up
                            if (i - 4 >= 0 && board[i-2][j] == GREENFROG && board[i-4][j] == OPEN){
                                successors.add(new HoppersConfig(this, "Up", i, j));
                            }
                            // Checks down
                            if (i + 4 < numRows && board[i+2][j] == GREENFROG && board[i+4][j] == OPEN){
                                successors.add(new HoppersConfig(this, "Down", i, j));
                            }
                        }
                        // Checks up left
                        if (i - 2 >= 0 && j - 2 >= 0 && board[i-1][j-1] == GREENFROG && board[i-2][j-2] == OPEN){
                            successors.add(new HoppersConfig(this, "UpLeft", i, j));
                        }
                        // Checks up right
                        if (i - 2 >= 0 && j + 2 < numCols && board[i-1][j+1] == GREENFROG && board[i-2][j+2] == OPEN){
                            successors.add(new HoppersConfig(this, "UpRight", i, j));
                        }
                        // Checks down left
                        if (i + 2 < numRows && j - 2 >= 0 && board[i+1][j-1] == GREENFROG && board[i+2][j-2] == OPEN){
                            successors.add(new HoppersConfig(this, "DownLeft", i, j));
                        }
                        // Checks down right
                        if (i + 2 < numRows && j + 2 < numCols && board[i+1][j+1] == GREENFROG && board[i+2][j+2] == OPEN){
                            successors.add(new HoppersConfig(this, "DownRight", i, j));
                        }
                    }
                }
            }
            return successors;
        }

        /**
         * The toString method for hoppers
         * @return The string
         */
        @Override
        public String toString(){
            StringBuilder returner = new StringBuilder();
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    returner.append(board[i][j]);
                    returner.append(" ");
                }
                returner.append("\n");
            }
            return returner.toString();
        }

        /**
         * Compares two objects to see if they are equal
         * @param other The other object to compare to
         * @return A boolean (true if equal)
         */
        @Override
        public boolean equals(Object other){
            if (other instanceof HoppersConfig){
                return Arrays.deepEquals(this.board, ((HoppersConfig) other).board);
            }
            return false;
        }

        /**
         * Gets the hashCode value for the config
         * @return the hash code
         */
        @Override
        public int hashCode(){
            return Arrays.deepHashCode(board);
        }

    public char[][] getBoard() {
        return board;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }
}