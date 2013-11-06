package si.android.toy.view;

import si.android.toy.view.validation.ViewValidator;

public interface ValidatingView {
	void setValidator(ViewValidator validator, String caption);
	boolean isValid();
}
