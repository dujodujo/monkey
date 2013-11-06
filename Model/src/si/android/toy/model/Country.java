package si.android.toy.model;

import si.android.toy.util.StringUtil;

public class Country {
	private String displayName;
	private String iso;
	
	public Country(String displayName, String isoCode) {
		this.displayName = displayName;
		setISOCode(iso);
	}
	
	public Country() {}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getISOCode(String isoCode) {
		return this.iso;
	}
	
	public void setISOCode(String iso) {
		String normalized = iso;
		if(normalized == null) {
			normalized = "";
		}
		normalized = normalized.toLowerCase();
		this.iso = normalized;
	}

	@Override
	public String toString() {
		return displayName + " " + iso;
	}
	
	public boolean isValid() {
		return !StringUtil.isNullOrEmpty(displayName) &&
			!StringUtil.isNullOrEmpty(iso);
	}

}
