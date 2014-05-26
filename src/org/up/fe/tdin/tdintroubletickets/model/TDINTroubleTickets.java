package org.up.fe.tdin.tdintroubletickets.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.up.fe.tdin.tdintroubletickets.R;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class TDINTroubleTickets extends Application {

	private static TDINTroubleTickets instance;
	private static boolean hasLogin = false;
	
	private boolean loadedPrefs = false;
	private String lastUpdate;

	@Override
	public void onCreate() {
		Log.v("TDINTroubleTickets:onCreate()", "...");
		super.onCreate();
		instance = this;

		this.loadedPrefs = checkSharedPrefs();
	}

	public static Context context() {
		return instance.getApplicationContext();
	}


	/**
	 * Loads the sharedPreferences and returns if it was successfull
	 * @return boolean
	 */
	private boolean checkSharedPrefs() {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

		// String userid = settings.getString("userid", "notset");
		// boolean loggedOut = settings.getBoolean("loggedOut", true);

		//try {
			// if( name.equals("notset") || email.equals("notset") || pw.equals("notset") || 
			// 	userid.equals("notset") || token.equals("notset") || date.equals("1999-12-12 00:00:00") ) {
			// 	throw new ParseException("Parse exception", 0);
			// }
			//Date sharedDate = new SimpleDateFormat(getString(R.string.time_format), Locale.ENGLISH).parse(date);

			//setName(name);
			//setLoggedOut(loggedOut);
			//this.lastUpdate = lastUpdate;
			return true;
		//} catch (ParseException e) {
		//	return false;
		//}
	}


	/**
	 * Saves the shared preferences
	 */
	public void saveSharedPrefs() { 
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
//		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();

		// editor.putString("userid", user_id);
		// editor.putString("name", name);
		// editor.putString("email", email);
		// editor.putString("realEmail", realEmail);
		// editor.putString("pw", pw);
		// editor.putString("token", token);
		// editor.putBoolean("loggedOut", loggedOut);
		// SimpleDateFormat dFormat = new SimpleDateFormat(getString(R.string.time_format), Locale.getDefault());
		// editor.putString("expirationDate", dFormat.format(expirationDate).toString());

		// Commit the edits!
		editor.commit();
	}

	public static void clearPreferences(Context context) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Editor defaultPrefsPut = settings.edit();
		defaultPrefsPut.clear();
		defaultPrefsPut.commit();
	}

	public void saveLastUpdate() {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
		SimpleDateFormat dFormat = new SimpleDateFormat(getString(R.string.time_format), Locale.getDefault());
		String lastUpdate = dFormat.format(new Date()).toString();
		editor.putString("lastUpdate", lastUpdate);
		editor.commit();
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}
}
