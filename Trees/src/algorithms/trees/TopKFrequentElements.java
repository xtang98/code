package algorithms.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class TopKFrequentElements {

	/**
	 * 347. Top K Frequent Elements
	 * Given a non-empty array of integers, return the k most frequent elements.
		For example,
		Given [1,1,1,2,2,3] and k = 2, return [1,2].
		
		Note: 
		You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
		Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
		IDEA ONE: 1) build a count map: key --> count
				  2) go over keys to pump (key, count) pair to a priority queue of K elements (serves as minheap)
	 * @param nums
	 * @param k
	 * @return
	 */
	public static List<Integer> topKFrequentUsingPQ(int[] nums, int k) {
		List<Integer> ret = new ArrayList<>();
		
		//build a map: key->count
		Map<Integer, Integer> map = new HashMap<>();
		for (int n: nums) {
			map.put(n, 1+map.getOrDefault(n, 0));
		}
		//use priority queue as min heap, top element is always the smallest
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>(k, new Comparator<Pair>(){
			@Override
			public int compare(Pair a, Pair b) {
				return a.count - b.count; //natural order
			}
		});
		for (int key: map.keySet()) {
			if (pq.size()<k) {
				pq.offer(new Pair(key, map.get(key)));
			} else {
				if (pq.peek().count<map.get(key)) {
					pq.poll();
					pq.offer(new Pair(key, map.get(key)));
				}
			}
		}
		//convert pq to result, do a reverse if we want the highest freq element at the beginning...
		while (!pq.isEmpty()) {
			ret.add(pq.poll().key);
		}
		Collections.reverse(ret);
		return ret;
	}
	/**
	 * Same as above except the  second step, we put keys int to a array where array's index is the count
	 * @param nums
	 * @param k
	 * @return
	 */
	public static List<Integer> topKFrequentNotUsingPQ(int[] nums, int k) {
		List<Integer> ret = new ArrayList<>();
		
		//build a map: key->count
		Map<Integer, Integer> map = new HashMap<>();
		for (int n: nums) {
			map.put(n, 1+map.getOrDefault(n, 0));
		}
		//use array of buckets to hold keys where the index is the count
		Set<Integer>[] buckets = new Set[nums.length+1];
		for (int key: map.keySet()) {
			int count = map.get(key);
			if (buckets[count] == null) {
				buckets[count] = new HashSet<Integer>();
			}
			buckets[count].add(key);
		}
		//count buckets from last index (top frequent ones), put them into ret as long as ret.size<k
		for (int i=buckets.length-1; i>=0 && ret.size()<k; i--) {
			if (buckets[i] != null && buckets[i].size()>0) {
				for (int b: buckets[i]) {
					if (ret.size()>=k) break;
					ret.add(b);
				}
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(topKFrequentUsingPQ(new int[]{1,3,6,6,1,1,1}, 2));
		System.out.println(topKFrequentNotUsingPQ(new int[]{1,3,6,6,1,1,1}, 2));

	}

}

class Pair {
	int key, count;
	Pair(int k, int c) {key = k; count=c;}
}
