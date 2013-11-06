package si.android.toy.view.validator.textxview;

import android.content.Context;
import si.android.toy.view.validation.ViewValidator;

public class ZipCodeValidator extends RegexValidator implements ViewValidator {
	// TODO
	private final static String REGEX = "";
	
	public ZipCodeValidator(Context context) {
		super(context, REGEX);
	}
}
