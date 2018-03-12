package com.vpmobitech.tungwahtsymsnect;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.*;

/**
 * Created by VPM-Sujit on 1/22/2018.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE="TungDB.DB";
    public static final String TABLE="Reminders";
    public static final String TABLE_GRAPH="Graph";

    public static final int VERSION=1;

    public static final String SR_NO="SrNo";
    public static final String I_ID="_id";
    public static final String REM_NAME="rem_name";
    public static final String MED_NAME="med_name";
    public static final String REM_TIME="rem_time";
    public static final String OTHER="other";
    public static final String ALARM_ID="alarmID";


    public static final String UPPER_BP="upper_bp";
    public static final String LOWER_BP="lower_bp";
    public static final String SUGAR="sugar";
    public static final String WEIGHT="weight";
    public static final String PULSE="pulse";
    public static final String DATE="date";
    public static final String DELETE="del";

    /*public static final String BP="upper_bp";
    public static final String HB="hb";
    public static final String CBC="cbc";
    public static final String DATE="date";*/

    // for internal db
    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }


   public ArrayList<String> xDate;
   public ArrayList<String> xAxis;
   public ArrayList<String> yAxis;
    // for external db
   /* public DBHelper(final Context context)
    {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "Tung"
                + File.separator + DATABASE, null, VERSION);
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Reminders(SrNo INTEGER PRIMARY KEY AUTOINCREMENT,med_name varchar(20),rem_time varchar(20),alarmID varchar(20))");
        db.execSQL("create table Graph(_id integer PRIMARY KEY AUTOINCREMENT,upper_bp varchar(20),lower_bp varchar(20),sugar varchar(20),weight varchar(20),pulse varchar(20),date varchar(20),del BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table Reminders");
        db.execSQL("drop table Graph");
        onCreate(db);
    }

    public List<DataModel> getdata(){
        // DataModel dataModel = new DataModel();
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE+" ;",null);

        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
            String SrNo = cursor.getString(cursor.getColumnIndexOrThrow("SrNo"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("med_name"));
            String city = cursor.getString(cursor.getColumnIndexOrThrow("rem_time"));
            int alarmID = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("alarmID")));

            dataModel.setName(name);
            dataModel.setCity(city);
            dataModel.setSrNo(SrNo);
            dataModel.setAlarmID(alarmID);

//            dataModel.setCounty(country);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getCity());
        }

        return data;
    }



    public List<DataModel> getdataGrpha(String dname,int noOfRec){
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        xAxis=new ArrayList<String>();
        xDate=new ArrayList<String>();
        yAxis=new ArrayList<String>();
        Cursor cursor = db.rawQuery("select "+dname+",date from "+TABLE_GRAPH +" Order by "+DBHelper.I_ID+" DESC LIMIT "+noOfRec+";",null);//order by date limit=10

//        Cursor cursor = db.query(DBHelper.TABLE_GRAPH, new String[]{dname}, null, null, null,
//                null, DBHelper.I_ID +" DESC LIMIT "+noOfRec);

        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new DataModel();
            String cname = cursor.getString(cursor.getColumnIndexOrThrow(dname));

            yAxis.add(cname);

            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));

            xAxis.add(date);

            String DateInitial = date.substring( 0, date.indexOf("-"));

            String remainder = date.substring(date.indexOf("-")+1, date.length());

            System.out.println("remainder=2="+remainder+"    DateInitial=2="+DateInitial);


            xDate.add(DateInitial);

            System.out.println("cname=2="+cname+"    date=2="+date);


            stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getCity());
        }



        return data;
    }
}
