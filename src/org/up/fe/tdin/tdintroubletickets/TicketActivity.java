package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.model.*;
import org.up.fe.tdin.tdintroubletickets.helper.ComService;
import org.up.fe.tdin.tdintroubletickets.helper.JSONHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.util.Log;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import org.json.JSONObject;

public class TicketActivity extends Activity implements OnItemSelectedListener {
	TDINTroubleTickets tdin;
	int ticket_pos;
	int spinner_pos;
	Ticket ticket;
	ProgressDialog dialog;

	// Array of choices
	String depts[] = {"Dept. 1", "Dept. 2", "Dept. 3"};
	String depts_uuid[] = {"536c1c57-5ff4-4ac6-b9d2-59b2e1c5656e", "123", "123"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tdin = (TDINTroubleTickets) getApplication();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String ticket_type = extras.getString("position");
			ticket_pos = Integer.parseInt(ticket_type);
			ticket = User.userTickets.get(ticket_pos);
		} else {
			// panic
		}

		setContentView(R.layout.activity_ticket);
		setTicketInfo();

	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.reply:
				show_reply();
				return true;
			case R.id.seconday_ticket:
				show_createSecondaryTicket();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * show secondary ticket button click handler
	 */
	public void show_createSecondaryTicket() {
		hideReplies();
		LinearLayout secondary_ticket_layout = (LinearLayout)findViewById(R.id.create_secondary_layout);
		secondary_ticket_layout.setVisibility(LinearLayout.VISIBLE);
	}
	
	/**
	 * create secondary ticket button click handler
	 */
	public void createSecondaryTicket(View v) {
		//TODO: create secondary ticket
		EditText msg = (EditText) findViewById(R.id.secondary_msg);
		EditText title = (EditText) findViewById(R.id.secondary_title);
		new ComService(
			"/secondaryTickets/addFromApp/" + 
				depts_uuid[spinner_pos] + "/" + 
				ticket.uuid + "/" + 
				title.getText().toString()  + "/" + 
				msg.getText().toString(), // route
			TicketActivity.this, // this context
			"replyDone", // callback
			false // show progress bar
			);
		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(R.string.fetching_data));
		dialog.setCancelable(false);
		dialog.show();
	}
	
	/**
	 * reply button click handler
	 * to show reply fields
	 */
	public void show_reply() {
		hideReplies();
		LinearLayout reply_layout = (LinearLayout)findViewById(R.id.reply_layout);
		reply_layout.setVisibility(LinearLayout.VISIBLE);
	}
	
	/**
	 * reply button click handler
	 * to show reply fields
	 */
	public void reply(View v) {
		// new progressbar
		EditText msg = (EditText) findViewById(R.id.reply_msg);
		new ComService(
			"/tickets/reply/" + ticket.uuid + "/" + msg.getText().toString(), 
			TicketActivity.this, // this context
			"replyDone", // callback
			false // show progress bar
			);
		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(R.string.fetching_data));
		dialog.setCancelable(false);
		dialog.show();
	}
	
	
	/**
	 * cancel button click handler
	 */
	public void cancelReply(View v) {
		LinearLayout secondary_ticket_layout = (LinearLayout)findViewById(R.id.create_secondary_layout);
		secondary_ticket_layout.setVisibility(LinearLayout.GONE);
		LinearLayout reply_layout = (LinearLayout)findViewById(R.id.reply_layout);
		reply_layout.setVisibility(LinearLayout.GONE);
	}
	
	/**
	 * cancel button click handler
	 */
	public void hideReplies() {
		LinearLayout secondary_ticket_layout = (LinearLayout)findViewById(R.id.create_secondary_layout);
		secondary_ticket_layout.setVisibility(LinearLayout.GONE);
		LinearLayout reply_layout = (LinearLayout)findViewById(R.id.reply_layout);
		reply_layout.setVisibility(LinearLayout.GONE);
	}

	public void setTicketInfo() {
		TextView view = (TextView) findViewById(R.id.title);
		view.setText(ticket.title);

		view = (TextView) findViewById(R.id.description);
		view.setText(ticket.description);
		
		view = (TextView) findViewById(R.id.name);
		view.setText(ticket.user_name);
		
		view = (TextView) findViewById(R.id.email);
		view.setText(ticket.user_email);
		
		view = (TextView) findViewById(R.id.date);
		view.setText(ticket.created_at);
		
		view = (TextView) findViewById(R.id.id);
		view.setText(ticket.uuid);

		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.departments);

		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depts);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		spinner.setOnItemSelectedListener(this);
		
	}

	/**
	 * Callback method for login communication.
	 */
	public void replyDone(String result) {
		// Prevents exceptions
		if(result == null) {
			result = "";
		}

		Log.d("got it ", result);

		JSONObject json = JSONHelper.string2JSON(result);
		String status = JSONHelper.getValue(json, "status");

		// If the accept was successfull
		if(status.equals("ok")) {
			//startHome();
			// GET [user][User][id];
			//String uuid = JSONHelper.getValue(json, "user", "Solver", "id");
			//String uuid = "[user][User][id]";
			//User.unassignedTickets.remove(ticket_pos);
			//Toast.makeText(this, "Accepted ticket", Toast.LENGTH_SHORT).show();
			//finish();
			fetchTickets();
		} else {
			// Show an error
			dialog.dismiss();
			Toast.makeText(this, "Reply failed", Toast.LENGTH_SHORT).show();
		}
	}

	public void startHome() {
		// Start the new activity.
		Toast.makeText(this, "Accepted ticket", Toast.LENGTH_SHORT).show();
		Intent home_intent = new Intent(TicketActivity.this, HomeActivity.class);
		home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(home_intent);
		finish(); // Finishes this activity.
	}

	public void fetchTickets() {
		// Comservice
		new ComService(
			"solvers/assigned/" + tdin.getUUID(), // route
			TicketActivity.this, // this context
			"fetchedTickets", // callback
			false // show progress bar
			);
		// fetchedTickets()
	}

	public void fetchedTickets(String result) {
		// If all is good
		// 1st - save the tickets to the db.
		Log.d("fetchedTickets():result", result);
		JSONObject json = JSONHelper.string2JSON(result);
		String status = JSONHelper.getValue(json, "status");
		// If the login was successfull
		if(status.equals("ok")) {
			
			// Parse the tickets
			// parseTickets();
			User.parseUserTickets(json);
			User.updateTicketsDB(this);

			// Fetch unassigned tickets
			fetchUnassignedTickets();
		} else {
			// Show an error
			dialog.dismiss();
			//Log.d("fetchedTickets()", "failed login");
			Toast.makeText(this, "Something failed", Toast.LENGTH_SHORT).show();
			findViewById(R.id.button_login).setEnabled(true);
			findViewById(R.id.button_register).setEnabled(true);
		}
	}

	public void fetchUnassignedTickets() {
		// Comservice
		new ComService(
			"tickets/unassigned", // route
			TicketActivity.this, // this context
			"fetchedUnassignedTickets", // callback
			false // show progress bar
			);
	}

	public void fetchedUnassignedTickets(String result) {
		// If all is good
		// 1st - save the tickets to the db.
		//Log.d("fetchedUnassignedTickets():result", result);
		JSONObject json = JSONHelper.string2JSON(result);
		String status = JSONHelper.getValue(json, "status");
		// If the login was successfull
		if(status.equals("ok")) {
			
			// Parse the tickets
			User.parseUnassignedTickets(json);
			User.updateTicketsDB(this);
			dialog.dismiss();
			startHome();
		} else {
			// Show an error
			dialog.dismiss();
			//Log.d("fetchedUnassignedTickets()", "failed login");
			Toast.makeText(this, "Something failed", Toast.LENGTH_SHORT).show();
			findViewById(R.id.button_login).setEnabled(true);
			findViewById(R.id.button_register).setEnabled(true);
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		// An item was selected. You can retrieve the selected item using
		spinner_pos = pos;
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}
}
