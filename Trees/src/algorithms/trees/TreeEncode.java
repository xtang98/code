/**
 * 
 */
package algorithms.trees;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author xtang98
 *
 */
public class TreeEncode {
	
    // let me play with this format 1<2,3<4,5>>, in general
    // r, r<left,>, r<,right>, r<left, right> 

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        if (root == null) return "";
        String head = Integer.toString(root.val);
        if (root.left == null && root.right == null) return head;
        //go recursive, at least one child exists
        String left = serialize(root.left);
        String right = serialize(root.right);
        String ret = head+"<"+left+","+right+">";
        //System.out.println(ret);
        return ret;
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        //System.out.println("data="+data);
        if (data == null || data.length() ==0) return null;
        if (!data.contains("<")) { // no subtree anymore
            return new TreeNode(Integer.parseInt(data));
        } else { //deal with subtree
            int leftIdx = data.indexOf("<");
            int rightIdx = data.lastIndexOf(">");
            int commaIdx = findRightComma(data, leftIdx, rightIdx);
            TreeNode root = new TreeNode(Integer.parseInt(data.substring(0,leftIdx)));
            String left = data.substring(leftIdx+1, commaIdx);//<,2> ==> empty string
            String right = data.substring(commaIdx+1, rightIdx);//<3,> ==>empty string
            root.left = deserialize(left);
            root.right = deserialize(right);
            return root;
        }
    }
    
    private static int findRightComma(String data, int leftIdx, int rightIdx) {
        //return the comma that has no left < after leftIdx
        int count=0;
        for (int i=leftIdx+1; i<rightIdx; i++) {
            if (data.charAt(i) == '<') count++;
            if (data.charAt(i) == '>') count--;
            if (count == 0 && data.charAt(i) ==',') return i;
        }
        return -1;
    }

    //this is to implement leetCode OJ serializer, this is essentially doing a level traversal with null handling
    //while removing a node, adding both children to queue if it has AT LEAST one child
    // (1, null, 2): while removing 1, we will add null,2 to the queue
    public static String serializeOJ(TreeNode root) {
    	if (root == null) return null;
    	Queue<TreeNode> q = new LinkedList<TreeNode>(); //add/remove/element
    	q.add(root);
    	StringBuilder sb = new StringBuilder();
    	while (!q.isEmpty()) {
    		TreeNode cur = q.remove();
    		if (cur == null) { 
    			//cur is null and q is empty, tree is exhausted, no need to add the null to end!
    			if (!q.isEmpty()) sb.append("null,");
    			continue;
    		}
    		if (cur.left != null || cur.right != null) {
    			q.add(cur.left);
    			q.add(cur.right);
    		}
    		sb.append(Integer.toString(cur.val));
    		if (!q.isEmpty() && !(q.size()==1 && q.element() == null)) {sb.append(',');}
    	}
    	return sb.toString();
    }
    public static TreeNode deserializeOJ(String data) {
    	if (data == null || data.length()<1) return null;
    	String[] vals = data.split(",");
    	Queue<TreeNode> s1 = new LinkedList<TreeNode>(), s2= new LinkedList<TreeNode>();
    	TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
    	s1.add(root);
    	int idx =1;
    	while (!s1.isEmpty() && idx<vals.length) {
    		//move front, then add children to second queue, first left, then right
    		TreeNode cur = s1.remove();
    		String leftVal = vals[idx++];
    		if (!leftVal.equals("null")) {
    			cur.left = new TreeNode(Integer.parseInt(leftVal));
    			s2.add(cur.left);
    		}
    		if (idx<vals.length) {
    			String rightVal = vals[idx++];
    			if (!rightVal.equals("null")) {
    				cur.right = new TreeNode(Integer.parseInt(rightVal));
    				s2.add(cur.right);
    			}
	    		//if current stack s1 is exhausted, swap s1 and s2 so that we process next level
	    		if (s1.isEmpty()) {
	    			Queue<TreeNode> temp = s1;
	    			s1 = s2;
	    			s2 = temp;
	    		}
    		}
    	}
    	return root;
    }
    
    /** make the binary tree to points to left siblings, then first node points down the node next row
     *    1
     *  2   3
     *        4
     * becomes:
     * 1->nil
     * |
     * 2->3
     * |
     * 4
     * @param root
     */
    public static void transformRightDown(TreeNode root) {
    	if (root == null) return;
    	//q1 stores current row, q2 stores next row
    	//for q1: enque children first
    	//		  always fix right pointer, 
    	//			if first node, preHead.left -> cur; preHead = cur;
    	//		  	else set left as null
    	//			populate q2
    	//if q1 exhausted, swap q2 and q2
    	
    	Queue<TreeNode> q1 = new LinkedList<TreeNode>(), q2 = new LinkedList<TreeNode>();
    	TreeNode preHead = null;
    	q1.add(root);
    	boolean first = true;
    	while (!q1.isEmpty()) {
    		TreeNode cur = q1.remove();
    		//enqueue children
    		if (cur.left != null) q2.add(cur.left);
    		if (cur.right!= null) q2.add(cur.right);
    		//adjust pointers without worrying losing children
    		cur.right = q1.peek(); //right points to its neighbor always
    		cur.left =null;
    		if (first) {
    			if (preHead != null) preHead.left = cur;
    			preHead = cur;
    			first = false;
    		}
    		//if q1 is down, swap q1 and q2 for next round
    		if (q1.isEmpty()) {
    			if (q2.isEmpty()) return; //no more nodes to process
    			Queue<TreeNode> tmp = q1;
    			q1 = q2;
    			q2 = tmp;
    			first = true;
    		}
    	}
    }
    
    public static void transformRightDownOneQueue(TreeNode root) {
    	if (root == null) return;
    	//q1 stores current row, use two counter to track the current row and next row
    	//for q1: enque children first
    	//		  always fix right pointer, 
    	//			if first node, preHead.left -> cur; preHead = cur;
    	//		  	else set left as null
    	//			populate q2
    	//if q1 exhausted, swap q2 and q2
    	/*   1->nil (curHead = 1 now)
    	 * 	 |
    	 * 	 2->3 (curHead = 2 now) ->nil
    	 * 	 |
    	 *   4->nil
    	 *   
    	 * working through example 1<2,3<,4>>, inside the loop
    	 * #0: first=T, curCount = 1, nextCount = 0, queue=[1]
    	 * #1: first=T, curCount=0=>2, nextCount=2=>0, queue=[2,3], twist pointers for 1
    	 * #2: first=T, cur = 2, curCount=1, nextCount=0, twist pointers for cur=2
    	 * #3: first=F, cur = 3, curCount=0==>1, nextCount=1==>0, twist pointers for cur=3
    	 * #4: first=T, cur = 4, curCount=1==>0, nextCount=0, twist pointers for cur=4
    	 */
    	
    	Queue<TreeNode> q1 = new LinkedList<TreeNode>();
    	TreeNode preHead = null;
    	q1.add(root);
    	boolean first = true;
    	int curCount=1, nextCount=0;
    	while (!q1.isEmpty()) {
    		TreeNode cur = q1.remove();
    		curCount--;
    		
    		//enqueue children
    		if (cur.left != null) {q1.add(cur.left); nextCount++;}
    		if (cur.right!= null) {q1.add(cur.right); nextCount++;}
    		//adjust pointers without worrying losing children
    		if (curCount>0) 
    			cur.right = q1.peek(); //right points to its neighbor always, except the last one
    		else
    			cur.right = null;
    		cur.left =null;
    		if (first) {
    			if (preHead != null) preHead.left = cur;
    			preHead = cur;
    			first = false;
    		}
    		//current row exhausted
    		if (curCount == 0) {
    			first = true;
    			curCount = nextCount;
    			nextCount=0;
    		}
    	}
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.right = new TreeNode(4);
		String treeStr = TreeEncode.serialize(root);
		System.out.println(treeStr);
		TreeNode root1 = TreeEncode.deserialize(treeStr);
		System.out.println(TreeEncode.serialize(root1));
		//compare root and root1?
		System.out.println("They are identical? "+TreeNode.identical(root, root1));
		
		//https://leetcode.com/faq/#binary-tree
		//expected: [5,4,7,3,null,2,null,-1,null,9]
		System.out.println("=====Testing OJ version of serailzier and deserializer!=====");
		System.out.println("expected: [5,4,7,3,null,2,null,-1,null,9]");
		TreeNode rootOJ = new TreeNode(5);
		rootOJ.left = new TreeNode(4);rootOJ.left.left = new TreeNode(3);rootOJ.left.left.left = new TreeNode(-1);
		rootOJ.right = new TreeNode(7);rootOJ.right.left = new TreeNode(2);rootOJ.right.left.left = new TreeNode(9);
		String treeStrOJ = TreeEncode.serializeOJ(rootOJ);
		TreeNode rootOJ1 = TreeEncode.deserializeOJ(treeStrOJ);
		System.out.println("serialzied:"+treeStrOJ);
		System.out.println("deserailzie then serialzied:"+
				TreeEncode.serializeOJ(TreeEncode.deserializeOJ(treeStrOJ)));
		
		System.out.println("original tree and round trip tree are identical? "+TreeNode.identical(rootOJ, rootOJ1));
		
		//test transformDownRight
		System.out.println("before transform:"+TreeEncode.serialize(root));
		TreeEncode.transformRightDownOneQueue(root);
		System.out.println("after transform:"+TreeEncode.serialize(root));
	}

}
