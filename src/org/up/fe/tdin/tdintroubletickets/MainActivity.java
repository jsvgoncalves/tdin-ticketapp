package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.model.TDINTroubleTickets;
import org.up.fe.tdin.tdintroubletickets.model.User;
import org.up.fe.tdin.tdintroubletickets.model.Ticket;
import org.up.fe.tdin.tdintroubletickets.helper.TicketsDataSource;
import org.up.fe.tdin.tdintroubletickets.helper.ComService;
import org.up.fe.tdin.tdintroubletickets.helper.JSONHelper;

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
import android.widget.EditText;

import org.json.JSONObject;

public class MainActivity extends Activity {

	TDINTroubleTickets tdin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tdin = (TDINTroubleTickets) getApplication();
		// DEBUG - add ticket
		// Ticket ticket = new Ticket(123, 1, "uuid", "created_at", "updated_at");
		// User.userTickets.add(ticket);
		// User.updateTicketsDB(this);

		// TicketsDataSource db = new TicketsDataSource(this);
		// db.clearTickets();

		if(!tdin.hasLoadedPrefs() || tdin.isLoggedOut()) {
			setContentView(R.layout.activity_main);
			// Restore user prefs to the form.
		} else {
			startHome();
		}
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
		// Disable the button
		v.setEnabled(false);
		// Disable the register button
		Button createAccbt = (Button) findViewById(R.id.button_register);
		createAccbt.setEnabled(false);
		String email = ((EditText) findViewById(R.id.form_email)).getText().toString();
		String pw = ((EditText) findViewById(R.id.form_pw)).getText().toString();

		if (!email.equals("") && !pw.equals("")) {
			tdin.setEmail(email);
			tdin.setPw(pw);
			//tdin.setPw(md5Helper.hashMD5(pw));
			doLogin();
		} else {
			v.setEnabled(true);
			createAccbt.setEnabled(true);
		}
	}

	/**
	 * Performs the login communication action.
	 */
	public void doLogin() {
		new ComService(
			"/", // route
			MainActivity.this, // this context
			"loginDone", // callback
			true // show progress bar
			);
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
		// Prevents exceptions
		if(result == null) {
			result = "";
		}

		Log.d("loginDone()", "Have I logged in?");
		Log.d("loginDone():result", result);

		JSONObject json = JSONHelper.string2JSON(result);
		String status = JSONHelper.getValue(json, "status");
		Log.d("loginDone():status", status);

		// If the login was successfull
		if(status.equals("ok")) {
			startHome();
		} else {
			// Show an error
			Log.d("loginDone()", "failed login");
			Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
			findViewById(R.id.button_login).setEnabled(true);
			findViewById(R.id.button_register).setEnabled(true);
		}
	}

	public void startHome() {
		// Start the new activity.
		Intent home_intent = new Intent(MainActivity.this, HomeActivity.class);
		startActivity(home_intent);
		finish(); // Finishes this activity.
	}
}
