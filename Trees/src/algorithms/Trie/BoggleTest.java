/**
 * 
 */
package algorithms.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author xtang98
 *
 */
public class BoggleTest extends TestCase {
	
	static char[][] boggle   = new char[][]   {{'g','i','z'},
								        {'u','e','k'},
								        {'q','s','e'}};
	static Boggle game = new Boggle();						        

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testDictionary() {
		String[] dict = {"geeks", "for", "quiz", "go"};
		assertEquals(game.dictionary.length, 4);
		System.out.println("dictionary content:");
		for (int i=0; i<game.dictionary.length; i++) {
			System.out.println(game.dictionary[i]);
			assertEquals(dict[i], game.dictionary[i]);
		}
	}

	@Test
	public void testTrieFind() {
		
		List<String> inputs = new ArrayList<String>(
				Arrays.asList(	"geeks", 	"for", 	"quiz", 	"go", "", 	"about","abx","abo","ab"));
		List<Boolean> expected = new ArrayList<Boolean>(
				Arrays.asList(	 true, 		true, 	true, 		true, false, false, false, false, false)); 
		inputs.forEach(l -> System.out.println("find "+l+"==>"+game.trie.find(l)));
		for (int i=0; i<inputs.size(); i++) {
			boolean exp = expected.get(i);
			boolean actual = game.trie.find(inputs.get(i));
			assertEquals("input ["+inputs.get(i)+"]:", exp, actual);
		}
	}
	
	@Test
	public void testAllWordsMethod() {
		
		System.out.println("Find words in the boggle:");
		game.allWords(boggle).forEach(l -> System.out.println(l));
	}
}
