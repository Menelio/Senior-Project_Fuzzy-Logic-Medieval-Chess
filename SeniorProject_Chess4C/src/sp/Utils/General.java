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
	private static int turnCount=0;
	
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
		
		if(startRow<0 || startRow >7 || startColumn<0 || startColumn >7) {
			return false;
		}
		
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

	/**
	 * @return the turnCount
	 */
	public static int getTurnCount() {
		return turnCount;
	}

	/**<h1>Increment turn count</h1>
	 * <p>Increments turn count by one </p>
	 */
	public static void incrementTurnCount() {
		General.turnCount++;
	}
	
	/*----------------------Calculate Move Value Code----------------------*/

	//values for calculate move value equation
	//Black King
	 static int BlackKingMoveValue = 10;
	 static int	BlackKingCapValue = 60;
	 static double BlackKingAvrDefeatRate= .36;
	 static double BlackKingAvrSuccessRate= .55;
	//Black Queen
	 static int BlackQueenMoveValue = 50;
	 static int	BlackQueenCapValue = 20;
	 static double BlackQueenAvrDefeatRate= .36;
	 static double BlackQueenAvrSuccessRate= .53;
	//Black Knight
	 static int BlackKnightMoveValue = 60;
	 static int	BlackKnightCapValue = 40;
	 static double BlackKnightAvrDefeatRate= .39;
	 static double BlackKnightAvrSuccessRate= .42;
	//Black Bishop
	 static int BlackBishopMoveValue = 20;
	 static int	BlackBishopCapValue = 50;
	 static double BlackBishopAvrDefeatRate= .44;
	 static double BlackBishopAvrSuccessRate= .41;
	//Black Rook
	 static int BlackRookMoveValue = 40;
	 static int	BlackRookCapValue = 30;
	 static double BlackRookAvrDefeatRate= .28;
	 static double BlackRookAvrSuccessRate= .36;
	//Black Pawn
	 static int BlackPawnMoveValue = 40;
	 static int	BlackPawnCapValue = 30;
	 static double BlackPawnAvrDefeatRate= .69;
	 static double BlackPawnAvrSuccessRate= .22;
	
	//Gold King
	 static int GoldKingMoveValue = 10;
	 static int	GoldKingCapValue = 60;
	 static double GoldKingAvrDefeatRate= .36;
	 static double GoldKingAvrSuccessRate= .55;
	//Gold Queen
	 static int GoldQueenMoveValue = 50;
	 static int	GoldQueenCapValue = 20;
	 static double GoldQueenAvrDefeatRate= .36;
	 static double GoldQueenAvrSuccessRate= .53;
	//Gold Knight
	 static int GoldKnightMoveValue = 60;
	 static int	GoldKnightCapValue = 40;
	 static double GoldKnightAvrDefeatRate= .39;
	 static double GoldKnightAvrSuccessRate= .42;
	//Gold Bishop
	 static int GoldBishopMoveValue = 20;
	 static int	GoldBishopCapValue = 50;
	 static double GoldBishopAvrDefeatRate= .44;
	 static double GoldBishopAvrSuccessRate= .41;
	//Gold Rook
	 static int GoldRookMoveValue = 40;
	 static int	GoldRookCapValue = 30;
	 static double GoldRookAvrDefeatRate= .28;
	 static double GoldRookAvrSuccessRate= .36;
	//Gold Pawn
	 static int GoldPawnMoveValue = 40;
	 static int	GoldPawnCapValue = 30;
	 static double GoldPawnAvrDefeatRate= .69;
	 static double GoldPawnAvrSuccessRate= .22;
	 
	/**<h1>Calculate value of move</h1>
	 * <p>Calculates the value of a move given start row, start column,
	 * end row, end column and current Square[][] boardArray
	 * </p>
	 * @param startRow int of starting row.
	 * @param startColumn int of starting column.
	 * @param endRow int of end row.
	 * @param endColumn int of end column
	 * @param boardArray 2D array of Square[][] 
	 * @param team Is the Team of attacker.
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
		Team attackerTeam = boardArray[startRow][startColumn].getPiece().getTeam();
		int capValueA = capValue(attacker, attackerTeam);
		int movValue  = moveValue(attacker, attackerTeam);
		double avrSuccess= getAvrSuccessRate(attacker,attackerTeam);
		
		//calculate move value if attacking
		if(boardArray[endRow][endColumn].getPiece() != null) {
			PieceType defender = boardArray[endRow][endColumn].getPiece().getPieceType();
			Team defenderTeam = boardArray[endRow][endColumn].getPiece().getTeam();
			double avrDefeat= getAvrDefeatRate(defender, defenderTeam);
			int capValue = capValue(defender, defenderTeam);

			int value = (int)( (-(capValueA - movValue)*(1/avrDefeat)) + (capValue * avrSuccess ));
			return value;
			
		}else {//calculate move value if not attacking

			int value = -(capValueA+movValue);
			return value;
		}
		
	}
	
	/**<h1>Returns the value of piece</h1>
	 * <p>Given the team and piece type this method will returns the 
	 * currten value of the piece </p>
	 * @param team Team of piece
	 * @param type Piece Type of piece
	 * @return double Value of piece
	 * @author Menelio Alvarez*/
	public static double getValueOfPiece(Team team, PieceType type) {
		
		double value=0;
		//(-(capkValueA - movValue)*(1/avrDefeat)) + (capValue * avrSuccess ))
		switch(type) {
		case PAWN:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		case ROOK:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		case BISHOP:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		case KNIGHT:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		case QUEEN:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		case KING:
			if(team == Team.BLACK) {
				return (BlackPawnMoveValue);	
			}else {
				return (GoldPawnMoveValue);
			}
		}
		
		return value;
	}
	
	/*Get average defeat rate*/
	private static double getAvrDefeatRate(PieceType piece, Team team) {
		switch(piece) {
		case PAWN:
			if(team == Team.BLACK) {
				return BlackPawnAvrDefeatRate;	
			}else {
				return GoldPawnAvrDefeatRate;
			}
		case ROOK:
			if(team == Team.BLACK) {
				return BlackRookAvrDefeatRate;	
			}else {
				return GoldRookAvrDefeatRate;
			}
		case BISHOP:
			if(team == Team.BLACK) {
				return BlackBishopAvrDefeatRate;	
			}else {
				return GoldBishopAvrDefeatRate;
			}
		case KNIGHT:
			if(team == Team.BLACK) {
				return BlackKnightAvrDefeatRate;	
			}else {
				return GoldKnightAvrDefeatRate;
			}
		case QUEEN:
			if(team == Team.BLACK) {
				return BlackQueenAvrDefeatRate;	
			}else {
				return GoldQueenAvrDefeatRate;
			}
		case KING:
			if(team == Team.BLACK) {
				return BlackKingAvrDefeatRate;	
			}else {
				return GoldKingAvrDefeatRate;
			}
		}
		return 0.0;
	}
	
	/*Get average defeat rate*/
	private static double getAvrSuccessRate(PieceType piece, Team team) {
		switch(piece) {
		case PAWN:
			if(team == Team.BLACK) {
				return BlackPawnAvrSuccessRate;	
			}else {
				return GoldPawnAvrSuccessRate;
			}
		case ROOK:
			if(team == Team.BLACK) {
				return BlackRookAvrSuccessRate;	
			}else {
				return GoldRookAvrSuccessRate;
			}
		case BISHOP:
			if(team == Team.BLACK) {
				return BlackBishopAvrSuccessRate;	
			}else {
				return GoldBishopAvrSuccessRate;
			}
		case KNIGHT:
			if(team == Team.BLACK) {
				return BlackKnightAvrSuccessRate;	
			}else {
				return GoldKnightAvrSuccessRate;
			}
		case QUEEN:
			if(team == Team.BLACK) {
				return BlackQueenAvrSuccessRate;	
			}else {
				return GoldQueenAvrSuccessRate;
			}
		case KING:
			if(team == Team.BLACK) {
				return BlackKingAvrSuccessRate;	
			}else {
				return GoldKingAvrSuccessRate;
			}
		}
		return 0.0;
	}
	
	/*Get move Value*/
	private static int moveValue(PieceType piece, Team team) {
		switch(piece) {
		case PAWN:
			if(team == Team.BLACK) {
				return BlackPawnMoveValue;	
			}else {
				return GoldPawnMoveValue;
			}
		case ROOK:
			if(team == Team.BLACK) {
				return BlackRookMoveValue;	
			}else {
				return GoldRookMoveValue;
			}
		case BISHOP:
			if(team == Team.BLACK) {
				return BlackBishopMoveValue;	
			}else {
				return GoldBishopMoveValue;
			}
		case KNIGHT:
			if(team == Team.BLACK) {
				return BlackKnightMoveValue;	
			}else {
				return GoldKnightMoveValue;
			}
		case QUEEN:
			if(team == Team.BLACK) {
				return BlackQueenMoveValue;	
			}else {
				return GoldQueenMoveValue;
			}
		case KING:
			if(team == Team.BLACK) {
				return BlackKingMoveValue;	
			}else {
				return GoldKingMoveValue;
			}
		}
		return 0;
	
	}
	
	/*Get capture Value*/
	private static int capValue(PieceType piece, Team team) {
			switch(piece) {
			case PAWN:
				if(team == Team.BLACK) {
					return BlackPawnCapValue;	
				}else {
					return GoldPawnCapValue;
				}
			case ROOK:
				if(team == Team.BLACK) {
					return BlackRookCapValue;	
				}else {
					return GoldRookCapValue;
				}
			case BISHOP:
				if(team == Team.BLACK) {
					return BlackBishopCapValue;	
				}else {
					return GoldBishopCapValue;
				}
			case KNIGHT:
				if(team == Team.BLACK) {
					return BlackKnightCapValue;	
				}else {
					return GoldKnightCapValue;
				}
			case QUEEN:
				if(team == Team.BLACK) {
					return BlackQueenCapValue;	
				}else {
					return GoldQueenCapValue;
				}
			case KING:
				if(team == Team.BLACK) {
					return BlackKingCapValue;	
				}else {
					return GoldKingCapValue;
				}
			}
			return 0;
		}

	
	/*----------------------A lot of Getters and Setters----------------------*/
	
	/**
	 * @return the blackKingMoveValue
	 */
	public static int getBlackKingMoveValue() {
		return BlackKingMoveValue;
	}

	/**
	 * @param blackKingMoveValue the blackKingMoveValue to set
	 */
	public static void setBlackKingMoveValue(int blackKingMoveValue) {
		BlackKingMoveValue = blackKingMoveValue;
	}

	/**
	 * @return the blackKingCapValue
	 */
	public static int getBlackKingCapValue() {
		return BlackKingCapValue;
	}

	/**
	 * @param blackKingCapValue the blackKingCapValue to set
	 */
	public static void setBlackKingCapValue(int blackKingCapValue) {
		BlackKingCapValue = blackKingCapValue;
	}

	/**
	 * @return the blackKingAvrDefeatRate
	 */
	public static double getBlackKingAvrDefeatRate() {
		return BlackKingAvrDefeatRate;
	}

	/**
	 * @param blackKingAvrDefeatRate the blackKingAvrDefeatRate to set
	 */
	public static void setBlackKingAvrDefeatRate(double blackKingAvrDefeatRate) {
		BlackKingAvrDefeatRate = blackKingAvrDefeatRate;
	}

	/**
	 * @return the blackKingAvrSuccessRate
	 */
	public static double getBlackKingAvrSuccessRate() {
		return BlackKingAvrSuccessRate;
	}

	/**
	 * @param blackKingAvrSuccessRate the blackKingAvrSuccessRate to set
	 */
	public static void setBlackKingAvrSuccessRate(double blackKingAvrSuccessRate) {
		BlackKingAvrSuccessRate = blackKingAvrSuccessRate;
	}

	/**
	 * @return the blackQueenMoveValue
	 */
	public static int getBlackQueenMoveValue() {
		return BlackQueenMoveValue;
	}

	/**
	 * @param blackQueenMoveValue the blackQueenMoveValue to set
	 */
	public static void setBlackQueenMoveValue(int blackQueenMoveValue) {
		BlackQueenMoveValue = blackQueenMoveValue;
	}

	/**
	 * @return the blackQueenCapValue
	 */
	public static int getBlackQueenCapValue() {
		return BlackQueenCapValue;
	}

	/**
	 * @param blackQueenCapValue the blackQueenCapValue to set
	 */
	public static void setBlackQueenCapValue(int blackQueenCapValue) {
		BlackQueenCapValue = blackQueenCapValue;
	}

	/**
	 * @return the blackQueenAvrDefeatRate
	 */
	public static double getBlackQueenAvrDefeatRate() {
		return BlackQueenAvrDefeatRate;
	}

	/**
	 * @param blackQueenAvrDefeatRate the blackQueenAvrDefeatRate to set
	 */
	public static void setBlackQueenAvrDefeatRate(double blackQueenAvrDefeatRate) {
		BlackQueenAvrDefeatRate = blackQueenAvrDefeatRate;
	}

	/**
	 * @return the blackQueenAvrSuccessRate
	 */
	public static double getBlackQueenAvrSuccessRate() {
		return BlackQueenAvrSuccessRate;
	}

	/**
	 * @param blackQueenAvrSuccessRate the blackQueenAvrSuccessRate to set
	 */
	public static void setBlackQueenAvrSuccessRate(double blackQueenAvrSuccessRate) {
		BlackQueenAvrSuccessRate = blackQueenAvrSuccessRate;
	}

	/**
	 * @return the blackKnightMoveValue
	 */
	public static int getBlackKnightMoveValue() {
		return BlackKnightMoveValue;
	}

	/**
	 * @param blackKnightMoveValue the blackKnightMoveValue to set
	 */
	public static void setBlackKnightMoveValue(int blackKnightMoveValue) {
		BlackKnightMoveValue = blackKnightMoveValue;
	}

	/**
	 * @return the blackKnightCapValue
	 */
	public static int getBlackKnightCapValue() {
		return BlackKnightCapValue;
	}

	/**
	 * @param blackKnightCapValue the blackKnightCapValue to set
	 */
	public static void setBlackKnightCapValue(int blackKnightCapValue) {
		BlackKnightCapValue = blackKnightCapValue;
	}

	/**
	 * @return the blackKnightAvrDefeatRate
	 */
	public static double getBlackKnightAvrDefeatRate() {
		return BlackKnightAvrDefeatRate;
	}

	/**
	 * @param blackKnightAvrDefeatRate the blackKnightAvrDefeatRate to set
	 */
	public static void setBlackKnightAvrDefeatRate(double blackKnightAvrDefeatRate) {
		BlackKnightAvrDefeatRate = blackKnightAvrDefeatRate;
	}

	/**
	 * @return the blackKnightAvrSuccessRate
	 */
	public static double getBlackKnightAvrSuccessRate() {
		return BlackKnightAvrSuccessRate;
	}

	/**
	 * @param blackKnightAvrSuccessRate the blackKnightAvrSuccessRate to set
	 */
	public static void setBlackKnightAvrSuccessRate(double blackKnightAvrSuccessRate) {
		BlackKnightAvrSuccessRate = blackKnightAvrSuccessRate;
	}

	/**
	 * @return the blackBishopMoveValue
	 */
	public static int getBlackBishopMoveValue() {
		return BlackBishopMoveValue;
	}

	/**
	 * @param blackBishopMoveValue the blackBishopMoveValue to set
	 */
	public static void setBlackBishopMoveValue(int blackBishopMoveValue) {
		BlackBishopMoveValue = blackBishopMoveValue;
	}

	/**
	 * @return the blackBishopCapValue
	 */
	public static int getBlackBishopCapValue() {
		return BlackBishopCapValue;
	}

	/**
	 * @param blackBishopCapValue the blackBishopCapValue to set
	 */
	public static void setBlackBishopCapValue(int blackBishopCapValue) {
		BlackBishopCapValue = blackBishopCapValue;
	}

	/**
	 * @return the blackBishopAvrDefeatRate
	 */
	public static double getBlackBishopAvrDefeatRate() {
		return BlackBishopAvrDefeatRate;
	}

	/**
	 * @param blackBishopAvrDefeatRate the blackBishopAvrDefeatRate to set
	 */
	public static void setBlackBishopAvrDefeatRate(double blackBishopAvrDefeatRate) {
		BlackBishopAvrDefeatRate = blackBishopAvrDefeatRate;
	}

	/**
	 * @return the blackBishopAvrSuccessRate
	 */
	public static double getBlackBishopAvrSuccessRate() {
		return BlackBishopAvrSuccessRate;
	}

	/**
	 * @param blackBishopAvrSuccessRate the blackBishopAvrSuccessRate to set
	 */
	public static void setBlackBishopAvrSuccessRate(double blackBishopAvrSuccessRate) {
		BlackBishopAvrSuccessRate = blackBishopAvrSuccessRate;
	}

	/**
	 * @return the blackRookMoveValue
	 */
	public static int getBlackRookMoveValue() {
		return BlackRookMoveValue;
	}

	/**
	 * @param blackRookMoveValue the blackRookMoveValue to set
	 */
	public static void setBlackRookMoveValue(int blackRookMoveValue) {
		BlackRookMoveValue = blackRookMoveValue;
	}

	/**
	 * @return the blackRookCapValue
	 */
	public static int getBlackRookCapValue() {
		return BlackRookCapValue;
	}

	/**
	 * @param blackRookCapValue the blackRookCapValue to set
	 */
	public static void setBlackRookCapValue(int blackRookCapValue) {
		BlackRookCapValue = blackRookCapValue;
	}

	/**
	 * @return the blackRookAvrDefeatRate
	 */
	public static double getBlackRookAvrDefeatRate() {
		return BlackRookAvrDefeatRate;
	}

	/**
	 * @param blackRookAvrDefeatRate the blackRookAvrDefeatRate to set
	 */
	public static void setBlackRookAvrDefeatRate(double blackRookAvrDefeatRate) {
		BlackRookAvrDefeatRate = blackRookAvrDefeatRate;
	}

	/**
	 * @return the blackRookAvrSuccessRate
	 */
	public static double getBlackRookAvrSuccessRate() {
		return BlackRookAvrSuccessRate;
	}

	/**
	 * @param blackRookAvrSuccessRate the blackRookAvrSuccessRate to set
	 */
	public static void setBlackRookAvrSuccessRate(double blackRookAvrSuccessRate) {
		BlackRookAvrSuccessRate = blackRookAvrSuccessRate;
	}

	/**
	 * @return the blackPawnMoveValue
	 */
	public static int getBlackPawnMoveValue() {
		return BlackPawnMoveValue;
	}

	/**
	 * @param blackPawnMoveValue the blackPawnMoveValue to set
	 */
	public static void setBlackPawnMoveValue(int blackPawnMoveValue) {
		BlackPawnMoveValue = blackPawnMoveValue;
	}

	/**
	 * @return the blackPawnCapValue
	 */
	public static int getBlackPawnCapValue() {
		return BlackPawnCapValue;
	}

	/**
	 * @param blackPawnCapValue the blackPawnCapValue to set
	 */
	public static void setBlackPawnCapValue(int blackPawnCapValue) {
		BlackPawnCapValue = blackPawnCapValue;
	}

	/**
	 * @return the blackPawnAvrDefeatRate
	 */
	public static double getBlackPawnAvrDefeatRate() {
		return BlackPawnAvrDefeatRate;
	}

	/**
	 * @param blackPawnAvrDefeatRate the blackPawnAvrDefeatRate to set
	 */
	public static void setBlackPawnAvrDefeatRate(double blackPawnAvrDefeatRate) {
		BlackPawnAvrDefeatRate = blackPawnAvrDefeatRate;
	}

	/**
	 * @return the blackPawnAvrSuccessRate
	 */
	public static double getBlackPawnAvrSuccessRate() {
		return BlackPawnAvrSuccessRate;
	}

	/**
	 * @param blackPawnAvrSuccessRate the blackPawnAvrSuccessRate to set
	 */
	public static void setBlackPawnAvrSuccessRate(double blackPawnAvrSuccessRate) {
		BlackPawnAvrSuccessRate = blackPawnAvrSuccessRate;
	}

	/**
	 * @return the goldKingMoveValue
	 */
	public static int getGoldKingMoveValue() {
		return GoldKingMoveValue;
	}

	/**
	 * @param goldKingMoveValue the goldKingMoveValue to set
	 */
	public static void setGoldKingMoveValue(int goldKingMoveValue) {
		GoldKingMoveValue = goldKingMoveValue;
	}

	/**
	 * @return the goldKingCapValue
	 */
	public static int getGoldKingCapValue() {
		return GoldKingCapValue;
	}

	/**
	 * @param goldKingCapValue the goldKingCapValue to set
	 */
	public static void setGoldKingCapValue(int goldKingCapValue) {
		GoldKingCapValue = goldKingCapValue;
	}

	/**
	 * @return the goldKingAvrDefeatRate
	 */
	public static double getGoldKingAvrDefeatRate() {
		return GoldKingAvrDefeatRate;
	}

	/**
	 * @param goldKingAvrDefeatRate the goldKingAvrDefeatRate to set
	 */
	public static void setGoldKingAvrDefeatRate(double goldKingAvrDefeatRate) {
		GoldKingAvrDefeatRate = goldKingAvrDefeatRate;
	}

	/**
	 * @return the goldKingAvrSuccessRate
	 */
	public static double getGoldKingAvrSuccessRate() {
		return GoldKingAvrSuccessRate;
	}

	/**
	 * @param goldKingAvrSuccessRate the goldKingAvrSuccessRate to set
	 */
	public static void setGoldKingAvrSuccessRate(double goldKingAvrSuccessRate) {
		GoldKingAvrSuccessRate = goldKingAvrSuccessRate;
	}

	/**
	 * @return the goldQueenMoveValue
	 */
	public static int getGoldQueenMoveValue() {
		return GoldQueenMoveValue;
	}

	/**
	 * @param goldQueenMoveValue the goldQueenMoveValue to set
	 */
	public static void setGoldQueenMoveValue(int goldQueenMoveValue) {
		GoldQueenMoveValue = goldQueenMoveValue;
	}

	/**
	 * @return the goldQueenCapValue
	 */
	public static int getGoldQueenCapValue() {
		return GoldQueenCapValue;
	}

	/**
	 * @param goldQueenCapValue the goldQueenCapValue to set
	 */
	public static void setGoldQueenCapValue(int goldQueenCapValue) {
		GoldQueenCapValue = goldQueenCapValue;
	}

	/**
	 * @return the goldQueenAvrDefeatRate
	 */
	public static double getGoldQueenAvrDefeatRate() {
		return GoldQueenAvrDefeatRate;
	}

	/**
	 * @param goldQueenAvrDefeatRate the goldQueenAvrDefeatRate to set
	 */
	public static void setGoldQueenAvrDefeatRate(double goldQueenAvrDefeatRate) {
		GoldQueenAvrDefeatRate = goldQueenAvrDefeatRate;
	}

	/**
	 * @return the goldQueenAvrSuccessRate
	 */
	public static double getGoldQueenAvrSuccessRate() {
		return GoldQueenAvrSuccessRate;
	}

	/**
	 * @param goldQueenAvrSuccessRate the goldQueenAvrSuccessRate to set
	 */
	public static void setGoldQueenAvrSuccessRate(double goldQueenAvrSuccessRate) {
		GoldQueenAvrSuccessRate = goldQueenAvrSuccessRate;
	}

	/**
	 * @return the goldKnightMoveValue
	 */
	public static int getGoldKnightMoveValue() {
		return GoldKnightMoveValue;
	}

	/**
	 * @param goldKnightMoveValue the goldKnightMoveValue to set
	 */
	public static void setGoldKnightMoveValue(int goldKnightMoveValue) {
		GoldKnightMoveValue = goldKnightMoveValue;
	}

	/**
	 * @return the goldKnightCapValue
	 */
	public static int getGoldKnightCapValue() {
		return GoldKnightCapValue;
	}

	/**
	 * @param goldKnightCapValue the goldKnightCapValue to set
	 */
	public static void setGoldKnightCapValue(int goldKnightCapValue) {
		GoldKnightCapValue = goldKnightCapValue;
	}

	/**
	 * @return the goldKnightAvrDefeatRate
	 */
	public static double getGoldKnightAvrDefeatRate() {
		return GoldKnightAvrDefeatRate;
	}

	/**
	 * @param goldKnightAvrDefeatRate the goldKnightAvrDefeatRate to set
	 */
	public static void setGoldKnightAvrDefeatRate(double goldKnightAvrDefeatRate) {
		GoldKnightAvrDefeatRate = goldKnightAvrDefeatRate;
	}

	/**
	 * @return the goldKnightAvrSuccessRate
	 */
	public static double getGoldKnightAvrSuccessRate() {
		return GoldKnightAvrSuccessRate;
	}

	/**
	 * @param goldKnightAvrSuccessRate the goldKnightAvrSuccessRate to set
	 */
	public static void setGoldKnightAvrSuccessRate(double goldKnightAvrSuccessRate) {
		GoldKnightAvrSuccessRate = goldKnightAvrSuccessRate;
	}

	/**
	 * @return the goldBishopMoveValue
	 */
	public static int getGoldBishopMoveValue() {
		return GoldBishopMoveValue;
	}

	/**
	 * @param goldBishopMoveValue the goldBishopMoveValue to set
	 */
	public static void setGoldBishopMoveValue(int goldBishopMoveValue) {
		GoldBishopMoveValue = goldBishopMoveValue;
	}

	/**
	 * @return the goldBishopCapValue
	 */
	public static int getGoldBishopCapValue() {
		return GoldBishopCapValue;
	}

	/**
	 * @param goldBishopCapValue the goldBishopCapValue to set
	 */
	public static void setGoldBishopCapValue(int goldBishopCapValue) {
		GoldBishopCapValue = goldBishopCapValue;
	}

	/**
	 * @return the goldBishopAvrDefeatRate
	 */
	public static double getGoldBishopAvrDefeatRate() {
		return GoldBishopAvrDefeatRate;
	}

	/**
	 * @param goldBishopAvrDefeatRate the goldBishopAvrDefeatRate to set
	 */
	public static void setGoldBishopAvrDefeatRate(double goldBishopAvrDefeatRate) {
		GoldBishopAvrDefeatRate = goldBishopAvrDefeatRate;
	}

	/**
	 * @return the goldBishopAvrSuccessRate
	 */
	public static double getGoldBishopAvrSuccessRate() {
		return GoldBishopAvrSuccessRate;
	}

	/**
	 * @param goldBishopAvrSuccessRate the goldBishopAvrSuccessRate to set
	 */
	public static void setGoldBishopAvrSuccessRate(double goldBishopAvrSuccessRate) {
		GoldBishopAvrSuccessRate = goldBishopAvrSuccessRate;
	}

	/**
	 * @return the goldRookMoveValue
	 */
	public static int getGoldRookMoveValue() {
		return GoldRookMoveValue;
	}

	/**
	 * @param goldRookMoveValue the goldRookMoveValue to set
	 */
	public static void setGoldRookMoveValue(int goldRookMoveValue) {
		GoldRookMoveValue = goldRookMoveValue;
	}

	/**
	 * @return the goldRookCapValue
	 */
	public static int getGoldRookCapValue() {
		return GoldRookCapValue;
	}

	/**
	 * @param goldRookCapValue the goldRookCapValue to set
	 */
	public static void setGoldRookCapValue(int goldRookCapValue) {
		GoldRookCapValue = goldRookCapValue;
	}

	/**
	 * @return the goldRookAvrDefeatRate
	 */
	public static double getGoldRookAvrDefeatRate() {
		return GoldRookAvrDefeatRate;
	}

	/**
	 * @param goldRookAvrDefeatRate the goldRookAvrDefeatRate to set
	 */
	public static void setGoldRookAvrDefeatRate(double goldRookAvrDefeatRate) {
		GoldRookAvrDefeatRate = goldRookAvrDefeatRate;
	}

	/**
	 * @return the goldRookAvrSuccessRate
	 */
	public static double getGoldRookAvrSuccessRate() {
		return GoldRookAvrSuccessRate;
	}

	/**
	 * @param goldRookAvrSuccessRate the goldRookAvrSuccessRate to set
	 */
	public static void setGoldRookAvrSuccessRate(double goldRookAvrSuccessRate) {
		GoldRookAvrSuccessRate = goldRookAvrSuccessRate;
	}

	/**
	 * @return the goldPawnMoveValue
	 */
	public static int getGoldPawnMoveValue() {
		return GoldPawnMoveValue;
	}

	/**
	 * @param goldPawnMoveValue the goldPawnMoveValue to set
	 */
	public static void setGoldPawnMoveValue(int goldPawnMoveValue) {
		GoldPawnMoveValue = goldPawnMoveValue;
	}

	/**
	 * @return the goldPawnCapValue
	 */
	public static int getGoldPawnCapValue() {
		return GoldPawnCapValue;
	}

	/**
	 * @param goldPawnCapValue the goldPawnCapValue to set
	 */
	public static void setGoldPawnCapValue(int goldPawnCapValue) {
		GoldPawnCapValue = goldPawnCapValue;
	}

	/**
	 * @return the goldPawnAvrDefeatRate
	 */
	public static double getGoldPawnAvrDefeatRate() {
		return GoldPawnAvrDefeatRate;
	}

	/**
	 * @param goldPawnAvrDefeatRate the goldPawnAvrDefeatRate to set
	 */
	public static void setGoldPawnAvrDefeatRate(double goldPawnAvrDefeatRate) {
		GoldPawnAvrDefeatRate = goldPawnAvrDefeatRate;
	}

	/**
	 * @return the goldPawnAvrSuccessRate
	 */
	public static double getGoldPawnAvrSuccessRate() {
		return GoldPawnAvrSuccessRate;
	}

	/**
	 * @param goldPawnAvrSuccessRate the goldPawnAvrSuccessRate to set
	 */
	public static void setGoldPawnAvrSuccessRate(double goldPawnAvrSuccessRate) {
		GoldPawnAvrSuccessRate = goldPawnAvrSuccessRate;
	}

	/**
	 * @param turnCount the turnCount to set
	 */
	public static void setTurnCount(int turnCount) {
		General.turnCount = turnCount;
	}

	
	

}
