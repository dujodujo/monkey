package si.android.toy.adapter;

import java.io.IOException;

import si.android.toy.ApplicationState;
import si.android.toy.model.UserData;
import si.android.toy.util.SharedPrefsAdapter;

import android.content.Context;
import android.util.Log;

public class UserDataAdapter {
	private static final String TAG = "UserDataAdapter";
	
	public static UserData retrieve(ApplicationState appState) {
		return appState.getUserData();
	}
	
	public static UserData retrieveStart(Context context) {
		return fromSharedPrefs(context);
	}
	
	public static synchronized boolean persist(ApplicationState appState, UserData userData) {
		appState.setUserData(userData);
		return toSharedPrefs(appState, userData);
	}
	
	private static UserData fromSharedPrefs(Context context) {
		return SharedPrefsAdapter.retrieve(context);
	}
	
	private static boolean toSharedPrefs(Context context, UserData userData) {
		SharedPrefsAdapter.persist(context, userData);
		return true;
	}
}
