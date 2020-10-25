package sp.AI;

import java.util.ArrayList;
import java.util.List;

import sp.application.Move;
import sp.pieces.Piece.PieceType;
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
	public List<Move> genMoves() {
		
		List<Move> moves = new ArrayList<Move>();
		
		switch(pieceType) {
			case PAWN:
				//position offsets
				int rowOffset[] = {1, 1, 1, 0, 0, -1, -1, -1}; 
				int colOffset[] = {-1, 0, 1, -1, 1, -1, 0, 1};
				
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
