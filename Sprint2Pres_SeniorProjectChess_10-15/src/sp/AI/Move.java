/*Contributing team members
 * Menelio Alvarez
 * */
package sp.AI;

import java.util.Comparator;

import sp.pieces.Piece.PieceType;

public class Move  {
	//global variable all final, set in constructors 
	 final private int startRow;
	 final private int startColumn;
	 final private int endRow;
	 final private int endColumn;
	 final private boolean attacking;
	 final private PieceType targetPiece;
	 final private int valueOfMove;
	 final private Move nextMove;
	 final private String PieceID;
	 
	 /**<h1>Defualt arguement constructor</h1>
	  * <p> The only constructor sets all fields. Contains starting 
	  * ending row and column, weather the move is and attack, if
	  * it is an attack type of target piece, and int value of move
	  * based on potential target pieces. All fields are final
	  * </p>
	  * @param startRow int of starting row.
	  * @param startColumn int of starting column.
	  * @param endRow int of end row.
	  * @param endColumn int of end column
	  * @parm attacking bool indicating if this move is an attack
	  * @param targetPiece PieceType of targeted bpiece
	  * @param nextMove Next move in this series of moves 
	  * @author Menelio Alvarez
	  * */
	 public Move(int startRow,int startColumn, int endRow,int endColumn, boolean attacking, PieceType targetPiece, int valueOfMove, Move nextMove, String PieceID) {
		 this.startRow = startRow;
		 this.startColumn = startColumn;
		 this.endRow = endRow;
		 this.endColumn = endColumn;
		 this.attacking = attacking;
		 this.targetPiece = targetPiece;
		 this.valueOfMove = valueOfMove;
		 this.nextMove = nextMove;
		 this.PieceID = PieceID;
		 
	 }

	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @return the startColumn
	 */
	public int getStartColumn() {
		return startColumn;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * @return the endColumn
	 */
	public int getEndColumn() {
		return endColumn;
	}

	/**
	 * @return the attacking
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * @return the targetPiece
	 */
	public PieceType getTargetPiece() {
		return targetPiece;
	}

	/**
	 * @return the valueOfMove
	 */
	public int getValueOfMove() {
		return valueOfMove;
	}

	/**
	 * @return the nextMove
	 */
	public Move getNextMove() {
		return nextMove;
	}

	/**
	 * @return the pieceID
	 */
	public String getPieceID() {
		return PieceID;
	}

	 
	 	 
}
