package algorithms.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(int val) {
		this.val = val;
	}

	private static void visit(TreeNode n) {System.out.print(n.val+",");}
	public static void preTraversal(TreeNode root) {
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		while (!s.empty()) {
			//pop and visit top
			TreeNode cur = s.pop();
			visit(cur);
			//push right and then left, so that left will be visit first next time
			if (cur.right != null) s.push(cur.right);
			if (cur.left != null) s.push(cur.left);
		}
	}
	/*
	 *         10
	 *        /  \
	 *      5      15
	 *     / \    /  \
	 *    1   7  10   19
	 */
	public static void inTraversal(TreeNode root) {
		Stack<TreeNode> s = new Stack<TreeNode>();
		TreeNode cur = root;
		while (cur !=null || !s.empty()) {
			// push cur and move down left
			if (cur!= null) {
				s.push(cur);
				cur = cur.left;
			} else {
				// then pop, visit and assign cur to right (could be null)
				cur = s.pop();
				visit(cur);
				cur = cur.right;
			}
		}
	}
	/* go through example
	 * 1.       cur= root=10
	 * loop #1: if block , s=[10*, cur=5
	 * #2: s=[10,5*, cur=1
	 * #3: s=[10,,5,1*, cur = null
	 * #4: else block, s=[10,5], print 1, cur=null
	 * #5: else block, s=[10], print 5, cur=7
	 * #6: if block, s=[10,7], cur=null
	 * #7: else block, s=[10], print 7, cur= null
	 * #8: else block, s=[], print 10, cur=15
	 * #9: if block, s=[15], cur=10
	 * #10: if block,s=[15,10*, cur =null
	 * #11: else block, s=[15* print 10, cur=null
	 * #11: else block, s=[], print 15, cur = 10
	 * #11: if block, s=[10], cur=null
	 * #12: else block, s=[], print 10, cur = null **** this is the only time both cur and stack are empty, terminate!!!
	 * 
	 * 
	 */
	
	

	public static void postTraversal(TreeNode root) {
		if (root == null) return;
		
		Stack<TreeNode> s = new Stack<TreeNode>();
		TreeNode cur = root, pre = null;
		
		while (cur != null || !s.empty())  {
			if (cur != null) {
				//push left
				s.push(cur);
				cur = cur.left;
			} else {
				//peek, process right, visit if right part is DONE
				//DONE means: 1) right is null 2) right is visited already
				cur = s.peek();
				if (cur.right == null || cur.right == pre) { //right is DONE!
					pre = s.pop();
					visit(pre);
					cur = null; //so that it will keeps popping
				} else {
					cur = cur.right; //if right is not null, next round it will do push-left
				}
			}
		} 
	}
	/* code walk through for
	 *       3
	 *      / \
	 *     1   2
	 * init, s=[3,1]
	 * #1: cur=1, pop 1, pre=1 s=[3] visit 1
	 * #2: cur=3, push 2, s=[3,2] 
	 * #3: cur=2, pop 2, pre=2, s=[3], visit 2
	 * #4: cur=3, cur.right == pre, pop 3, pre=3, visit 3
	 * 
	 */
	
	
	 
	/**
	 * Two elements of a binary search tree (BST) are swapped by mistake.
		Recover the tree without changing its structure.
		Note:
		A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
		Subscribe to see which companies asked this question
	 * @param root
	 */
	 // my idea is to use in-order traversal to identify "anti-pattern", see below for detail:
	 // 1,2,3,4,5
	 // 1,5,3,4,2
	 //    pre =1, cur=5 ok, 
	 //    pre =5, cur=3 anti-pattern, take the pre
	 //... pre=4, cur=2, anti-pattern, take the cur
	// what about 1,2==> 2,1
	// what about 1,2,3 ==> 1,3,2: pre=3, cur=2, anti, take pre as first, second=cur
    public static void recoverTree(TreeNode root) {
        // in order iterator go over once, 
        // find the first node: it's next's value goes down instead of up
        // find the second node: it's gong down comparing with its pre
        TreeNode first=null, second=null;
        if (root == null) return;
        Stack<TreeNode> s = new Stack<TreeNode>();
        TreeNode cur = root, pre=null;
        while (cur != null || !s.empty()) {
            //push cur then move left
            if (cur != null) {
                s.push(cur);
                cur = cur.left;
            } else {
                //do some visiting, check anti-pattens
                cur = s.pop();
                //compare it with pre
                if (pre != null && cur.val<pre.val) {
                    if (first == null) first = pre; //note need to handle two elements [2,1], so assign second always even for the first encounter
                    second = cur; 
                }
                pre= cur;
                //update cur
                cur = cur.right;
            }
        }
        
        //swap value for first and second
        if (first == null) return;
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
        
    }
    
    public static boolean identical(TreeNode n1, TreeNode n2) {
    	if (n1 == null && n2 == null) return true;
    	if ((n1 != null && n2 == null) || (n1 == null && n2 != null)) return false;
    	if (n1 != null && n2 != null && n1.val != n2.val) return false;
    	//go recursive if both not null and value matches
    	return identical(n1.left, n2.left) && identical(n1.right, n2.right);
    }
    
    //isBST: recursive + pass on range from root all the way down...
    //closestValue: recursive + pass on <min, node> pair all the way down...
	public static void main(String[] args) {
		TreeNode node = new TreeNode(10);
		node.left = new TreeNode(5);
			node.left.left = new TreeNode(1);
			node.left.right = new TreeNode(7);
		node.right = new TreeNode(15);
			node.right.left = new TreeNode(13);
			node.right.right = new TreeNode(19);
		TreeNode.preTraversal(node);
		System.out.println();
		TreeNode.inTraversal(node);
		System.out.println();
		TreeNode.postTraversal(node);
		
		TreeNode r = new TreeNode(3);
		r.left = new TreeNode(1);
		r.right = new TreeNode(2);
		TreeNode.inTraversal(r);
		TreeNode.recoverTree(r);
		TreeNode.inTraversal(r);
		
		List<List<Integer>> listOfList = new ArrayList<List<Integer>>();
		listOfList.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4)));
		listOfList.add(new LinkedList<Integer>(Arrays.asList(30,40)));
		//listOfList.forEach(l=>System.out.println(l));; //l => println(Arrays.toString(l.toArray()))
		for (List<Integer> l: listOfList) {
			System.out.println(Arrays.toString(l.toArray()));
		}
	}

}
