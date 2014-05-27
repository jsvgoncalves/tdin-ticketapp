package org.up.fe.tdin.tdintroubletickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.up.fe.tdin.tdintroubletickets.model.TDINTroubleTickets;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class HomeActivity extends Activity {

	TDINTroubleTickets tdin;
	ArrayList<Map<String,String>> ticketsArray;
	private SimpleAdapter simpleAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		tdin = (TDINTroubleTickets) getApplication();
		setContentView(R.layout.activity_home);
		super.onCreate(savedInstanceState);

		initList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initList() {
		ticketsArray = new ArrayList<Map<String,String>>();
		HashMap<String, String> ticketItem = new HashMap<String, String>();
		ticketItem.put("first-line", "No tickets");
		ticketItem.put("second-line", "You have not been assigned any trouble tickets.");
		ticketsArray.add(ticketItem);

		ListView lv = (ListView) findViewById(R.id.history_list_view);
		simpleAdpt = new SimpleAdapter(this, ticketsArray, android.R.layout.simple_list_item_2,
				new String[] {"first-line", "second-line"}, new int[] {android.R.id.text1,android.R.id.text2});

		lv.setAdapter(simpleAdpt);

		// Create a message handling object as an anonymous class.
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				// Do something in response to the click
				// Log.e("mylog", "clicked a ticket " + position);
			}
		};

		lv.setOnItemClickListener(mMessageClickedHandler);
	}

}
