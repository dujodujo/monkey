package si.android.toy.view.validation.spinner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class OptionalSelectionSpinnerAdapter<T> extends BaseAdapter implements SpinnerAdapter {
	private Context context;
	private SpinnerAdapter adapter;
	private T emptyItem;
	
	public OptionalSelectionSpinnerAdapter(Context context, SpinnerAdapter adapter, T emptyItem) {
		super();
		
		this.context = context;
		this.adapter = adapter;
		this.emptyItem = emptyItem;
	}

	public int getCount() {
		return this.adapter.getCount() + 1;
	}

	public T getItem(int position) {
		if(position == 0) {
			return (T) emptyItem;
		} else {
			return (T) this.adapter.getItem(position - 1);
		}
	}
	
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View newView;
		if(position == 0) {
			newView = new TextView(context);
		} else {
			newView = adapter.getView(position - 1, null, parent);
		}
		return newView;
	}
	
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View newView;
		if(position == 0) {
			newView = new TextView(parent.getContext());
		} else {
			newView = adapter.getDropDownView(position - 1, null, parent);
		}
		return newView;
	}
}