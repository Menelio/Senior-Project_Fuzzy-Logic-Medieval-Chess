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
	
	
	
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of SubordinateAI with given values
	 * <p>
	 * @param teamColor Color of team this AI belongs to
	 * @param pieceType Piece type associated with this AI
	 * @author Menelio Alvarez
	 */
	public SubordinateAI(Team teamColor, PieceType pieceType) {
		super();
		this.teamColor = teamColor;
		this.pieceType = pieceType;
	}




	@Override
	public List<Move> genMoves(Square[][] boardArray, int row, int col) {
		
		List<Move> moves = new ArrayList<Move>();
		
		switch(pieceType) {
			case PAWN:
				
				//position offsets
				int rowOffset[] = {1, 1, 1, 0, 0, -1, -1, -1}; 
				int colOffset[] = {-1, 0, 1, -1, 1, -1, 0, 1};
				
				//gen possible moves
				for(int i=0; i < 8;i++) {
					if(//check is square is on the board
					   (row+rowOffset[i] > 0 && row+rowOffset[i]< 8) && (col+colOffset[i] >= 0 && col+colOffset[i]<8) 
					   &&//and that is not occupied or is not occupied by friendly piece
					   (boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece()==null || boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getTeam() != this.teamColor)
					) {
						//create move parameters
						 int startRow = row;
						 int startColumn = col;
						 int endRow = row+rowOffset[i] ;
						 int endColumn = col+colOffset[i];
						 if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece()!=null && boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getTeam() != this.teamColor) {
							 boolean attacking= true;
							 PieceType targetPiece = boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getPieceType();
							 
						 }else {
							 boolean attacking= false;
							 PieceType targetPiece = null;
						 }
						 int valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);

						 Move nextMove = null;//TODO This is supose to be the root node in the tree of moves for this piece so no move is assigned along with it but I'm not sure how to start tree.
						
					}
				}
			break;
			case ROOK:

			break;
			case KNIGHT:

			break;
			case QUEEN:

			break;
			
		}
		

		return moves;
	}

}
