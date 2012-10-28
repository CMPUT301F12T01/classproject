package ca.ualberta.cs.c301f12t01.localStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBInstance extends SQLiteOpenHelper{
	/*
	 * 
	 */
	
	private static final String DATABASE_NAME = "TaskSource.db";
	
	private static final String DATABASE_CREATE = "CREATE TABLE Tasks "
		      + "(user INTEGER NOT NULL,"
		      + "shared INTEGER,"
			  + "id INTEGER PRIMARY KEY,"
		      + "summary TEXT"
		      + "description TEXT); "
		      + "CREATE TABLE Request "
		      + "(request_id INTEGER PRIMARY KEY," 
		      + "description TEXT,"
		      + "mediatype TEXT,"
			  + "quantity INTEGER,"
			  + "amountfulfilled INTEGER,"
			  + "task_id INTEGER NOT NULL ,"
		      + "foreign key(task_id) references Tasks(id) NOT NULL; "
		      + "CREATE TABLE Report "
		      + "(scope INTEGER,"
		      + "id INTEGER PRIMARY KEY,"
			  + "task_id INTEGER NOT NULL,"
		      + "foreign key(task_id) references Tasks(id) NOT NULL;"
		      + "CREATE TABLE Response "
		      + "(response_id INTEGER PRIMARY KEY,"
		      + "mediatype TEXT,"
			  + "report_id INTEGER,"
		      + "foreign key(report_id) references Report(id) NOT NULL, " 
			  + "media BLOB);";
	
	public DBInstance(Context context) {
	    super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		//TODO make this into a singleton? 	
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS Tasks");
	    db.execSQL("DROP TABLE IF EXISTS Reports");
	    db.execSQL("DROP TABLE IF EXISTS Request");
	    db.execSQL("DROP TABLE IF EXISTS Response");
	    onCreate(db);
	} 
}
