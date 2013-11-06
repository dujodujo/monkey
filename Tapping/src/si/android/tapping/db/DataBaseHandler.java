package si.android.tapping.db;

import si.android.tapping.TapApplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
	private static final String TAG = DataBaseHandler.class.getSimpleName();
	private static final String DATABASE_NAME = "TappingCounters";
	private static final int DATABASE_VERSION = 1;
	
	public DataBaseHandler() {
		super(TapApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String counters = "CREATE TABLE " + CounterDb.TABLE + "(" +
			CounterDb._ID + " integer primary key, " +
			CounterDb.LABEL + " text, " +
			CounterDb.COUNT + " int, " +
			CounterDb.LOCKED + " int)";
		db.execSQL(counters);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//pass
	}
}
