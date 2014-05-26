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
		// Log.d("loginClick()", "...");
		new ComService(
			"/",
			MainActivity.this,
			"loginDone",
			true
			); // Hide progress bar cuz we will set it manually
	}

	/**
	 * Register button handler
	 */
	public void registerClick(View v){
		// Log.d("registerClick()", "...");
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
