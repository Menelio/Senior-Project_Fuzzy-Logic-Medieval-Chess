package sp.AI;

import java.util.List;

import sp.application.Square;

public abstract class AI {
	//List of moves available to this AI
	private List<Move> currentMoveList;
	
	/**<h1> Generate move list</h1>
	 * <p>Generates list of moves capable by this piece
	 * and sorts them by most advantageous
	 * </p>
	 * @param boardArray 2D array of Square[][] 
	 * @param row int of current row of this piece
	 * @param col int of current column of this piece
	 * @return List<Move> List of moves in Move Objects
	 * @author Menelio Alvarez*/
	public abstract List<Move> genMoves(Square[][] boardArray, int row, int col);

	public List<Move> getCurrentMoveList() {
		return currentMoveList;
	}
	
	
	
}
