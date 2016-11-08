package algorithms.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {
	static class Pair {
		int min;
		int max;
		Pair(int a, int b) {min=a; max=b;}
	}
	/**
	 * For each level, print the avg of all nodes in that level (avg using double?! clarify the requirements)
	 * @param root
	 */
	public static void printAverageByLevel(TreeNode root) {
		// level by levle, accumulate total, when switch level, do (float)total/n
		if (root == null) return;
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		int curCount=1,nextCount=0, curMax = curCount, total =0;
		while(!q.isEmpty()) {
			//deque and enque, update counters
			TreeNode cur = q.remove();
			curCount--;
			if (cur.left != null) {q.add(cur.left);nextCount++;}
			if (cur.right != null) {q.add(cur.right);nextCount++;}
			
			//add to total
			total += cur.val;
			
			//if curCount ==0, switch row
			//avg = (float)total/curMax, update curMax as well
			//print out avg
			if (curCount == 0) {
				double avg = (double)total/curMax;
				System.out.println(avg);
				curCount = nextCount; 
				nextCount=0;
				curMax = curCount;
				total = 0;
			}
		}
	}
	
	public static void printVertical(TreeNode root) {
		//DFS once, put all nodex in a hash indexed with distance to center
		// [1,2,3] will become 0->1, -1->2, +1->3
		HashMap<Integer, List<TreeNode>> map = new HashMap<>();
		Pair minmax = new Pair(0,0);
		dfs(root, 0, map, minmax);
		//Integer[] keys = map.keySet().toArray(new Integer[map.size()]);
		//Arrays.sort(keys);
		for (int i=minmax.min; i<=minmax.max; i++) {
			List<TreeNode> list = map.get(i);
			list.forEach(e->System.out.print(e.val+","));
			System.out.println();
		}
	}
	
	private static void dfs(TreeNode root, int key, HashMap<Integer, List<TreeNode>> map, Pair minmax) {
		if (root == null) return;
		if (key<minmax.min) minmax.min = key;
		else if (key>minmax.max) minmax.max = key;
		if (!map.containsKey(key)) {
			map.put(key, new ArrayList<TreeNode>());
		}
		map.get(key).add(root);
		dfs(root.left, key-1, map, minmax);
		dfs(root.right, key+1, map, minmax);
	}

}
