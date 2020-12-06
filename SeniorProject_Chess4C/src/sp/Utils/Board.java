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
 		Pawn[] pawn = new Pawn[8];
 		for(int i =0; i < pawn.length;i++) {
 			if(i < 3) {
 				pawn[i]= new Pawn(Team.BLACK,1, i, 0);
 				leftBishopSubordinatesBlack.add((SubordinateAI) pawn[i].getAi());		
 			}else if(i < 5){
 				pawn[i]= new Pawn(Team.BLACK,1, i, 1);
 				KingSubordinatesBlack.add((SubordinateAI) pawn[i].getAi());
 			}else {
 				pawn[i]= new Pawn(Team.BLACK,1, i,2);
 				rightBishopSubordinatesBlack.add((SubordinateAI) pawn[i].getAi());
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
 		
 		Queen queen = new Queen(Team.BLACK,0, 3);
 		KingSubordinatesBlack.add((SubordinateAI) queen.getAi());
 		
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
 						p = queen;
 					}
 					if (col == 5) {
 						p = kingBlack;
 					}
 				}
 				if (row == 2) {
 						p = pawn[col-1];
				}
 				if (row == 7 ) {
 					if(col <4) {
 						p = new Pawn(Team.GOLD, row-1, col-1, 0);
 					}else if(col >=4 &&  col <6) {
 						p = new Pawn(Team.GOLD, row-1, col-1, 1);
 					}else {
 						p = new Pawn(Team.GOLD, row-1, col-1, 2);
 					}
 				}
 				if (row == 8) {
 					if (col == 1 || col == 8) {
 						p = new Rook(Team.GOLD, row-1, col-1);
 					}
 					if (col == 2 ) {
 						p = new Knight(Team.GOLD, row-1, col-1,0);
 					}
 					if (col == 7 ) {
 						p = new Knight(Team.GOLD, row-1, col-1,2);
 					}
 					
 					if (col == 3) {
 						p = new Bishop(Team.GOLD, row-1, col-1, null, 0);
 					}
 					
 					if (col == 6) {
 						p = new Bishop(Team.GOLD, row-1, col-1, null, 2);
 					}
 					
 					if (col == 4) {
 						p = new Queen(Team.GOLD, row-1, col-1);
 					}
 					if (col == 5) {
 						p = new King(Team.GOLD, row-1, col-1, null,null,null);
 					}
 				}
 				
 				boardArray[row-1][col-1] = new Square(row-1, col-1, p, r);
 			}
 		}
 		
		return boardArray;
	
 	}
 	
}
