package org.up.fe.tdin.tdintroubletickets.model;

public class Ticket {

	public int id = -1;
	public int ticket_type = -1;
	public String uuid = "Undefined";
	public String created_at = "Undefined";
	public String updated_at = "Undefined";
	public String title = "Undefined";
	public String description = "Undefined";
	
	public Ticket(){

	}
	
	public Ticket(int id, int ticket_type, String uuid, String created_at, 
			String updated_at, String title, String description) {
		this.id = id;
		this.ticket_type = ticket_type;
		this.uuid = uuid;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.title = title;
		this.description = description;
	}
}