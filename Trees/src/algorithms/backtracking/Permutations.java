/**
 * 
 */
package algorithms.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xuerong.tang
 *
 */
public class Permutations {
	
	/***
	 * [1,2,3] without duplicate, 
	 * @param num
	 * @return
	 */
	public static List<List<Integer>> permutationsWithNoDup(int[] nums) {
		List<List<Integer>> sol = new ArrayList<>();
		rec(nums, 0, sol);
		return sol;
	}
	private static void rec(int[] nums, int idx, List<List<Integer>> sol) {
		//terminate when idx reaches last digit
		if (idx >= nums.length-1) {
			//stream nums, boxed to Integer, then collect to list
			List<Integer> l = Arrays.stream(nums).boxed().collect(Collectors.toList());
			//make a copy of it
			sol.add(new ArrayList<Integer>(l));
			return;
		}
		for ( int i = idx; i<nums.length; i++) {
			//swap current with any digit after it
			swap(nums, idx, i);
			//go recursive
			rec(nums, idx+1, sol);
			//swap back to restore the nums
			swap(nums, idx, i);
		}
	}
	private static void swap(int[] nums, int i, int j) {
		if (i==j) return;
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
	
	/**
	 * [1,2,2,3] or [1,2,3,2], so we need to avoid swapping duplicate to the front each round
	 * @param nums
	 * @return
	 */
	public static List<List<Integer>> permutationsWithDup(int[] nums) {
		//sorting won't help for nested levles since it breaks the order after swapping
		//make this way of avoiding dup failed:
		//i>idx && nums[i] == nums[i-1] (this will work only if the array after idx is always sorted
		//Arrays.sort(nums);
		//now using a hashset to avoid duplicate to be swapped to the beginning
		List<List<Integer>> sol = new ArrayList<>();
		rec1(nums, 0, sol);
		return sol;
	}
	private static void rec1(int[] nums, int idx, List<List<Integer>> sol) {
		//terminate condition
		if (idx >= nums.length-1) {
			sol.add(new ArrayList<Integer>(
					Arrays.stream(nums).boxed().collect(Collectors.toList())
					));
			return;
		}
		//for each digit after idx, go recursive -- 
		//swapping it to front, be careful about repeated digit though
		//use a hashset to avoid swapping dup to the front
		Set<Integer> set = new HashSet<>();
		for (int i=idx; i<nums.length; i++) {
			if (set.contains(nums[i])) continue; //skip repeat number
			set.add(nums[i]);
			swap(nums, idx, i);
			rec1(nums, idx+1, sol);
			swap(nums, idx, i);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("[1,2,3,4] permutation is:");
		List<List<Integer>> sol = permutationsWithNoDup(new int[]{1,2,3,4});
		for (List<Integer> l: sol) {
			System.out.println(l);
		}
		
		System.out.println("[1,2,2,3] permutation (with dup) is:");
		sol = permutationsWithDup(new int[]{1,2,2,3});
		for (List<Integer> l: sol) {
			System.out.println(l);
		}

		System.out.println("[0,1,0,9,0] permutation (with dup) is:");
		sol = permutationsWithDup(new int[]{0,1,0,9,0});
		for (List<Integer> l: sol) {
			System.out.println(l);
		}

	}

}
