package algorithms.Strings;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringProcessingTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatchedWithStar() {
		assertEquals(true, StringProcessing.MatchedWithStar("aab", "a*"));
		assertEquals(true, StringProcessing.MatchedWithStar("aa", "a*"));
		assertEquals(true, StringProcessing.MatchedWithStar("abc", "a*c"));
		assertEquals(false, StringProcessing.MatchedWithStar("ba", "a*"));
	}

	@Test
	public void testFreqSort() {
		assertEquals("bbbbaa", StringProcessing.frequencySort("aabbbb"));
	}
	@Test
	public void testFindAnagrams() {
		assertEquals("[1]", StringProcessing.findAnagramSubstrings("aabbbb", "ab").toString());
		assertEquals("[0, 6]", StringProcessing.findAnagramSubstrings("cbaebabacd", "abc").toString());
		assertEquals("[2, 4]", StringProcessing.findAnagramSubstrings("aaabcbaaaa", "abc").toString());
		
	}
	@Test
	public void testLongestSubstringNoDup() {
		assertEquals(3, StringProcessing.lengthOfLongestSubstringNoDup("ababc"));
		assertEquals(3, StringProcessing.lengthOfLongestSubstringNoDup("abcabcbb"));
		assertEquals(1, StringProcessing.lengthOfLongestSubstringNoDup("bbbbbbbbbb"));
		assertEquals(2, StringProcessing.lengthOfLongestSubstringNoDup("abba"));
		assertEquals(3, StringProcessing.lengthOfLongestSubstringNoDup("pwwkew"));
		
		
		
		
	}	
	
}
