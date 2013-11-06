package si.android.toy.view;

import si.android.toy.view.validation.spinner.OptionalSelectionSpinnerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class OptionalSelectionSpinner<T> extends Spinner {
	private Context context;
	private T emptyItem;
	
	public OptionalSelectionSpinner(Context context, AttributeSet attrs, int defStyle, T emptyItem) {
		super(context, attrs, defStyle);
		
		this.context = context;
		this.emptyItem = emptyItem;
	}
	
	public OptionalSelectionSpinner(Context context, AttributeSet attrs, T emptyItem) {
		super(context, attrs);
		
		this.context = context;
		this.emptyItem = emptyItem;
	}
	
	public OptionalSelectionSpinner(Context context, T emptyItem) {
		super(context);
		
		this.context = context;
		this.emptyItem = emptyItem;
	}

	
	@Override
	public void setAdapter(SpinnerAdapter adapter) {
		if(adapter instanceof OptionalSelectionSpinnerAdapter) {
			super.setAdapter(adapter);
		} else {
			super.setAdapter(new OptionalSelectionSpinnerAdapter<T>(this.context, adapter, this.emptyItem));
		}
	}

	@Override
	public T getSelectedItem() {
		return (T) super.getSelectedItem();
	}
}
