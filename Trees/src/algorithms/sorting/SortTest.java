package algorithms.sorting;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMergeSort() {
		int[] nums = new int[] {3,2,1};
		System.out.println(Arrays.toString(nums));
		Sort.mergeSort(nums);
		assertTrue("[1, 2, 3]".equals(Arrays.toString(nums)));
		
		nums = new int[]{3,2};
		Sort.mergeSort(nums);
		assertEquals("[2, 3]",Arrays.toString(nums));

		nums = new int[]{3,2, 8,9,4,5,1,18,3,7};
		Sort.mergeSort(nums);
		assertEquals("[1, 2, 3, 3, 4, 5, 7, 8, 9, 18]",Arrays.toString(nums));
	}
	
	@Test
	public void testQuickSort() {
		int[] nums = new int[] {3,2,1};
		System.out.println(Arrays.toString(nums));
		Sort.quickSort(nums);
		System.out.println(Arrays.toString(nums));
		
		assertEquals("[1, 2, 3]", Arrays.toString(nums));
		
		nums = new int[]{3,2};
		Sort.quickSort(nums);
		assertEquals("[2, 3]",Arrays.toString(nums));
		
		
		nums = new int[]{3,2, 8,9,4,5,1,18,3,7};
		Sort.quickSort(nums);
		assertEquals("[1, 2, 3, 3, 4, 5, 7, 8, 9, 18]",Arrays.toString(nums));
	}

}
