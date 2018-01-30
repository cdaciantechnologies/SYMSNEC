package com.vpmobitech.tungwahtsymsnect;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.SystemClock;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.vpmobitech.tungwahtsymsnect.Receiver.MyAlarm;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.sql.Time;
        import java.util.*;

public class SetAlarm extends AppCompatActivity {

    ImageView imgSetAlaram;
    TimePicker timePicker;
    Button btnSetAlaram;
    ImageButton btnBack;
    EditText txtMedicinName;
    TextView tvMedName,tvtbnm;
    ListView lstAlarms;

    //    DatabaseHelper database;
    RecyclerView recyclerView;
    RecycleAdapter recycler;
    java.util.List<DataModel> datamodel;

    String langPos,Medicine_Name,AM,PM,Set_Alarm,Time_is_set,Health_data_section,Alarm_Section,Camera;

    DBHelper helper;
    SQLiteDatabase db;


    RequestQueue requestQueue;
    public static final String showURLGetPinNo = "http://www.vpmobitech.com/Samiksha_Vaccination/GetPinNo.php";

    public static final String JSON_ARRAY1 = "result";
    int hours,minutes;
    static int alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        init();

        helper = new DBHelper(this);

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            langPos = preferences.getString("langPos", "");
            Medicine_Name = preferences.getString("Medicine_Name", "");
            AM = preferences.getString("AM", "");
            PM = preferences.getString("PM", "");
            Set_Alarm = preferences.getString("Set_Alarm", "");
            Time_is_set = preferences.getString("Time_is_set", "");
            Health_data_section = preferences.getString("Health_data_section", "");
            Alarm_Section = preferences.getString("Alarm_Section", "");
            Camera = preferences.getString("Camera", "");
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        System.out.println("Lan pos==="+langPos+Medicine_Name+Set_Alarm);

        if (langPos.equals("1"))
        {
            btnSetAlaram.setText(Set_Alarm);
            tvMedName.setText(Medicine_Name);
            tvtbnm.setText(Medicine_Name);
        }
        else if (langPos.equals("0")){
            btnSetAlaram.setText(Set_Alarm);
            tvMedName.setText(Medicine_Name);
            tvtbnm.setText(Medicine_Name);

        }
        else {

        }

        try {
//            getData();
            helper = new DBHelper(SetAlarm.this);
            datamodel=  helper.getdata();
            recycler =new RecycleAdapter(SetAlarm.this,datamodel);


            Log.i("HIteshdata",""+datamodel);
            RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(reLayoutManager);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recycler);





        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        btnSetAlaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String MedName=txtMedicinName.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SetAlarm.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("MedName",MedName);
                editor.apply();

                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }

                hours = timePicker.getCurrentHour();
                minutes = timePicker.getCurrentMinute();

                alarmId = Integer.valueOf(String.valueOf(hours) + String.valueOf(minutes));
                System.out.println("alarmId==**=="+alarmId);

                setAlarm(calendar.getTimeInMillis());
                startActivity(new Intent(SetAlarm.this,SetAlarm.class));


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SetAlarm.this,MainActivity.class));

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SetAlarm.this,MainActivity.class));

    }
    private void init() {

//        imgSetAlaram=(ImageView)findViewById(R.id.imgSetAlaram);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btnSetAlaram = (Button) findViewById(R.id.btnSetAlaram);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        txtMedicinName = (EditText) findViewById(R.id.txtMedicinName);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        lstAlarms = (ListView) findViewById(R.id.lstAlarms);
        tvMedName = (TextView) findViewById(R.id.tvMedName);
        tvtbnm = (TextView) findViewById(R.id.tvtbnm);
    }

    private void setAlarm(long time) {



        Intent intent = new Intent(this, MyAlarm.class);
        //intent.putExtra("row_id", rowId);


        PendingIntent sender = PendingIntent.getBroadcast(this, alarmId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time, sender);

        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();


        InsertVaccinChartInLocal();

    }

    public void InsertVaccinChartInLocal()
    {
        helper=new DBHelper(SetAlarm.this);
        // Add Record with help of ContentValues and DBHelper class object
        ContentValues val=new ContentValues();

        String strDateTime = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();

        val.put(DBHelper.MED_NAME, txtMedicinName.getText().toString());
        val.put(DBHelper.REM_TIME, strDateTime.toString());
        val.put(DBHelper.ALARM_ID, alarmId);


        db=helper.getWritableDatabase();
        db.insert(DBHelper.TABLE, null, val);
        db.close();

        Toast.makeText(this, "Record Successfully Added in IF Vaccin Chart local DB", Toast.LENGTH_LONG).show();
        datamodel=  helper.getdata();
        recycler =new RecycleAdapter(SetAlarm.this,datamodel);
        /*recycler=dataModels.remove(getAdapterPosition());
        notifyDataSetChanged();*/
    }


}
