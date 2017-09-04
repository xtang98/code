package algorithms.Trie;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrieTest {

	@Test
	public void testValidWord() {
		ArrayTrie trie = new ArrayTrie();
		assertTrue(trie.root.arr[0] == null);
		String word = "abc";
		assertFalse(trie.find(word));
		trie.insert("abc");
		assertTrue(trie.find(word));
	}
	
	@Test
	public void testInvalidWord() {
		ArrayTrie trie = new ArrayTrie();
		String word = "Abc";
		try {
			trie.insert(word);
		} catch(RuntimeException e) {
			assertTrue(e.getMessage().contains("allowed char is [a-z]"));
		}
	}
	
	@Test
	public void testHashTrieInsert() {
		HashTrie hTrie = new HashTrie();
		hTrie.insert("ABC");
		assertEquals(hTrie.root.map.get('a'), null);
		assertFalse(hTrie.root.map.get('A').isLeaf);
		assertFalse(hTrie.root.map.get('A').map.get('B').isLeaf);
		assertTrue(hTrie.root.map.get('A').map.get('B').map.get('C').isLeaf);
		
		assertTrue(hTrie.startWith("A"));
		assertTrue(hTrie.startWith("AB"));
		assertTrue(hTrie.startWith("ABC"));
		assertFalse(hTrie.startWith("a"));
		assertFalse(hTrie.startWith("ABCD"));
		
		
		
		
	}

}
