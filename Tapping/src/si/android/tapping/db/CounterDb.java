package si.android.tapping.db;

import java.util.ArrayList;

import si.android.tapping.observer.Counter;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CounterDb {
	public static final String TABLE = "Counters";
	public static String _ID = "_id";
	public static final String COUNT = "counter";
	public static final String LABEL = "label";
	public static final String LOCKED = "locked";
	
	public CounterDb() {}
	
	public ArrayList<Counter> getAllCounters() {
		ArrayList<Counter> list = new ArrayList<Counter>();
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
		while(cursor.moveToNext()) {
			Counter counter = new Counter();
			counter.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
			counter.setCounter(cursor.getInt(cursor.getColumnIndex(COUNT)));
			counter.setLabel(cursor.getString(cursor.getColumnIndex(LABEL)));
			counter.setLocked(cursor.getInt(cursor.getColumnIndex(LOCKED)) == 1);
			list.add(counter);
		}
		cursor.close();
		db.close();
		return list;
	}
	
	public Counter get(int id) {
		Counter counter = null;
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		Cursor cursor = db.query(TABLE, null, _ID +"=?", new String[] {Integer.toString(id)}, null, null, null);
		if(cursor.moveToFirst()) {
			counter = new Counter();
			counter.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
			counter.setCounter(cursor.getInt(cursor.getColumnIndex(COUNT)));
			counter.setLabel(cursor.getString(cursor.getColumnIndex(LABEL)));
			counter.setLocked(cursor.getInt(cursor.getColumnIndex(LOCKED)) == 1);
		}
		cursor.close();
		db.close();
		return counter;
	}
	
	public long insert(Counter counter) {
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		ContentValues values = new ContentValues();
		if(counter.getId() > 0) {
			values.put(_ID, counter.getId());
		}
		values.put(LABEL, counter.getLabel());
		values.put(COUNT, counter.getCounter());
		values.put(LOCKED, counter.isLocked());
		long num = db.insert(TABLE, null, values);
		db.close();
		return num;
	}
	
	public long update(Counter counter) {
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(_ID, counter.getId());
		values.put(COUNT, counter.getCounter());
		values.put(LABEL, counter.getLabel());
		values.put(LOCKED, counter.isLocked());
		long num = db.update(TABLE, values, _ID + "=?", new String[] {Integer.toString(counter.getId())});
		db.close();
		return num;
	}
	
	public void delete(Counter counter) {
		delete(counter.getId());
	}
	
	public void delete(int id) {
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		db.delete(TABLE, _ID + "=?", new String[] {Integer.toString(id)});
		db.close();
	}
	
	public void deleteAll() {
		SQLiteDatabase db = new DataBaseHandler().getWritableDatabase();
		db.delete(TABLE, null, null);
		db.close();
	}
}