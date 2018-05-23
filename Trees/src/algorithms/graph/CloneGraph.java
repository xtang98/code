package algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class UndirectedGraphNode {
	      int label;
	      List<UndirectedGraphNode> neighbors;
	      UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
	  };
	  
public class CloneGraph {

	/**133 Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
	 * Give a node to a graph, clone the whole graph returning the cloned root
	 * @param node
	 * @return
	 */
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        //queue based bread first search, each time enqueue the original node
        if (node == null) return null;
        Queue<UndirectedGraphNode> q = new LinkedList<>();
        q.offer(node);
        //use a map to remember new label-->Node relationships
        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        map.put(node.label, new UndirectedGraphNode(node.label));
        while (!q.isEmpty()) {
            UndirectedGraphNode cur = q.poll();
            UndirectedGraphNode curCopy = map.get(cur.label);
            for (UndirectedGraphNode n: cur.neighbors) {
                if (map.containsKey(n.label)) {
                    //this means the node is arealdy created, just add it to neighbors
                    curCopy.neighbors.add(map.get(n.label));
                } else {
                    //this is a new node, put into queue for further processing
                    q.offer(n);
                    //time to create a new copy for this node
                    UndirectedGraphNode child = new UndirectedGraphNode(n.label);
                    curCopy.neighbors.add(child);
                    map.put(n.label, child);
                }
            }
        }
        return map.get(node.label);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
