package com.vpmobitech.tungwahtsymsnect.Graph;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vpmobitech.tungwahtsymsnect.AlertDialogActivity;
import com.vpmobitech.tungwahtsymsnect.DBHelper;
import com.vpmobitech.tungwahtsymsnect.MainActivity;
import com.vpmobitech.tungwahtsymsnect.R;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUpperbp, txtLowerbp, txthb, txtcbc, txtPulse;
    Button btnAdd, btnShowGraph;
    ImageButton btnBack;
    TableLayout table_layout;
    SQLController sqlcon;
    TextView tbgtxt;
    TextView tv,tvBloodSugar,tvPulse,tvBP,tvWeight,tvTimepM,tvPound;
    String langPos, Medicine_Name, AM, PM, Graph, Set_Alarm, Time_is_set, Health_data_section, Alarm_Section, Camera;
    String Blood_Presure,Pulse,Weight,Sugar,Blood_Sugar,UBP,LBP,Time_Min,Pound,Date;

    Spinner spnDieses;

    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        init();
        helper = new DBHelper(GraphActivity.this);
        sqlcon = new SQLController(this);

        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            langPos = preferences.getString("langPos", "");
            Medicine_Name = preferences.getString("Medicine_Name", "");
            AM = preferences.getString("AM", "");
            PM = preferences.getString("PM", "");
            Graph = preferences.getString("Graph", "");
            Set_Alarm = preferences.getString("Set_Alarm", "");
            Time_is_set = preferences.getString("Time_is_set", "");
            Health_data_section = preferences.getString("Health_data_section", "");
            Alarm_Section = preferences.getString("Alarm_Section", "");
            Camera = preferences.getString("Camera", "");

            Blood_Presure = preferences.getString("Blood_Presure", "");
            Pulse = preferences.getString("Pulse", "");
            Weight = preferences.getString("Weight", "");
            Sugar = preferences.getString("Sugar", "");
            Blood_Sugar = preferences.getString("Blood_Sugar", "");
            UBP = preferences.getString("UBP", "");
            LBP = preferences.getString("LBP", "");
            Time_Min = preferences.getString("Time/Min", "");
            Pound = preferences.getString("Pound", "");
            Date = preferences.getString("Date", "");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("langPos===load==" + langPos);
        if (langPos.equals("1")) {
            tbgtxt.setText(Graph);
            tvBloodSugar.setText(Blood_Sugar);
            tvPulse.setText(Pulse);
            tvBP.setText(Blood_Presure);
            tvWeight.setText(Weight);
            tvTimepM.setText(Time_Min);
            tvPound.setText(Pound);

        } else if (langPos.equals("0")) {
            tbgtxt.setText("Add Graph Details");
            tvBloodSugar.setText(Blood_Sugar);
            tvPulse.setText(Pulse);
            tvBP.setText(Blood_Presure);
            tvWeight.setText(Weight);
            tvTimepM.setText(Time_Min);
            tvPound.setText(Pound);
        } else {

        }


        addHeaders();
        BuildTable();








      /*  final TableRow row = new TableRow(this);
       *//* row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));*//*

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=row.getId();

                System.out.println("row id=123="+id);
                show_dialog();
            }
        });*/


    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor, int gravity) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setGravity(gravity);
        tv.setTextColor(color);
        tv.setTextSize(12);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 0, 0, 1);
        return params;
    }


    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = findViewById(R.id.tableLayout1);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, Date, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
        tr.addView(getTextView(0, UBP, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
        tr.addView(getTextView(0, LBP, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
        tr.addView(getTextView(0, Pulse, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
        tr.addView(getTextView(0, Weight, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
        tr.addView(getTextView(0, Sugar, Color.WHITE, Typeface.BOLD, Color.BLACK, Gravity.CENTER_HORIZONTAL));
//        tr.addView(getTextView(0, "Action", Color.WHITE, Typeface.BOLD, Color.BLACK,Gravity.CENTER_HORIZONTAL));
        tl.addView(tr, getTblLayoutParams());
    }


    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }

    private void init() {

        txtUpperbp = (EditText) findViewById(R.id.txtUpperbp);
        txtLowerbp = (EditText) findViewById(R.id.txtLowerbp);
        txthb = (EditText) findViewById(R.id.txthb);
        txtcbc = (EditText) findViewById(R.id.txtcbc);
        txtPulse = (EditText) findViewById(R.id.txtPulse);
        tbgtxt = (TextView) findViewById(R.id.tbgtxt);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShowGraph = (Button) findViewById(R.id.btnShowGraph);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        tvBloodSugar = (TextView) findViewById(R.id.tvBloodSugar);
        tvPulse = (TextView) findViewById(R.id.tvPulse);
        tvBP = (TextView) findViewById(R.id.tvBP);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvTimepM = (TextView) findViewById(R.id.tvTimepM);
        tvPound = (TextView) findViewById(R.id.tvPound);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertReminderInLocal();
            }
        });

        btnShowGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, ShowGraph.class));
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this, MainActivity.class));
                finish();
            }
        });

    }


    public void InsertReminderInLocal() {

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        System.out.println("Date=1==" + date);

        helper = new DBHelper(GraphActivity.this);
        // Add Record with help of ContentValues and DBHelper class object
        ContentValues val = new ContentValues();


        val.put(DBHelper.UPPER_BP, txtUpperbp.getText().toString());
        val.put(DBHelper.LOWER_BP, txtLowerbp.getText().toString());
        val.put(DBHelper.SUGAR, txthb.getText().toString());
        val.put(DBHelper.WEIGHT, txtcbc.getText().toString());
        val.put(DBHelper.PULSE, txtPulse.getText().toString());
        val.put(DBHelper.DATE, date);


        db = helper.getWritableDatabase();
        db.insert(DBHelper.TABLE_GRAPH, null, val);
        db.close();

        Toast.makeText(this, "Record Successfully Added in IF Vaccin Chart local DB", Toast.LENGTH_LONG).show();

        txtUpperbp.setText("");
        txtLowerbp.setText("");
        txthb.setText("");
        txtcbc.setText("");
        txtPulse.setText("");
//        BuildTable();

        startActivity(new Intent(GraphActivity.this, GraphActivity.class));
    }

    public void fetchdata() {
        db = helper.getReadableDatabase();

        Cursor mCursor = db.query(DBHelper.TABLE_GRAPH, null, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        String[] columns = new String[]{


                helper.DATE,
                helper.UPPER_BP,
                helper.LOWER_BP,
                helper.PULSE,
                helper.WEIGHT,
                helper.SUGAR
        };

        //the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.date,
                R.id.upper,
                // R.id.lower,
                R.id.pulse,
                R.id.weight,
                R.id.sugar
        };

        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_row,
                mCursor,
                columns,
                to);
//        ListView list=(ListView) findViewById(R.id.listViewIncome);
//        list.setAdapter(dataAdapter);

    }


    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        System.out.println("On Long key Pressed....");
       /* TableLayout container = (TableLayout) v.getParent();
        // delete the row and invalidate your view so it gets
        // redrawn
        container.removeView(v);
        container.invalidate();*/
        return super.onKeyLongPress(keyCode, event);
    }

    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        TableLayout container = (TableLayout) v.getParent();
        // delete the row and invalidate your view so it gets
        // redrawn
        System.out.println("On Long key Pressed..22..");
        container.removeView(v);
        container.invalidate();
        return false;
    }

    private void BuildTable() {

        sqlcon.open();
        Cursor c = sqlcon.readEntry();

        final int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();

        View.OnClickListener tablerowOnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //GET TEXT HERE
                //String currenttext = ((TextView)v).getText().toString());

                String a= String.valueOf(tv.getText());
                System.out.println("row selected --> " + v.getTag());

                int id= (int) v.getTag();



                TableRow tablerow = (TableRow) v;
                TextView sample = (TextView) tablerow.getChildAt(0);
                String date=sample.getText().toString();

                System.out.println("Row Text: " + date);

                show_dialog(date);

            }
        };


        // outer for loop
        for (int i = 0; i < rows; i++) {

            final TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.setOnClickListener(tablerowOnClickListener);


            // inner for loop
            for (int j = 0; j < cols; j++) {

                tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(14);
                tv.setPadding(0, 5, 0, 5);
                tv.setText(c.getString(j));
                tv.setTag(i);

                row.setTag(i);

                System.out.println("TV======" + tv.toString());


                row.addView(tv);

            }

            c.moveToNext();

            table_layout.addView(row);

        }
        sqlcon.close();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GraphActivity.this, MainActivity.class));

    }


    public void show_dialog(final String date) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                GraphActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Delete Record");

        // Setting Dialog Message
        alertDialog.setMessage("You want to delete record?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.app_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed

                DeleteRecord(date);

                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void DeleteRecord(String date) {
        helper=new DBHelper(this);
        db=helper.getWritableDatabase();

        System.out.println("DAte=="+date);

        String whereClause = "date=?";
        String[] whereArgs = new String[] { String.valueOf(date) };

        db.delete(helper.TABLE_GRAPH, whereClause, whereArgs);
        db.close();

        Toast.makeText(GraphActivity.this,"Record Deleted Succefully",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(GraphActivity.this,GraphActivity.class));


    }
}