package si.android.toy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class CountriesList extends ArrayList<Country> {
	private static final long serialVersionUID = 1L;

	public CountriesList() {
		super();
		initialize();
	}
	
	private void initialize() {
		for(Locale local : Locale.getAvailableLocales()) {
			String name = local.getDisplayCountry();
			String iso = local.getCountry();
			
			Country country = new Country(name, iso);
			
			if(!iso.equals("") && !contains(country)) {
				add(country);
			}
		}
		Collections.sort(this, new CountryComparator());
	}
	
	private class CountryComparator implements Comparator<Country> {
		public int compare(Country lhs, Country rhs) {
			return lhs.getDisplayName().compareTo(rhs.getDisplayName());
		}
	}
}
