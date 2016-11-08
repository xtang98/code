package algorithms.trees;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryTreeTest {
	static TreeNode root = new TreeNode(1);
	static {
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(5);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(9);
		root.right.right = new TreeNode(4);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPrintAvg() {
		BinaryTree.printAverageByLevel(root);
	}
	
	@Test
	public void testPrintVertical() {
		BinaryTree.printVertical(root);
	}

}
