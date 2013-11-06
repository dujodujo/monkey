package si.android.toy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtil {
	
	public static <T>List<T> getRandomSubSelection(List<T> sourceList,
			int itemsToSelect, Random random) {
		int sourceSize = sourceList.size();
		
		if(sourceSize == 0 || itemsToSelect <= 0 || sourceSize < itemsToSelect) {
			throw new IllegalArgumentException();
		}
		
		// Generate an array representing the element to select from 0...
		// to number of available elements after the previous 
		// elements have been selected.
		int selections[] = new int[itemsToSelect];
		ArrayList<T> resultArray = new ArrayList<T>();
		for(int count = 0; count < itemsToSelect; count++) {
			int selection = random.nextInt(sourceSize - count);
			selections[count] = selection;
			
			for(int scanIndex = count - 1; scanIndex >=0; scanIndex--) {
				if(selection >= selections[scanIndex]) {
					selection++;
				}
			}
			resultArray.add(sourceList.get(selection));
		}
		return resultArray;
	}
}
