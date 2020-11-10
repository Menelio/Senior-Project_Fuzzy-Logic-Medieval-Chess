/*Contributing team members
 * Luis Nguyen
 * */
package sp.pieces;

import java.util.List;

import sp.AI.BishopAI;
import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Piece.PieceType;

public class Bishop extends Piece {
	BishopAI ai;
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
	public Bishop(Team team, int row, int column, List<SubordinateAI> subordinate ) {
		
		//if statement to set image based on team
		if(team == Team.GOLD) {
			super.setImg("file:Assets/gold_bishop.png");
			
		}else {
			super.setImg("file:Assets/black_bishop.png");
		}
		
		super.setRow(row);
		super.setColumn(column);
		//super.pieceType = PieceType.BISHOP;
		super.setTeam(team);
		super.setPieceType(PieceType.BISHOP);
		
		
		if(team == Team.BLACK) {
			ai = (new BishopAI(subordinate,Team.BLACK ,row, column));
			
		}else {
			super.setAi(null);
		}
		
	}
	/* see super class for full description
	 * Implements to isLegalMove method from
	 * super class*/
	@Override
	public boolean isLegalMove(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray) {
		if(this.getTeam() == Team.GOLD) {
			if (startRow - endRow == 1) {
				if (endColumn == startColumn || Math.abs(startColumn - endColumn) == 1) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			if (endRow - startRow == 1) {
				if (endColumn == startColumn || Math.abs(startColumn - endColumn) == 1) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		return false;
	}

	@Override
	public BishopAI getAi() {
		return ai;
	}
	
	public String toString() {
		return "Bishop";
	}
}
