package org.up.fe.tdin.tdintroubletickets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TicketActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);
	}	
	
	/**
	 * create secondary ticket button click handler
	 */
	public void createSecondaryTicket(View v) {
		//TODO: create secondary ticket
	}
	
	/**
	 * reply button click handler
	 */
	public void reply(View v) {
		//reply to ticket
	}

}
