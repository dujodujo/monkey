package si.android.toy.adapter;

import android.util.Log;
import si.android.toy.model.Address;
import si.android.toy.model.Country;

public class AddressAdapter {
	public static Address fromLocationAddress(android.location.Address location) {
		Log.d("BLA BLA", location.getLongitude() + "!");
		String street = location.getThoroughfare();
		if(location.getFeatureName() != null) {
			street += " " + location.getFeatureName();
		}
	
		String zipCode = location.getPostalCode();
		String city = location.getLocality();
		String countryName = location.getCountryName();
		String isoCode = location.getCountryCode();
		
		Country country = new Country(countryName, isoCode);
		Address address = new Address(street, zipCode, city, country);
		
		return address;
	}
}
