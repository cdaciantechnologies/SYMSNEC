package com.vpmobitech.tungwahtsymsnect.Graph;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.vpmobitech.tungwahtsymsnect.DBHelper;

public class SQLController {

	private DBHelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new DBHelper(ourcontext);
		database = dbhelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	/*public void insertData(String name, String lname) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.MEMBER_FIRSTNAME, name);
		cv.put(DBHelper.MEMBER_LASTNAME, lname);
		database.insert(DBHelper.TABLE_MEMBER, null, cv);

	}*/



	public Cursor readEntry() {
		// TODO Auto-generated method stub
		String[] allColumns = new String[] {DBHelper.I_ID,DBHelper.DATE, DBHelper.UPPER_BP,DBHelper.LOWER_BP,
				DBHelper.PULSE,DBHelper.WEIGHT,DBHelper.SUGAR };//,DBHelper.DELETE

		Cursor c = database.query(DBHelper.TABLE_GRAPH, allColumns, null, null, null,
				null, DBHelper.I_ID +" DESC LIMIT 10");

		if (c != null) {
			c.moveToFirst();
		}
		return c;

	}

}
