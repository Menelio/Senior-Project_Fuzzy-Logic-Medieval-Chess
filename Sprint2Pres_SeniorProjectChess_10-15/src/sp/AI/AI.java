//Test

package sp.AI;

import java.util.List;

import sp.application.Move;

public abstract class AI {

	private List<Move> currentMoveList;
	
	/**<h1> Generate move list</h1>
	 * <p>Generates list of moves capable by this piece
	 * and sorts them by most advantageous
	 * </p>
	 * @return List<Move> List of moves in Move Objects*/
	public abstract List<Move> genMoves();

	public List<Move> getCurrentMoveList() {
		return currentMoveList;
	}
	
	
	
}
