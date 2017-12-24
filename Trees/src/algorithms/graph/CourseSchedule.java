package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

public class CourseSchedule {

	//207. Course Schedule
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
			//pair[0] needs pair[1], so in graph it is: pair[1] --> pair[0] 
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean yes = canFinish(2, new int[][] {{1,0},{0,1}});
		System.out.println("canFinish(2, {{0,1},{1,0}}="+yes);
		
		yes = canFinish(2, new int[][] {{1,0}});
		System.out.println("canFinish(2, {1,0}}="+yes);

	}
}
