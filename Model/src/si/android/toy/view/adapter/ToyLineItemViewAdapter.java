package si.android.toy.view.adapter;

import java.util.List;

import si.android.toy.model.Toy;
import si.android.toy.model.ToyLineItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ToyLineItemViewAdapter extends ToyViewAdapter {
	private final List<ToyLineItem> toyLineItems;
	
	public ToyLineItemViewAdapter(Context context, List<ToyLineItem> toyLineItems) {
		super(context, toyLineItems);
		
		this.toyLineItems = toyLineItems;
	}

	@Override
	public ToyLineItem getItem(int position) {
		return toyLineItems.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}

	@Override
	protected void customizeView(int position, ViewHolder holder) {
		ToyLineItem toy = toyLineItems.get(position);
		customizeView(toy, holder);
	}

	@Override
	protected <T extends Toy> void customizeView(Toy toy, ViewHolder holder) {
		super.customizeView(toy, holder);
		customizeQuantityTextView(holder.getQuantityView(), 
			String.valueOf(((ToyLineItem) toy).getQuantity()));
	}

	@Override
	protected void customizeQuantityTextView(TextView quantityView, String text) {
		quantityView.setText(text);
	}

	@Override
	protected void customizeRemoveImage(ImageView removeImage) {
		super.customizeRemoveImage(removeImage);
	}
}