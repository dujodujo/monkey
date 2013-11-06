package si.android.toy.view.adapter;

import si.android.toy.R;
import si.android.toy.model.Address;
import si.android.toy.model.EmptyCountry;
import si.android.toy.util.WidgetUtils;
import android.util.Log;
import android.view.View;

public class AddressViewAdapter {
	public static Address fromView(View addressView) {
		String street = WidgetUtils.getEditTextText(addressView, R.id.address_street);
		String zipCode = WidgetUtils.getEditTextText(addressView, R.id.address_zipCode);
		String city = WidgetUtils.getEditTextText(addressView, R.id.address_city);
		
		return new Address(street, zipCode, city, new EmptyCountry());
	}
	
	public static void fromAddress(View addressView, Address address) {
		WidgetUtils.getEditText(addressView, R.id.address_street).setText(address.getStreet());
		WidgetUtils.getEditText(addressView, R.id.address_zipCode).setText(address.getZipCode());
		WidgetUtils.getEditText(addressView, R.id.address_city).setText(address.getCity());
		
		//Spinner spinner = WidgetUtils.getSpinner(addressView, R.id.address_country);
	}
	
	public static String getLocationName(Address address) {
		return address.toString();
	}
}
