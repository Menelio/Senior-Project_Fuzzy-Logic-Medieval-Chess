/*Contributing team members
 * Richard OlgalTree
*/
package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.Move;
import board.Tile;
import board.GeneralUtils;

public class Knight extends Piece {
	// constructor
	// takes in the position of the piece, and the team of the piece
	public Knight(final int piecePosition, final Team pieceTeam) {
		
		super(PieceType.KNIGHT, piecePosition, pieceTeam);

	}

	// method to return legal moves that the Knight may make
	// takes in the chess board as parameter
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		//create a char[][] version of the board board
		char[][] charBoard= GeneralUtils.get2dCharBoard(board);
		// list variable to hold legal moves
		final List<Move> legalMoves = new ArrayList<>();	
		
		//pieces 2d position array (x is at 0, y is at 1)
		int[] pos2d = GeneralUtils.convertPosTo2D(piecePosition);
		
		for(int i=-5; i < 5; i++ ) {
			for(int j=-5; j < 5; j++ ) {
				final int candidateDestinationCoord = GeneralUtils.convertPosTo1D(pos2d[0]+j, pos2d[1]+i);
				/* if a candidate move coordinate is within board bounds, goes on
				   to test whether the candidate tile is occupied >>*/
				if (GeneralUtils.isValidTileCoordinate(candidateDestinationCoord)) {
					
					// Tile object to know what the coordinate is
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
					
					// if candidate tile is not occupied, adds to list of legal moves
					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
					}
					// destination tile is occupied
					else {
						// gets the piece and the team of the occupied tile
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Team pieceTeam = pieceAtDestination.getPieceTeam();
						
						// adds tile to list of legal moves (piece occupying tile will be captured)
						if (this.pieceTeam != pieceTeam) {
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoord, pieceAtDestination));
						}
					}
				}	
			
			}
		}
		return legalMoves;	
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KNIGHT.toString();
	}
	
	// NEED TO ADD MOVE EXCLUSION METHODS WHEN APPLICABLE

}
