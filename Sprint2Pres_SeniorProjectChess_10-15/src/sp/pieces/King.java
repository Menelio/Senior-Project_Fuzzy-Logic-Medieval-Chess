/*Contributing team members
 * Steven Hansen
 * Menelio Alvarez
 * */
package sp.pieces;

import java.util.List;

import sp.AI.AI;
import sp.AI.BishopAI;
import sp.AI.KingAI;
import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Piece.PieceType;

public class King extends Piece {
	
	/**<h2>Constructor</h2>
	 * <p>
	 * This Constructor takes only the team argument and 
	 * two integer for the column and row
	 * </p>
	 * @param team Team enum
	 * @param row int row
	 * @param column int column
	 * @author Menelio Alvarez
	 * */
	public King(Team team, int row, int column, List<AI> subordinate ) {
		//if statement to set image based on team
		if(team == Team.GOLD) {
			super.setImg("file:Assets/gold_king.png");
		}else {
			super.setImg("file:Assets/black_king.png");
		}
		
		super.setRow(row);
		super.setColumn(column);
		super.setTeam(team);
		super.setPieceType(PieceType.KING);
		
		if(team == Team.BLACK) {
			super.setAi(new KingAI(subordinate,Team.BLACK ));
		}else {
			super.setAi(null);
		}
	}
	/* see super class for full description
	 * Implements to isLegalMove method from
	 * super class*/
	@Override
	public boolean isLegalMove(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray) {
		if(Math.abs(startRow - endRow) < 4 && Math.abs(startColumn - endColumn) < 4) {
			return sp.Utils.General.doesPathExist(startRow, startColumn, endRow, endColumn, 3, boardArray);
		}
		return false;
		
	}
	
	public String toString() {
		return "King";
	}
	

}
