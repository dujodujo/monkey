package si.android.toy.view;

import si.android.toy.R;
import si.android.toy.view.validation.ViewValidator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ValidatingSpinner<T> extends OptionalSelectionSpinner<T> implements ValidatingView {
	private ViewValidator validator;
	private boolean errorIconEnabled;

	public ValidatingSpinner(Context context, AttributeSet attrs, int defStyle, T emptyItem) {
		super(context, attrs, defStyle, emptyItem);
	}

	public ValidatingSpinner(Context context, AttributeSet attrs, T emptyItem) {
		super(context, attrs, emptyItem);
	}

	public ValidatingSpinner(Context context, T emptyItem) {
		super(context, emptyItem);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(errorIconEnabled && !isValid()) {
			drawErrorIcon(canvas);
		}
	}
	
	private void drawErrorIcon(Canvas canvas) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.input_error);
		canvas.drawBitmap(bitmap, 0, 0, new Paint());
	}
	
	public void setValidator(ViewValidator validator, String caption) {
		this.validator = validator;
	}
	
	public boolean isValid() {
		if(validator == null)
			return true;
		return validator.validate(this);
	}
	
	@Override
	public void setSelection(int position, boolean animate) {
		super.setSelection(position, animate);
		this.errorIconEnabled = true;
	}
	
	@Override
	public void setSelection(int position) {
		super.setSelection(position);
		this.errorIconEnabled = true;
	}
}
