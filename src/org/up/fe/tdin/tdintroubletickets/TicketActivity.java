package org.up.fe.tdin.tdintroubletickets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class TicketActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);
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
		//TODO: reply/solve ticket
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

	
	
}
