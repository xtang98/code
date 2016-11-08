package algorithms.sorting;

import java.util.Arrays;

public class Sort {
	public static void mergeSort(int[] nums) {
		mergeSort(nums, 0, nums.length-1); //[2,1] (nums, 0,1)
	}
	private static void mergeSort(int[] nums, int left, int right) {
		int len = 	right - left + 1;
		if (len<2) return;

		//divide and merge
		int mid = left+len/2;
		mergeSort(nums, left, mid-1);
		mergeSort(nums, mid, right);
		//merge phase [2,1] ==> [2] [1]
		int[] marr = new int[len];
		int i=left, j= mid, idx=0;
		while (i<mid && j<=right) {
			if (nums[i]<nums[j]) marr[idx++] = nums[i++];
			else marr[idx++] = nums[j++];
		}
		while(i<mid) marr[idx++] = nums[i++];
		while(j<=right) marr[idx++] = nums[j++];
		//copy it back from left to right
		for (int k=0; k<marr.length; k++) {
			nums[left+k] = marr[k];
		}
	}
	
	/*
	 * 3,2,1
	 * 
	 * pivot=3, i=1, j=2
	 * while(i<j)
	 * 		#1 loop: i=3, j=2, no swap for i,j, exit loop
	 * swap l=0 and j=2 
	 * [1,2,3]
	 */
	public static void quickSort(int[] nums) {
		quickSort(nums, 0, nums.length-1);
	}
	
	private static void quickSort(int[] nums, int l, int r) {
		if (l>=r) return;
		int len = r-l+1;
		if (len<2) return;
		//at least two elements, do partition
		int idx = partition(nums, l, r);
		quickSort(nums, l, idx-1);
		quickSort(nums, idx+1, r);
	}
	private static int partition(int[] nums, int l, int r) {
		int pivot = nums[l];
		int i=l; // i is the separator for smaller elements, e.g. 1,2,3 or 3,2,1 or 2,1,3
		for (int j=i; j<=r; j++) { //use j to scan through all elements, move smaller ones to front
			if (nums[j]<pivot) {
				i++;
				swap(nums, i, j);
			}
		}
		// now i points to a smaller elements, swap i with l, return i as the pivot index
		swap(nums, i, l);
		return i;
	}
	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
	}
}
