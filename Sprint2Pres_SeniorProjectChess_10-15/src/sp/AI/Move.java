package sp.AI;

import sp.pieces.Piece.PieceType;

public class Move {
	//global variable all final, set in constructors 
	 final private int startRow;
	 final private int startColumn;
	 final private int endRow;
	 final private int endColumn;
	 final private boolean attacking;
	 final private PieceType targetPiece;
	 final private int valueOfMove;
	 final private Move nextMove;
	 
	 
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
	 public Move(int startRow,int startColumn, int endRow,int endColumn, boolean attacking, PieceType targetPiece, int valueOfMove, Move nextMove) {
		 this.startRow = startRow;
		 this.startColumn = startColumn;
		 this.endRow = endRow;
		 this.endColumn = endColumn;
		 this.attacking = attacking;
		 this.targetPiece = targetPiece;
		 this.valueOfMove = valueOfMove;
		 this.nextMove = nextMove;
	 }

	 //getters
	public int getStartRow() {
		return startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public PieceType getTargetPiece() {
		return targetPiece;
	}

	public int getValueOfMove() {
		return valueOfMove;
	}

	public Move getNextMove() {
		return nextMove;
	}
	 
	
	 
}
