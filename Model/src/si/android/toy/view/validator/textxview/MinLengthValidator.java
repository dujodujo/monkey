package si.android.toy.view.validator.textxview;

import si.android.toy.view.validation.ViewValidator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class MinLengthValidator implements ViewValidator {
	protected Context context;
	protected int minLength;
	
	public MinLengthValidator(Context context, int minLength) {
		super();
		
		this.context = context;
		this.minLength = minLength;
	}

	public boolean validate(View view) {
		return ((TextView)view).getText().length() >= this.minLength; 
	}
}
