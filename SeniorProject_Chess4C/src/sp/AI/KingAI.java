/*Contributing team members
 * Steven Hansen
 * Menelio Alvarez
 * */
package sp.AI;
//TODO AI Move Pass
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.scene.control.skin.Utils;

import sp.Utils.MoveValueSorter;
import sp.application.Square;
import sp.pieces.Team;
import sp.pieces.Piece;
import sp.pieces.Piece.PieceType;

public class KingAI extends AI{
	private List<SubordinateAI> subordinate;
	private Team teamColor; 
	private BishopAI leftBishop;
	private BishopAI rightBishop;
	private int currentCorp=0;
	private int tacticalStance=1;//0 defensive, 1 neutral, 2 Offensive 
	
	/**<h1> Default argument Constructor</h1>
	 * <p> Creates instance of KingAI with given values
	 * <p>
	 * @param leftBishopAI Left Bishop AI command by this king
	 * @param rightBishopAI Right Bishop AI command by this king
	 * @param teamColor Team color of king
	 * @author Menelio Alvarez
	 */
	public KingAI(List<SubordinateAI> subordinate, AI leftBishop, AI rightBishop, Team teamColor, int row, int col) {
		this.subordinate = subordinate;
		if(leftBishop instanceof BishopAI) {
			this.leftBishop = (BishopAI) leftBishop;
		}else {
			this.leftBishop= null;
		}
		if(rightBishop instanceof BishopAI) {
			this.rightBishop =(BishopAI) rightBishop;
		}else {
			this.rightBishop= null;
		}
		this.teamColor = teamColor;
		super.row = row;
		this.column = col;
		this.id = ""+row+""+col;
	}
	//TODO Comments
	//See super comments
	@Override
	public List<Move> genMoves(Square[][] boardArray) {
		checkCorpForImposters();
		//all moves 
		List<Move> master= new ArrayList<Move>();
		List<Move> scenarioMoves= new ArrayList<Move>();
		//check for scenarios
		if(aiScenariosCheck(boardArray) !=null) {
			scenarioMoves.addAll(aiScenariosCheck(boardArray));
			return scenarioMoves;
		}

		master.addAll(genCorpMoves(boardArray));
		List<Move> toReturn=new ArrayList<Move>();
		//trim master move list so only one move per piece, select best move
		for(int i=0; i < master.size();i++) {
			
			List<Move> currentPieceMoves= new ArrayList<Move>();//List to hold current pieces being consider moves
			String currentID = master.get(i).getPieceID();
				
			while(i < master.size() && master.get(i).getPieceID() == currentID ) {
				currentPieceMoves.add(master.get(i));
				i++;
			}
			if(i<master.size()) {
				currentPieceMoves.add(master.get(i));
			}
			//sort current list by best first and add top to toReturn
			currentPieceMoves.sort(new MoveValueSorter());
			Collections.reverse(currentPieceMoves);
			toReturn.add(currentPieceMoves.get(0));
	
		}
		//sort list by MoveValue in descending order
		toReturn.sort(new MoveValueSorter());
		Collections.reverse(toReturn);
		//Collections.shuffle(toReturn);
		

		return toReturn;
	}

	//get appropriate corps moves
	private List<Move> genCorpMoves(Square[][] boardArray){
		List<Move> master= new ArrayList<Move>();
		if(currentCorp ==0) {
			//gen Corp 1 moves
			//populate master list moves with genMove form subs
			for(int i=0; i < subordinate.size();i++) {
				master.addAll(subordinate.get(i).genMoves(boardArray));
			}	
			
			master.addAll(genKingMoves(boardArray));
		}else if(currentCorp ==1) {
			//gen Corp 2 moves 
			//populate master list moves with genMove form left Bishop
			
			master.addAll(leftBishop.genMoves(boardArray));
		}else if(currentCorp == 2) {
			//Corp 3 moves 
			//populate master list moves with genMove form right Bishop	
			
			master.addAll(rightBishop.genMoves(boardArray));
		}
		if(currentCorp<2) {
			currentCorp ++;
		}else {
			currentCorp =0;
		}
		return master;
	}
	
	//Gen moves for king
	private List<Move> genKingMoves(Square[][] boardArray) {
		int row = this.row;
		int col = this.column;
		//create move parameters
		int startRow;
		int startColumn; 
		int endRow; 
		int endColumn; 
		boolean attacking;
		PieceType targetPiece;
		int valueOfMove;
		Move nextMove;
		
		List<Move> toReturn= new ArrayList<Move>();
		
		int rowOffset[] = { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 }; //setup offsets
		int colOffset[] = {-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3,-3,-2,-1, 0, 1, 2, 3 };
		
		for(int i=0; i < 28; i++ ) {
			if((row+rowOffset[i] > 0 && row+rowOffset[i]< 8) && (col+colOffset[i] >= 0 && col+colOffset[i]<8) &&//check if move is on the board 
					sp.Utils.General.doesPathExist(row, col, row+rowOffset[i], col+colOffset[i], 3, boardArray)){//check if there is a path to end square
				if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece()==null ) {//if this isn't an attack
					//create move parameters
					startRow = row;
					startColumn = col;
					endRow = row+rowOffset[i] ;
					endColumn = col+colOffset[i];
					attacking= false;
					targetPiece = null;					
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
					nextMove = null;
				}else if(boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getTeam() != this.teamColor&&
						((Math.abs(row-rowOffset[i])<2) && (Math.abs(col-colOffset[i])<2))){//if it is an attack
					//create move parameters
					startRow = row;
					startColumn = col;
					endRow = row+rowOffset[i] ;
					endColumn = col+colOffset[i];
					attacking= true;
					targetPiece = boardArray[row+rowOffset[i]][col+colOffset[i]].getPiece().getPieceType();
					valueOfMove = sp.Utils.General.calcMoveValue(row, col, row+rowOffset[i], col+colOffset[i], boardArray);
					nextMove = null;
					toReturn.add(new Move(startRow, startColumn, endRow, endColumn,attacking, targetPiece,valueOfMove,nextMove,this.id));
				}
				
			} 
		}
		return toReturn;
	}
	
	//Adjusts behavior of AI based on status of game
	private List<Move> aiScenariosCheck(Square[][] boardArray){
		double currentArmyValue= currentArmyValue();//sum of all pieces value current max with default values 208.8
		int numberOfPawns=countPawns();
		double currentEnemyArmyValue= currentEnemyArmyValue(boardArray);//sum of all pieces value current max with default values 208.8
		int numberOfEnemyPawns=countEnemyPawns(boardArray);
		

		
		if((currentArmyValue -currentEnemyArmyValue) <-100 && tacticalStance != 0) {
			tacticalStance = 0;
			if(this.teamColor ==Team.GOLD) {
				sp.Utils.General.setGoldKnightAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()/2);
				sp.Utils.General.setGoldRookAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()/2);
			}else {
				sp.Utils.General.setBlackKnightAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()/2);
				sp.Utils.General.setBlackRookAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()/2);
			}
			System.out.println("---AI on defensive");
		}else if((currentArmyValue -currentEnemyArmyValue) > 100&& tacticalStance != 2) {
			tacticalStance =2;
			if(this.teamColor ==Team.GOLD) {
				sp.Utils.General.setGoldKnightAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*2);
				sp.Utils.General.setGoldRookAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*2);
			}else {
				sp.Utils.General.setBlackKnightAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*2);
				sp.Utils.General.setBlackRookAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*2);
			}
			System.out.println("---AI on Offensive");
		}
		
		if(currentArmyValue <100) {
			if(this.teamColor ==Team.GOLD) {
				sp.Utils.General.setGoldKingAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*3);
				sp.Utils.General.setGoldQueenAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*3);
			}else {
				sp.Utils.General.setBlackKingAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*3);
				sp.Utils.General.setBlackQueenAvrSuccessRate(sp.Utils.General.getGoldKnightAvrSuccessRate()*3);
			}
			System.out.println("---AI has buffed King and Queen");
		}
		
		//System.out.println("Flag pawns="+numberOfPawns+" army value="+currentArmyValue+" Epawns="+numberOfEnemyPawns+" Earmy value="+currentEnemyArmyValue+ " Tact"+ tacticalStance);
		
		/*TODO Implement these Scenario ideas
		 * 
		 * Scenario #1
		 * 	if enemy Bishop exposed and knight is out attack or add to bishop capture value 
		 * 	
		 * Scenario #2
		 * 	If enemy knight is out increase move value of ai Bishop, and maybe don't move adjacent pawns
		 * 
		 * Scenario #3
		 *  if 1 enemy bishop is died, be aggressive increase cap value of second bishop and possibly king
		 * 
		 * Scenario #4
		 * 	if 2 enemy bishops are died increase capValue and move value of king( you want to attack enemy king and not move ai king, may want to tweak formula)  
		 * 
		 * Scenario #5
		 *  if 1 ai bishop is died
		 *  increase capValue and move value of Bishop(want to kill at least one enemy Bishop and not lose our other Bishop)
		 *  
		 * Scenario #6
		 *  if 2 ai bishops are died
		 *  	if has at least one knight go Kamakazi,
		 *  	else turtle
		 *  
		 * */
		return null;
	}
	
	//count number of pawns in army
	private int countPawns() {
		int count =0;
		for(int i =0 ; i < subordinate.size();i++) {
			if(subordinate.get(i).getPieceType()==Piece.PieceType.PAWN) {
				count++;
			}
		}
		if(leftBishop !=null) {
			for(int i =0 ; i < leftBishop.getSubordinate().size();i++) {
				if(leftBishop.getSubordinate().get(i).getPieceType()==Piece.PieceType.PAWN) {
					count++;
				}
			}
		}
		if(rightBishop != null) {
			for(int i =0 ; i < rightBishop.getSubordinate().size();i++) {
				if(rightBishop.getSubordinate().get(i).getPieceType()==Piece.PieceType.PAWN) {
					count++;
				}
			}
		}
		return count;
	}
	
	//count number of enemy pawns
	private int countEnemyPawns(Square[][] boardArray) {
		int count=0;
		for(int i=0; i < boardArray.length;i++) {
			for(int j=0; j < boardArray[0].length;j++) {
				if(boardArray[i][j].getPiece()!=null && boardArray[i][j].getPiece().getTeam()!= this.teamColor && boardArray[i][j].getPiece().getPieceType() == PieceType.PAWN) {
					count++;
				}
			}	
		}
		return count;
	}
	
	//determine value of army
	private double currentArmyValue() {
		double count =0;
		for(int i =0 ; i < subordinate.size();i++) {		
			count+=sp.Utils.General.getValueOfPiece(subordinate.get(i).getTeamColor(), subordinate.get(i).getPieceType());
		}
		count+=sp.Utils.General.getValueOfPiece(this.teamColor,Piece.PieceType.KING);
		if(leftBishop !=null) {
			for(int i =0 ; i < leftBishop.getSubordinate().size();i++) {
				count+=sp.Utils.General.getValueOfPiece(leftBishop.getSubordinate().get(i).getTeamColor(), leftBishop.getSubordinate().get(i).getPieceType());
			}
			count+=sp.Utils.General.getValueOfPiece(this.teamColor,Piece.PieceType.BISHOP);
		}
		if(rightBishop != null) {
			for(int i =0 ; i < rightBishop.getSubordinate().size();i++) {
				count+=sp.Utils.General.getValueOfPiece(rightBishop.getSubordinate().get(i).getTeamColor(), rightBishop.getSubordinate().get(i).getPieceType());
			}
			count+=sp.Utils.General.getValueOfPiece(this.teamColor,Piece.PieceType.BISHOP);
		}
		return Math.round(count * 100.0) / 100.0;	
	}
	//determine value of enemy army
	private double currentEnemyArmyValue(Square[][] boardArray) {
		double count=0;
		for(int i=0; i < boardArray.length;i++) {
			for(int j=0; j < boardArray[0].length;j++) {
				if(boardArray[i][j].getPiece()!=null && boardArray[i][j].getPiece().getTeam()!= this.teamColor) {
					count+=sp.Utils.General.getValueOfPiece(boardArray[i][j].getPiece().getTeam(),boardArray[i][j].getPiece().getPieceType());
				}
			}	
		}
		return Math.round(count * 100.0) / 100.0;	
	}
	
	
	/**
	 * @return subordinate
	 */
	public List<SubordinateAI> getSubordinate() {
		return subordinate;
	}



	/**
	 * @return the leftBishop
	 */
	public BishopAI getLeftBishop() {
		return (BishopAI)leftBishop;
	}



	/**
	 * @param leftBishop the leftBishop to set
	 */
	public void setLeftBishop(BishopAI leftBishop) {
		this.leftBishop = leftBishop;
	}



	/**
	 * @return the rightBishop
	 */
	public BishopAI getRightBishop() {
		return (BishopAI)rightBishop;
	}



	/**
	 * @param rightBishop the rightBishop to set
	 */
	public void setRightBishop(BishopAI rightBishop) {
		this.rightBishop = rightBishop;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @param subordinate the subordinate to set
	 */
	public void setSubordinate(List<SubordinateAI> subordinate) {
		this.subordinate = subordinate;
	}

	public void addSubordinates(List<SubordinateAI> subordinate) {
		this.subordinate.addAll(subordinate);
	}
	
	
	/////////////////////////////////////////////////Debugging check corp for imposters
	public void checkCorpForImposters() {
		for(int i=0; i < subordinate.size();i++) {
			if(subordinate.get(i).getTeamColor() != this.teamColor){
				System.out.println("!!!!!!!!!!!!!!Imposter in Kings Subs !!!!!!!!!!"+subordinate.get(i).getId()+", "+subordinate.get(i).getTeamColor());
			}
		}
		for(int i=0; i < leftBishop.getSubordinate().size();i++) {
			if(leftBishop.getSubordinate().get(i).getTeamColor() != this.teamColor){
				System.out.println("!!!!!!!!!!!!!!Imposter in leftBishop Subs !!!!!!!!!!" +leftBishop.getSubordinate().get(i).getId()+", "+leftBishop.getSubordinate().get(i).getTeamColor());
			}
		}
		for(int i=0; i < rightBishop.getSubordinate().size();i++) {
			if(rightBishop.getSubordinate().get(i).getTeamColor() != this.teamColor){
				System.out.println("!!!!!!!!!!!!!!Imposter in rightBishop Subs !!!!!!!!!!"+rightBishop.getSubordinate().get(i).getId()+", "+rightBishop.getSubordinate().get(i).getTeamColor());
			}
		}
	}
	
}
