package org.up.fe.tdin.tdintroubletickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.up.fe.tdin.adapter.TabsPagerAdapter;
import org.up.fe.tdin.tdintroubletickets.model.TDINTroubleTickets;
import org.up.fe.tdin.tdintroubletickets.model.User;
import org.up.fe.tdin.tdintroubletickets.model.Ticket;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

@SuppressLint("NewApi")
public class HomeActivity  extends FragmentActivity {

	TDINTroubleTickets tdin;
	ArrayList<Map<String,String>> ticketsArray;
	private SimpleAdapter simpleAdpt;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		tdin = (TDINTroubleTickets) getApplication();
		super.onCreate(savedInstanceState);
		String[] tabs = { getString(R.string.tab_item_assigned), getString(R.string.tab_item_unassigned)};

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			setContentView(R.layout.activity_tabs);

			// Initilization
			viewPager = (ViewPager) findViewById(R.id.pager);
			actionBar = getActionBar();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

			viewPager.setAdapter(mAdapter);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        

			// Adding Tabs
			for (String tab_name : tabs) {
				actionBar.addTab(actionBar.newTab().setText(tab_name)
						.setTabListener((TabListener) this));
			}

		}else{
			setContentView(R.layout.activity_tabs_support);
			mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
			mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

			mTabHost.addTab(mTabHost.newTabSpec(tabs[0]).setIndicator(tabs[0]),
					AssignedTicketsFragment.class, null);
			mTabHost.addTab(mTabHost.newTabSpec(tabs[1]).setIndicator(tabs[1]),
					UnAssignedTicketsFragment.class, null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initList() {
		ticketsArray = new ArrayList<Map<String,String>>();

		// If there is no history just show a message
		if (User.userTickets.isEmpty()) {
			HashMap<String, String> ticketItem = new HashMap<String, String>();
			ticketItem.put("first-line", "No tickets");
			ticketItem.put("second-line", "You have not been assigned any trouble tickets.");
			ticketsArray.add(ticketItem);
		} else { // Else lets add ticketItems
			// Each Map is one List entry with 2 lines
			for (Ticket ticket : User.userTickets) {
				HashMap<String, String> ticketItem = new HashMap<String, String>();
				ticketItem.put("first-line", "T" + ticket.ticket_type);
				ticketItem.put("second-line", ticket.uuid);
				ticketItem.put("id", ticket.id + "");
				ticketsArray.add(ticketItem);
			}
		}

		ListView lv = (ListView) findViewById(R.id.history_list_view);
		simpleAdpt = new SimpleAdapter(this, ticketsArray, android.R.layout.simple_list_item_2,
				new String[] {"first-line", "second-line"}, new int[] {android.R.id.text1,android.R.id.text2});

		lv.setAdapter(simpleAdpt);

		// Create a message handling object as an anonymous class.
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Log.d("initList()", "clicked a ticket " + position);
				Intent intent = new Intent(HomeActivity.this, TicketActivity.class);
				intent.putExtra("position", position + "");
				startActivity(intent);
			}
		};

		lv.setOnItemClickListener(mMessageClickedHandler);
	}
}
