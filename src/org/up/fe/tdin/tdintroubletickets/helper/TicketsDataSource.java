package org.up.fe.tdin.tdintroubletickets.helper;

import java.util.ArrayList;

import org.up.fe.tdin.tdintroubletickets.model.Ticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TicketsDataSource {

	// Database fields
	private SQLiteDatabase db;
	private SQLTroubleTickets ticketsSQL;
	private String[] allColumns = { 
			SQLTroubleTickets.FIELD_TICKET_ID,
			SQLTroubleTickets.FIELD_TICKET_UUID,
			SQLTroubleTickets.FIELD_TICKET_TYPE };

	public TicketsDataSource(Context context) {
		ticketsSQL = new SQLTroubleTickets(context);
		this.open();
	}

	public void open() throws SQLException {
		db = ticketsSQL.getWritableDatabase();
	}

	public void close() {
		ticketsSQL.close();
	}

	/**
	 * Inserts a ticket
	 * @param ticket
	 * @return the database id of the recently inserted ticket
	 */
	public long insertTicket(Ticket ticket) {
		ContentValues values = new ContentValues();
		values.put(SQLTroubleTickets.FIELD_TICKET_TYPE, ticket.ticket_type);
		values.put(SQLTroubleTickets.FIELD_TICKET_UUID, ticket.uuid);
		
		return db.insert(SQLTroubleTickets.TABLE_TICKETS, null, values);
	}

	public void deleteTicket(Ticket ticket) {
		long id = ticket.id;
		
		db.delete(SQLTroubleTickets.TABLE_TICKETS, SQLTroubleTickets.FIELD_TICKET_ID
		+ " = " + id, null);
	}
	
	public ArrayList<Ticket> getAllTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		//		database.rawQuery("SELECT  * FROM " + SQLTroubleTickets.TABLE_TICKETS, null);
		Cursor cursor = db.query(SQLTroubleTickets.TABLE_TICKETS,
		allColumns, "ticket_type = 1", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Ticket ticket = cursorToTicket(cursor);
			tickets.add(ticket);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return tickets;
	}
	
	public ArrayList<Ticket> getAllTickets(int type) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		//		database.rawQuery("SELECT  * FROM " + SQLTroubleTickets.TABLE_TICKETS, null);
		String[] args = { type + "" };
		Cursor cursor = db.query(SQLTroubleTickets.TABLE_TICKETS,
		allColumns, "ticket_type = ?", args, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Ticket ticket = cursorToTicket(cursor);
			tickets.add(ticket);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return tickets;
	}
	
	public boolean clearTickets() {
		db.delete(SQLTroubleTickets.TABLE_TICKETS, null, null);
		return true;
	}

	private Ticket cursorToTicket(Cursor cursor) {
		Ticket ticket = new Ticket();
		ticket.id = (int) cursor.getLong(0);
		ticket.ticket_type = Integer.parseInt(cursor.getString(2));
		ticket.uuid = cursor.getString(1);
		return ticket;
	}
}