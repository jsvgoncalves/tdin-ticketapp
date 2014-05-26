package org.up.fe.tdin.tdintroubletickets;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends Activity{

	TextView name_txt;
	TextView email_txt;
	TextView password_txt;
	TextView password_repeat_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initFieldVars();
	}

	/**
	 * Add Listeners to buttons in view
	 */
	private void initFieldVars() {
		//Textviews that contain all the data
		name_txt = (TextView)findViewById(R.id.form_name);
		email_txt = (TextView) findViewById(R.id.form_email);
		password_txt = (TextView) findViewById(R.id.form_pw);
		password_repeat_txt = (TextView) findViewById(R.id.form_pw2);
	}

	/**
	 * Cancel button click handler
	 */
	public void cancelClick(View v) {
		finish();
	}

	/**
	 * Register button click handler
	 */
	public void registerClick(View v) {
		//TODO: Register account on server
		if( name_txt.getText().toString().length() == 0 ){
			showError( getString(R.string.form_error_no_user) ); //empty user name
		} else if( !isValidEmail(email_txt.getText().toString()) ){
			showError( getString(R.string.form_error_invalid_email) ); //invalid email
		} else if( !password_txt.getText().toString().equals(password_repeat_txt.getText().toString()) ){
			showError( getString(R.string.form_error_diff_pass) ); //show different passwords error
		} 
	}

	/*
	 * @source: http://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address-on-android
	 */
	private boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	/**
	 * Show error message for 3 seconds fadding in and out
	 * @param error_msg -> the error message to be displayed
	 */
	private void showError(final String error_msg){
		final LinearLayout error_layout = (LinearLayout) findViewById(R.id.error_lay);
		final TextView error_text = (TextView) findViewById(R.id.register_error);
		error_text.setText(error_msg);
		AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
		anim.setDuration(3000);
		anim.setRepeatMode(Animation.REVERSE);
		error_layout.startAnimation(anim);
	}

}
