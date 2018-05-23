/**
 * 
 */
package algorithms.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author xtang98
 *
 */
public class NestedList {
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String encode(NestedList list) {
		
		return null;
	}
	
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		

	}

}

class NestedInteger {
	Integer val;
	List<NestedInteger> list;
	public NestedInteger() {
		list = new ArrayList<>();
	}
	public NestedInteger(int v) {
		val = v;
	}
	public NestedInteger(List<NestedInteger> l) {
		list = l;
	}
	public boolean IsInteger() {
		return val != null;
	}
}
