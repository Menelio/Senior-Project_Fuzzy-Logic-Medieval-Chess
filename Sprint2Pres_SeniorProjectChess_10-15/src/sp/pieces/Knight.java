/*Contributing team members
 *Menelio Alvarez
 * */
package sp.pieces;

import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Piece.PieceType;

public class Knight extends Piece {
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
	public Knight(Team team, int row, int column ) {
		
		//if statement to set image based on team
		if(team == Team.GOLD) {
			super.setImg("file:Assets/gold_knight.png");
		}else {
			super.setImg("file:Assets/black_knight.png");
		}
		
		super.setRow(row);
		super.setColumn(column);		
		super.setTeam(team);
		super.setPieceType(PieceType.KNIGHT);
		if(team == Team.BLACK) {
			super.setAi(new SubordinateAI(Team.BLACK, PieceType.KNIGHT));
		}else {
			super.setAi(null);
		}
	}
	/* see super class for full description
	 * Implements to isLegalMove method from
	 * super class*/
	@Override 
	public boolean isLegalMove(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray) {
		if(Math.abs(startRow - endRow) < 6 && Math.abs(startColumn - endColumn) < 6) {
			return sp.Utils.General.doesPathExist(startRow, startColumn, endRow, endColumn, 5, boardArray);
		}
		return false;
	}
	
	public String toString() {
		return "Knight";
	}
}
