package si.android.toy.model;

import java.io.Serializable;

import si.android.toy.util.StringUtil;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String street;
	private String zipCode;
	private String city;
	private Country country;
	
	public Address(String street, String zipCode, String city, Country country) {
		super();

		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}

	public Address() {
		super();
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return StringUtil.parseStreet(street) + "," + city + "," + country.getDisplayName() + "," + zipCode;
	}

	public boolean isValid() {
		if(StringUtil.isNullOrEmpty(street) || StringUtil.isNullOrEmpty(zipCode) ||
			StringUtil.isNullOrEmpty(city) /*|| country.isValid()*/) {
			return true;
		} else {
			return false;
		}
	}
}
