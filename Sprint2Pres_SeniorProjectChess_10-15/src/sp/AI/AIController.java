package sp.AI;

import java.util.List;

import sp.application.Square;
import sp.pieces.Team;

public class AIController {

	private KingAI kingAI;

	private Team teamColor = Team.BLACK;
	
	
	
	/**TODO Fill out comments and check
	 * @param kingAI
	 */
	public AIController(KingAI kingAI) {
		super();
		this.kingAI = kingAI;
	}

	
	/**TODO Fill out comments
	 * */
	public List<Move> requestMoves(Square[][] boardArray, int row, int col){
		List<Move> master = kingAI.genMoves(boardArray, row, col);
		return master;
	}
	
	public KingAI TEstGetKAI() {
		return this.kingAI;
	}
}
