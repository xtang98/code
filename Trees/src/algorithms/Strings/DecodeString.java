package algorithms.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//import algorithms.list.NestedList;

public class DecodeString {
	/**
	 * Stack-based on recursive solution: for each char:
	 * case digit: accumulate to count
	 * case [: push the count
	 * case ]: pop the count, process the string on the top of the strbuilder stack
	 * default: accumulate chars to the top of the strbuilder stack
	 * 394. Decode a string using the rule: k[encoded_string] meaning chars inside brackets repeated k times
	 * s = "3[a]2[bc]", return "aaabcbc".
	 * s = "3[a2[c]]", return "accaccacc".
	 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
	 * @param s
	 * @return
	 */
    public static String decodeStringStack(String s) {
    	Stack<Integer> countSt = new Stack<>();
    	Stack<StringBuilder> sbSt = new Stack<>();
    	sbSt.add(new StringBuilder());
    	int count=0;
    	for (char c: s.toCharArray()) {
    		if (Character.isDigit(c)) {
    			count=10*count+(c-'0');
    		} else if (c == '[') {
    			//push count and a new strBuilder
    			countSt.push(count);
    			sbSt.push(new StringBuilder());
    			count=0;
    		} else if (c == ']') {
    			//pop phase, pop from both stack, compose new inner, append it back to the current top of sbStack
    			int repeat = countSt.pop();
    			String inner = sbSt.peek().toString();
    			for (int i=1; i<repeat; i++) sbSt.peek().append(inner);
    			inner = sbSt.pop().toString();
    			sbSt.peek().append(inner);
    		} else {
    			sbSt.peek().append(c);
    		}
    	}
    	return sbSt.peek().toString();
    }
    
    public static class StackItem {
    	int count;
    	StringBuilder buffer;
    	public StackItem(int c) {
    		count = c;
    		buffer = new StringBuilder();
    	}
    }
    /***
     * One stack non recursive approach, each stack item  holding a counter and a partial result
     * .e.g  ab2[xy3[mn]], stack will be sth like this before we are reaching any pop phase
     * [3-->mn]
     * [2-->xy]
     * [-1-->ab] (bottom element, the counter is useless, can assign any value to it
     * 
     * Then after first popping, stack looks like this (mnmnmn is appended to lower layer
     * [2->xymnmnmn]
     * [-1-->ab]
     * 
     * Then after second popping, stack looks like this:
     * [-1-->abxymnmnmnxymnmnmn], now we can simply return the top element
     * @param s
     * @return
     */
    public static String decodeStringOneStack(String s) {
    	Stack<StackItem> st = new Stack<>();
    	st.push(new StackItem(-1));
    	int count = 0;
    	for (char c: s.toCharArray()) {
    		if (Character.isDigit(c)) {
    			// accumulate count
    			count = count*10+ (c-'0');
    		} else if (c == '[') {
    			// push a StackItem to the stack
    			st.push(new StackItem(count));
    			count = 0;
    		} else if (c == ']') {
    			// pop and expand and append to top StackItem
    			StackItem top = st.pop();
    			for (int i=0; i<top.count; i++) {
    				st.peek().buffer.append(top.buffer);
    			}
    		} else {
    			// append the char to the top StackItem
    			st.peek().buffer.append(c);
    		}
    	}
    	return st.pop().buffer.toString();
    }
    
    /**
	 * Recursive solution simplified using a global counter, the general idea is keep a global index that
	 * keeps moving forward during any level of recursive call, each recursive call start with that global index to
	 * process the string after it.
	 * Borrow c++ solution from web here:
	 * https://leetcode.com/problems/decode-string/discuss/87543/0ms-simple-C++-solution
	 * 394. Decode a string using the rule: k[encoded_string] meaning chars inside brackets repeated k times
	 * s = "3[a]2[bc]", return "aaabcbc".
	 * s = "3[a2[c]]", return "accaccacc".
	 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
	 * @param s
	 * @return
	 */
    public static String decodeStringSimple(String s) {
    	int[] idx = new int[1];
    	return decodeStringWitCounter(s, idx);
    }
    //process that string after index, eg ab2[c3[x]]
    private static String decodeStringWitCounter(String s, int[] idx) {
    	StringBuilder ret = new StringBuilder();
    	while (idx[0]<s.length()) {
    		//inner most call will hit the inner most one and return
    		if (s.charAt(idx[0]) == ']') return ret.toString(); 
    		if (!Character.isDigit(s.charAt(idx[0]))) {
    			ret.append(s.charAt(idx[0]));
    			idx[0]++;
    		} else {
    			// now we are at 2[c3[x]] for the above example, time to go recursive
    	    	int count = 0;
    	   		while (idx[0]<s.length() && Character.isDigit(s.charAt(idx[0]))) {
        			count =count*10+(s.charAt(idx[0]++)-'0');
        		}
    			idx[0]++; //skip [
    			String tmp = decodeStringWitCounter(s, idx);
    			//System.out.println("inner part = ["+tmp+"] with count="+count);
    			idx[0]++; //skip ]
    			while (count-->0) ret.append(tmp);
    		}    		
    	}
    	return ret.toString();
    }
    
	/**
	 * Recursive solution to
	 * 394. Decode a string using the rule: k[encoded_string] meaning chars inside brackets repeated k times
	 * s = "3[a]2[bc]", return "aaabcbc".
	 * s = "3[a2[c]]", return "accaccacc".
	 * s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
	 * @param s
	 * @return
	 */
    public static String decodeString(String s) {
        if (s == null || s.length() ==0) return s;
        StringBuilder sb = new StringBuilder();
        List<String> parts = split(s);
        for (String p: parts) {
        	if (p.indexOf('[')<0) {
                sb.append(p);
            } else {
                int factor = Integer.parseInt(p.substring(0, p.indexOf('[')));
                String inner = p.substring(p.indexOf('[')+1, p.length()-1);
                for (int i=0; i<factor; i++)
                	sb.append(decodeString(inner));
            }
        }
        return sb.toString();
    }

	//split a non empty string, after hitting matching "]"
    public static List<String> split(String s) {
        List<String> ret = new ArrayList<>();
        //move j from 0 to end, whenever hitting matching "]", do a reset of i, j
        int i=0, j=0, count=0;
        while (j<s.length()) {
            if (s.charAt(j) == '[') count++;
            if (s.charAt(j) == ']'){
                if (count > 1) count--;
                else { //match portion is found!! reset everything
                	String inner = s.substring(i,j+1);
                	//could be ab2[blah], so need to parse out whatever before a number
                	StringBuilder first = new StringBuilder();
                	for (int k=0; k<inner.length(); k++) {
                		if (inner.charAt(k)<='9' && inner.charAt(k)>=0) break;
                		first.append(inner.charAt(k));
                	}
                	if (first.length()>0)
                		ret.add(first.toString());
                	ret.add(inner.substring(first.length()));
                    i=j+1;
                    count=0;
                }
            }
            j++;
        }
        //if i<j, that means more work to do
        if (i<j) {
            ret.add(s.substring(i));
        }
        return ret;
    }
	public static void main(String[] args) {
		System.out.println(split("3[a]2[bc]"));
		System.out.println(split("3[a2[c]]"));
		System.out.println(split("2[abc]3[cd]ef"));
		System.out.println(split("ef"));
		System.out.println(split("a2[c]"));		
		
		System.out.println(decodeString("3[a]2[bc]"));
		System.out.println(decodeString("3[a2[c]]"));
		System.out.println(decodeString("2[abc]3[cd]ef"));
		System.out.println(decodeString("ef"));
		
		System.out.println(decodeStringStack("3[a]2[bc]"));
		System.out.println(decodeStringStack("3[a2[c]]"));
		System.out.println(decodeStringStack("2[abc]3[cd]ef"));
		System.out.println(decodeStringStack("ef"));

		System.out.println(decodeStringOneStack("3[a]2[bc]"));
		System.out.println(decodeStringOneStack("3[a2[c]]"));
		System.out.println(decodeStringOneStack("2[abc]3[cd]ef"));
		System.out.println(decodeStringOneStack("ef"));
		
		System.out.println(decodeStringSimple("3[a]2[bc]"));
		System.out.println(decodeStringSimple("3[a2[c]]"));
		System.out.println(decodeStringSimple("2[abc]3[cd]ef"));
		System.out.println(decodeStringSimple("ef"));


	}

}
