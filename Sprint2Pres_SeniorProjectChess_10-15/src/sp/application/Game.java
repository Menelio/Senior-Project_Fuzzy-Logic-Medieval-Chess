// MA's Test 2

/*Contributing team members
 * Richard Ogletree
 * Menelio Alvarez
 * */
package sp.application;

import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sp.AI.AIController;
import sp.pieces.Piece;
import sp.pieces.Piece.PieceType;
import sp.pieces.Team;

public class Game {
	
	
	//global variable
	private Square[][] boardArray;//Board of square objects
	private AIController ai;//AI controller
	private Player player1;//players
	private Player player2;
	
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
	// turn stuff
	private Team currentTurnColor = Team.GOLD;
	// number of moves a player has taken on their current turn
	private int numberOfMoves = 0;
	
	public String currentPiece="";
	
	/**<h1>Default argument Constructor</h1> 
	 * <p>Sets up a game with a given 2D array of 
	 * Square[][] objects for the board. Sets up
	 * game for a Player and and AI 
	 * </p>
	 * @param boardArray 2D array of Square[][] 
	 * objects for the board
	 * @author Menelio Alvarez
	 * */
	public Game() {
		this.boardArray = sp.Utils.Board.setUpDefaultBoard();
		this.ai= null; //null until ai is implemented
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
 	public void processMove(ListView<String> movesList, int row, int column) {
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
					
					}else if(diceRollSuccess(boardArray[startRow][startColumn].getPiece(), boardArray[endRow][endColumn].getPiece(), movesList)) {
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
 	public void passMove(ListView<String> movesList) {
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

	/**<h1>Reset Board TODO Doesn't work</h1> 
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
	public boolean diceRollSuccess(Piece attacker, Piece defender, ListView movesList) {
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

	
}
