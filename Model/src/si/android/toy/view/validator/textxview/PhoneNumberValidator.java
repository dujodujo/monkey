package si.android.toy.view.validator.textxview;

import android.content.Context;
import si.android.toy.view.validation.ViewValidator;

public class PhoneNumberValidator extends RegexValidator implements ViewValidator {
	// TODO
	private final static String REGEX = "";
	
	public PhoneNumberValidator(Context context) {
		super(context, REGEX);
	}
}
