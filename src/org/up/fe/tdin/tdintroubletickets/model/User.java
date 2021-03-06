package org.up.fe.tdin.tdintroubletickets.model;

import java.util.ArrayList;
import java.util.Date;

import org.up.fe.tdin.tdintroubletickets.helper.JSONHelper;
import org.up.fe.tdin.tdintroubletickets.helper.TicketsDataSource;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * Contains any values that are common
 * to multiple activities such as user
 * preferences stored locally or user
 * data downloaded on startup.
 * @author joao
 *
 */
public class User {
	
	// User preferences
	/**
	 * User's real name
	 */
	private static String name;
	/**
	 * Email is used as login name
	 */
	private static String email;
	/**
	 * Encrypted password
	 */
	private static String password;
	
	/**
	 * Database user ID
	 */
	private static int userID = -1;
	
	public static ArrayList<Ticket> unassignedTickets = new ArrayList<Ticket>();
	public static ArrayList<Ticket> userTickets = new ArrayList<Ticket>();
	
	public static String getName() {
		return name;
	}


	public static void setName(String name) {
		User.name = name;
	}


	public static String getEmail() {
		return email;
	}


	public static void setEmail(String email) {
		User.email = email;
	}


	public static String getPassword() {
		return password;
	}


	public static void setPassword(String password) {
		User.password = password;
	}

	public static int getID() {
		return userID;
	}
	
	public static void setID(int id) {
		User.userID = id;
	}


	public static void resetTickets() {
		unassignedTickets.clear();
		userTickets.clear();
	}
	
	public static void parseUserTickets(JSONObject json) {
		ArrayList<String> userT = JSONHelper.getArray(json, "solver", "Ticket");
		parseUserTicketArray(userT, userTickets);
	}

	private static void parseUserTicketArray(ArrayList<String> tickets_str, ArrayList<Ticket> tickets) {
		JSONObject json;
		tickets.clear();
		for (String ticketStr : tickets_str) {
			json = JSONHelper.string2JSON(ticketStr);
			try {
				Ticket t = new Ticket(0,
						0,
						JSONHelper.getValue(json, "id"),
						JSONHelper.getValue(json, "created"),
						JSONHelper.getValue(json, "modified"),
						JSONHelper.getValue(json, "title"),
						JSONHelper.getValue(json, "description"),
						JSONHelper.getValue(json, "User", "name"),
						JSONHelper.getValue(json, "User", "email")
						);
				tickets.add(t);
			} catch (Exception e) {
				System.err.println(e.toString());
				System.err.println("Invalid JSON while retrieving tickets!");
			}
		}
		
	}

	public static void parseUnassignedTickets(JSONObject json) {
		ArrayList<String> userT = JSONHelper.getArray(json, "tickets");
		parseUnassignedTicketArray(userT, unassignedTickets);
	}

	private static void parseUnassignedTicketArray(ArrayList<String> tickets_str, ArrayList<Ticket> tickets) {
		JSONObject json;
		tickets.clear();
		for (String ticketStr : tickets_str) {
			json = JSONHelper.string2JSON(ticketStr);
			try {
				Ticket t = new Ticket(0,
						0,
						JSONHelper.getValue(json, "Ticket", "id"),
						JSONHelper.getValue(json, "Ticket", "created"),
						JSONHelper.getValue(json, "Ticket", "modified"),
						JSONHelper.getValue(json, "Ticket", "title"),
						JSONHelper.getValue(json, "Ticket", "description"),
						JSONHelper.getValue(json, "User", "name"),
						JSONHelper.getValue(json, "User", "email")
						);
				tickets.add(t);
			} catch (Exception e) {
				System.err.println(e.toString());
				System.err.println("Invalid JSON while retrieving tickets!");
			}
		}
		
	}

	/**
	 * Clears all the tickets in db and then insert the current ones
	 */
	public static void updateTicketsDB(Context context) {
		TicketsDataSource db = new TicketsDataSource(context);

		// First clear all the records
		db.clearTickets();
		
		// Then insert
		for (Ticket ticket : unassignedTickets) {
			db.insertTicket(ticket);
		}
		
		for (Ticket ticket : userTickets) {
			db.insertTicket(ticket);
		}
	}


	/**
	 * Fetches the tickets from the database and updates the array list.
	 * @param context
	 */
	public static void fetchTicketsFromDB(Context context) {
		TicketsDataSource db = new TicketsDataSource(context);

		unassignedTickets = db.getAllTickets(1);
		userTickets = db.getAllTickets(2);
	}

}