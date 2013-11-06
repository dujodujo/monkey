package si.android.toy.util;

import si.android.toy.model.UserData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPrefsAdapter {
	private static final String KEY = "UserData";
	
	public static void persist(Context context, UserData userData) {
		//TODO
		String value = userData.toString();
		SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		
		editor.putString(KEY, value);
		editor.commit();
	}
	
	public static UserData retrieve(Context context) {
		SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(context);
		String value = prefs.getString(KEY, null);
		
		if(value == null) {
			return new UserData();
		}
		return new UserData();
	}
}
