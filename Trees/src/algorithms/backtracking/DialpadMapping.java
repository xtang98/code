package algorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public class DialpadMapping {

	static String mapping[] = {"0","1","abc","def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
	
	public static List<String> getAllStrings(String digits) {
		List<String> ret = new ArrayList<>();
		rec(digits, 0, "", ret);
		return ret;
	}
	private static void rec(String digits, int idx, String prefix, List<String> ret) {
		if (idx == digits.length()) {
			ret.add(prefix);
			return;
		}
		for (Character c: mapping[digits.charAt(idx)-'0'].toCharArray()) {
			rec(digits, idx+1, prefix+c, ret);
		}
	}
	public static void main(String[] args) {
		System.out.println(getAllStrings("123"));

	}

}
