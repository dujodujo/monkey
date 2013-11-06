package si.android.tapping.adapters;

import java.util.ArrayList;
import java.util.List;

import si.android.tapping.R;
import si.android.tapping.observer.Counter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CounterAdapter extends ArrayAdapter<Counter> {
	private LayoutInflater inflater;
	
	public CounterAdapter(Context context, ArrayList<Counter> items) {
		super(context, R.layout.tap_list_renderer, items);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		Counter counter = getItem(position);
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.tap_list_renderer, parent, false);
			holder = new Holder();
			holder.label = (TextView) convertView.findViewById(R.id.label);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		int color = ((position % 2) == 0 ? 0xFFF0FFE1 : 0xFFFFFFFF );
		convertView.setBackgroundColor(color);
		holder.label.setText(counter.getLabel());
		holder.count.setText(Integer.toString(counter.getCounter()));
		
		return convertView;
	}

	public class Holder {
		public TextView label;
		public TextView count;
	}
}
