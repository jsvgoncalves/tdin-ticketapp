package org.up.fe.tdin.tdintroubletickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.up.fe.tdin.tdintroubletickets.model.TDINTroubleTickets;
import org.up.fe.tdin.tdintroubletickets.model.Ticket;
import org.up.fe.tdin.tdintroubletickets.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class UnAssignedTicketsFragment extends android.support.v4.app.Fragment{
	TDINTroubleTickets tdin;
	SimpleAdapter simpleAdpt;
	ArrayList<Map<String,String>> ticketsArray;
	View rootView = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_home, container, false);
		tdin = (TDINTroubleTickets) getActivity().getApplication();
		initList();
		return rootView;
	}

	private void initList() {
		ticketsArray = new ArrayList<Map<String,String>>();

		// If there is no history just show a message
		if (User.unassignedTickets.isEmpty()) {
			HashMap<String, String> ticketItem = new HashMap<String, String>();
			ticketItem.put("first-line", "No tickets");
			ticketItem.put("second-line", "You have not been assigned any trouble tickets.");
			ticketsArray.add(ticketItem);
		} else { // Else lets add ticketItems
			// Each Map is one List entry with 2 lines
			for (Ticket ticket : User.unassignedTickets) {
				HashMap<String, String> ticketItem = new HashMap<String, String>();
				ticketItem.put("first-line", ticket.title);
				ticketItem.put("second-line", ticket.description);
				ticketItem.put("id", ticket.id + "");
				ticketsArray.add(ticketItem);
			}
		}

		ListView lv = (ListView) rootView.findViewById(R.id.history_list_view);
		simpleAdpt = new SimpleAdapter(getActivity(), ticketsArray, android.R.layout.simple_list_item_2,
				new String[] {"first-line", "second-line"}, new int[] {android.R.id.text1,android.R.id.text2});

		lv.setAdapter(simpleAdpt);

		// Create a message handling object as an anonymous class.
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Log.d("initList()", "clicked a ticket " + position);
				Intent intent = new Intent(getActivity(), AcceptTicketActivity.class);
				intent.putExtra("position", position + "");
				startActivity(intent);
			}
		};

		lv.setOnItemClickListener(mMessageClickedHandler);
	}

	@Override
	public void onResume() {
		super.onResume();
		initList();
	}
}
