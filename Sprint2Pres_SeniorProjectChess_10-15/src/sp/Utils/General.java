/*Contributing team members
 * Richard OlgalTree
 * Menelio Alvarez
 * */
package sp.Utils;

import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import sp.application.Square;
import sp.pieces.Piece.PieceType;
import sp.pieces.Team;
//TODO rework so each team has it's own values and they are changeable
public class General {
	
	//only to be used by doesPathExist
	private static class PathNode{
		int row;//row of path node
		int column;//column of path node
		int d;//distance from source
		public PathNode(int row, int column, int d) {
			super();
			this.row = row;
			this.column = column;
			this.d = d;
		}

	}
	
	/**<h1>Does Path Exist</h1>
	 * <p>Given starting & ending row & column, the max number of
	 * 	allowed, and Square[][] of the board this method
	 * 	returns a boolean indicating wither there is a path that 
	 *  does not include any team pieces.
	 * </p>
	 * @param startRow Integer of starting row
	 * @param startColumn Integer of starting column
	 * @param endRow Integer of ending row
	 * @param endColumn Integer of ending column
	 * @param maxNumberOfMoves Integer indicating max number of 
	 * allowed spaces
	 * @param boardArray 2D square array of board
	 * @return boolean true if there is a path
	 * @author Richard OlgalTree & Menelio Alvarez
	 * */
	public static boolean doesPathExist(int startRow, int startColumn, int endRow, int endColumn, int maxNumberOfMoves, Square[][] boardArray) {
		//to keep track of visited squares
		boolean[][] visited = new boolean[8][8];

		//team of piece
		Team team = null;
		if(boardArray[startRow][startColumn].getPiece() != null) {
			team = boardArray[startRow][startColumn].getPiece().getTeam();
		}else {
			return false;
		}
		//Que to keep track of path
		Queue<PathNode> pathQ= new LinkedList<>();
		
		
		//initialize with source
		PathNode p =new PathNode(startRow, startColumn, 0); 
		//add it to Q
		pathQ.add(p);

		//start search
		while(!pathQ.isEmpty()) {
			
			//set up current pathnode we're considering
			PathNode current = pathQ.peek();
			int curRow = current.row;
			int curCol = current.column;
			int dis = current.d;
			//if current node is distination return true
			
			if(curRow == endRow && curCol == endColumn) {
				return true;
			}
			
			//remove last node from que
			pathQ.remove();
			
            int moveCount=0;
			//These arrays are used to get row and column 
			//numbers of 4 neighbours of a given cell 
			int rowNum[] = {1, 1, 1, 0, 0, -1, -1, -1}; 
			int colNum[] = {-1, 0, 1, -1, 1, -1, 0, 1}; 
			
			for(int i=0; i < 8 ;i++) {
				int row = curRow + rowNum[i]; 
	            int col = curCol  + colNum[i]; 
	            
	           
	            // if adjacent square is valid, is empty, is with in maxNumberOfMoves   
	            // and not visited yet, enqueue it. 
	            if(row >=0 && col >=0 && row < 8 && col < 8 //check if valid
	            && moveCount < maxNumberOfMoves //check is with in maxNumberOfMoves
	            && (boardArray[row][col].getPiece() == null || //check if space is empty or enemy
	            (boardArray[row][col].getPiece().getTeam() != team && (row == endRow && col == endColumn))) 
	            && !visited[row][col]) {
	            	visited[row][col] = true;
	            	PathNode adj =new PathNode(row, col, (dis+1)); 
	            	pathQ.add(adj);
	            	
	            }
			}
			moveCount++;
		}
		
		return false;
	}
	
	/**<h1>Calculate value of move</h1>
	 * <p>Calculates the value of a move given start row, start column,
	 * end row, end column and current Square[][] boardArray
	 * </p>
	 * @param startRow int of starting row.
	 * @param startColumn int of starting column.
	 * @param endRow int of end row.
	 * @param endColumn int of end column
	 * @param boardArray 2D array of Square[][] 
	 * @return int representy value of move
	 * @Note Currently only returns 0, must be filled in.
	 * @author Menelio Alvarez
	 * */
	public static int calcMoveValue(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray) {
		//TODO (still needs tweaking) figure out how we should calculate move values.
		//create attacker and defender
		if(boardArray[startRow][startColumn].getPiece()==null) {
			return -1;
		}
		
		
		PieceType attacker = boardArray[startRow][startColumn].getPiece().getPieceType();
		
		int capkValueA = capValue(attacker);
		int movValue  = moveValue(attacker);
		double avrSuccess= getAvrSuccessRate(attacker);
		
		//calculate move value if attacking
		if(boardArray[endRow][endColumn].getPiece() != null) {
			PieceType defender = boardArray[endRow][endColumn].getPiece().getPieceType();
			
			double avrDefeat= getAvrDefeatRate(defender);
			int capValue = capValue(defender);

			int value = (int)( (-(capkValueA - movValue)*(1/avrDefeat)) + (capValue * avrSuccess ));
			return value;
			
		}else {//calculate move value if not attacking

			int value = -(capkValueA+movValue);
			return value;
		}
		
	}
	/*Get average defeat rate*/
	private static double getAvrDefeatRate(PieceType piece) {
		switch(piece) {
		case PAWN:
			return .69;	
		case ROOK:
			return .28;
		case BISHOP:
			return .44;
		case KNIGHT:
			return .39;
		case QUEEN:
			return .36;
		case KING:
			return .36;
		}
		return 0.0;
	}
	
	/*Get average defeat rate*/
	private static double getAvrSuccessRate(PieceType piece) {
		switch(piece) {
		case PAWN:
			return .22;	
		case ROOK:
			return .36;
		case BISHOP:
			return .41;
		case KNIGHT:
			return .42;
		case QUEEN:
			return .53;
		case KING:
			return .55;
		}
		return 0;
		
	}
	
	/*Get move Value*/
	private static int moveValue(PieceType piece) {
		switch(piece) {
		case PAWN:
			return 30;	
		case ROOK:
			return 40;
		case BISHOP:
			return 20;
		case KNIGHT:
			return 60;
		case QUEEN:
			return 50;
		case KING:
			return 10;
		}
		return 0;
	
	}
	
	/*Get capture Value*/
	private static int capValue(PieceType piece) {
		switch(piece) {
		case PAWN:
			return 10;	
		case ROOK:
			return 30;
		case BISHOP:
			return 50;
		case KNIGHT:
			return 40;
		case QUEEN:
			return 20;
		case KING:
			return 60;
		}
		return 0;
	}
	
	/**<h1> Replace Node in GridPane </h1>
	 * <p>Replace a node in a grid pane cell </p>
	 * @param gridPane Grid pane containing node
	 * @param col Column of node
	 * @param row Row of node
	 * @param n replacement node
	 * @author Menelio Alvarez
	 * */
	public static void getNodeFromGridPane(GridPane gridPane, int col, int row, Node n) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            gridPane.getChildren().remove(node); 
	        	node= n;
	        	return;
	        }
	    }
	}
}
