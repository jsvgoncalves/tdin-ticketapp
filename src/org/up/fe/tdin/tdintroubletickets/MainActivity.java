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
		// Try to login //Crashing here
		/*try {
			new ComService(
					"/", 
					MainActivity.this, 
					"loginDone",
					false
					); // Hide progress bar cuz we will set it manually
		} catch (Exception e) {
			Toast toast = Toast.makeText(MainActivity.this, getString(R.string.connectionexception), Toast.LENGTH_SHORT);
			toast.show();
		}*/
		addListeners();
	}

	/**
	 * Add listeners for button clicks (Login and register)
	 */
	private void addListeners() {
		//button used navigate to registration activity
		Button register_btn = (Button)findViewById(R.id.button_register);
		register_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent register_intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(register_intent);
			}
		});

		//button used to login and navigate to main activity
		Button login_btn = (Button)findViewById(R.id.button_login);
		login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Login on service
			}
		});

	}

	public void loginDone(String result) {
		Log.d("loginDone()", "I've logged in");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
