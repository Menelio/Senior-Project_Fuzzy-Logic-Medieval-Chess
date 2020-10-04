/*Contributing team members
 * Richard OlgalTree
*/
package pieces;

// enum that holds the two team colors
public enum Team {
	
	GOLD {
		@Override
		public int getDirection() {
			return -1;
		}
		
		@Override
		public boolean isGold() {
			return true;
		}
		
		@Override
		public boolean isBlack() {
			return false;
		}
	},
	BLACK {
		@Override
		public int getDirection() {
			return 1;
		}
		
		@Override
		public boolean isGold() {
			return false;
		}
		
		@Override
		public boolean isBlack() {
			return true;
		}
	};
	public abstract int getDirection();

	public abstract boolean isGold();
	public abstract boolean isBlack();
}
