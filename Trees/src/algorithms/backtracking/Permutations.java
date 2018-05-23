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
	 * [1,2,3] without duplicate, general idea is to swap elements to the front, do it recursively
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
	 * General idea is to swapping number to the front, using a hashset to skip visited element (dup)
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
	 * 17. Letter Combinations of a Phone Number:
	 * Input:Digit string "23"
	 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
	 * @param digits
	 * @return
	 */
	private static String[] numToChars = {"0","1","abc","def","ghi","jkl","lmn","opq","rst","uvw", "xyz"};
	public static List<String> letterCombinations(String digits) {
        List<String> sol = new ArrayList<>();
        rec(digits.toCharArray(), 0, sol, "");
        return sol;
    }
	private static void rec(char[] digits, int idx, List<String> sol, String prefix) {
		//termination condition
		if (idx == digits.length) {
			sol.add(prefix);
			return;
		}
		//go recursive for each possible char
		int digit = digits[idx] - '0';
		for (char c: numToChars[digit].toCharArray()) {
			rec(digits, idx+1, sol, prefix+c);
		}
	}

	/**
	 * 93. Restore IP Addresses
	 */
	public static List<String> restoreIpAddresses(String s) {
		List<String> sol = new ArrayList<>();
		restoreIp(s, 0, "", sol);
		return sol;
	}
	private static void restoreIp(String s, int count, String prefix, List<String> sol) {
		//termination condition, stop at 4 try no match we got a decoding or not
		if (count == 4) {
			if (prefix.startsWith("255.255.11")) {
				System.out.println(s+"--->"+prefix+", count="+count);
				System.out.println();
			}
			if (s.length()==0) {
				//used up precisely the whole string input, now we got a perfect decoding
				sol.add(prefix);
			}
			return;
		}
		//go recursive, drop the string by 1,2,3 chars if possible
		for (int i=1; i<=3 && i<=s.length(); i++) {
			String part1 = s.substring(0,i);
			String part2 = s.substring(i);
			if (part1.length()>1 && part1.startsWith("0")) continue; // "01" or "00" won't be valid, but siginle digit "0" is ok
			if (Integer.parseInt(part1)>255) continue; // ip must be between 0-255
			restoreIp(part2, count+1, prefix+part1+(count<3? ".":""), sol);
		}
	}
	
    /**
     * https://www.geeksforgeeks.org/generate-all-binary-strings-from-given-pattern/
     * Generate all binary strings from given pattern
     * Given a string containing of ‘0’, ‘1’ and ‘?’ wildcard characters, generate all binary strings that can be formed by replacing each wildcard character by ‘0’ or ‘1’.
	
	 * Input str = "1??0?101"
		Output: 
	        10000101
	        10001101
	        10100101
	        10101101
	        11000101
	        11001101
	        11100101
	        11101101
	 * Analysis: every "?" trigger two branches, so 2^k solutions where k is the number of "?"
	 */
    public static List<String> getAllStringsOf0And1(String s) {
    	List<String> ret = new ArrayList<>();
    	char[] cs = s.toCharArray();
    	rec01(cs, 0, ret);
    	return ret;
    }
    public static void rec01(char[] cs, int idx, List<String> ret) {
    	if (idx==cs.length) { //has to recurively process the last char as well since last char could be "?" as well
    		ret.add(new String(cs));
    		return;
    	}
    	if (cs[idx] != '?') {
    		//single branch
    		rec01(cs, idx+1, ret);
    	} else {
    		//two branches with 0 and 1 replacing ?
    		cs[idx] = '0'; 
    		rec01(cs, idx+1, ret);
    		cs[idx] = '1';
    		rec01(cs, idx+1, ret);
    		//need to backtrack so the upper level call can still have chance to process "?"
    		//otherwise let's say rec01(idx=1) branch out left and right, after left call process lower level calls and returns
    		//right call won't see "?" anymore, thus produce less result.
    		/*e.g. f(1?0?)  ==> f(100?) ==> f(1000)
    									==> f(1001) --- now need backtracking to restore the last char to be ? again
    						==> f(110?) --- without backtracking, by now char array becomes 1101 already, thus produce only one instead two strings 
    		*/
    		cs[idx] = '?';
    		
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
		System.out.println(letterCombinations("23"));
		System.out.println(letterCombinations("0238"));
		System.out.println(restoreIpAddresses("25525511135"));

		System.out.println(getAllStringsOf0And1("1??0?101"));
		System.out.println(getAllStringsOf0And1("1?0?"));

	}

}
