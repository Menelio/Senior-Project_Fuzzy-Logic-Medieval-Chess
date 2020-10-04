/*Contributing team members
 * Richard OlgalTree
*/
package player;

import board.Board;
import board.Move;
import pieces.Piece;
import pieces.Team;

import java.util.Collection;

public class GoldPlayer extends Player {
	
	public GoldPlayer(Board board, Collection<Move> goldStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
		super(board, goldStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getGoldPieces();
	}

	@Override
	public Team getTeam() {
		return Team.GOLD;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}
	

}
