package sp.AI;

import java.util.List;

import sp.application.Square;
import sp.pieces.Piece;
import sp.pieces.Team;

public class AIController {

	private KingAI kingAI;

	private Team teamColor = Team.BLACK;
	
	
	
	/**TODO Fill out comments and check
	 * @param kingAI
	 */
	public AIController(KingAI kingAI) {
		super();
		this.kingAI = kingAI;
	}

	
	/**TODO Fill out comments
	 * */
	public List<Move> requestMoves(Square[][] boardArray){
		List<Move> master = kingAI.genMoves(boardArray);
		return master;
	}
	
	//TODO add comments
	public Piece getPieceByID(String id, Square[][] boardArray ) {
		for(int i=0; i < boardArray.length;i++ ) {
			for(int j=0; j < boardArray[0].length;j++) {
				if(boardArray[i][j].getPiece().getAi().getId().equals(id)) {
					return boardArray[i][j].getPiece();
				}
			}
		}
		
		return null;
	}
	
	//TODO add Comments
	public void removePieceAIByID(String id) {
		if(kingAI.getId().equals(id)) {
			kingAI = null;//TODO Game at this point needs to end ad this will create a Null pointer exception next AI move
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
}
