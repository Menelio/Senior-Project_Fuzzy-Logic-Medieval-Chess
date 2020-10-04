/*Contributing team members
 * Richard OlgalTree
*/
package player;

import board.Board;
import board.Move;
import pieces.Piece;
import pieces.Team;

import java.util.Collection;

public class BlackPlayer extends Player {
	
	public BlackPlayer(Board board, Collection<Move> goldStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
		super(board, blackStandardLegalMoves, goldStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Team getTeam() {
		return Team.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.goldPlayer();
	}

}
