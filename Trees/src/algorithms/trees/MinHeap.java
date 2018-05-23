package algorithms.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinHeap {
	//array a with len n, 0-based with these functions
	// left = 2*i+1, right=2*i+2 on 0-based array
	// parent = (i-1)/2 (this means the last node's parent is (n-1)/2!!)
	int n;
	int[] a;
	int count = 0;
	protected MinHeap() {}
	public MinHeap(int n) {
		this.n = n;
		a = new int[n];
		count = 0;
	}
	
	//existing heap index is like this [0,1,2...,count-1]
	//a[count] points to an unoccupied spot right after the last element 
	public void insert(int key) {
		if (count>=n) throw new RuntimeException("heap overflow!");
		int i = count++;
		//travel up to find a spot for key
		while (i>0 && a[parent(i)]>key) {
			a[i] = a[parent(i)];
			i = parent(i);
		}
		a[i] = key;
	}
	
	public int minKey() {
		if (count == 0) throw new RuntimeException("no element in the heap!");
		return a[0];
	}
	
	public int extractMin() {
		if (count == 0) throw new RuntimeException("no element in the heap!");
		int min = a[0];
		a[0] = a[--count];
		heapify(a, count, 0);
		return min;
	}
	
	public static int left(int i) {return 2*i+1;}
	public static int right(int i) {return 2*i+2;}
	public static int parent(int i) {return (i-1)/2;}
	public static void swap(int[] a, int i, int j) {int t= a[i]; a[i]=a[j]; a[j]=t;}
	
	// heapify: i's left and right subtrees are already heaps, 
	// root i is not, so sink down the i by swapping with bigger ones!
	public static void heapify(int[] a, int n, int i) {
		//System.out.println("heapifying "+i+":"+Arrays.toString(a));
		int l = left(i);
		int r = right(i);
		int minIdx = i;
		if (l<n && a[l]<a[i]) minIdx = l;
		if (r<n && a[r]<a[minIdx]) minIdx = r;
		if (minIdx != i) {
			swap(a,i,minIdx);
			heapify(a,n,minIdx);
		}
	}
	
	//tail recursion can be easily converted to non-recursive version
	  @SuppressWarnings("unused")
	private void heapifyNR(int[] a /*array*/, int root /*root*/, int n /*array size*/) {
		    int i = root;
		    while (i<n) {
		      int l = 2*i+1; int r = 2*i+2;
		      int minIdx = i;
		      if (l<n && a[l]< a[i]) minIdx = l;
		      if (r<n && a[r]<a[minIdx]) minIdx = r;
		      //no action needed, done!
		      if (i == minIdx) break;
		      //swap i and maxIdx, move i to maxIdx
		      swap(a, i, minIdx);
		      i = minIdx;
		    }
	  }
	public static void buildHeap(int[] a, int n) {
		//starting from leaf's parent, leaf=n-1 
		for (int i= parent(n-1); i>=0;i--) {
			heapify(a, n, i);
		}
	}
	
	public static void heapSortBigToSmallUsingMinHeap(int[] a) {
		int len = a.length;
		buildHeap(a,len); //now we have a mean heap, min is at top
		for (int n=len-1; n>0; n--) {
			swap(a,n,0);
			heapify(a,n,0);
		}
	}
	
	public static void main(String[] args) {
		int[] a = new int[] {3,4,7,6, 99,23,3,5,8,9,456,78,2345};
		//MaxHeap.buildHeap(a, a.length);
		MinHeap.heapSortBigToSmallUsingMinHeap(a);
		System.out.println(Arrays.toString(a));
		MaxPriorityQueue pq = new MaxPriorityQueue(10);
		pq.insert(3);
		pq.insert(5);
		pq.insert(18);
		while (pq.size()>0) {
			System.out.print(pq.extractMax()+",");
		}
		System.out.println();

	    List<String> list = new ArrayList<String>(Arrays.asList(
	    	      "aa","abc","aba","","x","xy","aaaa", "ababa", "ababab"));
	    	    list.forEach(s-> System.out.println(s+"-->"+isPalindrome(s)));
	}
	    	  //aba len=3 len/2 is the middle element for odd sequence
	    	  //aa len=2, len/2=1, is the first one in the second half for even sequence!
	    	  public static boolean isPalindrome(String s) {
	    	    if (s == null) return false;
	    	    if (s.length()<2) return true; //empty or sing char string are palindromes
	    	    int n = s.length();
	    	    for (int i=0; i<n/2; i++) {
	    	      if (s.charAt(i) != s.charAt(n-i-1)) return false;
	    	    }
	    	    return true;
	    	  }

}
