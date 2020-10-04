/*Contributing team members
 * Richard OlgalTree
*/
package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pieces.Team;
import board.Board;
import board.GeneralUtils;
import board.Move;
import board.Tile;

public class Pawn extends Piece {
	
	// possible moves for a pawn
	private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9};

	// constructor
	public Pawn(final int piecePosition, final Team pieceTeam) {
		super(PieceType.PAWN, piecePosition, pieceTeam);
	}

	// calculates list of legal moves (both captures and non-captures)
	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		// holds list of legal moves
		final List<Move> legalMoves = new ArrayList<>();
		
		// loops through each possible move
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition;
			// determines whether there is a column exclusion. if so, breaks current iteration of loop
			if (isFirstColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset* this.getPieceTeam().getDirection()) ||
					isEighthColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset * this.getPieceTeam().getDirection())) {
				break;
			}
			// determines whether the team is Yellow team or Black team
			// determines which direction the piece is allowed to move
			candidateDestinationCoordinate = this.piecePosition + (this.getPieceTeam().getDirection() * currentCandidateOffset);
			// checks whether possible move coordinate is within bounds of chess board
			if (!GeneralUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				// gets tile at candidate destination
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				// adds move to list of legal moves if the tile is unoccupied
				if (!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}
				// if it is occupied, checks whether occupied by opponent team. if so, adds attack move to list of legal moves
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Team pieceTeam = pieceAtDestination.getPieceTeam();
					if (this.pieceTeam != pieceTeam) {
						legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
					break;
				}
			}
		}

		return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.PAWN.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return GeneralUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == 7 || candidateOffset == -9);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return GeneralUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
	}

}
