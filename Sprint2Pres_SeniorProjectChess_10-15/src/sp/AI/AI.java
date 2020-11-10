package sp.AI;

import java.util.List;

import sp.application.Square;

public abstract class AI {
	//List of moves available to this AI
	protected List<Move> currentMoveList;
	
	protected int row;
	protected int column;
	protected String id;
	
	
	//TODO update comments for no row/col
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

	public List<Move> getCurrentMoveList() {
		return currentMoveList;
	}
	
	//TODO update comments, updates Row/col
	public void updateRowCol(int row, int col) {
		this.row= row;
		this.column = col;
	}
	
	//TODO create comments
	public String getId() {
		return id;
	}
	//TODO create comments
	public int getRow() {
		return row;
	}
	//TODO create comments
	public void setRow(int row) {
		this.row = row;
	}

	//TODO create comments
	public int getColumn() {
		return column;
	}

	//TODO create comments
	public void setColumn(int column) {
		this.column = column;
	}

	//TODO create comments
	public void setCurrentMoveList(List<Move> currentMoveList) {
		this.currentMoveList = currentMoveList;
	}

	//TODO create comments
	public void setId(String id) {
		this.id = id;
	}

}
