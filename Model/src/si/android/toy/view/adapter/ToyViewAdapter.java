package si.android.toy.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import si.android.toy.R;
import si.android.toy.model.Toy;
import si.android.toy.util.StringUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ToyViewAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	private final Context context;
	private final List<? extends Toy> toys;
	
	public ToyViewAdapter(Context context, List<? extends Toy> toys) {
		super();
		
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		this.toys = toys;
	}
	
	public int getCount() {
		return toys.size();
	}

	public Toy getItem(int position) {
		return toys.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.toys_table_layout, null);
			holder = getViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		customizeView(position, holder);
		return convertView;
	}
	
	private ViewHolder getViewHolder(View view) {
		TextView quantityView = (TextView) view.findViewById(R.id.toys_quantity);
		TextView nameView = (TextView) view.findViewById(R.id.toys_name);
		TextView priceView = (TextView) view.findViewById(R.id.toys_price);
		ImageView removeImage = (ImageView) view.findViewById(R.id.toys_removeButton);
		
		return new ViewHolder(quantityView, nameView, priceView, removeImage);
	}
	
	protected void customizeView(int position, ViewHolder holder) {
		Toy toy = toys.get(position);
		customizeView(toy, holder);
	}
	
	protected <T extends Toy> void customizeView(Toy toy, ViewHolder holder) {
		customizeQuantityTextView(holder.getQuantityView(), "");
		
		holder.getNameView().setText(toy.getName());
		holder.getPriceView().setText(new DecimalFormat("0.00").format(toy.getPrice()));
		
		customizeRemoveImage(holder.getRemoveImage());
	}
	
	protected void customizeQuantityTextView(TextView quantityView, String text) {
		quantityView.setText(text);
		quantityView.setWidth(0);
	}
	
	protected void customizeRemoveImage(ImageView removeImage) {
		removeImage.setMaxWidth(0);
		removeImage.setVisibility(View.VISIBLE);
	}
	
	class ViewHolder {
		private TextView quantityView;
		private TextView nameView;
		private TextView priceView;
		private ImageView removeImage;
		
		public ViewHolder(TextView quantityView, TextView nameView, TextView priceView, ImageView removeImage) {
			super();
			
			this.quantityView = quantityView;
			this.nameView = nameView;
			this.priceView = priceView;
			this.removeImage = removeImage;
		}

		public TextView getQuantityView() {
			return quantityView;
		}

		public TextView getNameView() {
			return nameView;
		}

		public TextView getPriceView() {
			return priceView;
		}

		public ImageView getRemoveImage() {
			return removeImage;
		}
	}
}