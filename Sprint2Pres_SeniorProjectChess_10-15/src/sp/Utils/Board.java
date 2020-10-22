/*Contributing team members
 * Richard OlgalTree
 * Menelio Alvarez
 * */
package sp.Utils;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
 		for (int row = 1; row <= BOARD_SIZE; row++) {
 			for (int col = 1; col <= BOARD_SIZE; col++) {
 				Group temp = new Group();
 				Rectangle r = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, squareColors[(row+col)%2]);
 				Piece p = null;
 						
 				temp.getChildren().add(r);
 				if (row == 1) {
 					if (col == 1 || col == 8) {
 						p = new Rook(Team.BLACK, row-1, col-1);
 					}
 					if (col == 2 || col == 7) {
 						p = new Knight(Team.BLACK, row-1, col-1);
 					}
 					if (col == 3 || col == 6) {
 						p = new Bishop(Team.BLACK, row-1, col-1);
 					}
 					if (col == 4) {
 						p = new Queen(Team.BLACK, row-1, col-1);
 					}
 					if (col == 5) {
 						p = new King(Team.BLACK, row-1, col-1);
 					}
 				}
 				if (row == 2) {
						p = new Pawn(Team.BLACK, row-1, col-1);
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
 						p = new Bishop(Team.GOLD, row-1, col-1);
 					}
 					if (col == 4) {
 						p = new Queen(Team.GOLD, row-1, col-1);
 					}
 					if (col == 5) {
 						p = new King(Team.GOLD, row-1, col-1);
 					}
 				}
 				
 				boardArray[row-1][col-1] = new Square(row-1, col-1, p, r);
 			}
 		}
		return boardArray;
	
 	}
 	
}
