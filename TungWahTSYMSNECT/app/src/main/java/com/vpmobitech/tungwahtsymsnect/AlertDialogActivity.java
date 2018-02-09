package com.vpmobitech.tungwahtsymsnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vpmobitech.tungwahtsymsnect.Services.MyService;

import java.sql.Time;

import static android.app.PendingIntent.getActivity;

public class AlertDialogActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    String MedName;
    String hour,minutes,curTime;

    SQLiteDatabase db;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_alert_dialog); update

        helper = new DBHelper(this);

        hour= String.valueOf(new Time(System.currentTimeMillis()).getHours());
        minutes= String.valueOf(new Time(System.currentTimeMillis()).getMinutes());

        curTime=hour+minutes;

        FetchData(curTime);

       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        MedName = preferences.getString("MedName", "");*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Medicine Reminder")
                .setMessage("This is reminder for your medicine: "+MedName)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        stopService(new Intent(getApplicationContext(),MyService.class));
                        startActivity(new Intent(AlertDialogActivity.this,MainActivity.class));
                        System.exit(0);

                    }
                })/*
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                })*/;
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void FetchData(String alarmID){
         db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+helper.TABLE+" where alarmID="+alarmID+";",null);

        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext()) {

            MedName = cursor.getString(cursor.getColumnIndexOrThrow("med_name"));

            System.out.println("MedName======"+MedName);

        }

    }

}
