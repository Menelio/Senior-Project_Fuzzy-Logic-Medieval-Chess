package sp.AI;

import java.util.ArrayList;
import java.util.List;

import sp.pieces.Piece;
import sp.pieces.Piece.PieceType;
import sp.application.Square;
import sp.pieces.Team;

public class SubordinateAI extends AI {
	private Team teamColor; 
	private PieceType pieceType;
	private String id;
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of SubordinateAI with given values
	 * <p>
	 * @param teamColor Color of team this AI belongs to
	 * @param pieceType Piece type associated with this AI
	 * @param row int row of this AI
	 * @param row int Column of this AI
	 * @author Menelio Alvarez
	 */
	public SubordinateAI(Team teamColor, PieceType pieceType, int row, int col) {
		super();
		this.teamColor = teamColor;
		this.pieceType = pieceType;
		this.row= row;
		this.column = col;
		this.id = ""+row+""+col;
	}




	@Override
	public List<Move> genMoves(Square[][] boardArray) {
		int row = this.row;
		int col = this.column;
		List<Move> moves = new ArrayList<Move>();
		
		//create move parameters
		 int startRow;
		 int startColumn; 
		 int endRow; 
		 int endColumn; 
		 boolean attacking;
		 PieceType targetPiece;
		 int valueOfMove;
		 Move nextMove;
		 
		switch(pieceType) {
			case PAWN:
				//TODO Sharpen up Pawn AI move list generation (low priority
				//position offsets
				int rowOffset[] = {1, 1, 1}; 
				int colOffset[] = {-1, 0, 1};
				
				//gen possible moves
				for(int i=0; i < 3;i++) {
				
					
					if(//check is square is on the board
					   (row+rowOffset[i] > 0 && row+rowOffset[i]< 8) && (col+colOffset[i] >= 0 && col+colOffset[i]<8) 
					) {
						if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece()==null ) {
							
							//create move parameters
							startRow = row;
							startColumn = col;
							endRow = row+rowOffset[i] ;
							endColumn = col+colOffset[i];
							attacking= false;
							targetPiece = null;
							
							valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
	
							nextMove = null;//TODO(low priority This is suppose to be the root node in the tree of moves for this piece so no move is assigned along with it but I'm not sure how to start tree.
							
							 
							moves.add(new Move(startRow, startColumn, endRow, endColumn,attacking, targetPiece,valueOfMove,nextMove,this.id));
						}else if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getTeam() != this.teamColor){
							//create move parameters
							startRow = row;
							startColumn = col;
							endRow = row+rowOffset[i] ;
							endColumn = col+colOffset[i];
							attacking= true;
							targetPiece = boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getPieceType();
								 
							
							valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
	
							nextMove = null;
							
							 
							moves.add(new Move(startRow, startColumn, endRow, endColumn,attacking, targetPiece,valueOfMove,nextMove,this.id));
						}
					}
				}
			break;
			case ROOK:
				//TODO generate ROOK move, add to move list, and sort master list

			break;
			case KNIGHT:
				//TODO generate KNIGHT move, add to move list, and sort master list

			break;
			case QUEEN:
				//TODO generate QUEEN move, add to move list, and sort master list
			break;
			
		}
		

		return moves;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	
}
