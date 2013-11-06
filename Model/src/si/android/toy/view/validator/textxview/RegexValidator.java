package si.android.toy.view.validator.textxview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import si.android.toy.view.validation.ViewValidator;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class RegexValidator implements ViewValidator {
	protected Context context;
	protected Pattern pattern;
	
	public RegexValidator(Context context, String regex) {
		super();
		this.context = context;
		this.pattern = Pattern.compile(regex);
	}

	public boolean validate(View view) {
		Matcher matcher = pattern.matcher(((TextView) view).getText().toString());
		return matcher.matches();
	}
}
