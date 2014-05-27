package org.up.fe.tdin.tdintroubletickets.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLTroubleTickets extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "troubletickets";
	public static final String TABLE_TICKETS = "tickets";
	public static final String FIELD_TICKET_ID = "id";
	public static final String FIELD_TICKET_UUID = "uuid"; // 1
	public static final String FIELD_TICKET_TITLE = "title"; // 2
	public static final String FIELD_CREATING_USER_UUID = "user_uuid"; // 3
	public static final String FIELD_TICKET_STATUS = "status"; // 4
	public static final String FIELD_TICKET_CREATED_DATE = "created"; // 5
	public static final String FIELD_TICKET_MODIFIED_DATE = "modified"; // 6

	public static final String FIELD_TICKET_TYPE = "ticket_type";
	// TODO
	private static final String TICKETS_TABLE_CREATE =
			"CREATE TABLE " + TABLE_TICKETS + " (" +
			FIELD_TICKET_ID + " TEXT, " + //
			FIELD_TICKET_UUID + " TEXT, " + //1
			FIELD_TICKET_TITLE + " TEXT, " + //2
			FIELD_CREATING_USER_UUID + " TEXT, " + //3
			FIELD_TICKET_STATUS + " TEXT, " + //4
			FIELD_TICKET_CREATED_DATE + " TEXT, " + //5
			FIELD_TICKET_MODIFIED_DATE + " TEXT, " + //5
			FIELD_TICKET_TYPE + " TEXT);";

	SQLiteDatabase db;
	
	
	public SQLTroubleTickets(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TICKETS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
		onCreate(db);
	}
}