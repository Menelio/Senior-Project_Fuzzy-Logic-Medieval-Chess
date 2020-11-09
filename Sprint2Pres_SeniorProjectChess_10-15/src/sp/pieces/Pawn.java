/*Contributing team members
 * Richard Ogletree
 * Luis Nguyen
 * */
package sp.pieces;

import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Piece.PieceType;

public class Pawn extends Piece {
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
	public Pawn(Team team, int row, int column) {
		//if statement to set image based on team
		if(team == Team.GOLD) {
			super.setImg("file:Assets/gold_Pawn.png");
		}else {
			super.setImg("file:Assets/black_Pawn.png");
		}
		
		super.setRow(row);
		super.setColumn(column);
		super.setTeam(team);
		super.setPieceType(PieceType.PAWN);
		
		if(team == Team.BLACK) {
			super.setAi(new SubordinateAI(Team.BLACK, PieceType.PAWN));
		}else {
			super.setAi(null);
		}
	}
	
	//Comments in super class
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
	
	public String toString() {
		return "Pawn";
	}
}
