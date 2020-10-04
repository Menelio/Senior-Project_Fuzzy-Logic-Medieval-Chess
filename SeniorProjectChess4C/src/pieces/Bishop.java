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
import board.Move.MajorMove;

public class Bishop extends Piece {
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {7, 8, 9};

	public Bishop(final int piecePosition, final Team pieceTeam) {
		super(PieceType.BISHOP, piecePosition, pieceTeam);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		// loops through CANDIDATE_MOVE_VECTOR_COORDINATES and tests validity of each possible space
		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			// holds position of the bishop piece
			int candidateDestinationCoordinate = this.piecePosition;
			// tests whether the first or eighth column exclusion applies
			// if so, breaks from the current iteration of the loop and goes onto the next one
			if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
					isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
				break;
			}
			// if previous "if" didn't hold true, adds the offset to the candidateDestinationCoordinate
			candidateDestinationCoordinate += (this.getPieceTeam().getDirection() * candidateCoordinateOffset);
			// checks whether destination is a valid coordinate (within bounds of chess board)
			if (GeneralUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				// creates a Tile object to hold the destination tile
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				// if destination tile is not occupied, adds it to the list of legal moves
				if (!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}
				// if it is occupied, looks to see what piece occupies it and what team the piece is
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Team pieceTeam = pieceAtDestination.getPieceTeam();
					// if the occupying piece's team is different than the moving piece's team,
					// adds the attack move to the list of legal moves
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
		return Piece.PieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return GeneralUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return GeneralUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
	}

}
