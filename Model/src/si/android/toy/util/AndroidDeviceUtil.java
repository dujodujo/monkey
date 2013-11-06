package si.android.toy.util;

import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

public class AndroidDeviceUtil {
	public static boolean isGPSEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public static void showGPSOptions(Context context) {
		//context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}
	
	/*
	public static void showGPSDisabledAlert(final Context context) {
		DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showGPSOptions(context);
			}
		};
		
		AlertDialog alertDialog = WidgetUtils.createYesNoAlertDialog(
			context, R.drawable.house, R.string.androidUtil_gpsDisabledDialogTitle,
			context.getString(R.string.androidUtil_gpsDisabledDialogText),
			dialog);
		alertDialog.show();
	}
	*/
	
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager)
			context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getActiveNetworkInfo().isConnected();
	}
	
	public static String getPhoneNumber(Context context) {
		TelephonyManager manager = (TelephonyManager)
			context.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getLine1Number();
	}
}
