/*Contributing team members
 * Richard Ogletree
 * Menelio Alvarez
 * */
package sp.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sp.Utils.MoveValueSorter;
import sp.application.Square;
import sp.pieces.Piece.PieceType;
import sp.pieces.Team;

public class BishopAI extends AI {

	private List<SubordinateAI> subordinate;
	private Team teamColor; 
	private int row;
	private int column;
	private String id;
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of BishopAI with given values
	 * <p>
	 * @param subordinate List of subordinate AI this Bishop commands
	 * @param teamColor Color of team this AI belongs to
	 * @author Menelio Alvarez
	 */
	public BishopAI(List<SubordinateAI> subordinate, Team teamColor, int row, int col) {
		super();
		this.subordinate = subordinate;
		this.teamColor = teamColor;
		this.row = row;
		this.column = col;
		this.id = ""+row+""+col+"-"+PieceType.BISHOP;
	}
	
	//See super comments
	@Override
	public List<Move> genMoves(Square[][] boardArray) {
		int row = this.row;
		int col = this.column;
		//all moves capable of being made by this corp
		List<Move> master= new ArrayList<Move>();
		
		int startRow;
		int startColumn; 
		int endRow; 
		int endColumn; 
		boolean attacking;
		PieceType targetPiece;
		int valueOfMove;
		Move nextMove;
		
		// position offsets
		int bishopRowOffset[] = { 1, 1, 1, 0, 0, 0,-1,-1,-1};
		int bishopColOffset[] = {-1, 0, 1,-1, 0, 1,-1, 0, 1};
		
		// run through possible moves for bishop
		for (int i = 0; i < 3; i++) {
			if ((row+bishopRowOffset[i] > 0 && row+bishopRowOffset[i] < 8) && (col+bishopColOffset[i] >= 0 && col+bishopColOffset[i] < 8)) {
				if (boardArray[row+bishopRowOffset[i]][col+bishopColOffset[i]].getPiece() == null) {
					startRow = row;
					startColumn = col;
					endRow = row + bishopRowOffset[i] ;
					endColumn = col + bishopColOffset[i];
					attacking = false;
					targetPiece = null;
					
					
					// calculate the numeric value of the current move
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row + bishopRowOffset[i], col + bishopColOffset[i], boardArray);
					
					nextMove = null;
					
					master.add(new Move(startRow, startColumn, endRow, endColumn, attacking, targetPiece, valueOfMove, nextMove, this.id));
				}
				else if (boardArray[row+bishopRowOffset[i]][col+bishopColOffset[i]].getPiece().getTeam() != this.teamColor) {
					startRow = row;
					startColumn = col;
					endRow = row + bishopRowOffset[i];
					endColumn = col + bishopColOffset[i];
					attacking= true;
					targetPiece = boardArray[row+bishopRowOffset[i]][col+bishopColOffset[i]].getPiece().getPieceType();
					
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+bishopRowOffset[i], col+bishopColOffset[i], boardArray);
					
					nextMove = null;
					
					master.add(new Move(startRow, startColumn, endRow, endColumn, attacking, targetPiece, valueOfMove, nextMove, this.id));
				}
			}
		}
		//populate master list moves with genMove form subs
		for(int i=0; i < subordinate.size();i++) {
			master.addAll(subordinate.get(i).genMoves(boardArray));
		}
		master.sort(new MoveValueSorter());
		Collections.reverse(master);
		Collections.shuffle(master);
		
		
		return master;
	}
	/**<h2>Update BishopAI row and column</h2>
	 * <p>
	 *	Take two ints and set row and column
	 * </p>
	 * @param row int for row
	 * @param col int for column.
	 * @author Menelio Alvarez
	 * */
	public void updateRowCol(int row, int col) {
		this.row= row;
		this.column = col;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the subordinate
	 */
	public List<SubordinateAI> getSubordinate() {
		return subordinate;
	}
	
	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
}
