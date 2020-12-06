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
	public King(Team team, int row, int column, List<SubordinateAI> subordinate,BishopAI leftBishop, BishopAI rightBishop ) {
		super.setCorpNum(1);
		
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

		this.setAi( new KingAI(subordinate, leftBishop, rightBishop, team, row, column ));
				
	}
	/* see super class for full description
	 * Implements to isLegalMove method from
	 * super class*/
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

	/**
	 * @returns String "King"
	 * */
	public String toString() {
		return "King";
	}
	@Override
	public KingAI getAi() {
		// TODO Auto-generated method stub
		return (KingAI) super.ai;
	}
	@Override
	public void setAi(Object ai) {
		super.ai=(KingAI)ai;
		
	}
	

	
	
	

	

}
