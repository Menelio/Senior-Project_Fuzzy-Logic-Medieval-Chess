package sp.AI;

import java.util.ArrayList;
import java.util.List;

import sp.application.Square;
import sp.pieces.Team;

public class KingAI extends AI{
	private List<AI> subordinate;
	private Team teamColor; 
	
	
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of KingAI with given values
	 * <p>
	 * @param leftBishopAI Left Bishop AI command by this king
	 * @param rightBishopAI Right Bishop AI command by this king
	 * @param teamColor Team color of king
	 * @author Menelio Alvarez
	 */
	public KingAI(List<AI> subordinate, Team teamColor) {
		this.subordinate = subordinate;
		this.teamColor = teamColor;
	}



	@Override
	public List<Move> genMoves(Square[][] boardArray, int row, int col) {
		
		//all moves capable of being made by this corp
		List<Move> master= new ArrayList<Move>();
		
		//populate master list moves with genMove form subs
		for(int i=0; i < subordinate.size();i++) {
			master.addAll(subordinate.get(i).genMoves(boardArray, row, col));
		}
		
		//TODO generate king and its subs  move, add to master list, and sort master list
		
		return master;
	}


	//to Test
	public List<AI> getSubordinate() {
		return subordinate;
	}

	
	
}
