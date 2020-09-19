package sp.Chess.Gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
 
 
public class Main extends Application {
       
        // Sets standard board and square sizes
        private static final int BOARD_SIZE = 10;
        private static final int SQUARE_SIZE = 80;
        
        // Letters and numbers that go on top/side of grid
        private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        private String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8"};
        private String[] playerColor= {"White", "Black"};
       
       
       
        @Override
        public void start(Stage primaryStage) {
                try {
                       
                        // Root where all objects are placed
                        BorderPane root = new BorderPane();
                        
                        // Gridpane that is the chess board itself
                        GridPane chessBoard = new GridPane();
                           
                        //setup accessory pane to hold move list, move label, and what ever else that is not the board
                        GridPane accessoryPane = new GridPane();
                        
                        //Move list that keeps track of AI and Player moves
                        ListView movesList =new  ListView();

                        //Label displaying current move
                        Label currentMove = new Label(playerColor[0]);
                        
                        //add accessories to accessory Pane
                        accessoryPane.add(currentMove, 0, 0);
                        accessoryPane.add(movesList, 0, 1);
                        
                        // Calls methods that set up the layout, adds the squares, and adds the letters/numbers
                        configureBoardLayout(chessBoard);
                        addSquaresToBoard(chessBoard);
                        addNumbersLettersToBoard(chessBoard);
                       
                        //Board and accessory GridPane (this just seemed like the easiest way)
                        GridPane gameScreen = new GridPane();
                        gameScreen.add(chessBoard, 0, 0);
                        gameScreen.add(accessoryPane, 1, 0);
                        gameScreen.setAlignment(Pos.CENTER);
                        
                        //Events for mouse click, button presses, extra
                        //just for testing mouse clicks on board, can remove later
                        chessBoard.setOnMouseClicked(e->{
                        	//something random just to test
                        	updateBoard(chessBoard, movesList, currentMove);
                        });
                        
                        // Chess board is set to center of the root borderpane
                        root.setCenter(gameScreen);
                        Scene scene = new Scene(root,1200,700); 
                        primaryStage.setTitle("Chess");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
       
       
        // Adds squares to the chess board
        // Skips the topmost row and leftmost column (these will contain the letters and numbers)
        private void addSquaresToBoard(GridPane chessBoard) {
                Color[] squareColors = new Color[] {Color.GRAY, Color.LIGHTBLUE};
                for (int row = 1; row < BOARD_SIZE-1; row++) {
                        for (int col = 1; col < BOARD_SIZE-1; col++) {
                                chessBoard.add(new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]), col, row);
                        }
                }
        }
       
        // Configures the layout of the board (height/width of squares, etc.)
        public void configureBoardLayout(GridPane chessBoard) {
                for (int i = 0; i < BOARD_SIZE; i++) {
                       
                        // Different constraints for the topmost row and leftmost column
                        // These holds the letters and numbers. Needed different values for optimal alignment
                        if (i == 0) {
                                RowConstraints rowConstraints = new RowConstraints();
                                rowConstraints.setMinHeight(40);
                                rowConstraints.setPrefHeight(40);
                                rowConstraints.setMaxHeight(40);
                                rowConstraints.setValignment(VPos.CENTER);
                                chessBoard.getRowConstraints().add(rowConstraints);
                               
                                ColumnConstraints colConstraints = new ColumnConstraints();
                                colConstraints.setMinWidth(40);
                                colConstraints.setPrefWidth(40);
                                colConstraints.setMaxWidth(40);
                                colConstraints.setHalignment(HPos.CENTER);
                                chessBoard.getColumnConstraints().add(colConstraints);
                               
                        }
                       
                        // For the squares
                        else {
                                RowConstraints rowConstraints = new RowConstraints();
                                rowConstraints.setMinHeight(SQUARE_SIZE);
                                rowConstraints.setPrefHeight(SQUARE_SIZE);
                                rowConstraints.setMaxHeight(SQUARE_SIZE);
                                rowConstraints.setValignment(VPos.CENTER);
                                chessBoard.getRowConstraints().add(rowConstraints);
                       
                                ColumnConstraints colConstraints = new ColumnConstraints();
                                colConstraints.setMinWidth(SQUARE_SIZE);
                                colConstraints.setPrefWidth(SQUARE_SIZE);
                                colConstraints.setMaxWidth(SQUARE_SIZE);
                                colConstraints.setHalignment(HPos.CENTER);
                                chessBoard.getColumnConstraints().add(colConstraints);
                        }
                }
        }
       
        // Adds the numbers and letters to the topmost row and leftmost column of the chess board gridpane
        public void addNumbersLettersToBoard(GridPane chessBoard) {
               
                for (int i = 1; i < BOARD_SIZE - 1; i++) {
                        Text t1 = new Text(letters[i-1]);
                        t1.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                        chessBoard.add(t1, i, 0);
                        Text t2 = new Text(nums[i-1]);
                        t2.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                        chessBoard.add(t2, 0, i);
                       
                }
        }
        //updates the chess board when ever a move is made, adds info to the move list switchs the move label
        public void updateBoard(GridPane chessBoard, ListView movesList, Label currentMove) {
        	//update the chess board
        	//TODO 
        	
        	//Update the move list
            //these are just for testing
            movesList.getItems().add("Test turn 1");
            movesList.getItems().add("Test turn 2");
            movesList.getItems().add("Test turn 3");
            movesList.getItems().add("Test turn 4");
            
            //Switch the current move Label
            if(currentMove.getText().equals("White")) {
            	currentMove.setText(playerColor[1]);
            }else {
            	currentMove.setText(playerColor[0]);
            }
        }
        
        
        
        
        
        public static void main(String[] args) {
                launch(args);
        }
       
       
}