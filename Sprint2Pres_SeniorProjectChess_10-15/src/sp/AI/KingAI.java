package sp.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sp.Utils.MoveValueSorter;
import sp.application.Square;
import sp.pieces.Team;

public class KingAI extends AI{
	private List<AI> subordinate;
	private Team teamColor; 
	private BishopAI leftBishop;
	private BishopAI rightBishop;
	
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of KingAI with given values
	 * <p>
	 * @param leftBishopAI Left Bishop AI command by this king
	 * @param rightBishopAI Right Bishop AI command by this king
	 * @param teamColor Team color of king
	 * @author Menelio Alvarez
	 */
	public KingAI(List<AI> subordinate, BishopAI leftBishop, BishopAI rightBishop, Team teamColor, int row, int col) {
		this.subordinate = subordinate;
		this.leftBishop = leftBishop;
		this.rightBishop = rightBishop;
		this.teamColor = teamColor;
		super.row = row;
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
		
		List<Move> toReturn= new ArrayList<Move>();
		//trim master move list so only one move per piece, select best move
		for(int i=0; i < master.size();i++) {
			
			List<Move> currentPieceMoves= new ArrayList<Move>();//List to hold current pieces being consider moves
			String currentID = master.get(i).getPieceID();
			
				
			while(i < master.size() && master.get(i).getPieceID() == currentID ) {
				currentPieceMoves.add(master.get(i));
				i++;
			}

			if(i<master.size()) {
				currentPieceMoves.add(master.get(i));
			}

			//for now just randomly select a move from each piece
			int indx = (int)Math.random() *(currentPieceMoves.size()-1);
			toReturn.add(currentPieceMoves.get(indx));
	
		}
		//sort list by MoveValue in descending order
		toReturn.sort(new MoveValueSorter());
		Collections.reverse(toReturn);
		//TODO generate king and its subs  move, add to toReturn list, and sort master list

		return toReturn;
	}


	/**
	 * @return subordinate
	 */
	public List<AI> getSubordinate() {
		return subordinate;
	}



	/**
	 * @return the leftBishop
	 */
	public BishopAI getLeftBishop() {
		return leftBishop;
	}



	/**
	 * @param leftBishop the leftBishop to set
	 */
	public void setLeftBishop(BishopAI leftBishop) {
		this.leftBishop = leftBishop;
	}



	/**
	 * @return the rightBishop
	 */
	public BishopAI getRightBishop() {
		return rightBishop;
	}



	/**
	 * @param rightBishop the rightBishop to set
	 */
	public void setRightBishop(BishopAI rightBishop) {
		this.rightBishop = rightBishop;
	}


	
}
