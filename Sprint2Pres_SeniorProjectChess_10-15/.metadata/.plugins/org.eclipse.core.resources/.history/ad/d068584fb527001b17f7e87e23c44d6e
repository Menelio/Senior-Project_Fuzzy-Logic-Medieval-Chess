package sp.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.scene.control.skin.Utils;

import sp.Utils.MoveValueSorter;
import sp.application.Square;
import sp.pieces.Team;
import sp.pieces.Piece.PieceType;

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
		//populate master list moves with genMove form left Bishop
		master.addAll(leftBishop.genMoves(boardArray));
		//populate master list moves with genMove form right Bishop
		master.addAll(rightBishop.genMoves(boardArray));
		
		
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
			
			/*Old code
			//for now just randomly select a move from each piece
			int indx = (int)Math.random() *(currentPieceMoves.size()-1);
			toReturn.add(currentPieceMoves.get(indx));*/
			
			//sort current list by best first and add top to toReturn
			currentPieceMoves.sort(new MoveValueSorter());
			Collections.reverse(currentPieceMoves);
			toReturn.add(currentPieceMoves.get(0));
	
		}
		
		
		
		//create move parameters
		 int startRow;
		 int startColumn; 
		 int endRow; 
		 int endColumn; 
		 boolean attacking;
		 PieceType targetPiece;
		 int valueOfMove;
		 Move nextMove;
		
		int rowOffset[] = { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 }; //setup offsets
		int colOffset[] = {-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3 };
		
		for(int i=0; i < 28; i++ ) {
			if((row+rowOffset[i] > 0 && row+rowOffset[i]< 8) && (col+colOffset[i] >= 0 && col+colOffset[i]<8) &&//check if move is on the board 
					sp.Utils.General.doesPathExist(row, col, row+rowOffset[i], col+colOffset[i], 3, boardArray)){//check if there is a path to end square
				if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece()==null ) {//if this isn't an attack
					//create move parameters
					startRow = row;
					startColumn = col;
					endRow = row+rowOffset[i] ;
					endColumn = col+colOffset[i];
					attacking= false;
					targetPiece = null;					
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
					nextMove = null;
				}else if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getTeam() != this.teamColor){//if it is an attack
					//create move parameters
					startRow = row;
					startColumn = col;
					endRow = row+rowOffset[i] ;
					endColumn = col+colOffset[i];
					attacking= true;
					targetPiece = boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getPieceType();
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
					nextMove = null;
					toReturn.add(new Move(startRow, startColumn, endRow, endColumn,attacking, targetPiece,valueOfMove,nextMove,this.id));
				}
				
			} 
		}
		
		//sort list by MoveValue in descending order
		//toReturn.sort(new MoveValueSorter());
		//Collections.reverse(toReturn);
		
		

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
