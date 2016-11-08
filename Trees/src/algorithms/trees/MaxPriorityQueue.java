package algorithms.trees;
/**Implement PriorityQueue using MaxHeap with these methods
 * 1. int extractMax()
 * 2. int maxKey()
 * 3. void insert(int key)
 * @author xt
 *
 */

public class MaxPriorityQueue {
	MaxHeap heap;
	protected MaxPriorityQueue() {}
	public MaxPriorityQueue(int n) {
		heap = new MaxHeap(n);
	}
	
	public void insert(int key) {
		heap.insert(key);
	}
	
	public int maxKey() {
		return heap.maxKey();
	}
	
	public int extractMax() {
		return heap.extractMax();
	}
	
	public int size() {return heap.count;}
}
