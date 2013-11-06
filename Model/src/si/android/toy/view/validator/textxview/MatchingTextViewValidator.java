package si.android.toy.view.validator.textxview;

import si.android.toy.view.validation.ViewValidator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class MatchingTextViewValidator implements ViewValidator {
	protected Context context;
	protected TextView otherView;
	protected String otherViewCaption;
	
	public MatchingTextViewValidator(Context context, TextView otherView, String otherViewCaption) {
		super();
		
		this.context = context;
		this.otherView = otherView;
		this.otherViewCaption = otherViewCaption;
	}

	public boolean validate(View view) {
		boolean lengthOK = (((TextView)view).getText().length() > 0);
		boolean textEquals = ((TextView)view).getText().toString().equals(this.otherView.getText().toString());
		return (lengthOK && textEquals);
	}
}
