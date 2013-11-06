package si.android.toy.activity;

import si.android.toy.ApplicationState;
import si.android.toy.R;
import si.android.toy.adapter.UserDataAdapter;
import si.android.toy.model.UserData;
import si.android.toy.view.adapter.UserDataViewAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UserDataFormActivity extends AddressFromActivity {
	protected ApplicationState appState;
	protected UserDataViewAdapter userDataViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		appState = (ApplicationState)getApplication();
		initializeFormValues();
		userDataViewAdapter = new UserDataViewAdapter(rootView);
		initializeButtons();
	}
	
	@Override
	protected int getLayoutResourceID() {
		return R.layout.userinfo_form_merge;
	}

	protected void initializeFormValues() {
		UserDataViewAdapter.fromUserData(rootView, UserDataAdapter.retrieve(appState));
	}

	private void initializeButtons() {
		Button saveButton = (Button)findViewById(R.id.userInfo_saveButton);
		Button resetButton = (Button)findViewById(R.id.userInfo_resetButton);
		
		saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveForm();
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				resetForm();
			}
		});
	}
	
	
	protected synchronized void saveForm() {
		userDataViewAdapter.validateAllViews();
		if(!userDataViewAdapter.areAllViewsValid()) {
			Toast.makeText(this, "Not valid cannot save", Toast.LENGTH_SHORT).show();
		} else {
			saveUserData();
			Toast.makeText(this, R.string.userInfo_formSaved, Toast.LENGTH_SHORT).show();
		}
	}

	protected synchronized void resetForm() {
		userDataViewAdapter.resetAllViewValues();
		//resetUserData();
	}
	
	private void saveUserData() {
		UserData userData = UserDataViewAdapter.fromView(rootView);
		UserDataAdapter.persist(appState, userData);
	}

	private void resetUserData() {
		saveUserData();
	}
}
