package si.android.toy.util;

import si.android.toy.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class WidgetUtils {
	
	public static AlertDialog createOkAlertDialog(Context context, int iconID, int titleID, String message) {
		OnClickListener okClickListener = new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		return createOkAlertDialog(context, iconID, titleID, message, okClickListener);
	}
	
	public static AlertDialog createOkAlertDialog(Context context, int iconID, int titleID, 
		String message, OnClickListener okClickListener) {
		
		final AlertDialog alertDialog = createOkAlertDialog(context, iconID, 
			titleID, message, R.string.androidUtil_ok, okClickListener);
		return alertDialog;
	}
	
	private static AlertDialog createOkAlertDialog(Context context, 
			int iconID, int titleID, String message, 
			int okButtonTextID, OnClickListener okClickListener) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
			.create();
		alertDialog.setTitle(titleID);
		alertDialog.setMessage(message);
		alertDialog.setIcon(iconID);
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
			context.getString(okButtonTextID), okClickListener);
		
		return alertDialog;
	}
	
	public static AlertDialog createYesNoAlertDialog(Context context,
			int iconID, int titleID, String message, OnClickListener okClickListener) {
		return createOkCancelOrYesNoAlertDialog(context,
			iconID, titleID, message, R.string.androidUtil_yes, R.string.androidUtil_no, okClickListener);
	}
	
	private static AlertDialog createOkCancelOrYesNoAlertDialog(Context context,
			int iconID, int titleID, String message, int okOrYesTextResID, int cancelOrNoResID,
			OnClickListener okClickListener) {
		final AlertDialog alertDialog = createOkAlertDialog(context, iconID, 
			titleID, message, okOrYesTextResID, okClickListener);
		
		OnClickListener cancelClickListener = new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
			}
		};
		
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(cancelOrNoResID), cancelClickListener);
		return alertDialog;
	}
	
	public static AlertDialog createOkCancelAlertDialog(Context context,
			int iconID, int titleID, String message, OnClickListener okClickListener) {
		return createOkCancelOrYesNoAlertDialog(context, iconID, titleID, message, 
			R.string.androidUtil_ok, R.string.androidUtil_cancel, okClickListener);
	}
	
	public static <T> boolean setSpinnerSelectedItem(Spinner spinner, T item) {
		int count = spinner.getCount();
		for(int i = 0; i < count; i++) {
			@SuppressWarnings("unchecked")
			T spinnerItem = (T) spinner.getItemAtPosition(i);
			if(spinnerItem.equals(item)) {
				spinner.setSelection(i);
				return true;
			}
		}
		return false;
	}
	
	public static String getEditTextView(View parentView, int id) {
		return getEditText(parentView, id).getText().toString();
	}
	
	public static String getEditTextText(View parentView, int id) {
		return getEditText(parentView, id).getText().toString();
	}
	
	public static EditText getEditText(View parentView, int id) {
		return (EditText)parentView.findViewById(id);
	}
	
	public static Spinner getSpinner(View parentView, int id) {
		return (Spinner)parentView.findViewById(id);
	}
}
