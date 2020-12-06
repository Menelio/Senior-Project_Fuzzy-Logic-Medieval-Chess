/*Contributing team members
 * Richard OlgalTree
 * Menelio Alvarez
 * */
package sp.Utils;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sp.AI.AI;
import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Bishop;
import sp.pieces.King;
import sp.pieces.Knight;
import sp.pieces.Pawn;
import sp.pieces.Piece;
import sp.pieces.Queen;
import sp.pieces.Rook;
import sp.pieces.Team;

public class Board {
	private static final int BOARD_SIZE = 8;//hieght and length
	private static final int SQUARE_SIZE = 80;
	private static Color[] squareColors = new Color[] {Color.LIGHTGREY, Color.DARKRED};
	
	/**<h2>Set up default board</h2>
	 * <p>
	 *	Sets up a Square[][] with piece objects in their default positions
	 * </p>
	 * @return 2D array of square objects.
	 * @author Richard OlgalTree & Menelio Alvarez
	 * */
 	public static Square[][] setUpDefaultBoard() {
 		// Makes the board have light grey and dark red squares
 		Square[][] boardArray= new Square[BOARD_SIZE][BOARD_SIZE];
 		
 		//Setup piece Black AI Pieces
 		//list of subordinate AI
 		List<SubordinateAI> leftBishopSubordinatesBlack=new ArrayList<SubordinateAI>();
 		List<SubordinateAI> rightBishopSubordinatesBlack=new ArrayList<SubordinateAI>();
 		List<SubordinateAI> KingSubordinatesBlack=new ArrayList<SubordinateAI>();
 		//create sub piece and add them to list of sub AI
 		Pawn[] BlackPawn = new Pawn[8];
 		for(int i =0; i < BlackPawn.length;i++) {
 			if(i < 3) {
 				BlackPawn[i]= new Pawn(Team.BLACK,1, i, 0);
 				leftBishopSubordinatesBlack.add((SubordinateAI) BlackPawn[i].getAi());		
 			}else if(i < 5){
 				BlackPawn[i]= new Pawn(Team.BLACK,1, i, 1);
 				KingSubordinatesBlack.add((SubordinateAI) BlackPawn[i].getAi());
 			}else {
 				BlackPawn[i]= new Pawn(Team.BLACK,1, i,2);
 				rightBishopSubordinatesBlack.add((SubordinateAI) BlackPawn[i].getAi());
 			}
 		}
 		
 		Rook leftRookBlack = new Rook(Team.BLACK,0, 0);
 		KingSubordinatesBlack.add((SubordinateAI) leftRookBlack.getAi());
 		Rook rightRookBlack = new Rook(Team.BLACK,0, 7);
 		KingSubordinatesBlack.add((SubordinateAI) rightRookBlack.getAi());
 		
 		Knight leftKnightBlack = new Knight(Team.BLACK,0, 1, 0);
 		leftBishopSubordinatesBlack.add((SubordinateAI) leftKnightBlack.getAi());
 		Knight rightKnightBlack = new Knight(Team.BLACK,0, 6, 2);
 		rightBishopSubordinatesBlack.add((SubordinateAI) rightKnightBlack.getAi());
 		
 		//create AI Command pieces
 		Bishop leftBishopBlack = new Bishop(Team.BLACK,0, 2, leftBishopSubordinatesBlack, 0);
 		Bishop rightBishopBlack = new Bishop(Team.BLACK,0, 5, rightBishopSubordinatesBlack, 2);
 		
 		Queen queenBlack = new Queen(Team.BLACK,0, 3);
 		KingSubordinatesBlack.add((SubordinateAI) queenBlack.getAi());
 		
 		King kingBlack = new King(Team.BLACK, 0, 4, KingSubordinatesBlack, leftBishopBlack.getAi(), rightBishopBlack.getAi() );
 		
 		//////////////////////////////////////////Debugging check there are no piece on the wrong team
 		for(int i=0; i < kingBlack.getAi().getLeftBishop().getSubordinate().size();i++) {
 			if(kingBlack.getAi().getLeftBishop().getSubordinate().get(i).getTeamColor() != Team.BLACK) {
 				System.out.println("Board has assigned Gold "+kingBlack.getAi().getLeftBishop().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		
 		for(int i=0; i < kingBlack.getAi().getRightBishop().getSubordinate().size();i++) {
 			if(kingBlack.getAi().getRightBishop().getSubordinate().get(i).getTeamColor() != Team.BLACK) {
 				System.out.println("-**- Board.setUpDefault( BoardBoard has assigned Gold "+kingBlack.getAi().getRightBishop().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		
 		for(int i=0; i < kingBlack.getAi().getSubordinate().size();i++) {
 			if(kingBlack.getAi().getSubordinate().get(i).getTeamColor() != Team.BLACK) {
 				System.out.println("-**- Board.setUpDefault( Board has assigned Gold "+kingBlack.getAi().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		//////////////////////////////////////////Debugging
 		
 		//Setup piece Gold AI Pieces
 		//list of subordinate AI
 		List<SubordinateAI> leftBishopSubordinatesGold=new ArrayList<SubordinateAI>();
 		List<SubordinateAI> rightBishopSubordinatesGold=new ArrayList<SubordinateAI>();
 		List<SubordinateAI> KingSubordinatesGold=new ArrayList<SubordinateAI>();
 		//create sub piece and add them to list of sub AI
 		Pawn[] GoldPawn = new Pawn[8];
 		for(int i =0; i < GoldPawn.length;i++) {
 			if(i < 3) {
 				GoldPawn[i]= new Pawn(Team.GOLD,6, i, 0);
 				leftBishopSubordinatesGold.add((SubordinateAI) GoldPawn[i].getAi());		
 			}else if(i < 5){
 				GoldPawn[i]= new Pawn(Team.GOLD,6, i, 1);
 				KingSubordinatesGold.add((SubordinateAI) GoldPawn[i].getAi());
 			}else {
 				GoldPawn[i]= new Pawn(Team.GOLD,6, i,2);
 				rightBishopSubordinatesGold.add((SubordinateAI) GoldPawn[i].getAi());
 			}
 		}
 		
 		if(leftBishopSubordinatesGold.size()>0) {
 			System.out.println("-------------------");
 		}
 		
 		Rook leftRookGold = new Rook(Team.GOLD,7, 0);
 		KingSubordinatesGold.add((SubordinateAI) leftRookGold.getAi());
 		Rook rightRookGold = new Rook(Team.GOLD,7, 7);
 		KingSubordinatesGold.add((SubordinateAI) rightRookGold.getAi());
 		
 		Knight leftKnightGold = new Knight(Team.GOLD,7, 1, 0);
 		leftBishopSubordinatesGold.add((SubordinateAI) leftKnightGold.getAi());
 		Knight rightKnightGold = new Knight(Team.GOLD,7, 6, 2);
 		rightBishopSubordinatesGold.add((SubordinateAI) rightKnightGold.getAi());
 		
 		//create AI Command pieces
 		Bishop leftBishopGold= new Bishop(Team.GOLD,7, 2, leftBishopSubordinatesGold, 0);
 		Bishop rightBishopGold = new Bishop(Team.GOLD,7, 5, rightBishopSubordinatesGold, 2);
 		
 		Queen queenGold = new Queen(Team.GOLD,7, 3);
 		KingSubordinatesGold.add((SubordinateAI) queenGold.getAi());
 		
 		King kingGold = new King(Team.GOLD, 7, 4, KingSubordinatesGold, leftBishopGold.getAi(), rightBishopGold.getAi() );
 		
 		if(kingGold.getAi().getLeftBishop().getSubordinate().size() <1) {
 			System.out.println("Check");
 			System.exit(0);
 		}
 		
 		if(kingGold.getAi().getLeftBishop().getSubordinate().get(0)==null) {
 			System.out.println("CHECK");
 			System.exit(0);
 		}
 		
 		//////////////////////////////////////////Debugging check there are no piece on the wrong team
 		for(int i=0; i < kingGold.getAi().getLeftBishop().getSubordinate().size();i++) {
 			if(kingGold.getAi().getLeftBishop().getSubordinate().get(i).getTeamColor() != Team.GOLD) {
 				System.out.println("Board has assigned Gold "+kingGold.getAi().getLeftBishop().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		
 		for(int i=0; i < kingGold.getAi().getRightBishop().getSubordinate().size();i++) {
 			if(kingGold.getAi().getRightBishop().getSubordinate().get(i).getTeamColor() != Team.GOLD) {
 				System.out.println("-**- Board.setUpDefault( BoardBoard has assigned Gold "+kingGold.getAi().getRightBishop().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		
 		for(int i=0; i < kingGold.getAi().getSubordinate().size();i++) {
 			if(kingGold.getAi().getSubordinate().get(i).getTeamColor() != Team.GOLD) {
 				System.out.println("-**- Board.setUpDefault( Board has assigned Gold "+kingGold.getAi().getSubordinate().get(i).getId()+" to black team");
 			}
 		}
 		//////////////////////////////////////////Debugging
 		
 		
 		for (int row = 1; row <= BOARD_SIZE; row++) {
 			for (int col = 1; col <= BOARD_SIZE; col++) {
 				Group temp = new Group();
 				Rectangle r = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]);
 				Piece p = null;
 						
 				temp.getChildren().add(r);
 				if (row == 1) {
 					if (col == 1 || col == 8) {
 						if(col == 1 ) {
 							p = leftRookBlack;
 						}else {
 							p = rightRookBlack;
 							
 						}
 					}
 					if (col == 2 || col == 7) {
 						if(col == 2 ) {
 							p = leftKnightBlack;
 						}else {
 							p = rightKnightBlack;
 							
 						}
 					}
 					if (col == 3 || col == 6) {
 						if(col == 3 ) {
 							p = leftBishopBlack;
 						}else {
 							p = rightBishopBlack;
 						}
 						
 					}
 					if (col == 4) {
 						p = queenBlack;
 					}
 					if (col == 5) {
 						p = kingBlack;
 					}
 				}
 				if (row == 2) {
 						p = BlackPawn[col-1];
				}
 				if (row == 7 ) {
 					p = GoldPawn[col-1];
 				}
 				if (row == 8) {
 					if (col == 1 || col == 8) {
 						if(col == 1 ) {
 							p = leftRookGold;
 						}else {
 							p = rightRookGold;
 							
 						}
 					}
 					if (col == 2 || col == 7) {
 						if(col == 2 ) {
 							p = leftKnightGold;
 						}else {
 							p = rightKnightGold;
 							
 						}
 					}
 					if (col == 3 || col == 6) {
 						if(col == 3 ) {
 							p = leftBishopGold;
 						}else {
 							p = rightBishopGold;
 						}
 						
 					}
 					if (col == 4) {
 						p = queenGold;
 					}
 					if (col == 5) {
 						p = kingGold;
 					}
 				}
 				
 				boardArray[row-1][col-1] = new Square(row-1, col-1, p, r);
 			}
 		}
 		
		return boardArray;
	
 	}
 	
}
