/**
 * 
 */
package algorithms.Trie;

import java.util.HashMap;

/**
 * Implement Trie using HashMap, not as fast as array-based, but flexible and less space wasted for sparse Trie
 * @author xuerong.tang
 *
 */

class HashTrieNode {
	boolean isLeaf;
	HashMap<Character, HashTrieNode> map;
	HashTrieNode() {map = new HashMap<Character, HashTrieNode>();}
}
/**
 * public void insert(String word): insert word into trie
 * public boolean find(String word): returns true if word is found
 * private TrieNode findNode(String prefix): return the node if prefix is found
 * public boolean startWith(String prefix): return true if the prefix is in the trie
 * @author xuerong.tang
 *
 */
public class HashTrie {
	HashTrieNode root = new HashTrieNode();
	public void insert(String word) {
		HashTrieNode cur = root;
		for (int i=0; i<word.length(); i++) {
			char c = word.charAt(i);
			//if c is not already in trie insert it
			if (!cur.map.containsKey(c)) {
				cur.map.put(c, new HashTrieNode());
			}
			cur = cur.map.get(c);
		}
		cur.isLeaf = true;
	}
	
	public boolean find(String word) {
		HashTrieNode n = findNode(word);
		return n!=null && n.isLeaf;
	}
	
	public boolean startWith(String prefix) {
		HashTrieNode n = findNode(prefix);
		return n != null;
	}
	
	public HashTrieNode findNode(String prefix) {
		HashTrieNode cur = root;
		for (int i=0; i<prefix.length();i++) {
			Character c = prefix.charAt(i);
			if (!cur.map.containsKey(c)) return null;
			cur = cur.map.get(c);
		}
		return cur;
	}
}
