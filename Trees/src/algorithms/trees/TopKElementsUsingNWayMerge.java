/**
 * 
 */
package algorithms.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Queue;

/**
 * @author xtang98
 *
 */

//use to track both Integer ojbect and server index
class ServerItem  {
    int num, idx;
    public ServerItem(int n, int i) {num=n;idx=i;}
    public String toString() {return "["+num+"->at server "+idx+"]";}
}

public class TopKElementsUsingNWayMerge {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[] {1,1,1,1,2,2,2,3,4,4};
		int k = 3;
		//List<Integer> list = new TopKElements().topKFrequent(nums, k);
		List<Integer> list = new TopKElementsUsingNWayMerge().topKFrequentUsingPQ(nums, k);
		
		System.out.println(Arrays.toString(list.toArray()));
		
		
		/*MinHeap hp = new TopKElements().new MinHeap();
		hp.insert(new TopKElements().new Item(1,3));
		hp.print();
		
		hp.insert(new TopKElements().new Item(1,2));
		hp.print();
		
		hp.insert(new TopKElements().new Item(3,1));
		hp.print();
		
		
		hp.insert(new TopKElements().new Item(8,99));
		hp.print();
		*/

		//by default, Priority Queue sort elements in natural order, so 1,3,2 becomes 1,2,3 in the queue, q.poll() will return the head which is 1
		//of course you can also add a custom comparator
		java.util.PriorityQueue<Integer> q = new java.util.PriorityQueue<Integer>();
		q.add(3);
		q.add(1);
		q.add(2);
		
		System.out.println("Priority Queue in natural order:");
		while (q.size()>0) {
			System.out.print(q.poll()+",");
		}
		
		System.out.println("\nPriority Queue in reverse order with custom comparator:");

		q = new java.util.PriorityQueue<Integer>(10, new Comparator<Integer>(){
    		@Override
    		public int compare(Integer c1, Integer c2) {
                return (c2 - c1);
            }
    	});
		q.add(3);
		q.add(1);
		q.add(2);
		while (q.size()>0) {
			System.out.print(q.poll()+",");
		}
		System.out.println();
		
		//running example
		// 7,4,1
		// 8,5,2
		// 6,9,3,0, 10
		int N = 3;
		List<Integer>[] numbers = new List[N];
		numbers[0] = new ArrayList(Arrays.asList(7,4,1));
		numbers[1] = new ArrayList(Arrays.asList(8,5,2));
		numbers[2] = new ArrayList(Arrays.asList(6,9,3,0));
		numbers[2].add(10);
		System.out.println("N-way merge sort:"+numbers[0]+numbers[1]+numbers[2]+"\n==>"+sortServerCollection(numbers));
		
	}
	


	/**
	 * N-Way or K-Way Merge Sort using priority queue
	 * @param nums
	 * @return
	 */
	public static List<Integer> sortServerCollection(List<Integer>[] nums) {
		int N=nums.length;
		//sort nums for each server
		for (int i=0; i<N; i++) Collections.sort(nums[i]);
		//build a priority queue against server items with defined comparator on number
		java.util.PriorityQueue<ServerItem> pq = new java.util.PriorityQueue<ServerItem>(
				N,
				new Comparator<ServerItem>(){
		    		@Override
		    		public int compare(ServerItem c1, ServerItem c2) {
		                return (c1.num - c2.num);
		            }
		    	}
				);
		for (int i=0; i<N; i++) {
			ServerItem si = new ServerItem(nums[i].remove(0), i);
			if (!nums[i].isEmpty()) pq.offer(si);
		}
		//build result
		List<Integer> ret = new ArrayList<>();
		while (!pq.isEmpty()) {
			ServerItem cur = pq.poll();
			ret.add(cur.num);
			if (!nums[cur.idx].isEmpty()) pq.offer(new ServerItem(nums[cur.idx].remove(0), cur.idx));
		}
		
		return ret;
	}
	
	
	
	
	
	
	//using built in priority queue with custom comparer
    public List<Integer> topKFrequentUsingPQ(int[] nums, int k) {
        //build a num->count map
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], (int)map.get(nums[i])+1);
            } else {
                map.put(nums[i], 1);
            }
        }
        System.out.println("done wtih hash");
        // want to get biggest k, build and maintain a min heap of size k
    	//Comparator anonymous class implementation
    	Comparator<Item> itemComparator = new Comparator<Item>(){
    		@Override
    		public int compare(Item c1, Item c2) {
                return (c1.count - c2.count);
            }
    	};
        java.util.PriorityQueue<Item> heap = new java.util.PriorityQueue<Item>(100, itemComparator);
        for (Integer n: map.keySet()) {
            Item item = new Item(n, map.get(n));
            if (heap.size()<k) {
                heap.add(item);
            } else {
                Item min = heap.peek();
                if (item.count> min.count) {
                    heap.poll();
                    heap.offer(item);
                }
            }
        }
        System.out.println("done with minheap");

        //extract all from heap
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (heap.size()>0) {
        	Item cur = heap.poll();
        	System.out.println("current item in queue:"+cur);
        	list.add(0,cur.num);
        }
        return list;
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        //build a num->count map
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], (int)map.get(nums[i])+1);
            } else {
                map.put(nums[i], 1);
            }
        }
        System.out.println("done wtih hash");
        // want to get biggest k, build and maintain a min heap of size k
        MinHeap heap = new MinHeap();
        for (Integer n: map.keySet()) {
            Item item = new Item(n, map.get(n));
            if (heap.size()<k) {
                heap.insert(item);
            } else {
                Item min = heap.peekMin();
                if (item.count> min.count) {
                    heap.extractMin();
                    heap.insert(item);
                }
            }
        }
        System.out.println("done with minheap");
        heap.print();
        //extract all from heap
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (heap.size()>0) {
        	Item cur = heap.extractMin();
        	heap.print();
            list.add(0,cur.num);
        }
        return list;
    }
    
    public class MinHeap {
        int count = 0;
        Item[] items = new Item[100];
        
        public int size() {
            return count;
        }
        
        private void doubleSize() {
            Item[] t = new Item[2*items.length];
            for (int i=0; i<count; i++) {
                t[i] = items[i];
            }
            items = t;
        }

        
        //for insert to this heap, you need to explicitly create a new item, then insert will just take the item assign it to items array
        synchronized public void insert(Item cur) {
            //from leaf position upwards, move down the bigger ones
            int i = count++; //current empty slot
            if (count>=items.length) doubleSize();
            items[i] = cur;
            while (i>0 && items[parent(i)].count>items[i].count) {
            	swap(items, i, parent(i));
                i = parent(i);
            }
        }
        
        synchronized public Item peekMin() {
            return items[0];
        }
        
        synchronized public Item extractMin() {
            if (count == 0) throw new RuntimeException("MinHeap underflow!");
            Item cur = items[0];
            items[0] = items[count-1];
            items[count-1] = null;
            //need to do heapify from root
            heapify(items, 0, --count);
            return cur;
        }
        
        public void print() {
        	System.out.println("count:"+count+Arrays.toString(items));
        }
        private int parent(int i) {return (i-1)/2;}
        
        //starting from root i, down to leaf with n as the total heap size
        private void heapify(Item[] items, int i, int n) {
            System.out.println("i="+i+",n="+n);
            if (i>=n) return;
            int left = 2*i+1;
            int right = 2*i+2;
            int minIdx = i;
            if (left<n && items[left].count<items[i].count) minIdx = left;
            if (right<n && items[right].count<items[minIdx].count) minIdx = right;
            if (minIdx != i) {
                swap(items, i, minIdx);
                heapify(items, minIdx, n);
            }
        }
        
        private void swap(Item[] items, int i, int j) {
            Item t = new Item(0,0);
            t.assign(items[i]);
            items[i].assign(items[j]);
            items[j].assign(t);
        }
    }
    
    public class Item /*implements Comparable<Item>*/ {
        int num, count;
        public Item(int n, int c) {num=n;count=c;}
        public void assign(Item i) {num=i.num;count=i.count;}
        public String toString() {return "["+num+"->"+count+"]";}
		//@Override
		public int compareTo(Item o) {
			// TODO Auto-generated method stub
			return this.count - o.count;
		}
    }
}

