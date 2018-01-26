package algorithms.Strings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class StringProcessing {
	/**LeetCode:
	 * Longest Substring with At Most Two Distinct Characters
	 * e.g. aaabc ==> 4 because aaab is the max window with two chars (a, b), for  aaabcdddd => 5, because cdddd is the max window with two chars (c,d)
	 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
	 * In this solution, a hashmap is used to track the unique elements in the map. When a third character is added to the map, the left pointer needs to move right.
	 */
	public static int lengthOfLongestSubstringTwoDistinct(String s) {
		int len = 0, start = 0, end = 0, counter = 0;
		Map<Character, Integer> map = new HashMap<>();
		//loop over s using end pointer, the inner loop make sure window contains 2 distinct chars always
		while (end<s.length()) {
			//put char in map
			Character c = s.charAt(end);
			map.put(c, map.getOrDefault(c, 0)+1);
			//if count is 1, counter++
			if (map.get(c) == 1) counter++;
			end++;
			
			//while counter>2, window violates the 2-distinct property, advance start pointer until it passes the first distinct char
			while(counter>2) {
				//reduce map count for start char
				Character c0 = s.charAt(start);
				map.put(c0, map.get(c0)-1);
				//if it reaches 0, 2-distinct property is restored now, counter -- (this will exit the loop)
				if (map.get(c0) == 0) counter --; //
				start++;
			}
			//for every loop, update len to take the max(len, end-start) 
			System.out.println("start="+start+", end="+end+", counter = "+counter);
			len = Math.max(len, end-start);
		}
		return len;
	}
	/**
	 * Leetcode 30. Substring with Concatenation of All Words
	 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

		For example, given:
		s: "barfoothefoobarman"
		words: ["foo", "bar"]
		
		You should return the indices: [0,9].
		(order does not matter).
	 * @param s
	 * @param words
	 * @return
	 */
	public static List<Integer> findSubstringOfConcatAllWords(String s, String[] words) {
		//If we consider each word is a special "char", then it is reduced to anagram problem
		List<Integer> ret = new ArrayList<>();
		//build map for words, get mapCount
		Map<String, Integer> map = new HashMap<>();
		for (String w: words) {
			map.put(w, map.getOrDefault(w, 0)+1);
		}
		int mapCount = map.size();
		//init window (start, end) to be (0,0)
		int start = 0, end = 0, wordLen=words[0].length();
		
		//loop through s using end pointer
		while (end+wordLen<=s.length()) {
			System.out.println(map);

			String cur = s.substring(end, end+wordLen);
			//any matched word
			if (map.containsKey(cur)) {
				//map reduce by 1
				map.put(cur, map.get(cur)-1);
				//mapCount-- if this char reduces to 0
				if (map.get(cur) == 0) mapCount--;
			}
			end+=wordLen;

			//while mapCoount == 0 do inner loop using start pointer since we got a solution:
			while (mapCount == 0) {
				//if hit a mached char
				String startWord = s.substring(start, start+wordLen);
				if (map.containsKey(startWord)) {
					//increase the count in map
					map.put(startWord, map.get(startWord)+1);
					//if count>0, it means we should stop here for a solution
					if (map.get(startWord)>0) {
						//increase mapCount to exit inner loop
						mapCount++;
					}
					//if lengh matches, we got a solution
					if (end-start == wordLen*words.length) {
						ret.add(start);
					}
				}
				start+=wordLen;
			}
		}
		return ret;
	}
	/***
	 * LeetCode 76: Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

		For example,
		S = "ADOBECODEBANC"
		T = "ABC"
		Minimum window is "BANC".
		
		Note:
		If there is dup in T, window has to contain dup as well, so if T="aa", S="a", there is no solution
		If there is no such window in S that covers all characters in T, return the empty string "".
		
		If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

	 * @param s
	 * @param t
	 * @return
	 */
	public static String minWindowSubstring(String s, String t) {
        //construct map from t, get mapCount
		Map<Character, Integer> map = new HashMap<>();
		for (int i=0; i<t.length(); i++) {
			Character c = t.charAt(i);
			if (!map.containsKey(c)) map.put(c,  1);
			else map.put(c, map.get(c)+1); //for dupe, we need to record the total count
		}
		int mapCount = map.size();
		//init window (start, end), minIdx=-1, minLen = s.length()+1
		int start=0, end=0, minIdx=-1, minLen = s.length()+1;
		//loop through s using end
		while (end<s.length()) {
			//decrease map count for matching chars
			Character c = s.charAt(end);
			if (map.containsKey(c)) {
				map.put(c, map.get(c)-1);
				if (map.get(c) == 0) mapCount--; 
			}
			end++;

			//while mapCount ==0, we hit a potential solution
			while(mapCount==0) {
				//move start to the first matching char that makes count>0
				Character c0 = s.charAt(start);
				if (map.containsKey(c0)) {
					map.put(c0, map.get(c0)+1);
					if (map.get(c0)>0) { //have to check this e.g. aaaab vs aab
						mapCount++;
						//record solution
						if ((end-start)<minLen) {
							minIdx = start;
							minLen = end-start;
						}
					}
				}
				start++;
			}
		}
		return minIdx<0? "" : s.substring(minIdx, minIdx+minLen);
			
    }
	/***
	 * LeetCode 3: Given a string, find the length of the longest substring without repeating characters.
	 *	Given "abcabcbb", the answer is "abc", which the length is 3.
	 * 	Given "bbbbb", the answer is "b", with the length of 1.
	 *	Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
	 * @param s
	 * @return
	 */
	public static int lengthOfLongestSubstringNoDup(String s) {
		int max = 0, start=0, end=0; //maintain a window of no repeating chars
		Map<Character, Integer> map = new HashMap<>();
		while (end<s.length()) {
			Character cur = s.charAt(end);
			if (map.containsKey(cur)) {
				//time to move start "pass" dup char, make sure it is moving forward
				start = Math.max(start, map.get(cur)+1);
			}
			//put char into map no matter it is dup or not
			map.put(cur, end);
			//update max
			max = Math.max(max, end-start+1);
			end++;
		}
		return max;
	}
	
    /**
     * LeetCode 438: Find All Anagrams in a String
     * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
     * Input: s: "cbaebabacd" p: "abc"
	 * Output: [0, 6]
     * 
     * General idea is sliding window and against a map of Character -> Count, the map always reflect
     * how many chars are matched by the current window
     * @param s
     * @param p
     * @return
     */
    public static List<Integer> findAnagramSubstrings(String s, String p) {
        List<Integer> ret = new ArrayList<>();
        //convert p to map: Char->Count
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<p.length(); i++) {
            Character c = p.charAt(i);
            if (!map.containsKey(c)) map.put(c, 1);
            else map.put(c, 1+map.get(c));
        }
        //initial window (start, end) = (0,0) and mapCount
        int start = 0, end = 0, count = map.size(); 
        //move end all the way to the last char, through out the process,
        //map ALWAYS reflects how many chars are matched by the window
        //(matched one is deducted in count, thus count indicates #chars not yet matched)
        while (end<s.length()) {
            //move end until the map is "exhaused" (indicated by count==0)
            Character c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c)-1);
                if (map.get(c) == 0) count--; //precise match happens for this char
            }
            //System.out.println(ret+", start="+start+",end="+end+", map="+map);
            end++;
            
            //if count decreased to zero, we find a match
            //now need to shrink the start pointer to points to
            //the first char that is in the map, now (begin, end) 
            //is the solution if the length matches!!!
            //as soon as count becomes 1, we exit inner loop and new outer loop starts
            while (count == 0) {
                Character cc = s.charAt(start);
                if (map.containsKey(cc)) {
                    map.put(cc, map.get(cc)+1);
                    if (map.get(cc)>0) count++; 
                    //exit inner loop with precisely one char not matched in the map
                }
                //record sol if (start, end) is the same length
                if (end - start == p.length()) {
                    ret.add(start);
                }
                start++;
                //now if count>0, we will exit inner loop
                //(start, end) misses precisely one char, and the map state reflects that
            }
        }
        return ret;
    }
	
	
	
	
	/***
	 * return true if input string s matches p which is a string with * in it
	 * a, ab, aab all match a*, but b won't match a*
	 * use boolean m[i][j] indicate whether s[0..i-1] matches p[0..j-1], need to get m[n1][n2] where 
	 * n1 = len(s), n2=len(p) 
	 * @param s
	 * @param p
	 * @return
	 */
	public static boolean MatchedWithStar(String s, String p) {
		int n1 = s.length(), n2 = p.length();
		boolean[][] m = new boolean[n1+1][n2+1];
		for (int i=0; i<=n1; i++) {
			for (int j=0; j<=n2; j++) {
				//init condition (i=0 or j=0 precomputed)
				if (i==0 || j==0) {
					if (i==0 && j==0) m[i][j] = true; //empty matches empty
					else if (j==0) m[i][j] = false; //non empty s won't match empty p
					else m[i][j] = (p.charAt(j-1) == '*')? m[i][j-1]: false;
				} else {
					//transit m[i][j] =   
					//if (p[j-1] != '*"): m[i-1][j-1] if s[i-1] == p[j-1] else false; 
					//else:	or(m[i-1][j-1], m[i-1][j], m[i][j-1]) use * for one, any, none
					//0...i-1, i
					//0...j-1, *
					//Note: if '+' is allowed as well, then
					// m[i][j] = m[i-1][j-1] || m[i-1][j] (+ has to be used!!)
					if (p.charAt(j-1) != '*') {
						m[i][j] = (s.charAt(i-1) == p.charAt(j-1))? m[i-1][j-1]: false;
 					} else {
 						m[i][j] = (m[i-1][j-1] || m[i-1][j] || m[i][j-1]);
 					}
				}
			}
		}
		return m[n1][n2];
	}

	    public static String frequencySort(String s) {
	        if (s == null || s.length()<3) return s;
	        //hash it char->(char,count) pair
	        HashMap<Character, Pair> map = new HashMap<Character, Pair>();
	        for (int i=0; i<s.length(); i++) {
	            Character c = s.charAt(i);
	            if (!map.containsKey(c)) {
	                map.put(c, new Pair(c,1));
	            } else {
	                map.get(c).count++;
	            }
	        }
	        //put paris in priority queue, reverse order
	        Comparator<Pair> pairComparator = new Comparator<Pair>(){
	            @Override
	            public int compare(Pair a, Pair b) {
	                return b.count - a.count; // in reverse order
	            } 
	        };
	        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(10, pairComparator);
	        for (Character c: map.keySet()) {
	            pq.offer(map.get(c));
	        }
	        //convert it to output
	        StringBuilder sb = new StringBuilder();
	        while (pq.size()>0) {
	            Pair pair = pq.poll();
	            for (int i=0; i<pair.count; i++) {
	                sb.append(pair.c);
	            }
	        }
	        return sb.toString();
	    }
	    
	    public static void main(String[] args) {
	    	//System.out.println("ok");
	    	//System.out.println(findSubstringOfConcatAllWords("aaa", new String[] {"aa","aa"}));
	    	//System.out.println(findSubstringOfConcatAllWords("wordgoodgoodgoodbestword", 
	    	//		new String[] {"word","good","best","good"}));
	    	
	    	System.out.println("barfoothefoobarman [foo, bar]\n");
	    	System.out.println(findSubstringOfConcatAllWords("barfoothefoobarman", 
	    			new String[] {"foo","bar"}));
	    	System.out.println("max window for 2 dinstict chars:");
	    	System.out.println(lengthOfLongestSubstringTwoDistinct("aaabc"));
	    	System.out.println(lengthOfLongestSubstringTwoDistinct("aaabcdddd"));
	    	
	    }
	}
	class Pair {
	    Character c;
	    int count;
	    public Pair(Character c, int count) {
	        this.c=c;
	        this.count = count;
	        }
	}
