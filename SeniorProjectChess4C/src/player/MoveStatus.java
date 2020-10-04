/*Contributing team members
 * Richard OlgalTree
*/
package player;

public enum MoveStatus {
	
	DONE {
		@Override
		boolean isDone() {
			return true;
		}
	};
	
	abstract boolean isDone();

}
