package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.common.solver.SolverReturn;
import puzzles.jam.ptui.ConsoleApplication;

import java.util.*;

public class HoppersModel {

    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();
    /** the current configuration */
    public HoppersConfig currentConfig;
    /** the different game states */
    public enum GameState{ONGOING, WON, LOST, CHEAT, ILLEGAL_MOVE, SELECTION1BAD, SELECTION2BAD,
                          LOAD, SELECTED1, SELECTED2, CHEATED_WIN, INDEX_ERROR, RESET}
    /** The current game state */
    private GameState gameState;

    /** The first selected piece */
    private int[] select1 = new int[]{-1, -1};

    /** The filename being loaded */
    private String fileName;

    /**
     * The view calls this to add itself as an observer.
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
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

    /** A hashmap that contains messages for each game state but honesty this is never actually
     *  used, but it would take too long to remove it*/
    private static final EnumMap< HoppersModel.GameState, String > STATE_MSGS =
            new EnumMap<>( Map.of(
                    HoppersModel.GameState.WON, "You won!",
                    HoppersModel.GameState.LOST, "You lost 😥.",
                    HoppersModel.GameState.ONGOING, "Make a guess!",
                    HoppersModel.GameState.ILLEGAL_MOVE, "Illegal word.",
                    HoppersModel.GameState.CHEAT, "Cheating") );

//-------------------------------------------------------------------------------------------------

    /**
     * Creates a new game
     * @param mandatedFile the file to be used
     */
    public void newGame(String mandatedFile){
        this.fileName = mandatedFile;
        this.currentConfig = new HoppersConfig(mandatedFile);
        this.gameState = GameState.LOAD;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        this.gameState = GameState.ONGOING;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        select1[0] = -1;
        select1[1] = -1;
    }

    /**
     * Used to load a new file when the load button is pressed
     * @param file The file the user selected
     */
    public void load(String file){
        currentConfig = new HoppersConfig(file);
        this.fileName = file;
        newGame(file);
        this.gameState = GameState.LOAD;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        select1[0] = -1;
        select1[1] = -1;
        // Checks for win
        if (this.currentConfig.isSolution()){
            this.gameState = GameState.WON;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        }
    }

    /**
     * The method used when the use selected a space
     * @param iStr The position of the space
     * @param jStr The position of the space
     */
    public void select(String iStr, String jStr){
        ArrayList<HoppersConfig> validNeighbors = new ArrayList<>();
        Collection<Configuration> tempCol = currentConfig.getNeighbors();

        for (Configuration temp : tempCol){
            validNeighbors.add((HoppersConfig) temp);
        }

        boolean end = true;
        int i = Integer.parseInt(iStr);
        int j = Integer.parseInt(jStr);

        if (i < 0 || i >= currentConfig.getNumRows() || j < 0 || j >= currentConfig.getNumCols()){
            this.gameState = GameState.INDEX_ERROR;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
            select1[0] = -1;
            select1[1] = -1;
            return;
        }

        //First selection (starting position)
        if (select1[0] == -1){
            // Invalid starting position
            if (this.currentConfig.getBoard()[i][j] != HoppersConfig.GREENFROG && this.currentConfig.getBoard()[i][j] != HoppersConfig.REDFROG){
                this.gameState = GameState.SELECTION1BAD;
                this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
            }
            // Valid starting position
            else {
                select1[0] = i;
                select1[1] = j;
                this.gameState = GameState.SELECTED1;
                this.alertObservers(iStr + jStr);
            }
        }
        //Second selection (jumping to position)
        else {
            for (HoppersConfig validNext : validNeighbors) {
                // Valid jump
                if (validNext.getBoard()[select1[0]][select1[1]] == HoppersConfig.OPEN
                    && (validNext.getBoard()[i][j] == HoppersConfig.GREENFROG || validNext.getBoard()[i][j] == HoppersConfig.REDFROG)
                    && (currentConfig.getBoard()[i][j] != HoppersConfig.GREENFROG && currentConfig.getBoard()[i][j] != HoppersConfig.REDFROG)){
                    this.currentConfig = validNext;
                    this.gameState = GameState.SELECTED2;
                    this.alertObservers(String.valueOf(select1[0]) + String.valueOf(select1[1]) + iStr + jStr);
                    this.gameState = GameState.ONGOING;
                    this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
                    select1[0] = -1;
                    select1[1] = -1;
                    end = false;
                    break;
                }
            }
            if (end){
                this.gameState = GameState.SELECTION2BAD;
                this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
                select1[0] = -1;
                select1[1] = -1;
            }
        }
        // Checks to see if you won
        if (this.currentConfig.isSolution()){
            this.gameState = GameState.WON;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        }
    }

    /**
     * Used when the user presses cheats
     */
    public void cheat(){
        this.gameState = GameState.CHEAT;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        SolverReturn checker = new SolverReturn();
        List<Configuration> path = checker.solve(currentConfig);
        // Checks to see if there is a valid next position (not valid)
        if (path.size() == 0){
            this.gameState = GameState.LOST;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        }
        // Checks to see if there is a valid next position (not valid)
        else{
            this.currentConfig = (HoppersConfig) path.get(1);
            this.gameState = GameState.ONGOING;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        }
        select1[0] = -1;
        select1[1] = -1;
        // Checks to see if you won
        if (this.currentConfig.isSolution()){
            this.gameState = GameState.CHEATED_WIN;
            this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        }
    }

    /**
     * Used when the user resets the game
     */
    public void reset(){
        this.gameState = GameState.RESET;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        currentConfig = new HoppersConfig(fileName);
        this.gameState = GameState.ONGOING;
        this.alertObservers(HoppersModel.STATE_MSGS.get(this.gameState));
        select1[0] = -1;
        select1[1] = -1;
    }

    /**
     * Returns the file name of the game
     * @return the file name
     */
    public String getFileName(){
        return fileName;
    }

    public GameState getGameState(){
        return this.gameState;
    }

}
