package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.tdintroubletickets.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;

public class AcceptTicketActivity extends Activity {
	TDINTroubleTickets tdin;
	int ticket_pos;
	Ticket ticket;

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
