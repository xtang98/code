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
				// then pop, visit and switch to right
				cur = s.pop();
				visit(cur);
				cur = cur.right;
			}
		}
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
