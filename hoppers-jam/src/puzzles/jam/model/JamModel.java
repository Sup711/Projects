package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.SolverReturn;

import java.util.*;

/**
 * Model for Jam
 * @author Suzayet Hoque, sh7354@rit.edu
 */

public class JamModel {
    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    private String filename;

    /**
     * Possible game states
     */
    public enum GameState{ONGOING, WON, LOST, CHEAT,
        ILLEGAL_MOVE, SELECTION1BAD, SELECTION2BAD, LOAD, SELECTED1, SELECTED2, INDEX_ERROR}


    /**
     * Enummap for print statements
     */
    private static final EnumMap< JamModel.GameState, String > STATE_MSGS =
            new EnumMap<>( Map.of(
                    JamModel.GameState.WON, "You won!",
                    JamModel.GameState.ONGOING, "Make a guess!",
                    JamModel.GameState.ILLEGAL_MOVE, "Illegal move."
            ) );

    /** used to check select inputs */
    private int[] select1 = new int[]{-1, -1};


    /** Gamestate of the game */
    private GameState gameState;

    /** the current configuration */
    private JamConfig currentConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }


    /**
     * gets current config
     * @return jamconfig
     */
    public JamConfig getCurrentConfig(){
        return this.currentConfig;
    }


    /**
     * shows next solution to the game
     */
    public void hint(){
        if(gameState == GameState.ONGOING){
            SolverReturn solution = new SolverReturn();
            List<Configuration> path =   solution.solve(currentConfig);
            currentConfig= (JamConfig) path.get(1);
            gameState = GameState.CHEAT;
            this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        }

    }

    /**
     * Loads a game
     * @param file game file
     */
    public void load(String file){
        currentConfig = new JamConfig(file);
        newGame(file);
        this.gameState = JamModel.GameState.LOAD;
        this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        // Checks for win
        if (this.currentConfig.isSolution()){
            this.gameState = JamModel.GameState.WON;
            this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        }
    }


    /**
     * Alert observers of the new game and initalize select
     * @param mandatedFile game file
     */
    public void newGame(String mandatedFile){
        this.filename = mandatedFile;
        this.currentConfig = new JamConfig(mandatedFile);
        this.gameState = JamModel.GameState.LOAD;
        this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        this.gameState = JamModel.GameState.ONGOING;
        this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        select1[0] = -1;
        select1[1] = -1;
    }


    /**
     * resets the game to first move
     */
    public void reset(){
        currentConfig = new JamConfig(filename);
        this.gameState= JamModel.GameState.ONGOING;
        this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        select1[0]=0;
        select1[1]=0;
    }

    /**
     * Select the cars to move
     * Select the places were to move car
     * @param iStr Row
     * @param jStr Col
     */
    public void select(String iStr, String jStr){
        ArrayList<JamConfig> validNeighbors = new ArrayList<>();
        Collection<Configuration> tempCol = currentConfig.getNeighbors();

        for (Configuration temp : tempCol){
            validNeighbors.add((JamConfig) temp);
        }

        int i = Integer.parseInt(iStr);
        int j = Integer.parseInt(jStr);


        boolean valid= false;


        if (i < 0 || i >= currentConfig.getROWS() || j < 0 || j >= currentConfig.getCOLS()){
            this.gameState = JamModel.GameState.INDEX_ERROR;
            this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
            select1[0] = -1;
            select1[1] = -1;
            return;
        }


        //First selection (starting position)
        if (select1[0] == -1){
            // Invalid starting position
            if (this.currentConfig.getBoard()[i][j] == this.currentConfig.BLANK ||
                    i > this.currentConfig.getROWS() || j > this.currentConfig.getCOLS()){


                this.gameState = JamModel.GameState.SELECTION1BAD;
                this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
            }
            // Valid starting position
            else {
                select1[0] = i;
                select1[1] = j;
                this.gameState = JamModel.GameState.SELECTED1;
                this.alertObservers(iStr + jStr);
            }
        }
        //Second selection (jumping to position)
        else {
            JamConfig next=   currentConfig.JamConfig (select1[0], select1[1]);

            for (JamConfig validNext : validNeighbors) {
                // Valid jump
//                if (validNext.getBoard()[i][j] == this.currentConfig.BLANK &&
//                        i>0 && i<this.currentConfig.getROWS() && j>0 && j< this.currentConfig.getCOLS())
                    if(next.equals(validNext))    {
                    this.currentConfig = validNext;
                    this.gameState = JamModel.GameState.SELECTED2;
                    this.alertObservers(select1[0] + String.valueOf(select1[1]) + iStr + jStr);
                    this.gameState = JamModel.GameState.ONGOING;
                    this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
                    valid = true;
                    break;
                    // Invalid jump
                }

            }
            if(!valid){
                this.gameState = JamModel.GameState.SELECTION2BAD;
                this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
            }

            select1[0] = -1;
            select1[1] = -1;
        }
        // Checks to see if you won
        if (this.currentConfig.isSolution()){
            this.gameState = JamModel.GameState.WON;
            this.alertObservers(JamModel.STATE_MSGS.get(this.gameState));
        }
    }



    // ******** Queries, for View ********

    /**
     * How's the game going?
     * (May not be needed since the game state is sent as client data.)
     *
     * @return the current state
     */
    public GameState gameState() {
        return this.gameState;
    }


    /**
     * gets filename
     * @return string of filename
     */
    public String getFilename(){return this.filename;}
}
