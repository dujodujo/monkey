package si.android.toy.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
	
	public static String limitStringLength(String original, int maxLength, String appendChars) {
		if(original == null) {
			return "";
		}
		if(original.length() <= maxLength) {
			return original;
		}
		if(appendChars == null) {
			appendChars = "";
		}
		
		return original.substring(0, maxLength - 1 - appendChars.length()) + appendChars; 
	}
	
	public static String join(String[] items, String joinChars, boolean appendEmptyItems) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < items.length; i++) {
			boolean append = true;
			if(isNullOrEmpty(items[i]) && !appendEmptyItems) {
				append = false;
			}
			
			if(append) {
				builder.append(items[i]);
				builder.append(joinChars);
			}
		}
		return builder.toString();
	}
	
	public static String parseStreet(String original) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < original.length(); i++) {
			char aChar = original.charAt(i);
			if(aChar == ' ') {
				stringBuilder.append("+");
			} else {
				stringBuilder.append(aChar);
			}
		}
		return stringBuilder.toString();
	}
	
	//Converts first character of given String to lower case
	public static String toFirstLowerCase(String original) {
		if(isNullOrEmpty(original)) {
			return original;
		} else if(original.length() == 1) {
			return original.toLowerCase();
		} else {
			return original.substring(0, 1).toLowerCase() +
				original.substring(1);
		}
	}
	
	// Converts the first character of a given String to Upper case.
	public static String toFirstUpperCase(String original) {
		if(isNullOrEmpty(original)) {
			return original;
		} else if(original.length() == 1) {
			return original.toUpperCase();
		} else {
			return original.substring(0, 1).toUpperCase() +
				original.substring(1);
		}
	}
	
	// Is String null or empty.
	public static boolean isNullOrEmpty(String original) {
		return original == null || original.equals("");
	}
}
