package algorithms.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
Input: dictionary[] = {"GEEKS", "FOR", "QUIZ", "GO"};
 
boggle[][]   = {{'G','I','Z'},
                {'U','E','K'},
                {'Q','S','E'}};
isWord(str): returns true if str is present in dictionary
            else false.

Output:  Following words of dictionary are present
  GEEKS
  QUIZ
*/
public class Boggle {
	String[] dictionary = {"geeks", "for", "quiz", "go"};
	ArrayTrie trie;
	int maxLen = 0;
	public Boggle() {
		// TODO Auto-generated constructor stub
		trie = new ArrayTrie();
		for (String s: dictionary) {
			trie.insert(s);
			if (s.length()>maxLen) maxLen = s.length();
		}
	}
	
	public List<String> allWords(char[][] boggle) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<boggle.length; i++) {
			for (int j=0; j<boggle[0].length; j++) {
				dfs(boggle, i, j, list, "");
			}
		}
		return list;
	}
	
	private void dfs(char[][] boggle, int i, int j, List<String> list, String prefix) {
		int n = boggle.length, m = boggle[0].length;
		if (i<0 || i>=n || j<0 || j>=m) return; //out of boundary
		prefix+=boggle[i][j];
		if (trie.find(prefix)) {
			list.add(prefix); //even find a word, cannot stop here, since it can be a prefix of anotehr word
		}
		//no need to continue this route if any of these is true:
		// 1. prefix is over the max len of the word in dictionary
		// 1. prefix so far is not matching the prefix tree!
		if (prefix.length()>maxLen || !trie.startWith(prefix)) {
			return; 
		}
		
		//now go recursive 8 directions, sometimes asked for 4, depending on if we count on diagonal ones
		dfs(boggle, i-1,j,list, prefix); //up
		dfs(boggle, i-1,j-1,list, prefix); //up
		
		dfs(boggle, i+1,j,list, prefix); //down
		dfs(boggle, i+1,j+1,list, prefix); //down
		
		dfs(boggle, i,j-1,list, prefix); //left
		dfs(boggle, i+1,j-1,list, prefix); //left
		
		dfs(boggle, i,j+1,list, prefix); //right
		dfs(boggle, i-1,j+1,list, prefix); //right
		
	}
	
	//5,1,5
	// consider max ending at i
	//i=0: include 5 [5];  i=1: include 1, [0+1], ie a[i]+preNot
	//     not [0]				not include 1, max(preInclude, preNot...
	//so basically 
	public static int getMaxNonConsecutive(int[] nums) {
		int len = nums.length;
		if (len == 0) return 0;
		if (len == 1) return nums[0]; //single elements
		int sumInclude = nums[0];
		int sumNotIncl = 0;
		for (int i=1; i<len; i++) {
			int preInclude = sumInclude;
			int preNotIncl = sumNotIncl;
			sumInclude = preNotIncl + nums[i]; // current plus preNotInclul
			sumNotIncl = Math.max(preInclude, preNotIncl);
		}
		return Math.max(sumInclude, sumNotIncl);
	}
	
	
	public static void main(String[] args) {
		char[][] boggle   = new char[][]   {{'g','i','z'},
							                {'u','e','k'},
							                {'q','s','e'}};
		Boggle game = new Boggle();
		new ArrayList<String>(Arrays.asList("geeks", "for", "quiz", "go", "", "about","abx","abo","ab")).forEach(l -> System.out.println("find "+l+"==>"+game.trie.find(l)));
		System.out.println("Find words in the boggle:");
		game.allWords(boggle).forEach(l -> System.out.println(l));
		String word = "BCD";
		for (int i=0; i<=word.length(); i++) {
			System.out.println(word.substring(0,i)+"(A)"+word.substring(i));
		}
		String s = "1,2,3,4";
		String [] vals = s.split(",");
		for (int i=0; i<vals.length; i++) System.out.println(vals[i]);
		
		int[] nums = new int[] {5,1,5};
		int max = getMaxNonConsecutive(nums);
		System.out.print("max("+Arrays.toString(nums)+")="+max);
		
		
	}
}
