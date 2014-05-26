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

	private String email;
	private String pw;
	
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

		String email = settings.getString("email", "notset");
		String pw = settings.getString("pw", "notset");
		// boolean loggedOut = settings.getBoolean("loggedOut", true);

		try {
			// Are the values not set?
			if( email.equals("notset") || pw.equals("notset")){
				throw new ParseException("Parse exception", 0);
			}
			// If all values are set, set them in the app context.
			setEmail(email);
			setPw(pw);
			return true;
		} catch (ParseException e) {
			return false;
		}
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

		editor.putString("email", email);
		editor.putString("pw", pw);

		// Commit the edits!
		editor.commit();
	}

	public static void clearPreferences(Context context) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		Editor defaultPrefsPut = settings.edit();
		defaultPrefsPut.clear();
		defaultPrefsPut.commit();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
