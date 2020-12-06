/*Contributing team members
 * Richard Ogletree
 * Menelio Alvarez
 * Stephen May
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
import sp.Utils.General;
import sp.pieces.Piece;
import sp.pieces.Piece.PieceType;
import sp.pieces.Team;

public class Game {
	
	
	//global variable
	private static Square[][] boardArray;//Board of square objects
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
	private boolean attacking = false;
	private boolean attackSuccess = false;
	private int rollResult=-1;
	private boolean KntMoveAndAtt=false;//is this move a the knight attack and move
	private boolean popUpKnightWindow=false;
	Piece piece; //piece bieng considerd
	private Piece enemyPiece;//piece Being attacked
	private boolean moveComplete;//move ready to be processed
	
	public boolean[] movedArmies= {false,false,false};
	// turn stuff
	private Team currentTurnColor = Team.GOLD;
	// number of moves a player has taken on their current turn
	private int numberOfMoves = 0;
	//dice roll Image view
	ImageView diceRoll=new ImageView("file:Assets/Dice_Its_1.gif");
	public String currentPiece="";
	//Is game PVE
	private boolean isPVE=false;
	private boolean isEVE=false;

	private int numberOfGoldMoves= 3;
	private int numberOfBlackMoves= 3;
	//Winner
	private Team winner = null; 
	public Timeline timeline = null;
	
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
 	 * @param column Integer row of player click increment
 	 * @author Richard OlgalTree & Menelio Alvarez
 	 * <p>*/
	public void processMove(ListView<String> movesList, int row, int column, GridPane accessoryPane, Pane dicePane) {
		//if there is no AI
		if(!isPVE|| currentTurnColor == Team.GOLD) {
			if(!isClicked ) { //if this is the first click

				startRow=row;
				startColumn=column;
				if(boardArray[startRow][startColumn].getPiece() != null && boardArray[startRow][startColumn].getPiece().getTeam() == currentTurnColor&& !movedArmies[boardArray[row][column].getPiece().getCorpNum()]) {//if this is a valid piece
					isClicked =true;
					piece = boardArray[row][column].getPiece();
					currentPiece=piece.toString();
					KntMoveAndAtt=false;
					//movedArmies[boardArray[row][column].getPiece().getCorpNum()]=true;

					return;
				}
			}else {//if second click
				
				//store end coordinates 
				endRow = row;
				endColumn = column;

				//check if this move is an attack
				attacking = (boardArray[endRow][endColumn].getPiece() != null && boardArray[endRow][endColumn].getPiece().getTeam() != currentTurnColor);
				
				//check if this is a valid move
				if(!piece.isLegalMove(startRow, startColumn, endRow, endColumn, boardArray)) {//If move isn't valid don't continue
					System.out.println("Invalid move.");
					return;
				}

				if(piece.getPieceType()==PieceType.KNIGHT ){
					KntMoveAndAtt=true;
				}
				
				
				if(attacking) {//if second click is on an enemy piece
					KntMoveAndAtt=false;
					
					enemyPiece = boardArray[endRow][endColumn].getPiece();
					//roll dice to determine if attack was successful
					attackSuccess = diceRollSuccess(piece, enemyPiece, movesList, accessoryPane, dicePane,KntMoveAndAtt);
					
					if(attackSuccess) {

						if(isPVE && enemyPiece.getTeam()==Team.BLACK) {//if losing piece is AI

							ai.removePieceAIByID(enemyPiece.getAi().getId());//after attack have to remove AI or will cause null pointer
						}
						updateBoard(startRow, startColumn, endRow, endColumn, false, movesList);//Player move
					}else{
						resetClick();
						incrementMoveCount();
						movesList.getItems().add(""+piece.getTeam() + 
                                "'s Attack failed."+piece.toString()+
                                " stays at "+piece.getRow()+", "+ piece.getColumn());
	
						System.out.println("Attack failed.");
						movedArmies[piece.getCorpNum()]=true;
					}
				}else if(boardArray[endRow][endColumn].getPiece() == null){//if not an attack make sure space is empty and move
					if(KntMoveAndAtt && surroundingsCheck(endRow, endColumn, piece.getTeam())) {
						popUpKnightWindow= true;
						
					}
					updateBoard(startRow, startColumn, endRow, endColumn, false, movesList);//Player move
				}
			}
		}else if(isPVE && currentTurnColor == Team.BLACK) {//If it is PVE

			//just doing the first three moves in list should be pre-sorted by king
			
			
			Move aiMoves = ai.requestMoves(boardArray);
			//TODO PATcH to fix Move being generate with null piece
			if(aiMoves != null) {
				while(boardArray[aiMoves.getStartRow()][aiMoves.getStartColumn()].getPiece() == null) {
					aiMoves = ai.requestMoves(boardArray);
					if(aiMoves == null) {
						continue;
					}
				}
			}
			
			if(aiMoves!=null) {
				startRow = aiMoves.getStartRow();
				startColumn = aiMoves.getStartColumn();
				endRow = aiMoves.getEndRow();
				endColumn = aiMoves.getEndColumn();
				attacking=aiMoves.isAttacking();
				piece = boardArray[startRow][startColumn].getPiece();
				
				//check if AI move is a knight move Combo
				KntMoveAndAtt= (piece.getPieceType()==Piece.PieceType.KNIGHT &&
								(Math.abs(startRow-endRow)>1 ||Math.abs(startColumn-endColumn)>1));
				
				if(attacking) {//if AI is attacking
					enemyPiece = boardArray[endRow][endColumn].getPiece();//get enemy piece
					attackSuccess=diceRollSuccess(piece,enemyPiece, movesList, accessoryPane, dicePane,KntMoveAndAtt);//roll dice
					
					if(attackSuccess) {//if dice roll is a success follow through with attack
						System.out.println("Attack Successied");
						

						
						updateBoard(startRow, startColumn, endRow, endColumn, true, movesList);//AI move
						
					}else {

						if(piece != null) {
							movedArmies[piece.getCorpNum()]=true;
						}
						
						//if failed AI attack was a knight move/attack Combo place knight near defending piece
						if(KntMoveAndAtt) { 
							int[] rowOffset = {-1, 0, 1};
							int[] colOffset = {-1, 0, 1};
							for(int i=0; i < rowOffset.length;i++) {
								for(int j=0; j < colOffset.length;j++) {
									if( (endRow+rowOffset[i])>=0 && (endRow+rowOffset[i])<8 && (endColumn+colOffset[j])>=0 && (endColumn+colOffset[j]) <8) {
										if((boardArray[endRow+rowOffset[i]][endColumn+colOffset[j]].getPiece()== null) &&
											General.doesPathExist(startRow, startColumn, endRow+rowOffset[i], endColumn+colOffset[j], 5, boardArray)){
											updateBoard(startRow, startColumn, endRow+rowOffset[i], endColumn+colOffset[j], true, movesList);
										}
									}
								}
							}
						}
					
						incrementMoveCount();
					}
				}else {

					updateBoard(startRow, startColumn, endRow, endColumn, true, movesList);//AI move
				}
			}else {
				//TODO AI Pass
				passMove(movesList,  accessoryPane, dicePane, true);
				
				
			}

		}
		KntMoveAndAtt=false;
	}
	
	//TODO COMMENTSspecial version of processMove that handles knights attack after responded yes to pop up window
	public void knightMoveAttack(ListView<String> movesList, int row, int column, GridPane accessoryPane, Pane dicePane) {
		if(boardArray[row][column].getPiece() != null && boardArray[row][column].getPiece().getTeam() != piece.getTeam()) {
			enemyPiece = boardArray[row][column].getPiece();
			startRow= piece.getRow();
			startColumn= piece.getColumn();
			endRow = row;
			endColumn =column;
			
			if(diceRollSuccess(piece,enemyPiece, movesList, accessoryPane, dicePane,true)) {
				if(isPVE && enemyPiece.getTeam()==Team.BLACK) {//if losing piece is AI

					ai.removePieceAIByID(enemyPiece.getAi().getId());//after attack have to remove AI or will cause null pointer
				}
				if(enemyPiece.getPieceType()==Piece.PieceType.KING) {
					if(enemyPiece.getTeam()==Team.GOLD) {
						winner = Team.BLACK;
					}else {
						winner = Team.GOLD;
					}
				}
				updateBoard(startRow, startColumn, endRow, endColumn, false, movesList);
				
			}else {
				incrementMoveCount();
			}
			KntMoveAndAtt=false;
			popUpKnightWindow= false;
			
	
		}else {
			popUpKnightWindow= true;
		}
	}
	
	//Update Square[][] board array
	private void updateBoard(int startRow, int startColumn, int endRow, int endColumn, boolean isAIMove, ListView<String> movesList) {
		//update Move list with move
		if(isAIMove) {
			movesList.getItems().add("AI-"+boardArray[startRow][startColumn].getPiece().getTeam() + 
				                     " has moved a "+boardArray[startRow][startColumn].getPiece()+
				                     " from "+startRow+", "+ startColumn+
				                     " to "+endRow+", "+ endColumn);
		}else {
			movesList.getItems().add(""+boardArray[startRow][startColumn].getPiece().getTeam() + 
	                                 " has moved a "+boardArray[startRow][startColumn].getPiece()+
	                                 " from "+startRow+", "+ startColumn+
	                                 " to "+endRow+", "+ endColumn);
		}
		
		//handle lose of bishops
		if(boardArray[endRow][endColumn].getPiece()!= null && boardArray[endRow][endColumn].getPiece().getPieceType()==PieceType.BISHOP) {
			
			if(boardArray[endRow][endColumn].getPiece().getTeam()==Team.BLACK) {
				numberOfBlackMoves--;
				if(boardArray[endRow][endColumn].getPiece().getAi().getId().equals("02-BISHOP")) {
					//ai.moveLeftBishopsSubsToKing();
					System.out.println("The King has inherited the left Bishop's subs");
				}
				if(boardArray[endRow][endColumn].getPiece().getAi().getId().equals("05-BISHOP")) {
					//ai.moveLeftBishopsSubsToKing();
					System.out.println("The King has inherited the right Bishop's subs");
				}
			}
			if(boardArray[endRow][endColumn].getPiece().getTeam()==Team.GOLD) {
				numberOfGoldMoves--;
			}
		}
		
		
		//update board
		if(!isAIMove ) {
			boardArray[endRow][endColumn].setPiece(boardArray[startRow][startColumn].getPiece());
			boardArray[endRow][endColumn].getPiece().setRow(endRow);
			boardArray[endRow][endColumn].getPiece().setColumn(endColumn);
			movedArmies[boardArray[endRow][endColumn].getPiece().getCorpNum()]=true;
			boardArray[startRow][startColumn].setPiece(null);
		}else {
			boardArray[endRow][endColumn].setPiece(boardArray[startRow][startColumn].getPiece());
			//update loacation of piece in piece and in its AI
			boardArray[endRow][endColumn].getPiece().setRow(endRow);
			boardArray[endRow][endColumn].getPiece().setColumn(endColumn);
			if(boardArray[endRow][endColumn].getPiece().getAi()!=null) {
				boardArray[endRow][endColumn].getPiece().getAi().setRow(endRow);
				boardArray[endRow][endColumn].getPiece().getAi().setColumn(endColumn);
			}
			//delete piece from previous location
			movedArmies[boardArray[endRow][endColumn].getPiece().getCorpNum()]=true;
			boardArray[startRow][startColumn].setPiece(null);
		}
		incrementMoveCount();
		resetClick();
	}
	
 	//increments moves
 	private void incrementMoveCount() {
		if(!popUpKnightWindow) {
			numberOfMoves++;
		}
		if(currentTurnColor == Team.GOLD && numberOfMoves == numberOfGoldMoves) {
			currentTurnColor = Team.BLACK;
			numberOfMoves = 0;
			movedArmies[0]=false;
			movedArmies[1]=false;
			movedArmies[2]=false;
			sp.Utils.General.incrementTurnCount();
		}else if(currentTurnColor == Team.BLACK && numberOfMoves == numberOfBlackMoves) {
			currentTurnColor = Team.GOLD;
			numberOfMoves = 0;
			movedArmies[0]=false;
			movedArmies[1]=false;
			movedArmies[2]=false;
			sp.Utils.General.incrementTurnCount();	
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
		if(!KntMoveAndAtt) {
			currentPiece="";
		}
		
 	}
	
	/**<h1>Pass Move</h1> 
	 * <p>Resets all click associate variables, and 
	 * increments numberOfMoves. Used to pass moves.  
	 * </p>
	 * @author Menelio Alvarez
	 * */
 	public void passMove(ListView<String> movesList, GridPane accessoryPane, Pane dicePane,boolean updateList) {
 		if(updateList) {
 			
 			movesList.getItems().add(currentTurnColor+" passed thier "+numberOfMoves+" move");
 		}
 		resetClick();
 		incrementMoveCount();
		if(isPVE && currentTurnColor == Team.BLACK) {//if pass ends on AI's move, AI makes three moves and sets player as currentTurnColor
			if (numberOfMoves == 3) {
				numberOfMoves = 0;
			}
			while(currentTurnColor == Team.BLACK) {
				processMove(movesList,startRow,startColumn, accessoryPane, dicePane);
			}
			currentTurnColor = Team.GOLD;
		}
 	}
 	/*check surrounding area for potential targets*/
 	private boolean surroundingsCheck(int endRow, int endColumn, Team knghtTeam) {
 		int[] OffsetRow= {-1,0,1};
 		int[] OffsetColumn= {-1,0,1};
 		
 		for(int i=0; i < OffsetRow.length;i++) {
 			for(int j=0; j < OffsetColumn.length; j++) {
 				int row = endRow+OffsetRow[i];
 				int col = endColumn+OffsetColumn[j];
 				if( (row>=0 && row < 8 && col >=0 && col < 8)  && (boardArray[row][col].getPiece()!=null && boardArray[row][col].getPiece().getTeam() != knghtTeam)) {
 					return true;
 				}
 			}
 		}
 		return false;
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
		movedArmies[0]=false;
		movedArmies[1]=false;
		movedArmies[2]=false;
 		setBoardArray(null);
 		this.boardArray = sp.Utils.Board.setUpDefaultBoard();
 		this.ai = null;
 		this.ai = new AIController((KingAI)this.boardArray[0][4].getPiece().getAi()); 
 		resetClick();
 		numberOfMoves= 0;
 		numberOfGoldMoves= 3;
 		numberOfBlackMoves= 3;
 		currentTurnColor = Team.GOLD;
 		winner=null;
 		
 		
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
	public boolean diceRollSuccess(Piece attacker, Piece defender, ListView<String> movesList, GridPane accessoryPane, Pane dicePane, boolean KntMoveAndAtt ) {
		movesList.getItems().add(attacker.getTeam()+" is attacking "+ defender.getTeam()+"'s "+defender.getPieceType()+" at "+(defender.getRow()+1)+
				                 ","+(defender.getColumn()+1)+" with their "+attacker.getPieceType());
		////////////////////////////////////////////Debuggin
		if(attacker==null || defender==null) {
 			System.out.println("game.diceRollSuccess() Roll Success null pointer avioded");
 			return false;
 		}
		////////////////////////////////////////////Debuggin
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
 			//knight move and attack combine
 			if(KntMoveAndAtt) {
 				movesList.getItems().add("Knight has combined a move an attack 1 has been subtracked from the dice roll");
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
	 			else if (defender.getPieceType() == PieceType.KNIGHT || defender.getPieceType() == PieceType.BISHOP) {
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
	 			else if (defender.getPieceType() == PieceType.QUEEN || defender.getPieceType() == PieceType.KING) {
	 					System.out.println("Dice roll of " + diceRoll);
	 					movesList.getItems().add("Dice roll of " + diceRoll + " for an unsuccessful attack.");
	 					return false;
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
 			}else {
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
		timeline = new Timeline();
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
			
	        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(.5), (ActionEvent event1) -> {
	        	
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

	/**
	 * @return the numberOfMoves
	 */
	public int getNumberOfMoves() {
		return numberOfMoves;
	}
	/**
	 * @return the numberOfGoldMoves
	 */
	public int getNumberOfGoldMoves() {
		return numberOfGoldMoves;
	}

	/**
	 * @return the numberOfBlackMoves
	 */
	public int getNumberOfBlackMoves() {
		return numberOfBlackMoves;
	}

	/**
	 * @return the kntMoveAndAtt
	 */
	public boolean isKntMoveAndAtt() {
		return KntMoveAndAtt;
	}

	/**
	 * @param kntMoveAndAtt the kntMoveAndAtt to set
	 */
	public void setKntMoveAndAtt(boolean kntMoveAndAtt) {
		KntMoveAndAtt = kntMoveAndAtt;
	}

	/**
	 * @return the popUpKnightWindow
	 */
	public boolean isPopUpKnightWindow() {
		return popUpKnightWindow;
	}

	/**
	 * @param popUpKnightWindow the popUpKnightWindow to set
	 */
	public void setPopUpKnightWindow(boolean popUpKnightWindow) {
		this.popUpKnightWindow = popUpKnightWindow;
	}
	
	
}
