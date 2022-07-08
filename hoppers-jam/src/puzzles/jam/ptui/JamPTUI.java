package puzzles.jam.ptui;


import puzzles.common.Observer;
import puzzles.jam.model.JamModel;
import java.io.PrintWriter;
import java.util.List;

/**
 * PTUI of the Jam Game
 * @author Suzayet Hoque, sh7354@rit.edu
 */


public class JamPTUI extends ConsoleApplication implements Observer<JamModel, String> {

    /**
     * model from Jam model
     */
    private JamModel model;


    /**
     * Initalizes the view
     * @throws Exception file exception or other exceptions
     */
    @Override
    public void init() throws Exception {
        this.model = new JamModel();
        this.model.addObserver(this);

        List<String> paramStrings = super.getArguments();
        if (paramStrings.size() == 1) {
            final String firstgame = paramStrings.get(0);
                this.model.newGame(firstgame);
            }

        }

    /**
     * Updates the view
     * @param jamModel model
     * @param msg to print to the view
     */
    @Override
    public void update(JamModel jamModel, String msg) {
        
        if (model.gameState() == JamModel.GameState.LOAD){
            System.out.println("Loaded: " + model.getFilename());
        }
        if (model.gameState() == JamModel.GameState.ONGOING){
            System.out.println(this.printBoard(model));
        }
        if (model.gameState() == JamModel.GameState.CHEAT){
            System.out.println("You cheated, but here's the next step:");
        }
        if (model.gameState() == JamModel.GameState.SELECTED1){
            System.out.println("Selected: " + msg.charAt(0) + " " + msg.charAt(1));
        }
        if (model.gameState() == JamModel.GameState.SELECTED2){
            System.out.println("Jumping from: " + msg.charAt(0) + " " + msg.charAt(1) + " to " + msg.charAt(2) + " " + msg.charAt(3));
        }
        if (model.gameState() == JamModel.GameState.SELECTION1BAD){
            System.out.println("Not a valid selection, select piece to move");
        }
        if (model.gameState() == JamModel.GameState.SELECTION2BAD){
            System.out.println("Not a valid jump, select piece to move");
        }
        if (model.gameState() == JamModel.GameState.LOST){
            System.out.println("You Lost :(");
        }
        if (model.gameState() == JamModel.GameState.WON){
            System.out.println("You Won :)");
        }

        if (model.gameState() == JamModel.GameState.INDEX_ERROR){
            System.out.println("Please select piece to move within range");
        }

        }

    /**
     * Starts the ptui
     * @param args file to load game
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        }
        else{
            ConsoleApplication.launch(JamPTUI.class, args);




        }
    }

    /**
     * Shows initial commands to the view
     * @param out print statement outwards
     * @throws Exception
     */
    @Override
    public void start(PrintWriter out) throws Exception {


        super.setOnCommand( "h", 1, ":  h(int) : show the next move",
                args -> this.model.hint()
        );
        super.setOnCommand( "l", 1, ": l(oad) the game",
                args -> this.model.load(args[0])

        );

        super.setOnCommand( "r", 0, ": r(eset) the game",
                args -> this.model.reset()
        );

        super.setOnCommand( "s", 2, ": s (elect) a move",
                args -> this.model.select(args[0], args[1])

        );


    }

    /**
     * prints the board of jam
     * @param model
     * @return String
     */
    public String printBoard(JamModel model){
        StringBuilder returner = new StringBuilder();

        returner.append("   ");
        for (int i = 0; i < model.getCurrentConfig().getCOLS(); i++) {
            returner.append(i).append(" ");
        }

        returner.append("\n").append("  ");
        for (int i = 0; i < model.getCurrentConfig().getCOLS()*2; i++) {
            returner.append("-");
        }


        returner.append("\n");
        for (int i = 0; i < model.getCurrentConfig().getROWS(); i++) {
            returner.append(i).append("| ");
            for (int j = 0; j < model.getCurrentConfig().getCOLS(); j++) {
                returner.append(model.getCurrentConfig().getBoard()[i][j]).append(" ");
            }
            returner.append("\n");
        }

        return returner.toString();
    }


}
