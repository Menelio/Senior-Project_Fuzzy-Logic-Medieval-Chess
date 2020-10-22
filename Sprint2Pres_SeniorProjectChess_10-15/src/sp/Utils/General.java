/*Contributing team members
 * Richard OlgalTree
 * Menelio Alvarez
 * */
package sp.Utils;

import java.util.LinkedList;
import java.util.Queue;

import sp.application.Square;

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
	            && (boardArray[row][col].getPiece() == null || //check if space is empty of enemy
	            (boardArray[row][col].getPiece().getTeam() != boardArray[startRow][startColumn].getPiece().getTeam() && (row == endRow && col == endColumn))) 
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
}
