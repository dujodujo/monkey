package si.enginestarter;

import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
	private static UserData instance;
	private static final String PREFERENCES_NAME = "GAME_USERDATA";
	private static final String UNLOCKED_LEVEL_KEY = "UNLOCKED_LEVELS";
	
	private SharedPreferences mSettings;
	private SharedPreferences.Editor mEditor;
	
	private int mUnlockedLevels;
	
	UserData() {}
	
	public synchronized static UserData getInstance() {
		if(instance == null) {
			instance = new UserData();
		}
		return instance;
	}
	
	public synchronized void init(Context context) {
		if(mSettings == null) {
			mSettings = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
			mEditor = mSettings.edit();
			mUnlockedLevels = mSettings.getInt(UNLOCKED_LEVEL_KEY, 1);
		}
	}
	
	public synchronized int getMaxUnlockedLevel() {
		return this.mUnlockedLevels;
	}
	
	public synchronized void unlockNextLevel() {
		this.mUnlockedLevels++;
		mEditor.putInt(UNLOCKED_LEVEL_KEY, this.mUnlockedLevels);
		mEditor.commit();
	}
}
