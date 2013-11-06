package si.android.toy.view.validation.spinner;

import si.android.toy.view.validation.ViewValidator;
import android.view.View;
import android.widget.Spinner;

public class OptionalSelectionSpinnerValidator implements ViewValidator {

	public boolean validate(View view) {
		Spinner spinner = (Spinner)view;
		return spinner.getSelectedItemPosition() > 0;
	}
}
