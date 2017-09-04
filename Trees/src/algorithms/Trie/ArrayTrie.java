package algorithms.Trie;

import java.util.ArrayList;
import java.util.Arrays;

//assume the trie handles 26 lower cases chars only
class TrieNode {
	boolean isLeaf; //package accessible
	TrieNode[] arr; //package accessible
	
	public TrieNode() {arr = new TrieNode[26];}
	public TrieNode(boolean leaf) {
		this();
		isLeaf = leaf;
	}
}
/**
 * Trie common methods:
 * insert(String word): insert the word into the trie
 * boolean find(String word): return true if it is a valid word
 * TrieNode searchNode(String s): return the node if s shows up in the tree (may not be a valid word); null otherwise
 * boolean startWith(String prefix): returns true if prefix shows up in the tree
 * 
 * @author 
 *
 */
public class ArrayTrie {
	TrieNode root;

	public ArrayTrie() {
		root = new TrieNode();
	}
	
	public void insert(String word) {
		TrieNode cur = root;
		for (int i=0; i< word.length(); i++) {
			char c = word.charAt(i);
			int idx = c-'a';
			if (idx<0 || idx>=26) {throw new RuntimeException("allowed char is [a-z]!");}
			if (cur.arr[idx] == null) {
				cur.arr[idx] = new TrieNode();
			}
			cur = cur.arr[idx];
		}
		cur.isLeaf = true;
	}
	
	public boolean startWith(String prefix) {
		TrieNode n = searchNode(prefix);
		return n==null? false: true;
	}
	
	public boolean find(String word) {
		TrieNode n = searchNode(word);
		return (n != null && n.isLeaf)? true: false;
	}
	
	public TrieNode searchNode(String s) {
		TrieNode cur = root;
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			int idx = c-'a';
			if (cur.arr[idx] == null) return null;
			cur = cur.arr[idx];
		}
		return cur;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayTrie trie = new ArrayTrie();
		trie.insert("about");
		trie.insert("above");
		trie.insert("ab");
		new ArrayList<String>(Arrays.asList("", "about","abx","abo","ab")).forEach(l -> System.out.println("find "+l+"==>"+trie.find(l)));
		new ArrayList<String>(Arrays.asList("about","abx","abo","ab")).forEach(l -> System.out.println("startWith "+l+"==>"+trie.startWith(l)));

	}

}
