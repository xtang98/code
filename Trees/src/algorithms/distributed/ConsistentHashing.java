package algorithms.distributed;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This is a simple illustration how Consistent Hashing is implemented to solve the problem a naive
 * disitributed hashing would face, i.e. adding or removing a cache node will require reshuffling almost
 * all the keys among nodes. 
 * Naive Hashing: key = hash(cacheObject) mod N where N is # of nodes avaiable
 * Say N = 3 initially, we got this picture for 12 keys 1..12
 * N1: 1,4,7,10
 * N2: 2,5,8,11
 * N3: 3,6,9,12
 * 
 * Now we added a new node to the cluster, N=4, the new picture should be like this:
 * N1: 1,5, 9
 * N2: 2,6,10
 * N3: 3,7,11
 * N4: 4,8,12
 * 
 * essentially all the keys that is bigger than N-1 is now reshuffled, which is a burst of workload for the system
 * could cause crash or slow perf on the system
 * 
 * The basic idea of consistent harshing is to use the same hash function to hash the cache objects and nodes the same way
 * Conceptually now all the nodes are in a ring structure, each key will find its node by going clockwise.
 * ------A-----k2-->-----B-----k1-->-----C--------
 * 
 * As you can see, k1 goes to C node whereas k2 goes to B node. Now when we add node D, say after
 * hashing, it is placed in between B and C, then only kys in C is affected, thus this apporach is 
 * more consistent now.
 * 
 * This approach stll has the issue of unbalanced bucket because the distance between nodes are random so it 
 * could happen one node takes on too much keys and the otehr takes on two less.
 * The solution is to introduce virtual nodes for each node, call them replicas, 
 * this kind of randomize the placement of all nodes evenly.
 * @author xtang
 *
 */
public class ConsistentHashing {
	HashFunc hashFunc;
	int numOfReplica;
	SortedMap<Integer, Object> ring = new TreeMap<>();
	
	public ConsistentHashing(HashFunc func, int numOfRep) {
		hashFunc = func;
		numOfReplica = numOfRep;
	}
	//add machien node to the ring
	public void addNode(CacheNode node) {
		for (int i=0; i<numOfReplica; i++) {
			int key = hashFunc.hash(node.toString()+i);
			ring.put(key, node);
		}
	}
	//remove machine node from the ring
	public void removeNode(CacheNode node) {
		for (int i=0; i<numOfReplica; i++) {
			int key = hashFunc.hash(node.toString()+i);
			ring.remove(key);
		}
	}
	//get the machine node that is dedicated for this object
	public CacheNode getNode(Object o) {
		if (ring.isEmpty()) return null; // no node in the ring yet
		//get the hash first
		int hash = hashFunc.hash(o);
		if (!ring.containsKey(hash)) {
			//find the first machine node that is trailing hash
			//Returns a view of the portion of this map whose keys are greater than or equal to hash
			SortedMap<Integer, Object> tailMap = ring.tailMap(hash);
			if (tailMap.isEmpty()) {
				hash = ring.firstKey();
			} else {
				hash = tailMap.firstKey();
			}
		}
		return (CacheNode)ring.get(hash);
	}
	//the above three methods for maintaining the ring of nodes only
	//no code yet for actually storing the cach object, that part is
	//dedicated to each node itself
	
	public static void main(String[] args) {
		ConsistentHashing cHash = new ConsistentHashing(new HashFunc(), 3);
	}
}
class CacheNode {
	String nodeName;
	Random rand = new Random();

	public CacheNode(String n) {
		nodeName = n;
	}
	@Override
	public String toString() {
		return nodeName+rand.nextInt(10)+"_";
	}
}
class HashFunc {
	//make a fake hash function here
	Map<Object, Integer> map = new HashMap<>();
	Random rand = new Random();
	int hashRange = 1000;
	public int hash(Object o) {
		if (map.containsKey(o)) return map.get(o);
		int hashVal = rand.nextInt(hashRange);
		while (map.containsKey(hashVal)) hashVal = rand.nextInt(hashRange);
		map.put(o, hashVal);
		return hashVal;
	}
}

