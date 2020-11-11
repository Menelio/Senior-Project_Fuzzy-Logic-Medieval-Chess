package sp.AI;

import java.util.ArrayList;
import java.util.List;

import sp.application.Square;
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
		this.id = ""+row+""+col;
	}




	@Override
	public List<Move> genMoves(Square[][] boardArray) {
		int row = this.row;
		int col = this.column;
		//all moves capable of being made by this corp
		List<Move> master= new ArrayList<Move>();
		
		//populate master list moves with genMove form subs
		for(int i=0; i < subordinate.size();i++) {
			master.addAll(subordinate.get(i).genMoves(boardArray));
		}
		
		//TODO generate bishops move, add to master list, and sort master list
		
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
	
	
}
