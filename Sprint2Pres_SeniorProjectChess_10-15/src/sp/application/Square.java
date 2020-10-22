/*Contributing team members
 * Richard OlgalTree
 * Menelio Alvarez
 * */
package sp.application;

import javafx.scene.shape.Rectangle;
import sp.pieces.Piece;

/**
 * @author Manny
 *
 */
public class Square {

	/*Global variables*/
	//coordinates of piece
	private int column;
	private int row;
	//Piece occuping this Square
	private Piece piece;
	//rectangle
	Rectangle rectangle;
	
	/**<h2>No argument Default constructor</h2>
	 * <p>
	 *This Constructor takes no arguments, set piece to null
	 *and the row and column of this square to -1
	 * </p>
	 * @author Menelio Alvarez
	 * */
	public Square() {
		this.column=-1;
		this.row =-1;
		this.piece = null;
		this.rectangle= null;
	}
	
	/**<h2>Arguement constructor</h2>
	 * <p>
	 *This Constructor takes a int row, int column, and 
	 *Piece object as it arguement and creates a square object 
	 *to those specifications.
	 * </p>
	 * @author Menelio Alvarez
	 * */
	public Square(int row, int column, Piece piece, Rectangle rectangle) {
		this.column=column;
		this.row =row;
		this.piece = piece;
		this.rectangle= rectangle;
	}
	
	/**<h2>Copy constructor</h2>
	 * <p>
	 *This Constructor takes a Square object as a argument
	 *and creates copy
	 * </p>
	 * @author Menelio Alvarez
	 * */
	public Square(Square square) {
		this.column=square.column;
		this.row =square.row;
		this.piece = square.piece;
		this.rectangle= square.rectangle;
	}
	
	/**<h2>Get row of Square</h2>
	 * <p>
	 *	Returns integer representing row index of Square
	 * </p>
	 * @return int Row of of piece.
	 * @author Menelio Alvarez
	 * */
	public int getRow() {
		return this.row;
	}
	
	/**<h2>Get column of piece</h2>
	 * <p>
	 *	Returns integer representing Column index of Square
	 * </p>
	 * @return int Column of of Square.
	 * @author Menelio Alvarez
	 * */
	public int getColumn() {
		return this.column;
	}
	
	/**<h2>Get piece</h2>
	 * <p>
	 *	Returns Piece object on this Square
	 * </p>
	 * @return Piece piece on this square.
	 * @author Menelio Alvarez
	 * */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**<h2>Set row of Square</h2>
	 * <p>
	 * Takes an integer as an argument as sets it as this 
	 * squares row.
	 * </p>
	 * @param row Integer value of column to set.
	 * @author Menelio Alvarez
	 * */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**<h2>Set column of Square</h2>
	 * <p>
	 * Takes an integer as an argument as sets it as this 
	 * squares column.
	 * </p>
	 * @param column Integer value of column to set.
	 * @author Menelio Alvarez
	 * */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**<h2>Set piece</h2>
	 * <p>
	 * Takes a piece object as an argument and sets piece
	 * on this square. 
	 * </p>
	 * @param  piece Piece to place on this square.
	 * @author Menelio Alvarez
	 * */
	public void setPiece(Piece piece) {
		this.piece =piece;
		
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	
}
