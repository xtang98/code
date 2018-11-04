package algorithms.sorting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		List<String> hexList = new ArrayList<>();
		for (int i=0; i<1000; i++) {
			list.add(Integer.toString(i));
			hexList.add(Integer.toHexString(i));
		}
		Object[] arr = list.toArray();
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(hexList.toArray()));
		
	}

}
