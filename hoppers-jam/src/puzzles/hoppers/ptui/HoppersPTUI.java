package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import java.io.PrintWriter;
import java.util.List;

/**
 * The hopper PTUI
 *
 * @author: Eliot Nagy (epn2643)
 */
public class HoppersPTUI extends ConsoleApplication implements Observer<HoppersModel, String> {

    /** The model that holds the data and methods */
    private HoppersModel model;

    /**
     * Initializes the game for the PTUI
     * @throws Exception an exception is might throw but won't because I
     * know how to code
     */
    public void init() throws Exception{
        this.model = new HoppersModel();
        this.model.addObserver(this);

        List<String> paramStrings = super.getArguments();
        if (paramStrings.size() == 1){
            final String firstGame = paramStrings.get(0);
            this.model.newGame(firstGame);
        }
    }

    /**
     * Sets up all the commands
     * @param out idk
     */
    public void start(PrintWriter out){
        super.setOnCommand("hint", 0, ": Show the next step", args -> this.model.cheat());
        super.setOnCommand("load", 1, ": Load the game from the file", args -> this.model.load(args[0]));
        super.setOnCommand("select", 2, ": Selects a board location (row, col)", args -> this.model.select(args[0], args[1]));
        super.setOnCommand("reset", 0, ": Resets the game", args -> this.model.reset());
    }

    /**
     * Updates the PTUI based on the game state
     * @param model the model to update to
     * @param msg a message attached to print out
     */
    @Override
    public void update(HoppersModel model, String msg) {
        if (model.getGameState() == HoppersModel.GameState.LOAD){
            System.out.println("Loaded: " + model.getFileName());
        }
        if (model.getGameState() == HoppersModel.GameState.ONGOING){
            System.out.println(this.printBoard(model));
        }
        if (model.getGameState() == HoppersModel.GameState.CHEAT){
            System.out.println("You cheated, but here's the next step:");
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTED1){
            System.out.println("Selected: " + msg.charAt(0) + " " + msg.charAt(1));
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTED2){
            System.out.println("Jumping from: " + msg.charAt(0) + " " + msg.charAt(1) + " to " + msg.charAt(2) + " " + msg.charAt(3));
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTION1BAD){
            System.out.println("Not a valid selection, select piece to move");
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTION2BAD){
            System.out.println("Not a valid jump, select piece to move");
        }
        if (model.getGameState() == HoppersModel.GameState.LOST){
            System.out.println("You Lost :(");
        }
        if (model.getGameState() == HoppersModel.GameState.WON){
            System.out.println("You Won :)");
        }
        if (model.getGameState() == HoppersModel.GameState.CHEATED_WIN){
            System.out.println("You Won :), but you also cheated to get here  :(");
        }
        if (model.getGameState() == HoppersModel.GameState.INDEX_ERROR){
            System.out.println("Please select piece to move within range");
        }
    }

    /**
     * Basically a toString that makes the board look nice
     * @param model the model being printed
     * @return the string
     */
    public String printBoard(HoppersModel model){
        StringBuilder returner = new StringBuilder();

        returner.append("   ");
        for (int i = 0; i < model.currentConfig.getNumCols(); i++) {
            returner.append(i).append(" ");
        }
        returner.append("\n").append("  ");
        for (int i = 0; i < model.currentConfig.getNumCols()*2; i++) {
            returner.append("-");
        }
        returner.append("\n");
        for (int i = 0; i < model.currentConfig.getNumRows(); i++) {
            returner.append(i).append("| ");
            for (int j = 0; j < model.currentConfig.getNumCols(); j++) {
                returner.append(model.currentConfig.getBoard()[i][j]).append(" ");
            }
            returner.append("\n");
        }
        return returner.toString();
    }

    /**
     * Starts the game
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
        else {
            ConsoleApplication.launch(HoppersPTUI.class, args);
        }
    }
}
