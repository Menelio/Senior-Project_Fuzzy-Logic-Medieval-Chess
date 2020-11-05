package sp.AI;

import java.util.List;

import sp.application.Square;
import sp.pieces.Team;

public class BishopAI extends AI {

	private List<SubordinateAI> subordinate;
	private Team teamColor; 
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of BishopAI with given values
	 * <p>
	 * @param subordinate List of subordinate AI this Bishop commands
	 * @param teamColor Color of team this AI belongs to
	 * @author Menelio Alvarez
	 */
	public BishopAI(List<SubordinateAI> subordinate, Team teamColor) {
		super();
		this.subordinate = subordinate;
		this.teamColor = teamColor;
	}




	@Override
	public List<Move> genMoves(Square[][] boardArray, int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

}
