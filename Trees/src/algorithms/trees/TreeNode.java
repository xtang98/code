package algorithms.trees;

import java.util.Stack;

public class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(int val) {
		this.val = val;
	}

	public static void visit(TreeNode n) {System.out.print(n.val+",");}
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
		
	}
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

	}

}
