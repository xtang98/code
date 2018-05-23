package algorithms.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

	int V;
	List<Integer>[] adj;
	public Graph(int n) {
		V = n;
		adj = new ArrayList[n];
		for (int i=0; i<V; i++) {
			adj[i] = new ArrayList<Integer>();
		}
	}
	/**
	 * add edge to a directed graph
	 * @param x
	 * @param y
	 */
	public void addEdge(int x, int y) {
		adj[x].add(y);
		//adj[y].add(x);
	}
	public void print() {
		for (int i=0; i<V; i++) {
			System.out.println(i+"-->"+adj[i]);
		}
	}
	/**
	 * bread first search starting from a source node s
	 * @param s
	 */
	public void bfs(int s) {
        System.out.print("bfs starting from ("+s+"): ");

		boolean[] visited = new boolean[V];
		Queue<Integer> q = new LinkedList<>();
		q.add(s);
		visited[s] = true;
		while (!q.isEmpty()) {
			//visit current node in queue
			int cur = q.poll();
			System.out.print(cur+",");
			//enqueue its neighbors if not visited yet
			for (int nbr: adj[cur]) {
				if (!visited[nbr]) {
					//has to mark it right after enqueue, otherwise it may be put into queue more than once!!!!!
					//e.g 0-->1,2, then 1-->2, if we mark after dequeue, when visiting 1, 2 is not visited yet, 1 will enqueue 2 again...
					q.offer(nbr);
					visited[nbr] = true;
				}
			}
		}
		System.out.println();
	}
	
	public void dfs(int s) {
		 System.out.print("dfs starting from ("+s+"): ");
		dfsUtil(s, new boolean[V]);
	}
	public void dfsUtil(int s, boolean[] visited) {
		System.out.print(s+",");
		visited[s] = true;
		for (int nbr: adj[s]) {
			if (!visited[nbr]) {
				dfsUtil(nbr, visited);
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph(4);
		 
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);
        g.print();
        g.bfs(0);
        g.bfs(2);
        g.dfs(2);
}

}
