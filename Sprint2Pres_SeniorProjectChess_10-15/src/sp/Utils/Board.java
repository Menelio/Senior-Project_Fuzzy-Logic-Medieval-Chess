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
 		List<SubordinateAI> leftBishopSubordinates=new ArrayList<SubordinateAI>();
 		List<SubordinateAI> rightBishopSubordinates=new ArrayList<SubordinateAI>();
 		List<AI> KingBishopSubordinates=new ArrayList<AI>();
 		//create sub piece and add them to list of sub AI
 		Pawn[] pawn = new Pawn[8];
 		for(int i =0; i < pawn.length;i++) {
 			pawn[i]= new Pawn(Team.BLACK,1, i);
 			
 			if(i < 3) {
 				leftBishopSubordinates.add((SubordinateAI) pawn[i].getAi());
 			}else {
 				rightBishopSubordinates.add((SubordinateAI) pawn[i].getAi());
 			}
 		}
 		
 		Rook leftRook = new Rook(Team.BLACK,0, 0);
 		leftBishopSubordinates.add((SubordinateAI) leftRook.getAi());
 		Rook rightRook = new Rook(Team.BLACK,0, 7);
 		rightBishopSubordinates.add((SubordinateAI) rightRook.getAi());
 		
 		Knight leftKnight = new Knight(Team.BLACK,0, 0);
 		leftBishopSubordinates.add((SubordinateAI) leftKnight.getAi());
 		Knight rightKnight = new Knight(Team.BLACK,0, 6);
 		rightBishopSubordinates.add((SubordinateAI) rightRook.getAi());
 		
 		//create AI Command pieces
 		Bishop leftBishop= new Bishop(Team.BLACK,0, 2, leftBishopSubordinates);
 		Bishop rightBishop= new Bishop(Team.BLACK,0, 5, rightBishopSubordinates);
 		
 		Queen queen = new Queen(Team.BLACK,0, 3);
 		KingBishopSubordinates.add(leftBishop.getAi());
 		KingBishopSubordinates.add(rightBishop.getAi());
 		KingBishopSubordinates.add(queen.getAi());
 		
 		King king = new King(Team.BLACK, 0, 4, KingBishopSubordinates, leftBishop.getAi(), rightBishop.getAi() );
 		
 		for (int row = 1; row <= BOARD_SIZE; row++) {
 			for (int col = 1; col <= BOARD_SIZE; col++) {
 				Group temp = new Group();
 				Rectangle r = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]);
 				Piece p = null;
 						
 				temp.getChildren().add(r);
 				if (row == 1) {
 					if (col == 1 || col == 8) {
 						if(col == 1 ) {
 							p = leftRook;
 						}else {
 							p = rightRook;
 						}
 					}
 					if (col == 2 || col == 7) {
 						if(col == 2 ) {
 							p = leftKnight;
 						}else {
 							p = rightKnight;
 						}
 					}
 					if (col == 3 || col == 6) {
 						if(col == 3 ) {
 							p = leftBishop;
 						}else {
 							p = rightBishop;
 						}
 						
 					}
 					if (col == 4) {
 						p = queen;
 					}
 					if (col == 5) {
 						p = king;
 					}
 				}
 				if (row == 2) {
 						p = pawn[col-1];
				}
 				if (row == 7) {
 					p = new Pawn(Team.GOLD, row-1, col-1);
 				}
 				if (row == 8) {
 					if (col == 1 || col == 8) {
 						p = new Rook(Team.GOLD, row-1, col-1);
 					}
 					if (col == 2 || col == 7) {
 						p = new Knight(Team.GOLD, row-1, col-1);
 					}
 					if (col == 3 || col == 6) {
 						p = new Bishop(Team.GOLD, row-1, col-1, null);
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
