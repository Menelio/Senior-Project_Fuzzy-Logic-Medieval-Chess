/*Contributing team members
	Steven Hansen
 * */
package sp.pieces;

import sp.AI.AI;
import sp.AI.SubordinateAI;
import sp.application.Square;
import sp.pieces.Piece.PieceType;

public class Queen extends Piece {
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
	public Queen(Team team, int row, int column ) {
		super.setCorpNum(1);
		//if statement to set image based on team
		if(team == Team.GOLD) {
			super.setImg("file:Assets/gold_queen.png");
		}else {
			super.setImg("file:Assets/black_queen.png");
		}
		
		super.setRow(row);
		super.setColumn(column);
		super.setTeam(team);
		super.setPieceType(PieceType.QUEEN);
		this.setAi(new SubordinateAI(team, PieceType.QUEEN, row, column));

	}

	@Override
	public boolean isLegalMove(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray) {
		if(boardArray[endRow][endColumn].getPiece()==null) {
			if(Math.abs(startRow - endRow) < 4 && Math.abs(startColumn - endColumn) < 4) {
				return sp.Utils.General.doesPathExist(startRow, startColumn, endRow, endColumn, 3, boardArray);
			}
		}else if( (Math.abs(startRow-endRow)<2) && (Math.abs(startColumn-endColumn)<2) ){
			return true;
		}
		return false;
	}

	public String toString() {
		return "Queen";
	}
	
	@Override
	public AI getAi() {
		return (SubordinateAI) super.ai;
	}

	@Override
	public void setAi(Object ai) {
		super.ai=(SubordinateAI)ai;
	}
}
