package puzzles.jam.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;


    private JamModel model;

    private Label gameEvents;
    private Button[][] playSpace;
    private Button[] gameButtons;


    private HashMap<Character, Color> CARCOLOR;

    public void init() {
        String filename = getParameters().getRaw().get(0);
        this.model = new JamModel();

        if (!filename.equals("")){
            this.model.newGame(filename);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
//        Button button1 = new Button();
//        button1.setStyle(
//                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
//                "-fx-background-color: " + X_CAR_COLOR + ";" +
//                "-fx-font-weight: bold;");
//        button1.setText("X");
//        button1.setMinSize(ICON_SIZE, ICON_SIZE);
//        button1.setMaxSize(ICON_SIZE, ICON_SIZE);

        BorderPane board = new BorderPane();
        GridPane top = new GridPane();
        GridPane center = new GridPane();
        GridPane bottom = new GridPane();

        CARCOLOR = new HashMap<>();
        gameEvents = new Label();
        playSpace = new Button[model.getCurrentConfig().getROWS()][model.getCurrentConfig().getCOLS()];
        gameButtons = new Button[3];

        CARCOLOR.put(JamConfig.X_CAR,Color.valueOf(X_CAR_COLOR));
        int car=0;
        for (int i = 0; i < model.getCurrentConfig().getROWS(); i++) {
            for (int j = 0; j < model.getCurrentConfig().getCOLS(); j++) {
                if(model.getCurrentConfig().getBoard()[i][j]== JamConfig.BLANK){
                    Button temp = new Button();
                    temp.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                            "-fx-font-weight: bold;"
                    );
                    temp.setMinSize(ICON_SIZE,ICON_SIZE);
                    temp.setMaxSize(ICON_SIZE,ICON_SIZE);
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    playSpace[i][j] = temp;
                    center.add(temp, j, i);
                }
                else{
                    char temp_car= model.getCurrentConfig().getBoard()[i][j];
                    if(!CARCOLOR.containsKey(temp_car)){
                        Random rand= new Random();
                        float red = rand.nextFloat();
                        float green = rand.nextFloat()/ 2;
                        float  blue = rand.nextFloat();
                        CARCOLOR.put(temp_car,Color.color(red,green,blue));


                    }
                    String color= CARCOLOR.get(temp_car).toString();


                    System.out.println(color);
                    String css = color.substring(2);



                    Button temp = new Button();
                    temp.setStyle(
                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                "-fx-background-color: " + css + ";" +
                "-fx-font-weight: bold;");


                    temp.setText(String.valueOf(temp_car));
                    temp.setMinSize(ICON_SIZE,ICON_SIZE);
                    temp.setMaxSize(ICON_SIZE,ICON_SIZE);
                    String iStr = String.valueOf(i);
                    String jStr = String.valueOf(j);
                    temp.setOnAction(event -> model.select(iStr, jStr));
                    playSpace[i][j] = temp;
                    center.add(temp, j, i);

                }




            }
        }
        Button load = new Button();
        load.setText("Load");
        load.setStyle("-fx-font: 24 Courier;");
//        load.setOnAction(actionEvent ->{
//                FileChooser filechooser= new FileChooser();
//                filechooser.setTitle("load game");
//                filechooser.setInitialDirectory(new File(RESOURCES_DIR));
//                });

        gameButtons[0] = load;
        bottom.add(load, 0, 0);

        Button reset = new Button();
        reset.setText("Reset");
        reset.setStyle("-fx-font: 24 Courier;");
        reset.setOnAction(actionEvent -> model.reset());
        gameButtons[0] = reset;
        bottom.add(reset, 1, 0);

        Button hint = new Button();
        hint.setText("Hint");
        hint.setStyle("-fx-font: 24 Courier;");
        hint.setOnAction(actionEvent -> model.hint());
        gameButtons[0] = hint;
        bottom.add(hint, 2, 0);

        gameEvents.setText("Loaded: " + model.getFilename());
        gameEvents.setStyle("-fx-font: 24 Courier;");
        top.add(gameEvents, 0, 0);

        top.setAlignment(Pos.TOP_CENTER);
        board.setTop(top);
        center.setAlignment(Pos.CENTER);
        board.setCenter(center);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        board.setBottom(bottom);

        Scene scene = new Scene(board);
        stage.setScene(scene);
        stage.show();

        this.model.addObserver(this);












    }

    @Override
    public void update(JamModel jamModel, String msg) {
        if (model.gameState() == JamModel.GameState.LOAD){
            gameEvents.setText("Loaded: " + model.getFilename());
        }
        if (model.gameState() == JamModel.GameState.ONGOING){
            this.updateplayspace(model);
        }
        if (model.gameState() == JamModel.GameState.CHEAT){
            gameEvents.setText("You cheated, but here's the next step:");
        }
        if (model.gameState() == JamModel.GameState.SELECTED1){
            gameEvents.setText("Selected: " + msg.charAt(0) + ", " + msg.charAt(1));
        }
        if (model.gameState() == JamModel.GameState.SELECTED2){
            gameEvents.setText("Jumping from: " + msg.charAt(0) + ", " + msg.charAt(1) + " to " + msg.charAt(2) + ", " + msg.charAt(3));
        }
        if (model.gameState() == JamModel.GameState.SELECTION1BAD){
            gameEvents.setText("Not a valid selection, select piece to move");
        }
        if (model.gameState() == JamModel.GameState.SELECTION2BAD){
            gameEvents.setText("Not a valid jump, select piece to move");
        }
        if (model.gameState() == JamModel.GameState.LOST){
            gameEvents.setText("You Lost :(");
        }
        if (model.gameState() == JamModel.GameState.WON){
            gameEvents.setText("You Won :)");
        }

        if (model.gameState() == JamModel.GameState.INDEX_ERROR){
            gameEvents.setText("Please select piece to move within range");
        }



    }



    public void updateplayspace(JamModel model){
        for(int i=0; i<model.getCurrentConfig().getROWS();i++){
            for(int j=0; j<model.getCurrentConfig().getCOLS(); j++){
                if(model.getCurrentConfig().getBoard()[i][j]== JamConfig.BLANK){
                    playSpace[i][j].setText("");
                    playSpace[i][j].setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";"
                            + "-fx-font-weight: bold;"
                    );
                }
                else{
                    char car = model.getCurrentConfig().getBoard()[i][j];
                    String color= CARCOLOR.get(car).toString();


                    System.out.println(color);
                    String css = color.substring(2);


                    playSpace[i][j].setText(String.valueOf(car));
                    playSpace[i][j].setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color:" + css + ";"
                                    + "-fx-font-weight: bold;"
                    );
                }
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
