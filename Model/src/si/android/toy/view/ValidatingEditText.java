package si.android.toy.view;

import si.android.toy.view.validation.ViewValidator;
import si.android.toy.view.validator.textxview.EmailValidator;
import si.android.toy.view.validator.textxview.PhoneNumberValidator;
import si.android.toy.view.validator.textxview.ZipCodeValidator;
import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.EditText;

public class ValidatingEditText extends EditText implements ValidatingView {
	private ViewValidator validator;
	
	public ValidatingEditText(Context context) {
		super(context);
	}

	public ValidatingEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ValidatingEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setValidator(ViewValidator validator, String caption) {
		this.validator = validator;
		
		initInputType(validator);
	}

	public boolean isValid() {
		return validator.validate(this);
	}
	
	private void initInputType(ViewValidator validator) {
		if(this.validator instanceof EmailValidator) {
			setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		} else if(this.validator instanceof PhoneNumberValidator) {
			setInputType(InputType.TYPE_CLASS_PHONE);
		} else if(this.validator instanceof ZipCodeValidator) {
			setInputType(InputType.TYPE_CLASS_NUMBER);
		} else if(!isPassword()) {
			setInputType(InputType.TYPE_CLASS_TEXT);
		} else if(isPassword()) {
			setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		
		setInputType(this.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
	}
	
	private boolean isPassword() {
		TransformationMethod method = this.getTransformationMethod();
		return method != null && method instanceof PasswordTransformationMethod;
	}
}
