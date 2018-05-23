package algorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class CourseSchedule {

	//207. Course Schedule: Given the total number of courses and a list of prerequisite pairs, 
	//     is it possible for you to finish all courses?
	// e.g. (0,1) means course 1 depends on 0, 1->0
	//general idea is to build a graph, then use dfs to do circle detection
	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		//init a graph as adj list
		List<Integer>[] graph = new ArrayList[numCourses];
		for (int i=0; i<numCourses; i++) {
			graph[i] = new ArrayList<>(); //every node has an empty list initially
		}		
		//loop through prerequisites to populate the graph
		for (int i=0; i<prerequisites.length; i++) {
			int[] pair = prerequisites[i];
			//[x,y] means x needs y, so in graph it is: y --> x
			graph[pair[1]].add(pair[0]);
		}
		//detect if graph has circle, for directed graph, we detect a circle if we hitting the
		//node twice while still in visiting mode
		//(need three states: 0: unvisited, 1: visiting, 2: visited
		int[] visited = new int[numCourses]; //initially all unvisited
		for (int i=0; i<numCourses; i++) {
			if (visited[i] == 0) {
				if (hasCircle(i, graph, visited)) return false;
			}
		}
		return true;
	}
	public static boolean hasCircle(int u, List<Integer>[] graph, int[] visited) {
		//termination condition
		if (visited[u] == 1) return true; //hit a cycle
		if (visited[u] == 2) return false; //hit a visited node, stop processing
		visited[u] = 1; //visiting
		//for all the edges, go recursive here
		for (Integer v: graph[u]) {
			if (hasCircle(v, graph, visited)) return true;
		}
		visited[u] = 2; //done, visited
		return false;  //no circle found
	}
	
	// 210: Given the total number of courses and a list of prerequisite pairs, 
	//return the ordering of courses you should take to finish all courses.
	// idea is similar to canFinish, just adjust hasCircle to return a order in the stack
	public static List<Integer> getOrder(int numCourses, int[][] prerequisites) {
		//build a graph by going over all the pairs
		List<Integer>[] g = new ArrayList[numCourses];
		for (int i=0; i<numCourses; i++) {
			g[i] = new ArrayList<Integer>();
		}
		for (int i=0; i<prerequisites.length; i++) {
			int u = prerequisites[i][0];
			int v = prerequisites[i][1]; //(u,v): u requires v, so v-->u
			g[v].add(u);
		}
		
		//dfs over the graph to detect circle: no circle means a solution, circle means no solution
		Stack<Integer> st = new Stack<>();
		boolean hasCircle = false;
		int[] visited = new int[numCourses]; //0: unvisited; 1: visiting; 2: visited
		//0-->1 2-->3-->4 
		for (int i=0; i<numCourses; i++) {
			if (visited[i] == 0) {
				if (hasCircle2(i, g, visited, st))
					hasCircle = true;
			}
		}
		
		//create ret
		List<Integer> ret = new ArrayList<>();
		if (!hasCircle) {
			while (!st.isEmpty()) ret.add(st.pop());
		}
		return ret;
	}
	//starting at unvisited u, check if there is a circle, push path to the stack
	//1-->2-->5, 3-->4-->5
	public static boolean hasCircle2(int u, List<Integer>[] g, int[] visited, Stack<Integer> st) {
		//termination
		if (visited[u] == 1) return true; // hitting a node in visiting mode, circle found!! e.g. 1-->2-->1
		if (visited[u] == 2) return false;  // hitting a node that is done, no circle!
		
		//go recursive for unvisited node
		visited[u] = 1; //mark as visiting
		for (int v: g[u]) {
			if (hasCircle2(v, g, visited, st)) return true;
		}
		visited[u] = 2; //mark as visited, done
		st.push(u); //push it to stack, so last in the push gets pushed first
		return false;
	}
	
	//684. Redundant Connection. General Idea: while we building the "undirected" graph, before putting in any edge (u,v), try to do a DFS to see
	//if we can reach from u to v, if we can, obviously this edge is redundant.
	public static int[] findRedundantConnection(int[][] edges) {
		//build a empty graph initially
		int maxIdx = getMaxIdx(edges);
		List<Integer>[] g = new ArrayList[maxIdx+1];
		for (int i=0; i<g.length; i++) {
			g[i] = new ArrayList<>();
		}
		//now try out every edge (u,v) to see if we can reach from u to v without this edge; if so, then (u,v) is redundant!!!
		for (int[] e: edges) {
			int u = e[0], v=e[1];
			if (canReach(g, u, v, new HashSet<Integer>())) return e;
			g[u].add(v);
			g[v].add(u);
		}
		return null;
    }
	
	private static boolean canReach(List<Integer>[] g, int u, int v, Set<Integer> visited) {
		if (visited.contains(u)) return false; //hit node already visited
		if (u==v) return true;
		visited.add(u);
		for (int x: g[u]) {
			if (canReach(g, x,v, visited)) return true;
		}
		return false;
	}
	private static int getMaxIdx(int[][] edges) {
		int ret = -1;
		for (int[] e: edges) {
			ret = Math.max(ret, Math.max(e[0], e[1]));
		}
		if (ret<0) throw new RuntimeException("no nodes found!");
		return ret;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean yes = canFinish(2, new int[][] {{1,0},{0,1}});
		System.out.println("canFinish(2, {{0,1},{1,0}}="+yes);
		
		yes = canFinish(2, new int[][] {{1,0}});
		System.out.println("canFinish(2, {1,0}}="+yes);

		System.out.println("getOrder(2, {1,0}}="+getOrder(2, new int[][] {{1,0}}));
		//0->1->2->5 3->4->5
		int numOfCourse = 6;
		int[][] courses = new int[][] {{1,0}, {2,1},{5,2},{4,3}, {5,4}};
		System.out.println("getOrder("+Arrays.deepToString(courses)+") ====== "+getOrder(numOfCourse, courses));
		System.out.println(findRedundantConnection(courses));
	}
}
