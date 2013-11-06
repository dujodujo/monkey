package si.android.toy.view.adapter;

import java.util.ArrayList;
import java.util.List;

import si.android.toy.R;
import si.android.toy.model.Address;
import si.android.toy.model.UserData;
import si.android.toy.util.WidgetUtils;
import si.android.toy.view.ValidatingView;
import si.android.toy.view.validation.ViewValidator;
import si.android.toy.view.validation.spinner.OptionalSelectionSpinnerValidator;
import si.android.toy.view.validator.textxview.EmailValidator;
import si.android.toy.view.validator.textxview.MinLengthValidator;
import si.android.toy.view.validator.textxview.PhoneNumberValidator;
import si.android.toy.view.validator.textxview.ZipCodeValidator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class UserDataViewAdapter {
	private List<ValidatingView> validatingViews;
	private static final int MIN_LENGTH_OF_TEXT_VALUES = 0;
	
	public UserDataViewAdapter(View userDataView) {
		initializeValidators(userDataView);
	}
	
	public static UserData fromView(View userDataView) {
		String name = WidgetUtils.getEditTextText(userDataView, R.id.userInfo_name);
		String phone = WidgetUtils.getEditTextText(userDataView, R.id.userInfo_phone);
		String email = WidgetUtils.getEditTextText(userDataView, R.id.userInfo_email);
		Address address = AddressViewAdapter.fromView(userDataView);
		
		return new UserData(name, phone, email, address);
	}
	
	public static void fromUserData(View userDataView, UserData userData) {
		WidgetUtils.getEditText(userDataView, R.id.userInfo_name).setText(userData.getName());
		WidgetUtils.getEditText(userDataView, R.id.userInfo_phone).setText(userData.getPhone());
		WidgetUtils.getEditText(userDataView, R.id.userInfo_email).setText(userData.getEmail());
		
		AddressViewAdapter.fromAddress(userDataView, userData.getAddress());
	}
	
	public void validateAllViews() {}
	
	public void validateAddressViews() {}
	
	public boolean areAllViewsValid() {
		synchronized(validatingViews) {
			for(ValidatingView view : validatingViews) {
				if(!view.isValid()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void resetAllViewValues() {
		synchronized(validatingViews) {
			for(ValidatingView view : validatingViews) {
				((EditText)view).setText("");
			}
		}
	}
	
	private void initializeValidators(View parentView) {
		validatingViews = new ArrayList<ValidatingView>();
		Context context = parentView.getContext();
		
		synchronized (validatingViews) {
			assignValidatorToValidatingView(parentView, R.id.address_street,
				R.string.address_street, new MinLengthValidator(context, MIN_LENGTH_OF_TEXT_VALUES));
			assignValidatorToValidatingView(parentView, R.id.address_zipCode,
				R.string.address_zipCode, new ZipCodeValidator(context));
			assignValidatorToValidatingView(parentView, R.id.address_city,
				R.string.address_city, new MinLengthValidator(context, MIN_LENGTH_OF_TEXT_VALUES));
			//assignValidatorToValidatingView(parentView, R.id.address_country,
				//R.string.address_country, new OptionalSelectionSpinnerValidator());
			assignValidatorToValidatingView(parentView, R.id.userInfo_name, 
					R.string.userInfo_name, new MinLengthValidator(context, MIN_LENGTH_OF_TEXT_VALUES));
			assignValidatorToValidatingView(parentView, R.id.userInfo_phone, 
				R.string.userInfo_phone, new PhoneNumberValidator(context));
			assignValidatorToValidatingView(parentView, R.id.userInfo_email,
				R.string.userInfo_email, new EmailValidator(context));
		}
	}
	
	private void assignValidatorToValidatingView(View parentView,
			int validatingViewResID, int fieldDisplayNameResID, ViewValidator validator) {
		
		ValidatingView view = (ValidatingView)parentView.findViewById(validatingViewResID);
		String caption = parentView.getContext().getResources().getString(fieldDisplayNameResID);
		view.setValidator(validator, caption);
		validatingViews.add(view);
	}
}
