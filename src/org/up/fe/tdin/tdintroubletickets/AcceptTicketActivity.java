package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.model.*;
import org.up.fe.tdin.tdintroubletickets.helper.ComService;
import org.up.fe.tdin.tdintroubletickets.helper.JSONHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import org.json.JSONObject;

public class AcceptTicketActivity extends Activity {
	TDINTroubleTickets tdin;
	int ticket_pos;
	Ticket ticket;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tdin = (TDINTroubleTickets) getApplication();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String ticket_type = extras.getString("position");
			ticket_pos = Integer.parseInt(ticket_type);
			ticket = User.unassignedTickets.get(ticket_pos);
		} else {
			// panic
		}

		setContentView(R.layout.activity_accept_ticket);
		setTicketInfo();
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept_ticket_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.accept:
				accept_ticket();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * show secondary ticket button click handler
	 */
	public void accept_ticket() {
		// new progressbar
		new ComService(
			"/tickets/accept/" + ticket.uuid, // route
			AcceptTicketActivity.this, // this context
			"acceptDone", // callback
			false // show progress bar
			);
		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(R.string.fetching_data));
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * Callback method for login communication.
	 */
	public void acceptDone(String result) {
		// Prevents exceptions
		if(result == null) {
			result = "";
		}

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
			Toast.makeText(this, "Accept failed", Toast.LENGTH_SHORT).show();
		}
	}

	public void startHome() {
		// Start the new activity.
		Toast.makeText(this, "Accepted ticket", Toast.LENGTH_SHORT).show();
		Intent home_intent = new Intent(AcceptTicketActivity.this, HomeActivity.class);
		home_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(home_intent);
		finish(); // Finishes this activity.
	}

	public void fetchTickets() {
		// Comservice
		new ComService(
			"solvers/view/" + tdin.getUUID(), // route
			AcceptTicketActivity.this, // this context
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
			//User.parseTickets(json);
			//User.updateTicketsDB(this);

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
			AcceptTicketActivity.this, // this context
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
		
	}
	
}
