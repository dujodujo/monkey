package si.android.toy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import si.android.toy.R;
import si.android.toy.model.Category;

public class ToyData {
	
	private static List<Category> allCategories;
	
	private static String[] categories = {"Dragonman", "Samurai", "Ratman",
									      "Mutant", "Crabman", "Sandman",
									      "Skeletor", "Batman"};
	
	private static int[] icons = {R.drawable.dragonman, R.drawable.samurai, 
								  R.drawable.ratman, R.drawable.mutant,
								  R.drawable.crabman, R.drawable.sandman,
								  R.drawable.skeletor, R.drawable.batman};
	
	public static void initilaizeCategories() {
		allCategories = new ArrayList<Category>();
		int min = Math.min(icons.length, icons.length);
		for(int i = 0; i < min ; i++) {
			allCategories.add(new Category(categories[i], icons[i]));
		}
		Collections.sort(allCategories);
	}
	
	public static List<Category> getCategories() {
		return allCategories;
	}
}
