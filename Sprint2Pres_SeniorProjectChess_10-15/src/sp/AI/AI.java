/*Contributing team members
 * Menelio Alvarez
 * */
package sp.AI;

import java.util.List;

import sp.application.Square;

public abstract class AI {
	//List of moves available to this AI
	protected List<Move> currentMoveList;
	
	protected int row;
	protected int column;
	protected String id;
	
	
	/**<h1> Generate move list</h1>
	 * <p>Generates list of moves capable by this piece
	 * and sorts them by most advantageous
	 * </p>
	 * @param boardArray 2D array of Square[][] 
	 * @param row int of current row of this piece
	 * @param col int of current column of this piece
	 * @return List<Move> List of moves in Move Objects
	 * @author Menelio Alvarez*/
	public abstract List<Move> genMoves(Square[][] boardArray);

	/**<h1>Get current List of moves for this AI
	 * </h1>
	 * <p>Returns the List of moves most recently generated
	 * by This AI.
	 * </p>
	 * @return List<Move> list of Move object
	 * @author Menelio Alvarez*/
	public List<Move> getCurrentMoveList() {
		return currentMoveList;
	}
	
	/**<h2>Update pieceAI row and column</h2>
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
	 * @return String ID of piece AI
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return int Row
	 */
	public int getRow() {
		return row;
	}


	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
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

	/**
	 * @param currentMoveList the currentMoveList to set
	 */
	public void setCurrentMoveList(List<Move> currentMoveList) {
		this.currentMoveList = currentMoveList;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



}
