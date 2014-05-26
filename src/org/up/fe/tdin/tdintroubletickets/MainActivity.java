package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.helper.ComService;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Login button handler
	 */
	public void loginClick(View v){
		Log.d("loginClick()", "yey");
		try {
			new ComService(
					"/", 
					MainActivity.this, 
					"loginDone",
					false
					); // Hide progress bar cuz we will set it manually
		} catch (Exception e) {
			Toast toast = Toast.makeText(MainActivity.this, getString(R.string.connectionexception), Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * Register button handler
	 */
	public void registerClick(View v){
		Log.d("registerClick()", "yey");
		Intent register_intent = new Intent(MainActivity.this, RegisterActivity.class);
		startActivity(register_intent);
	}

	/**
	 * Callback method for login communication.
	 */
	public void loginDone(String result) {
		Log.d("loginDone()", "Have I logged in?");
	}

}
