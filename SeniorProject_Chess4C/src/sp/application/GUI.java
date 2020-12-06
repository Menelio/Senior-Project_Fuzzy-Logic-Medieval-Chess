/*Contributing team members
 * Richard Ogletree
 * Edgar Rodriguez
 * Menelio Alvarez
 * Stephen May
 * */
package sp.application;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sp.pieces.Team;

public class GUI extends Application {
	// Sets standard board and square sizes
	private static final int BOARD_SIZE = 9;
	private static final int SQUARE_SIZE = 80;
	// Letters and numbers that go on top/side of grid
	private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
	private String[] nums = {"1", "2", "3", "4", "5", "6", "7", "8"};
	//Player color string for current turn Label
	private String[] playerColor = {"Gold", "Black"};
	//Game
	private Game game;
	private boolean isPVE= false;
	private boolean isEVE= false;
	GridPane accessoryPane;
	
	//for implementing click events
	boolean isClicked= false;// has a square been clicked

	//dice roll ImageView initialized with default image
	ImageView diceRoll=new ImageView("file:Assets/Dice Gifs/Blank.png");//= new ImageView("file:Assets/Dice1.gif");
	private int diceRollResult=-1;
	
	//is Game won
	boolean isGameWon= false;
	
	//Current move label
	Label currentMove;
	
 	//stuff for knight attack/move combo popup
 	GridPane knightPopUpPane = new GridPane();
 	Label knightPopLabel = new Label("Would you like to combine your move with an attack");
 	Button yesBtn= new Button("Yes");
 	Button noBtn= new Button("No");
 	Scene knightPopUpscene = new Scene(knightPopUpPane);
 	Stage knightPopUpWindow=new Stage();
 	boolean knghtMoveAttCombo= false;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
    	try {
    		/*Setting up menu Pane
    		 * */
    		//AnchorPane base
    		AnchorPane anchorMenuPane= new AnchorPane();
    		anchorMenuPane.setMinSize(500, 500);

    		//File t1 = new File(GUI.class.getResource("Assets/MenuScreen/Menu Screen/Menu_Screen_No_Button.jpg"));
    		Image t =new Image(new File("Assets/MenuScreen/Menu Screen/Menu_Screen_No_Button.jpg").toURI().toString());
    		
    		ImageView menuBackGround =new ImageView(t);
    		menuBackGround.setFitHeight(500);
    		menuBackGround.setFitWidth(500);
    		
    		AnchorPane.setTopAnchor(menuBackGround,  0.0);
    		AnchorPane.setLeftAnchor(menuBackGround, 0.0);
    		anchorMenuPane.getChildren().add(menuBackGround);
    		//gridPane for the menu buttons
    		GridPane menuButtons = new GridPane();
    		Pane menuPane = new Pane();
    		menuPane.getChildren().add(menuButtons);
    		
    		AnchorPane.setTopAnchor(menuPane, 200.0);
    		AnchorPane.setLeftAnchor(menuPane, 340.0);
    		anchorMenuPane.getChildren().add(menuPane);
    		
    		//Button images
    		ImageView pvpBtn = new ImageView("file:Assets/MenuScreen/Menu Screen/2_Players.png");
    		pvpBtn.setFitHeight(25);
    		pvpBtn.setFitWidth(125);
    		
    		ImageView pveBtn = new ImageView("file:Assets/MenuScreen/Menu Screen/Play_vs_AI.png");
    		pveBtn.setFitHeight(25);
    		pveBtn.setFitWidth(125);
    		
    		ImageView eveBtn = new ImageView("file:Assets/MenuScreen/Menu Screen/AI_vs_AI.png");
    		eveBtn.setFitHeight(25);
    		eveBtn.setFitWidth(125);
    		
    		ImageView abtBtn = new ImageView("file:Assets/MenuScreen/Menu Screen/About.png");
    		abtBtn.setFitHeight(25);
    		abtBtn.setFitWidth(125);
    		
    		ImageView extBtn = new ImageView("file:Assets/MenuScreen/Menu Screen/Exit.png");
    		extBtn.setFitHeight(25);
    		extBtn.setFitWidth(125);
    		//buttons
    		Button pvp = new Button("",pvpBtn );
    		pvp.setStyle("-fx-background-color: transparent;");
    		Button pve = new Button("",pveBtn);
    		pve.setStyle("-fx-background-color: transparent;");
    		
    		Button eve = new Button("",eveBtn);// have to finish graphic
    		eve.setStyle("-fx-background-color: transparent;");
    		
    		Button about = new Button("",abtBtn);
    		about.setStyle("-fx-background-color: transparent;");
    		Button exit = new Button("",extBtn);
    		exit.setStyle("-fx-background-color: transparent;");
    		
    		menuButtons.add(pvp, 0, 0);
    		menuButtons.add(pve, 0, 1);
    		menuButtons.add(eve, 0, 2);
    		menuButtons.add(about, 0, 3);
    		menuButtons.add(exit, 0, 4);    		
    			
    		Scene menuScene = new Scene(anchorMenuPane);
    		
    		/*Setup about pain
    		 * */
       		AnchorPane anchorAboutPane= new AnchorPane();
    		anchorAboutPane.setMinSize(500, 500);

    		ImageView aboutBackGround = new ImageView("file:Assets/AboutScreen/About Screen/About_Screen_No_Button.jpg");
    		aboutBackGround.setFitHeight(500);
    		aboutBackGround.setFitWidth(500);
    		
    		
    		AnchorPane.setTopAnchor(aboutBackGround,  0.0);
    		AnchorPane.setLeftAnchor(aboutBackGround, 0.0);
    		anchorAboutPane.getChildren().add(aboutBackGround);
    		
    		//gridPane for the about pane buttons
    		GridPane aboutButtons = new GridPane();
    		
    		AnchorPane.setTopAnchor(aboutButtons,  400.0);
    		AnchorPane.setLeftAnchor(aboutButtons, 300.0);
    		

    		
    		//back to menu button
       		ImageView bckImg = new ImageView("file:Assets/AboutScreen/About Screen/Back.png");
       		bckImg.setFitHeight(25);
       		bckImg.setFitWidth(125);
    		
    		Button back = new Button("",bckImg);
    		
    		aboutButtons.add(back, 0, 1);
    		
    		
    		AnchorPane.setTopAnchor(menuButtons, 200.0);
    		AnchorPane.setLeftAnchor(menuButtons, 200.0);
    		anchorAboutPane.getChildren().add(aboutButtons);
    		
    		Scene aboutScene = new Scene(anchorAboutPane);
    		
    		/*Setting up Game pane
    		 * */
    		//initialize game
    		game = new Game(false, false);
    		
    		// Root where all objects are placed
    		BorderPane root = new BorderPane();
    					
    		// setup accessory pane to hold move list, move label, and what ever else that is not the board
    		accessoryPane = new GridPane();
    		            
    		// Adds small gap between game board and accessory pane
    		VBox gap = new VBox();
    		gap.setMaxWidth(20);
    		gap.setMinWidth(20);
    		gap.setPrefWidth(20);
    		            
    		// Move list that keeps track of AI and Player moves
    		ListView<String> movesList = new  ListView<String>();
    		movesList.setPrefWidth(370);

    		// Label that just writes "CUrrent move:" in accessory pane
    		Label lab = new Label();
    		lab.setText("Current move:");
    		            
    		// Label displaying current move
    		currentMove = new Label(playerColor[0]);
    		
    		//Pass Button
    		Button passButton = new Button("Pass move");

    		// add accessories to accessory Pane
    		accessoryPane.add(gap, 0, 0);
    		accessoryPane.add(lab, 1, 0);
    		accessoryPane.add(currentMove, 1, 1);
    		accessoryPane.add(movesList, 1, 2);
    		accessoryPane.add(passButton, 1, 3);
    		
    		Pane dicePane = new Pane();
    		dicePane.getChildren().add(diceRoll);
    		accessoryPane.add(dicePane, 1, 4);

    		// Grid pane that is the chess board itself
    		GridPane chessBoard = new GridPane();
    		chessBoard.setAlignment(Pos.CENTER);
    					

    		
    		configureBoardLayout(chessBoard);
    		
    		//pass button event
    		passButton.setOnAction(e->{
    			
    			if(isEVE) {
    				if(game.getWinner()== null) {
    					game.processMove(movesList, 0, 0, accessoryPane,dicePane);
    					refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
    				}else {
    					isGameWon =true;
 						if(game.getWinner() == Team.BLACK) {
 							game.timeline.setOnFinished(ti->{ImageView diceRoll=new ImageView("file:Assets/winScreen/Black Wins.jpg");
 							diceRoll.setFitHeight(225);
 							diceRoll.setFitWidth(350);
 							dicePane.getChildren().clear(); 
 				        	dicePane.getChildren().add(diceRoll);});
 						}
 						else {
 							game.timeline.setOnFinished(ti->{ImageView diceRoll=new ImageView("file:Assets/winScreen/Gold Wins.jpg");
 							diceRoll.setFitHeight(225);
 							diceRoll.setFitWidth(350);
 							dicePane.getChildren().clear(); 
 				        	dicePane.getChildren().add(diceRoll);});
 						}	
    				}
    			}else if(!(isPVE && game.getCurrentTurnColor()==Team.BLACK)) {
    				game.passMove(movesList, accessoryPane,dicePane,true);
    				currentMove.setText(""+game.getCurrentTurnColor()+" Number of moves remaining "+(3-game.getNumberOfMoves()));
    				refreshBoard(chessBoard, movesList, accessoryPane, dicePane);
    			}
    		});
    		
    		refreshBoard(chessBoard, movesList, accessoryPane, dicePane);
    		addNumbersLettersToBoard(chessBoard);
			
    		// Board and accessory GridPane (this just seemed like the easiest way)
    		GridPane gameScreen = new GridPane();
    		gameScreen.add(chessBoard, 0, 0);
    		gameScreen.add(accessoryPane, 1, 0);
    		gameScreen.setAlignment(Pos.CENTER);
    		            			
    		// Chess board is set to center of the root border pane
    		root.setCenter(gameScreen);
    		Scene scene = new Scene(root,1200,750);
    					


    		//setup game pane Menus
    		setupMenus(root,scene, chessBoard, movesList,  accessoryPane, dicePane);

    		primaryStage.setTitle("Chess");
    		//primaryStage.setScene(scene);
    		primaryStage.setScene(menuScene);
    		
    		primaryStage.show();

    		/*Menu button events
    		 * */
    		pvp.setOnAction(e->{
    			primaryStage.setScene(scene);
    		});
    		
    		pve.setOnAction(e->{
    			isPVE =true;
    			game = new Game(true, false);
    			primaryStage.setScene(scene);
    		});
    		
    		eve.setOnAction(e->{
    			isEVE =true;
    			game = new Game(false, true);
    			primaryStage.setScene(scene);
    			passButton.setText("AI Turn");
    		});
    		
    		about.setOnAction(e->{
    			primaryStage.setScene(aboutScene);
    		});
    		
    		
    		exit.setOnAction(e->{
    			System.exit(0);
    		});
    		
    		//about pane back to menu button event
    		back.setOnAction(e->{
    			primaryStage.setScene(menuScene);
    		});
    		
    		//Setup Knight attack/move pop up
    		knightPopUpPane.add(knightPopLabel, 0, 0);
    		knightPopUpPane.setMinWidth(300.0);
    		knightPopUpPane.setMinHeight(150.0);
    		knightPopUpPane.add(yesBtn, 0, 1);
    		yesBtn.setOnAction(e->{
    			knghtMoveAttCombo = true;
    			knightPopUpWindow.close();
    		});
    		noBtn.setOnAction(e->{
    			knghtMoveAttCombo = false;
    			knightPopUpWindow.close();
    			game.passMove(movesList, gameScreen, dicePane,false);
    			refreshBoard( chessBoard, movesList, accessoryPane, dicePane);
    		});
    		
    		knightPopUpPane.add(noBtn, 0, 2);
    		
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
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
 		
 		for (int i = 1; i < BOARD_SIZE; i++) {
 			Text t1 = new Text(letters[i-1]);
 			t1.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
 			chessBoard.add(t1, i, 0);
 			Text t2 = new Text(nums[i-1]);
 			t2.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
 			chessBoard.add(t2, 0, i);
 			
 		}
 	}

 	//sets up menus
 	private void setupMenus(BorderPane root,Scene scene, GridPane chessBoard, ListView<String> movesList, GridPane accessoryPane,Pane dicePane) {    					
		//Set up menus
        // Images for the menu icons
        ImageView fileIcon = new ImageView("file:Assets/menuIcon.png");
        fileIcon.setFitHeight(20);
        fileIcon.setFitWidth(20);
        
        ImageView rulesIcon = new ImageView("file:Assets/rulesIcon.png");
        rulesIcon.setFitHeight(20);
        rulesIcon.setFitWidth(20);
        
        ImageView membersIcon = new ImageView("file:Assets/membersIcon.png");
        membersIcon.setFitHeight(20);
        membersIcon.setFitWidth(20);
        
        ImageView quitIcon = new ImageView("file:Assets/quitIcon.png");
        quitIcon.setFitHeight(20);
        quitIcon.setFitWidth(20);
        
        ImageView viewIcon = new ImageView("file:Assets/viewIcon.png");
        viewIcon.setFitHeight(20);
        viewIcon.setFitWidth(20);
        
        ImageView restartIcon = new ImageView("file:Assets/restartIcon.png");
        restartIcon.setFitHeight(20);
        restartIcon.setFitWidth(20);

        MenuBar menuBar = new MenuBar();
         
        Menu menuFile = new Menu("File");
        menuFile.setGraphic(fileIcon);
         	
        // External window that has the rules to the game
        // Rules were obtained from the "Fuzzy Logic Chess Rules" doc in the Google drive created by Stephen May
        MenuItem gameRules = new MenuItem("Rules");
         	gameRules.setGraphic(rulesIcon);
             gameRules.setOnAction(new EventHandler<ActionEvent>() {
            	 
                 @Override
                 public void handle(ActionEvent event) {
                 	
                 	Stage ruleStage = new Stage();
                 	ruleStage.setWidth(830);
                     ruleStage.setHeight(650);
                     Scene scene = new Scene(new Group());
                 
                     VBox root = new VBox();     
              
                     final WebView browser = new WebView();
                     final WebEngine webEngine = browser.getEngine();
                     

                     // Rule's text using HTML
                     String ruleText = 
                     		"<p>\r\n" + 
                     		"  This variant of chess allows for the game to more accurately represent a medieval battle. Pieces have different movement <br>\r\n" + 
                     		"  values as opposed to the normal version of chess, and each piece represents a different class of soldier from the medieval era. <br><br>\r\n" + 
                     		"\r\n" + 
                     		"\r\n" + 
                     		"  <u><b>Action Points</b></u> <br>\r\n" + 
                     		"\r\n" + 
                     		"  Each turn you will be allotted three action points to spend on your turn. An action can be defined as either moving or attacking<br>\r\n" + 
                     		"  (with some pieces being able to do both in one action).\r\n" + 
                     		"</p><br><br>\r\n" + 
                     		"\r\n" + 
                     		"<p2>\r\n" + 
                     		"  <u><b>Specific Piece Rules</b></u><br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>King (Royalty/Heavy unit)</b><br>\r\n" + 
                     		"  - Commands the Queen, two Rooks, and the center two Pawns<br>\r\n" + 
                     		"  - Can move in any direction, including diagonally<br>\r\n" + 
                     		"  - Does not have to move in a straight line (can change directions)<br>\r\n" + 
                     		"  - Can move up to three spaces<br>\r\n" + 
                     		"  - Can delegate its pieces to either of the two Bishop’s armies<br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>Queen (Royalty/Heavy unit)</b><br>\r\n" + 
                     		"  - Can move in any direction, including diagonally<br>\r\n" + 
                     		"  - Does not have to move in a straight line (can change directions)<br>\r\n" + 
                     		"  - Can move up to three spaces<br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>Bishops (Pikemen)</b><br>\r\n" + 
                     		"  - Each bishop will command for their side’s three Pawns and Knight<br>\r\n" + 
                     		"  - May move forward only, including diagonally<br>\r\n" + 
                     		"  - Can attack forward only, including diagonally<br>\r\n" + 
                     		"  - Can move one spot at a time<br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>Pawns (Light infantry)</b><br>\r\n" + 
                     		"  - May move forward only, including diagonally<br>\r\n" + 
                     		"  - Can attack forward only, including diagonally<br>\r\n" + 
                     		"  - Does not get to move two spaces on its first move, unlike normal chess<br>\r\n" + 
                     		"  -  Can move one spot at a time<br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>Rooks (Archers)</b><br>\r\n" + 
                     		"  -  Can move in any direction<br>\r\n" + 
                     		"  - Can move one spot at a time<br>\r\n" + 
                     		"  - Can attack by shooting an arrow (ranged attack) over up to two spaces away<br>\r\n" + 
                     		"    (not counting the tile that the Rook is on or the enemy is on, so it is technically a range of up to 3)<br><br>\r\n" + 
                     		"\r\n" + 
                     		"  <b>Knights (Mounted units)</b><br>\r\n" + 
                     		"  - Can move in any direction<br>\r\n" + 
                     		"  - Can move up to five spaces<br>\r\n" + 
                     		"  - Can not jump over pieces<br>\r\n" + 
                     		"  - Can change directions while moving<br>\r\n" + 
                     		"  - They are the only piece that can move and attack in the same action, if so the attack die roll gets a <b>-1</b> value<br><br>\r\n" + 
                     		"</p2> <br><br>\r\n" + 
                     		"\r\n" + 
                     		"<p3>\r\n" + 
                     		"  <u><b>Capturing/Attacking</b></u> <br>\r\n" + 
                     		"\r\n" + 
                     		"  - All units except for the Rooks may attack a tile adjacent to the unit<br>\r\n" + 
                     		"  - A successful attack will remove the enemy piece and the attacking piece will move on to the tile<br>\r\n" + 
                     		"  - An unsuccessful attack will result in the attacking piece remaining in place<br>\r\n" + 
                     		"  - Each attack will roll a die, and the value will determine if the attack is successful or not<br>\r\n" + 
                     		"\r\n" + 
                     		"\r\n" + 
                     		"</p3>";
                     
                     ScrollPane scrollPane = new ScrollPane();
                     scrollPane.setContent(browser);
                     webEngine.loadContent(ruleText);
                      
                     root.getChildren().addAll(scrollPane);
                     scene.setRoot(root);
              
                     ruleStage.setScene(scene);
                     ruleStage.show();

                 }
             });
             
         // External window that has information about the members of the group 
         // This can be removed if anyone doesn't want to their information released
         MenuItem memberInfo = new MenuItem("Member Information");
         memberInfo.setGraphic(membersIcon);
         memberInfo.setOnAction(new EventHandler<ActionEvent>() {
             	 
                 @Override
                 public void handle(ActionEvent event) {
                 	
                 		String email = 
                 	    "Members:\n" + "\n" +
                 	    "Menelio Alvarez (Team leader): MenelioAlvarez@gmail.com \n" +
                 	    "Bardia EsmaeilZadeh: besmaei2@gmail.com \n" +
                 	    "Steven Hansen: lastdaysonearth72@gmail.com\n" +
                 	    "Stephen May: Smay918@gmail.com\n" +
                 	    "Luis Nguyen: Nguyen.luis97@gmail.com\n" +
                 	    "Richard Ogletree: rogletree1990@gmail.com\n" +
                 	    "Edgar Rodriguez: rodrigedg@gmail.com\n";
      
                      
						Label memberInfo = new Label(email);
      
                     StackPane aboutLayout = new StackPane();
                     aboutLayout.getChildren().add(memberInfo);
      
                     Scene secondScene = new Scene(aboutLayout, 350, 200);
      
                     // New window (Stage)
                     Stage aboutWindow = new Stage();
                     aboutWindow.setTitle("Team Members");
                     aboutWindow.setScene(secondScene);

                     aboutWindow.show();
                 }
             });
             
         // Another way the user can exit out of the game
         MenuItem quit = new MenuItem("Quit");
         quit.setGraphic(quitIcon);
         quit.setOnAction(e->{
        	 System.exit(0);
         }); 

         menuFile.getItems().addAll(gameRules,memberInfo,quit);

         
         
         // View will let the user choose between a light/dark mode theme for the GUI
         Menu menuView = new Menu("View");
         	menuView.setGraphic(viewIcon);                       	
             CheckMenuItem darkMode = new CheckMenuItem("Dark mode");
             	menuView.getItems().addAll(darkMode);
             	darkMode.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
             	    if (isSelected) {
             	        scene.getRoot().setStyle("-fx-base: #373e43");
             	    } else {
             	        scene.getRoot().setStyle("");
             	    }
             	});
         
         
         // One way the user can restart the game without closing the program
         Menu menuRestart = new Menu("Restart");
         menuRestart.setGraphic(restartIcon);
         CheckMenuItem restart = new CheckMenuItem("restart");
         CheckMenuItem returns = new CheckMenuItem("return to menu");
         menuRestart.getItems().addAll(restart);
         menuRestart.getItems().addAll(returns);
         restart.setOnAction(e->{
        	 game.resetBoard();
        	 movesList.getItems().add("--------------------Game Reset--------------------");
        	 refreshBoard(chessBoard, movesList, accessoryPane, dicePane);
        	 isGameWon = false;
        	 dicePane.getChildren().clear();
        	 refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
         });
         returns.setOnAction(e->{
        	 //Scene(anchorMenuPane)
         });
         menuBar.getMenus().addAll(menuFile, menuView, menuRestart);
         root.setTop(menuBar);
         
 	}
 	
 	//called every time a move is made
 	private void refreshBoard(GridPane chessBoard, ListView<String> movesList, GridPane accessoryPane, Pane dicePane) {
 		
 		for (int row = 1; row < BOARD_SIZE; row++) {
 			for (int col = 1; col < BOARD_SIZE; col++) {
 				Group groupSquare;
 				if (game.getBoardArray()[row-1][col-1].getPiece() != null) {
 					if(game.movedArmies[game.getBoardArray()[row-1][col-1].getPiece().getCorpNum()] && game.getCurrentTurnColor() ==game.getBoardArray()[row-1][col-1].getPiece().getTeam()) {
 						ImageView temp= game.getBoardArray()[row-1][col-1].getPiece().getImgVw();
 						temp.setOpacity(.5);
 						groupSquare = new Group(game.getBoardArray()[row-1][col-1].rectangle, temp);
 					}else {
 						ImageView temp= game.getBoardArray()[row-1][col-1].getPiece().getImgVw();
 						temp.setOpacity(1);
 						groupSquare = new Group(game.getBoardArray()[row-1][col-1].rectangle, temp);
 					}
 				}
 				else {
 					groupSquare = new Group(game.getBoardArray()[row-1][col-1].rectangle);
 				}
 				chessBoard.add(groupSquare, col, row);
 				final int frow=row-1;
 				final int fcol=col-1;
 				
 				
 				currentMove.setText(""+game.getCurrentTurnColor()+" Number of moves remaining "+(3-game.getNumberOfMoves()));
 				
 		 		// Events for mouse click, button presses, extra	
 				groupSquare.setOnMouseClicked(e->{
 					if(!isGameWon) {
 						if(!knghtMoveAttCombo) {
		 					this.diceRollResult = game.rollDice();
		 					if(e.getButton()== MouseButton.PRIMARY) {
		 						game.processMove(movesList, frow, fcol, accessoryPane,dicePane);
		 						refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
		 						
		 						if(game.getCurrentTurnColor() == Team.BLACK) {
		 							currentMove.setText(""+game.getCurrentTurnColor()+" "+game.currentPiece+" Number of moves remaining "+(game.getNumberOfBlackMoves()-game.getNumberOfMoves()));
		 						}else {
		 							currentMove.setText(""+game.getCurrentTurnColor()+" "+game.currentPiece+" Number of moves remaining "+(game.getNumberOfGoldMoves()-game.getNumberOfMoves()));
		 						}
		 					}else {
		 						game.resetClick();
		 						if(game.getCurrentTurnColor() == Team.BLACK) {
		 							currentMove.setText(""+game.getCurrentTurnColor()+" "+game.currentPiece+" Number of moves remaining "+(game.getNumberOfBlackMoves()-game.getNumberOfMoves()));
		 						}else {
		 							currentMove.setText(""+game.getCurrentTurnColor()+" "+game.currentPiece+" Number of moves remaining "+(game.getNumberOfGoldMoves()-game.getNumberOfMoves()));
		 						}
		 					}
		
		 					//if PVE give AI three moves
		 					if(isPVE && game.getCurrentTurnColor() == Team.BLACK) {
		 						while(game.getCurrentTurnColor() == Team.BLACK) {
		 							game.processMove(movesList, frow, fcol, accessoryPane,dicePane);
		 							refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
			
			 						/*TODO still need to figure out how to make AI wait for each move
									try {
										TimeUnit.SECONDS.sleep(1);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}*/
		 						}
		 					}
		 					if(game.getWinner()!= null) {
		 						movesList.getItems().add("WINNER "+ game.getWinner());
		 						isGameWon = true;
		 						if(game.getWinner() == Team.BLACK) {
		 							game.timeline.setOnFinished(t->{ImageView diceRoll=new ImageView("file:Assets/winScreen/Black Wins.jpg");
		 							diceRoll.setFitHeight(225);
		 							diceRoll.setFitWidth(350);
		 							dicePane.getChildren().clear(); 
		 				        	dicePane.getChildren().add(diceRoll);});
		 						}
		 						else {
		 							game.timeline.setOnFinished(t->{ImageView diceRoll=new ImageView("file:Assets/winScreen/Gold Wins.jpg");
		 							diceRoll.setFitHeight(225);
		 							diceRoll.setFitWidth(350);
		 							dicePane.getChildren().clear(); 
		 				        	dicePane.getChildren().add(diceRoll);});
		 						}	

		 					}
		 					
	 					}else {
	 						game.knightMoveAttack(movesList, frow, fcol, accessoryPane, dicePane);
	 						//refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
	 						if(!game.isKntMoveAndAtt()) {
	 							knghtMoveAttCombo=false;
	 							refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
	 							
	 		 					if(isPVE && game.getCurrentTurnColor() == Team.BLACK) {
			 						while(game.getCurrentTurnColor() == Team.BLACK) {
			 							game.processMove(movesList, frow, fcol, accessoryPane,dicePane);
			 							refreshBoard(chessBoard, movesList, accessoryPane,dicePane);
				
				 						/*TODO still need to figure out how to make AI wait for each move
										try {
											TimeUnit.SECONDS.sleep(1);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}*/
			 						}
			 					}
	 						}
	 					}
 					}
 					//knight attack/move combo popup
 			 		if(game.isPopUpKnightWindow()) {
 			 			game.setKntMoveAndAtt(false);
 			 			game.setPopUpKnightWindow(false);
 			 			knightPopUpWindow.setScene(knightPopUpscene);
 			 			knightPopUpWindow.show();
 			 		}
 			 		
 		 		});
 				
 						
 			}
 		}
 	}
 	
 	
 	//main methods launches application
	public static void main(String args[]) {
		launch(args);
	}

}
