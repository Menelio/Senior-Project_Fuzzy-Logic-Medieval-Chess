package sp.AI;

import java.util.List;

import sp.application.Square;
import sp.pieces.Team;

public class KingAI extends AI{
	private BishopAI leftBishopAI;
	private BishopAI rightBishopAI;
	private Team teamColor; 
	
	
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of KingAI with given values
	 * <p>
	 * @param leftBishopAI Left Bishop AI command by this king
	 * @param rightBishopAI Right Bishop AI command by this king
	 * @param teamColor Team color of king
	 * @author Menelio Alvarez
	 */
	public KingAI(BishopAI leftBishopAI, BishopAI rightBishopAI, Team teamColor) {
		super();
		this.leftBishopAI = leftBishopAI;
		this.rightBishopAI = rightBishopAI;
		this.teamColor = teamColor;
	}



	@Override
	public List<Move> genMoves(Square[][] boardArray, int row, int col) {
		
		return null;
	}

	
}
