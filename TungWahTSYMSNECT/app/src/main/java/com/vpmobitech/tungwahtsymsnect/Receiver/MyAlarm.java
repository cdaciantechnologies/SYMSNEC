package com.vpmobitech.tungwahtsymsnect.Receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.vpmobitech.tungwahtsymsnect.AlertDialogActivity;
import com.vpmobitech.tungwahtsymsnect.MainActivity;
import com.vpmobitech.tungwahtsymsnect.Services.MyService;

/**
 * Created by Sujit Jamdade on 1/21/2018.
 */

public class MyAlarm extends BroadcastReceiver {

    //the method will be fired when the alarm is triggerred

    Context context;
    String MedName;
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday


        Intent svc=new Intent(context, MyAlarm.class);
        context.startService(new Intent(context,MyService.class));

        /*MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();*/
        Log.d("MyAlarmBelal", "Alarm just fired");
//        ShowAlert();
        Intent i = new Intent(context, AlertDialogActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);


    }

    public void ShowAlert() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        MedName = preferences.getString("MedName", "");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle("醫學提醒")
                .setMessage("這是提醒你的藥: "+MedName)
                .setCancelable(false)
                .setPositiveButton("好", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        context.stopService(new Intent(context,MyService.class));
                        context.startActivity(new Intent(context,MainActivity.class));
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
}