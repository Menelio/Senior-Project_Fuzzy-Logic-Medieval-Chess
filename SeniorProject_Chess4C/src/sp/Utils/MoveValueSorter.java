/*Contributing team members
 * Menelio Alvarez
 * */
package sp.Utils;

import java.util.Comparator;

import sp.AI.Move;

/*For sorting Move objects*/
public class MoveValueSorter implements Comparator<Move> 
{
    @Override
    public int compare(Move m1, Move m2) {
        Long m1L= (long) m1.getValueOfMove();
        Long m2L= (long) m2.getValueOfMove();
    	return m1L.compareTo(m2L);
    }
}