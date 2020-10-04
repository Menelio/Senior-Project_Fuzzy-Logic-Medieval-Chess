/*Contributing team members
 * Richard OlgalTree
*/
package board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pieces.Piece;

public abstract class Tile {
	
	// coordinate of tile to be created
	int tileCoord;
	
	// map to hold every possible empty tile
	// will end up being 64 because any tile on
	// the board could be empty at some point
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	// method to create the map that holds the possible empty tiles
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		// is 64 because 64 squares on a chess board
		for (int i = 0; i < GeneralUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	// method that creates a Tile
	// if there is a piece to go on the tile, creates a new OccupiedTile object,
	// otherwise pulls an EmptyTile from the EMPTY_TILES hash map based on
	// tileCoord passed in
	public static Tile createTile (final int tileCoord, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoord, piece) : EMPTY_TILES_CACHE.get(tileCoord);
	}
	
	// constructor that creates Tile object based on coordinate sent in
	private Tile (final int tileCoord) {
		this.tileCoord = tileCoord;
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	// subclass EmptyTile that creates an unoccupied Tile
	public static final class EmptyTile extends Tile {
		
		// constructor
		private EmptyTile (final int coord) {
			super(coord);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		// returns false because EmptyTile object will always be uoccupied
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		// returns null because there will not be a piece since
		// it is unoccupied
		@Override
		public Piece getPiece() {
			return null;
		}
		
	}
	
	// subclass that creates an occupied Tile
	public static final class OccupiedTile extends Tile {
		// piece that occupies the tile
		final Piece pieceOnTile;
		
		// constructor
		// takes in tile coordinate as well as the piece on the Tile
		private OccupiedTile (int tileCoord, final Piece pieceOnTile) {
			super(tileCoord);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceTeam().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
		}
		
		// returns true because there is a piece on it
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		// returns the piece currently on the Tile
		@Override
		public Piece getPiece() {
			return pieceOnTile;
		}
		
	}

}
