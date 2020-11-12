// MA's Test

/*Contributing team members
 * Richard Ogletree
 * Menelio Alvarez
 * */
package sp.application;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sp.AI.AIController;
import sp.AI.KingAI;
import sp.AI.Move;
import sp.pieces.Piece;
import sp.pieces.Piece.PieceType;
import sp.pieces.Team;

public class Game {
	
	
	//global variable
	private static Square[][] boardArray;//Board of square objects
	private AIController ai;//AI controller
	private Player player1;//players
	private Player player2;
	
	private double testt;
	
	//for implementing click events
	private boolean isClicked= false;// has a square been clicked
	//star row and column of move
	private int startRow=-1;//used to store row of clicked square
	private int startColumn=-1;//used to store column of clicked square
	//star row and column of move
	private int endRow=-1;//used to store row of second clicked square
	private int endColumn=-1;//used to store column of second clicked square
	//Used to indicate is move attacking and if is is successful
	private boolean attacking=false;
	private boolean attackSuccess = false;
	private int rollResult=-1;
	// turn stuff
	private Team currentTurnColor = Team.GOLD;
	// number of moves a player has taken on their current turn
	private int numberOfMoves = 0;
	//dice roll Image view
	ImageView diceRoll=new ImageView("file:Assets/Dice_Its_1.gif");
	public String currentPiece="";
	//Is game PVE
	boolean isPVE=false;

	//Winner

	private Team winner = null; 
	
	/**<h1>Default argument Constructor</h1> 
	 * <p>Sets up a game with a given 2D array of 
	 * Square[][] objects for the board. Sets up
	 * game for a Player and and AI 
	 * </p>
	 * @param isPVE indicates if game is against AI
	 * @author Menelio Alvarez
	 * */
	public Game(boolean isPVE) {
		this.boardArray = sp.Utils.Board.setUpDefaultBoard();
		
		if(isPVE) {
			this.ai = new AIController((KingAI)this.boardArray[0][4].getPiece().getAi()); 
			this.isPVE = true;
		}else {
			//System.out.println(this.boardArray[0][4].getPiece().getPieceType());
			this.ai= null; 
		}
		this.player1=null;//null for now until player is implemented
		this.player2=null;//null for now until player is implemented
	}
	
 	/**<h1>Processes Move</h1> 
 	 * <p>Interprets and tracks player clicks and updates boardArray
 	 * when a legal move is made. Also updates GUI's ListView with moves
 	 * @param moveList GUI's ListView
 	 * @param row Integer row of player click
 	 * @param column Integer row of player click
 	 * @author Richard OlgalTree & Menelio Alvarez
 	 * <p>*/
 	public void processMove(ListView<String> movesList, int row, int column, GridPane accessoryPane, Pane dicePane) {
		
 		if((!isPVE || currentTurnColor == Team.GOLD)) {
	 		if(!isClicked && boardArray[row][column].getPiece() != null && boardArray[row][column].getPiece().getTeam() == currentTurnColor) {
					startRow = boardArray[row][column].getRow();
					startColumn = boardArray[row][column].getColumn();
					isClicked = true;
					System.out.println("First click row="+startRow+" column="+startColumn);
					currentPiece = boardArray[startRow][startColumn].getPiece().toString();
				} else if (isClicked) {
					endRow = boardArray[row][column].getRow();
					endColumn = boardArray[row][column].getColumn();
	
					boolean canMove = boardArray[startRow][startColumn].getPiece().isLegalMove(startRow, startColumn, endRow, endColumn, boardArray);
	
					if (canMove) { //this line testing
					//check if attacking
						if(boardArray[row][column].getPiece()!= null ) {
							if (boardArray[row][column].getPiece().getTeam() == boardArray[startRow][startColumn].getPiece().getTeam()) {
								attacking = false;
								System.out.println("That's your own team, bozo!");
								return;//return without incrementing move numberOfMoves
							}
							else {
								attacking= true;
							}
						}else {
							attacking= false;
						}
	
						//update board and/or moveList
						if(!attacking && boardArray[row][column].getPiece()== null) {
							boardArray[endRow][endColumn].setPiece(boardArray[startRow][startColumn].getPiece());
							boardArray[startRow][startColumn].setPiece(null);
							movesList.getItems().add("Moved " + boardArray[endRow][endColumn].getPiece().getTeam() + " " +
									boardArray[endRow][endColumn].getPiece().getPieceType() + " from row " + (startRow+1) + " column " +
									String.valueOf((char)((startColumn+1)+64)) + " to row " + (endRow+1) + " column " +
									String.valueOf((char)((endColumn+1)+64)));
							System.out.println("Second click row="+boardArray[row][column].getRow()+" column="+boardArray[row][column].getColumn());//console print out
						
						}else if(diceRollSuccess(boardArray[startRow][startColumn].getPiece(), boardArray[endRow][endColumn].getPiece(), movesList, accessoryPane, dicePane)) {

							if(isPVE) {
								ai.removePieceAIByID(boardArray[endRow][endColumn].getPiece().getAi().getId());//after attack have to remove AI or will cause null pointer
							}
							boardArray[endRow][endColumn].setPiece(boardArray[startRow][startColumn].getPiece());
							boardArray[startRow][startColumn].setPiece(null);
							movesList.getItems().add("Attack Successful");
							movesList.getItems().add("Moved "+boardArray[endRow][endColumn].getPiece().getPieceType());
							movesList.getItems().add(" From row "+ (startRow+1)+" column "+(startColumn+1)); 
							movesList.getItems().add(" to row "+ (endRow+1)+" column "+ (endColumn+1));
							System.out.println("Second click row="+boardArray[row][column].getRow()+" column="+boardArray[row][column].getColumn());
						}else {
							movesList.getItems().add("Attack failed");
						}
						//reset
						startRow=-1;
						startColumn=-1;
						endRow = -1;
						endColumn = -1;
						isClicked = false;
						attacking=false;
						attackSuccess = false;
						currentPiece="";
					}
					else {
						System.out.println("Invalid move.");
						return;//return without incrementing move numberOfMoves
					}
					numberOfMoves++;
					if (numberOfMoves == 3) {
						if (currentTurnColor == Team.GOLD) {
							currentTurnColor = Team.BLACK;
						}
						else {
							currentTurnColor = Team.GOLD;
						}
						numberOfMoves = 0;
					}
				}
		}
		//If at the end of the player turn the following conditions are met, AI Move
		if(isPVE && currentTurnColor == Team.BLACK){
			
			//just doing the first three moves in list should be pre-sorted by king
			List<Move> aiMoves = ai.requestMoves(boardArray);
			
			for(int i=0; i< 1; i++) {
				startRow = aiMoves.get(i).getStartRow();
				startColumn = aiMoves.get(i).getStartColumn();
				endRow = aiMoves.get(i).getEndRow();
				endColumn = aiMoves.get(i).getEndColumn();
				
				if(i < aiMoves.size()) {//if you don't have the moves don't move
					//copy piece to new location
					if(aiMoves.get(i).isAttacking()) {//if AI is attacking
						if(diceRollSuccess(boardArray[aiMoves.get(i).getStartRow()][aiMoves.get(i).getStartColumn()].getPiece(), 
						   boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece(), movesList, accessoryPane, dicePane)) {//Roll Dice
							
							System.out.println("Attack Successied");
							
							boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].setPiece(boardArray[aiMoves.get(i).getStartRow()][aiMoves.get(i).getStartColumn()].getPiece());
							//update loacation of piece in piece and in its AI
							boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().setRow(aiMoves.get(i).getEndRow());
							boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().setColumn(aiMoves.get(i).getStartColumn());
							boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().getAi().setRow(aiMoves.get(i).getEndRow());
							boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().getAi().setColumn(aiMoves.get(i).getEndColumn());
							//delete piece from previous location
							boardArray[aiMoves.get(i).getStartRow()][aiMoves.get(i).getStartColumn()].setPiece(null);
							
							
						}else {
							System.out.println("failed");//for debuggingt
						}
					}else {
					
						boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].setPiece(boardArray[aiMoves.get(i).getStartRow()][aiMoves.get(i).getStartColumn()].getPiece());
						//update loacation of piece in piece and in its AI
						boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().setRow(aiMoves.get(i).getEndRow());
						boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().setColumn(aiMoves.get(i).getStartColumn());
						boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().getAi().setRow(aiMoves.get(i).getEndRow());
						boardArray[aiMoves.get(i).getEndRow()][aiMoves.get(i).getEndColumn()].getPiece().getAi().setColumn(aiMoves.get(i).getEndColumn());
						//delete piece from previous location
						boardArray[aiMoves.get(i).getStartRow()][aiMoves.get(i).getStartColumn()].setPiece(null);
					}
				}
				//update move list with AI moves.
				movesList.getItems().add("Moved " + boardArray[endRow][endColumn].getPiece().getTeam() + " " +
						boardArray[endRow][endColumn].getPiece().getPieceType() + " from row " + (startRow+1) + " column " +
						String.valueOf((char)((startColumn+1)+64)) + " to row " + (endRow+1) + " column " +
						String.valueOf((char)((endColumn+1)+64)));
			}
			
			numberOfMoves++;
			if (numberOfMoves == 3) {
			startRow=-1;
			startColumn=-1;
			endRow = -1;
			endColumn = -1;
			isClicked = false;
			attacking=false;
			attackSuccess = false;
			currentPiece="";
			currentTurnColor = Team.GOLD;
			numberOfMoves = 0;
			}
		}
 	}
 	
	/**<h1>Reset Click</h1> 
	 * <p>Resets all click associate variables. Used
	 * for de-selecting a piece after it's been click.  
	 * </p>
	 * @author Menelio Alvarez
	 * */
 	public void resetClick() {
		//reset
		startRow=-1;
		startColumn=-1;
		endRow = -1;
		endColumn = -1;
		isClicked = false;
		attacking=false;
		attackSuccess = false; 
		currentPiece="";
 	}
	
	/**<h1>Pass Move</h1> 
	 * <p>Resets all click associate variables, and 
	 * increments numberOfMoves. Used to pass moves.  
	 * </p>
	 * @author Menelio Alvarez
	 * */
 	public void passMove(ListView<String> movesList, GridPane accessoryPane, Pane dicePane) {
		startRow=-1;
		startColumn=-1;
		endRow = -1;
		endColumn = -1;
		isClicked = false;
		attacking=false;
		attackSuccess = false; 
		currentPiece="";
		numberOfMoves++;
		movesList.getItems().add(currentTurnColor+" passed thier "+numberOfMoves+" move");
		

		if (numberOfMoves == 3) {
			if (currentTurnColor == Team.GOLD) {
				currentTurnColor = Team.BLACK;
				
				if(isPVE) {//if pass ends on AI's move, AI makes three moves and sets player as currentTurnColor
					if (numberOfMoves == 3) {
						numberOfMoves = 0;
					}
					for(int i=0; i< 3; i++) {
						processMove(movesList,startRow,startColumn, accessoryPane, dicePane);
					}
					currentTurnColor = Team.GOLD;
				}
			}
			else {
				currentTurnColor = Team.GOLD;
			}
			numberOfMoves = 0;
		}
 	}
 	
	/**<h1>Get Board Array</h1> 
	 * <p>Returns 2D array of Square objects representing the game board
	 * </p>
	 * @author Menelio Alvarez
	 */
 	public Square[][] getBoardArray() {
		return boardArray;
	}

	/**<h1>Reset Board </h1> 
	 * <p>Used to reset the board.
	 * </p>
	 * @author Menelio Alvarez
	 */
 	public void resetBoard() {
 		this.boardArray = sp.Utils.Board.setUpDefaultBoard();
 		resetClick();
 		currentTurnColor = Team.GOLD;
 	}
	/**<h1>Setter for board array</h1> 
	 * <p>Set this games board array.
	 * </p>
	 * @author Menelio Alvarez
	 */
 	public void setBoardArray(Square[][] boardArray) {
		this.boardArray = boardArray;
	}

 	/**<h1>Dice Roll Success check</h1> 
 	 * <p>Used to generate a random number between 1 and 6
 	 * to calculate dice for to determine a successful attack</p>
 	 * @param attacker Piece that is attacking
 	 * @param defender Piece that is defending
 	 * @param movesList ListView for displaying out come
 	 * @author Richard OlgalTree
 	 * <p>*/
	public boolean diceRollSuccess(Piece attacker, Piece defender, ListView movesList, GridPane accessoryPane, Pane dicePane) {
 		ImageView[] dice = new ImageView[6];
 		dice[0] = new ImageView("file:Assets/1.png");
 		dice[1] = new ImageView("file:Assets/2.png");
 		dice[2] = new ImageView("file:Assets/3.png");
 		dice[3] = new ImageView("file:Assets/4.png");
 		dice[4] = new ImageView("file:Assets/5.png");
 		dice[5] = new ImageView("file:Assets/6.png");
 		for (int i = 0; i < 6; i++) {
 			dice[i].setFitHeight(80);
 			dice[i].setFitWidth(80);
 		}
 		
 		// generates a random dice roll
 		int diceRoll = (int) (Math.random()*6 + 1);
 		playDiceRole(dicePane,diceRoll);
 		
 		// Gets the piece type of the attacking piece and the
 		// defending piece. Depending on the combination of the
 		// two, says which dice roll makes a successful attack.
 		// Prints out a message on the console with the dice roll
 		// number and if the attack was successful or not. Then
 		// returns true/false.
 		if (attacker.getPieceType() == PieceType.PAWN) {
 			if (defender.getPieceType() == PieceType.PAWN) {
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.ROOK || defender.getPieceType() == PieceType.KNIGHT ||
 					 defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // bishop
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 		else if (attacker.getPieceType() == PieceType.ROOK) {
 			if (defender.getPieceType() == PieceType.PAWN || defender.getPieceType() == PieceType.BISHOP ||
 				defender.getPieceType() == PieceType.KNIGHT) {
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // defender must be rook
 				if (diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 		else if (attacker.getPieceType() == PieceType.KNIGHT) {
 			if (defender.getPieceType() == PieceType.PAWN) {
 				if (diceRoll == 2 || diceRoll == 3 || diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.KNIGHT || defender.getPieceType() == PieceType.BISHOP) {
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // defender must be rook
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 		else if (attacker.getPieceType() == PieceType.BISHOP) {
 			if (defender.getPieceType() == PieceType.PAWN) {
 				if (diceRoll == 3 || diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.ROOK || defender.getPieceType() == PieceType.KNIGHT ||
 					 defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // defender must be bishop
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 		else if (attacker.getPieceType() == PieceType.QUEEN) {
 			if (defender.getPieceType() == PieceType.PAWN) {
 				if (diceRoll == 2 || diceRoll == 3 || diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else if (defender.getPieceType() == PieceType.KNIGHT || defender.getPieceType() == PieceType.BISHOP ||
 					 defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // defender must be rook
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 		else { // attacker must be king
 			if (defender.getPieceType() == PieceType.PAWN) {
 				movesList.getItems().add("KING successfully attacks PAWN");
 				return true;
 			}
 			else if (defender.getPieceType() == PieceType.BISHOP || defender.getPieceType() == PieceType.KNIGHT ||
 					 defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
 				if (diceRoll == 4 || diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					
 					//if defender was king set winner
 					if(defender.getPieceType() == PieceType.KING) {
 						if(attacker.getTeam() == Team.BLACK) {
 							winner = Team.BLACK;
 						}else {
 							winner = Team.GOLD;
 						}
 					}
 					
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 			else { // defender must be rook
 				if (diceRoll == 5 || diceRoll == 6) {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for a successful attack.");
 					return true;
 				}
 				else {
 					System.out.println("Dice roll of " + diceRoll);
 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
 					return false;
 				}
 			}
 		}
 	}
 	
	/**<h1>Get Current Turn Color</h1>
	 * <p>Gets the color of the player whose turn it is
	 * </p>
	 * @author Menelio Alvarez
	 * */
	public Team getCurrentTurnColor() {
		return currentTurnColor;
	}

	/**<h1>Roll Dice</h1>
	 * <p>Used by the GUI to know if it should play the 
	 * roll dice animation. Returns the roll result and
	 * set the rollResult back to -1</p>
	 * @return int dice roll result
	 * */
	public int rollDice() {
		int result = rollResult;
		rollResult=-1;
		return result;
	}
	
	/*
	/*To play Dice roll animation needs to be cleaned up*/
	private void playDiceRole(Pane dicePane, int outCome) {
		Timeline timeline = new Timeline();
		switch(outCome) { 
		case 1:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/one.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		case 2:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/two.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		case 3:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/three.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		case 4:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/four.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		case 5:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/five.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		case 6:
			timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.1), (ActionEvent event1) -> {
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Rolling/Normal Speed.gif");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        }));
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(2), (ActionEvent event1) -> {
	        	
	        	diceRoll = new ImageView("file:Assets/Dice Gifs/Dice Numbers/six.png");
	        	dicePane.getChildren().clear(); 
	        	dicePane.getChildren().add(diceRoll);
	        })); 
			
			timeline.play();
			break;
		}
		
 	}

	/**
	 * @return the winner
	 */
	public Team getWinner() {
		return winner;
	}
	
	
}
