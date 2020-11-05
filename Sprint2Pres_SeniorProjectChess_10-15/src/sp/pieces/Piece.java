/*Contributing team members
 * Richard Ogletree
 * Menelio Alvarez
 * */
package sp.pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sp.application.Square;

public abstract class Piece {
	//coordinates of piece
	private int column;
	private int row;
	//Image view of piece 
	private ImageView imgV;
	//team protected
	private Team team;
	private PieceType pieceType;
	
	/**<h2>No argument Default constructor</h2>
	 * <p>
	 *This Constructor takes no arguments, sets the 
	 * row and column of this square to -1
	 * </p>
	 * @author Menelio Alvarez
	 * */
	public Piece() {
		this.column=-1;
		this.row =-1;
	}
	
	/**<h2>Get row of piece</h2>
	 * <p>
	 *	Returns integer representing row index of piece
	 * </p>
	 * @return int Row of of piece.
	 * @author Menelio Alvarez
	 * */
	public int getRow() {
		return this.row;
	}
	
	/**<h2>Get column of piece</h2>
	 * <p>
	 *	Returns integer representing Column index of piece
	 * </p>
	 * @return int Column of of piece.
	 * @author Menelio Alvarez
	 * */
	public int getColumn() {
		return this.column;
	}

	/**<h2>Set column of piece</h2>
	 * <p>
	 *	Takes an integer param representing Column index of 
	 *	piece and sets it as piece column
	 * </p>
	 * @param column integer to set column of piece.
	 * @author Menelio Alvarez
	 * */
	public void setColumn(int column) {
		this.column = column;
	}

	/**<h2>Set Row of piece</h2>
	 * <p>
	 *	Takes an integer param representing Row index of 
	 *	piece and sets it as piece Row
	 * </p>
	 * @param row integer to set row of piece.
	 * @author Menelio Alvarez
	 * */
	public void setRow(int row) {
		this.row = row;
	}
	
	/*Protected for now, because only the individual piece classes 
	 *that extend piece should set the image*/
	protected void setImg(String imagePath) {
		this.imgV = new ImageView(imagePath);
		imgV.setFitHeight(79);
		imgV.setFitWidth(79);
	}
	
	/**<h2>Get ImageView of piece</h2>
	 * <p>
	 *	Returns the image view representing this piece. 
	 *	Should not be null, if it is report to author.
	 * </p>
	 * @return Imageview image view of piece.
	 * @author Menelio Alvarez
	 * */
	public ImageView getImgVw() {
		return imgV;
	}
	
	/**<h2>Get team of piece</h2>
	 * <p>
	 *	Returns Team enum representing piece's team
	 * </p>
	 * @return Team team enum of piece.
	 * @author Menelio Alvarez
	 * */
	public Team getTeam() {
		return this.team;
	}
	
	/**<h2>Set team of piece</h2>
	 * <p>
	 *	Take an enum Team type to set this piece team
	 * </p>
	 * @param  team Team of piece.
	 * @author Menelio Alvarez
	 * */
	protected void setTeam(Team team) {
		this.team = team;
	}
	
	/**<h2>Is legal Move</h2>
	 * <p>
	 *	Returns boolean indicating if move is legal given 
	 *	a starting row and column, ending row and column, and
	 *	2d Array of square objects representing current board.
	 * </p>
	 * @param startRow int of row of piece current position
	 * @param startColumn int of column of piece current position
	 * @param endRow int of row of move, where piece will end
	 * @param endColumn int of column of move, where piece will end
	 * @param boardArray 2d array of Square object represent current 
	 * state of board.
	 * @author Menelio Alvarez
	 * */
	public abstract boolean isLegalMove(int startRow, int startColumn, int endRow, int endColumn, Square[][] boardArray);

	//enum of piece type
	public enum PieceType {
		PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
	}

	/**<h2>Get piece type</h2>
	 * <p>
	 *	Returns enum type for specific piece 
	 * </p>
	 * @return pieceType enum of piece type.
	 * @author Menelio Alvarez
	 * */
	public PieceType getPieceType() {
		return pieceType;
	}
	/**<h2>Set piece type</h2>
	 * <p>
	 *	Take enum type to set specific piece type
	 * </p>
	 * @param pieceType enum of piece type.
	 * @author Menelio Alvarez
	 * */
	protected void setPieceType(PieceType pieceType) {
		this.pieceType= pieceType;
	}
}
