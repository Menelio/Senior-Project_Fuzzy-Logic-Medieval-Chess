/*Contributing team members
 * Menelio Alvarez
 * */
package sp.AI;

import java.util.List;

import sp.application.Square;
import sp.pieces.Piece;
import sp.pieces.Team;

public class AIController {
	private KingAI kingAI;
	private Team teamColor = Team.BLACK;
	
	/**<h1>Argument Constructor</h1>
	 * @param kingAI King AI for this Ai
	 * @author Menelio Alvarez
	 */
	public AIController(KingAI kingAI) {
		super();
		this.kingAI = kingAI;
	}

	
	/**<h1> Request moves</h>
	 * <p> Returns moves capable by AIControl </p>
	 * @param square[][] Board array
	 * @return List of moves
	 * @author Menelio Alvarez
	 * */
	public Move requestMoves(Square[][] boardArray){
		List<Move> master = kingAI.genMoves(boardArray);
		if(master.size()>0) {
			return master.get(0);
		}else {
			return null;
		}
	}
	
	/**<h1> Get Piece By ID</h>
	 * <p> Searches Board for piece with given ID
	 * and returns it</p>
	 * @param id String is to search for
	 * @param square[][] Board array
	 * @return Piece with given id
	 * @author Menelio Alvarez
	 * */
	public Piece getPieceByID(String id, Square[][] boardArray ) {
		for(int i=0; i < boardArray.length;i++ ) {
			for(int j=0; j < boardArray[0].length;j++) {	
				if(boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getAi() != null) {
					if(boardArray[i][j].getPiece().getAi().getId().equals(id)) {
						return boardArray[i][j].getPiece();
						
					}
				}
			}
		}
		
		return null;
	}
	
	/**<h1> remove Piece with given ID</h>
	 * <p> Searches Board for piece with given ID
	 * and removes it</p>
	 * @param id String of piece to remove
	 * @author Menelio Alvarez
	 * */
	public void removePieceAIByID(String id) {
		if(kingAI.getId().equals(id)) {
			kingAI = null;
			return;
		}else {
			for(int i =0; i < kingAI.getSubordinate().size();i++) {
				if(kingAI.getSubordinate().get(i).getId().equals(id)) {
					kingAI.getSubordinate().remove(i);
					return;
				}
			}
			for(int i =0; i < kingAI.getLeftBishop().getSubordinate().size();i++) {
				if(kingAI.getLeftBishop().getSubordinate().get(i).getId().equals(id)) {
					kingAI.getLeftBishop().getSubordinate().remove(i);
					return;
				}
			}
			for(int i =0; i < kingAI.getRightBishop().getSubordinate().size();i++) {
				if(kingAI.getRightBishop().getSubordinate().get(i).getId().equals(id)) {
					kingAI.getRightBishop().getSubordinate().remove(i);
					return;
				}
			}
		}	
	}
	
	/**<h1> Move Left bishops subordinates to kings command</h1>
	 * */
	public void moveLeftBishopsSubsToKing() {
		kingAI.addSubordinates(kingAI.getLeftBishop().getSubordinate());
	}
	/**<h1> Move Right bishops subordinates to kings command</h1>
	 * */
	public void moveRightBishopsSubsToKing() {
		kingAI.addSubordinates(kingAI.getRightBishop().getSubordinate());
	}
}
