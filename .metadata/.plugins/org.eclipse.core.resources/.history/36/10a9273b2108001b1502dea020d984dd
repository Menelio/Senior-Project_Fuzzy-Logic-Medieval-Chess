/*Contributing team members
 * Richard OlgalTree
*/
package board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// holds utility methods that will be used by multiple classes
public class GeneralUtils {
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] EIGHTH_COLUMN = initColumn(7);
	
	public final List<Boolean> FIRST_ROW = initRow(0);
    public final List<Boolean> SECOND_ROW = initRow(8);
    public final List<Boolean> THIRD_ROW = initRow(16);
    public final List<Boolean> FOURTH_ROW = initRow(24);
    public final List<Boolean> FIFTH_ROW = initRow(32);
    public final List<Boolean> SIXTH_ROW = initRow(40);
    public final List<Boolean> SEVENTH_ROW = initRow(48);
    public final List<Boolean> EIGHTH_ROW = initRow(56);
	
	// variable for number of tiles on the chess board
	// used when creating the empty and occupied tiles
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	
	// no objects will be made of this class so want to prevent instantiation
	protected GeneralUtils() {
		throw new RuntimeException("Cannot be instatiated");
	}
	
	/* method to create a boolean array that is true in every space in the column
	   indicated by "columnNumber". is false in every other element. this helps with
	   getting column exclusions when determining if a candidate move is valid. */
	private static boolean[] initColumn(int columnNumber) {
		// size 64 covers the entire size of the board
		final boolean[] column = new boolean[64];
		// moves to each row and sets the column space to true
		do {
			column[columnNumber] = true;
			columnNumber += 8;
		} while (columnNumber < 64);
		return column;
	}
	
	private static List<Boolean> initRow(int rowNumber) {
        final Boolean[] row = new Boolean[NUM_TILES];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % NUM_TILES_PER_ROW != 0);
        return Collections.unmodifiableList(Arrays.asList(row));
    }
	
	// looks to see if a possible move destination is within the bounds of the chess board
	public static boolean isValidTileCoordinate(final int coordinate) {
		return coordinate >= 0 && coordinate < 64;
	}
	
}
