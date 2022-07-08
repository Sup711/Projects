package puzzles.hoppers.gui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Contains the hopper GUI
 *
 * @author: Eliot Nagy (epn2643)
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, String> {

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    /** The image for the red frog */
    private final Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    /** The image for the green frog */
    private final Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    /** The image for the lily pad */
    private final Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    /** The image for the water*/
    private final Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));

    /** The model that holds all the game data and logic */
    private HoppersModel model;
    /** The board the holds the GUI */
    private BorderPane board;
    /** The label that represents the game events*/
    private Label gameEvents;
    /** The array of open spaces */
    private ArrayList<ArrayList<Button>> playSpace;
    /** The array of game function buttons */
    private Button[] gameButtons;
    /** The stage used to represent for the GUI */
    private Stage stage;

    /**
     * Creates a new game using a provided file
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        this.model = new HoppersModel();

        if (!filename.equals("")){
            this.model.newGame(filename);
        }
        board = new BorderPane();
    }

    /**
     * Builds the GUI for the game
     * @param stage the stage that the gui will be put on
     * @throws Exception it might get thrown so account for it
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        GridPane top = new GridPane();
        GridPane bottom = new GridPane();

        gameEvents = new Label();
        gameButtons = new Button[3];

        //
        loadBoard();

        // The load button
        Button load = new Button();
        load.setText("Load");
        load.setStyle("-fx-font: 24 Courier;");
        load.setOnAction(actionEvent -> model.load(this.selectFile()));
        gameButtons[0] = load;
        bottom.add(load, 0, 0);

        // The reset button
        Button reset = new Button();
        reset.setText("Reset");
        reset.setStyle("-fx-font: 24 Courier;");
        reset.setOnAction(actionEvent -> model.reset());
        gameButtons[1] = reset;
        bottom.add(reset, 1, 0);

        // The hint button
        Button hint = new Button();
        hint.setText("Hint");
        hint.setStyle("-fx-font: 24 Courier;");
        hint.setOnAction(actionEvent -> model.cheat());
        gameButtons[2] = hint;
        bottom.add(hint, 2, 0);

        // The game event label
        gameEvents.setText("Loaded: " + model.getFileName());
        gameEvents.setStyle("-fx-font: 24 Courier;");
        top.add(gameEvents, 0, 0);

        top.setAlignment(Pos.TOP_CENTER);
        board.setTop(top);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        board.setBottom(bottom);

        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();

        this.model.addObserver(this);
    }

    /**
     * The update method changes the GUI based on the game state
     * @param hoppersModel The next model to update to
     * @param msg The message passed along with the model
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        if (model.getGameState() == HoppersModel.GameState.LOAD){
            gameEvents.setText("Loaded: " + model.getFileName());
            loadBoard();
        }
        if (model.getGameState() == HoppersModel.GameState.ONGOING){
            updatePlaySpace(model);
        }
        if (model.getGameState() == HoppersModel.GameState.CHEAT){
            gameEvents.setText("You cheated, but here's the next step:");
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTED1){
            gameEvents.setText("Selected: " + msg.charAt(0) + " " + msg.charAt(1));
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTED2){
            gameEvents.setText("Jumping from: " + msg.charAt(0) + " " + msg.charAt(1) + " to " + msg.charAt(2) + " " + msg.charAt(3));
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTION1BAD){
            gameEvents.setText("Not a valid selection, select piece to move");
        }
        if (model.getGameState() == HoppersModel.GameState.SELECTION2BAD){
            gameEvents.setText("Not a valid jump, select piece to move");
        }
        if (model.getGameState() == HoppersModel.GameState.LOST){
            gameEvents.setText("You Lost :(");
        }
        if (model.getGameState() == HoppersModel.GameState.WON){
            gameEvents.setText("You Won :)");
        }
        if (model.getGameState() == HoppersModel.GameState.CHEATED_WIN){
            gameEvents.setText("You Won :) but you also cheated :(");
        }
        if (model.getGameState() == HoppersModel.GameState.INDEX_ERROR){
            gameEvents.setText("Please select piece to move within range");
        }
        if (model.getGameState() == HoppersModel.GameState.RESET){
            gameEvents.setText("Reset Board");
        }
    }

    /**
     * Open the file explorer
     * @return The path of the selected file
     */
    public String selectFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        return file.getPath();
    }

    /**
     * Builds the actual play space
     * @param model The model to build it from
     */
    public void updatePlaySpace(HoppersModel model){
        for (int i = 0; i < model.currentConfig.getNumRows(); i++) {
            for (int j = 0; j < model.currentConfig.getNumCols(); j++) {
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.GREENFROG){
                    playSpace.get(i).get(j).setGraphic(new ImageView(greenFrog));
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.REDFROG){
                    playSpace.get(i).get(j).setGraphic(new ImageView(redFrog));
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.OPEN){
                    playSpace.get(i).get(j).setGraphic(new ImageView(lilyPad));
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.ILLEGAL){
                    playSpace.get(i).get(j).setGraphic(new ImageView(water));
                }
            }
        }
    }

    /**
     * Loads in a new board when the load button is pressed
     */
    public void loadBoard(){
        GridPane center = new GridPane();
        playSpace = new ArrayList<ArrayList<Button>>();
        for (int i = 0; i < model.currentConfig.getNumRows(); i++) {
            ArrayList<Button> tempArray = new ArrayList<>();
            for (int j = 0; j < model.currentConfig.getNumCols(); j++) {
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.GREENFROG){
                    Button temp = new Button();
                    temp.setGraphic(new ImageView(greenFrog));
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    temp.setBackground(new Background(new BackgroundFill(Color.web("#1291e3"), new CornerRadii(0), Insets.EMPTY)));
                    tempArray.add(temp);
                    center.add(temp, j, i);
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.REDFROG){
                    Button temp = new Button();
                    temp.setGraphic(new ImageView(redFrog));
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    temp.setBackground(new Background(new BackgroundFill(Color.web("#1291e3"), new CornerRadii(0), Insets.EMPTY)));
                    tempArray.add(temp);
                    center.add(temp, j, i);
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.OPEN){
                    Button temp = new Button();
                    temp.setGraphic(new ImageView(lilyPad));
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    temp.setBackground(new Background(new BackgroundFill(Color.web("#1291e3"), new CornerRadii(0), Insets.EMPTY)));
                    tempArray.add(temp);
                    center.add(temp, j, i);
                }
                if (model.currentConfig.getBoard()[i][j] == HoppersConfig.ILLEGAL){
                    Button temp = new Button();
                    temp.setGraphic(new ImageView(water));
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    temp.setBackground(new Background(new BackgroundFill(Color.web("#1291e3"), new CornerRadii(0), Insets.EMPTY)));
                    tempArray.add(temp);
                    center.add(temp, j, i);
                }
            }
            playSpace.add(tempArray);
        }
        center.setAlignment(Pos.CENTER);
        board.setCenter(center);
    }

    /**
     * The main class the launches the GUI
     * @param args args from the command line
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
