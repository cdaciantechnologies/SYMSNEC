package com.vpmobitech.tungwahtsymsnect.Graph;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import com.vpmobitech.tungwahtsymsnect.DBHelper;
import com.vpmobitech.tungwahtsymsnect.MainActivity;
import com.vpmobitech.tungwahtsymsnect.R;
import com.vpmobitech.tungwahtsymsnect.SetAlarm;

import java.util.ArrayList;

public class ShowGraph extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    DBHelper helper ;
    private LineChart mChart;
    ImageButton btnBack;
    Spinner spnDieses;
    TextView tbgtxt;
    String langPos,Medicine_Name,AM,PM,Graph,Set_Alarm,Time_is_set,Health_data_section,Alarm_Section,Camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To make full screen layout

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_graph);

        spnDieses=(Spinner)findViewById(R.id.spnDieses);
        helper = new DBHelper(this);

        btnBack=(ImageButton)findViewById(R.id.btnBack);
        tbgtxt=(TextView) findViewById(R.id.tbgtxt);


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
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        System.out.println("langPos===load=="+langPos);
        System.out.println("Health_data_section===load=="+Health_data_section);
        if (langPos.equals("1")) {
            tbgtxt.setText(Health_data_section);

        }else if (langPos.equals("0")) {
            tbgtxt.setText(Health_data_section);
        }else{

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              startActivity(new Intent(ShowGraph.this,GraphActivity.class));
            }
        });

        spnDieses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos=spnDieses.getSelectedItemPosition();

                if (pos==0)
                {
                    helper.getdataGrph("upper_bp");
                }
                if (pos==1)
                {
                    helper.getdataGrph("lower_bp");
                }
                else if(pos==2)
                {
                    helper.getdataGrph("sugar");
                }
                else if(pos==3)
                {
                    helper.getdataGrph("weight");
                }
                else if(pos==4)
                {
                    helper.getdataGrph("pulse");
                }

                System.out.println("Arraylistof X=="+helper.xAxis.toString());
                System.out.println("Arraylistof Y=="+helper.yAxis.toString());
                System.out.println("Arraylistof Y=="+helper.xDate.toString());

                // add data

                setData();
                mChart.setOnChartGestureListener(ShowGraph.this);
                mChart.getLineData();
//                mChart.setOnTouchListener(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelRotationAngle(270);








        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setDescription("Demo Line Chart");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();

    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
//        System.out.println("XVALS==="+helper.xAxis.toString());

        xVals.addAll(helper.xAxis);
        System.out.println("XVALS==="+xVals.toString());
       // xVals=helper.xAxis;
      /*  xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("400");*/

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();

//        yVals.addAll(0,helper.yAxis);

        float val;
        for (int i=0;i<helper.yAxis.size();i++)
        {
            val= Float.parseFloat(helper.yAxis.get(i));
            yVals.add(new Entry(val, i));
        }
        /*yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));*/

        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);



        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowGraph.this,MainActivity.class));

    }
}