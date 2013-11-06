package si.android.toy.model;

import si.android.toy.util.StringUtil;

public class UserData {
	private String name;
	private String phone;
	private String email;
	private Address address;
	
	public UserData(String name, String phone, String email, Address address) {
		super();

		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}
	
	public UserData() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return name + " " + phone + " " + email + " " +
			address.toString();
	}
	
	public boolean isValid() {
		if(StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(phone) ||
		   StringUtil.isNullOrEmpty(email) || address == null || !address.isValid()) {
			return true;
		} else {
			return false;
		}
	}
}
