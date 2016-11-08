package algorithms.trees;

import java.util.Stack;

public class BinarySearchTree {

	TreeNode root;
	public BinarySearchTree(TreeNode n) {
		root = n;
	}
	public void insert(int[] vals) {
		for (int i=0; i<vals.length; i++) {
			insert(vals[i]);
		}
	}
	/**
	 * Insert a node, allowing dup value (should we disallow?)
	 * @param v
	 */
	public void insert(int v) {
		TreeNode pre = null, cur = root;
		if (cur == null) {
			root = new TreeNode(v);
		}
		while (cur != null) {
			pre = cur;
			if (v<cur.val) { //left
				cur = cur.left;
			} else {
				cur = cur.right;
			}
		}
		//now pre is parent and cur is the null node
		if (pre.right == null && v > pre.val) {
			pre.right = new TreeNode(v);
		} else {
			pre.left = new TreeNode(v);
		}
		//     10
		//  5      20
		//      *
		//insert 15
	}
	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree(new TreeNode(30));
		bst.insert(new int[] {5,10,15, 40,50,35});
		InOrderIterator it = new InOrderIterator(bst.root);
		while (it.hasNext()) {
			System.out.print(it.next().val+",");
		}
		System.out.println();
		for (int k=1; k<=10; k++) {
			TreeNode cur = bst.kthElement(bst.root, k);
			System.out.println(k+"th="+ (cur == null? "null":Integer.toString(cur.val)));
		}
	}

	
	/**
	 * return the kth node in the BST, idea is simple, use the iterator k times, if less then k elements, return null
	 * @param root
	 * @return
	 */
	public static TreeNode kthElement(TreeNode root, int k) {
		if (k<1) throw new RuntimeException("k must be 1 or more!");
		InOrderIterator it = new InOrderIterator(root);
		while (it.hasNext()) {
			TreeNode cur = it.next();
			if (--k == 0) return cur;
		}
		return null;
	}
}

class InOrderIterator {
	TreeNode cur;
	Stack<TreeNode> s = new Stack<TreeNode>();
	public InOrderIterator(TreeNode root) {
		cur = root;
	}
	
	public boolean hasNext() {
		return cur != null || !s.empty();
	}
	
	public TreeNode next() {
		if (!hasNext()) return null;
		
		//push left for cur while cur is not null
		while (cur != null) {
			s.push(cur);
			cur = cur.left;
		}
		
		//pop and assign right to cur
		TreeNode tmp = s.pop();
		cur = tmp.right;
		return tmp;
	}
}
