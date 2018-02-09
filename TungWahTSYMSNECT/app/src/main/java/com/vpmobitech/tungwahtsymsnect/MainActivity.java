package com.vpmobitech.tungwahtsymsnect;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vpmobitech.tungwahtsymsnect.Graph.GraphActivity;
import com.vpmobitech.tungwahtsymsnect.Services.LockService;
import com.vpmobitech.tungwahtsymsnect.Services.MyService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView webView;
    MenuItem nav_camera,nav_alarm,nav_graph;
    Menu mnu_camera,mnu_alarm,mnu_graph;
    static DrawerLayout drawer;
    NavigationView navigationView;

    String langPos,Medicine_Name,AM,PM,Graph,Set_Alarm,Time_is_set,Health_data_section,Alarm_Section,Camera;

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://flamesquadsdemo.com/tw/");

        startService(new Intent(getApplicationContext(), LockService.class));

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        LocationPermission();

        SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        langPos = String.valueOf(preferences1.getString("langPos", "1"));


        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            langPos = preferences.getString("langPos", "");
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






        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        mnu_alarm=navigationView.getMenu();
        mnu_camera=navigationView.getMenu();
        mnu_graph=navigationView.getMenu();

        nav_alarm=mnu_alarm.findItem(R.id.nav_alarm);
        nav_camera=mnu_camera.findItem(R.id.nav_camera);
        nav_graph=mnu_graph.findItem(R.id.nav_graph);

        System.out.println("langPos===load=="+langPos);
        if (langPos.equals("1")) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("langPos",langPos);

            editor.putString("Medicine_Name","藥名");
            editor.putString("AM","上午");
            editor.putString("PM","下午");
            editor.putString("Set_Alarm","用藥提示");
            editor.putString("Graph","健康指數");
            editor.putString("Time_is_set","時間已設定");
            editor.putString("Health_data_section","健康指數");
            editor.putString("Alarm_Section","用藥提示");
            editor.putString("Camera","照相區");


            editor.putString("Blood_Presure","血壓");
            editor.putString("Pulse","脈搏");
            editor.putString("Weight","重量");
            editor.putString("Sugar","血糖");
            editor.putString("Blood_Sugar","血糖");
            editor.putString("UBP","上壓");
            editor.putString("LBP","下壓");
            editor.putString("Time/Min","時間/每分鐘");
            editor.putString("Pound","磅");
            editor.putString("Date","日期");
            editor.putString("btnAdd","加");
            editor.putString("btnShowGraph","顯示圖表");
            editor.putString("Action","刪除");
            editor.putString("SelectCategory","選擇類別");
            editor.putString("UpperLimit","上限");
            editor.putString("LowerLimit","下限 ");
            editor.apply();

            SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
//            langPos = preferences.getString("langPos", "");
            Medicine_Name = preferences2.getString("Medicine_Name", "");
            AM = preferences2.getString("AM", "");
            PM = preferences2.getString("PM", "");
            Graph = preferences2.getString("Graph", "");
            Set_Alarm = preferences2.getString("Set_Alarm", "");
            Time_is_set = preferences2.getString("Time_is_set", "");
            Health_data_section = preferences2.getString("Health_data_section", "");
            Alarm_Section = preferences2.getString("Alarm_Section", "");
            Camera = preferences2.getString("Camera", "");

            nav_camera.setTitle(Camera);
            nav_alarm.setTitle(Set_Alarm);
            nav_graph.setTitle(Graph);

            System.out.println("langPos"+langPos);
            System.out.println("Medicine_Name"+Medicine_Name);
            System.out.println("AM"+AM);
            System.out.println("PM"+PM);
            System.out.println("Graph"+Graph);
            System.out.println("Set_Alarm"+Set_Alarm);
            System.out.println("Time_is_set"+Time_is_set);
            System.out.println("Health_data_section"+Health_data_section);
            System.out.println("Alarm_Section"+Alarm_Section);
            System.out.println("Camera"+Camera);


        }else if (langPos.equals("0")) {
            nav_camera.setTitle(Camera);
            nav_alarm.setTitle(Set_Alarm);
            nav_graph.setTitle(Graph);
        }else{

        }
    }

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            AlertShow();
        } else {
            AlertShow();
            super.onBackPressed();
        }
    }*/

    /*@Override
    public void onBackPressed() {
        AlertShow();

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        } else if (id == R.id.nav_alarm) {
             /*navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));*/
            startActivity(new Intent(MainActivity.this,SetAlarm.class));
            finish();
        } else if (id == R.id.nav_graph) {
            startActivity(new Intent(MainActivity.this,GraphActivity.class));
            finish();
        }/* else if (id == R.id.nav_manage) {
            ShowLangDialog();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LocationPermission()
    {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                ){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            //txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[2])){
                //txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
        //txtPermissions.setText("We've got all permissions");
//        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        System.out.println("We got All Permissions");
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }


    public void ShowLangDialog()
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
        final Spinner spinLanguage = (Spinner) dialog.findViewById(R.id.spinLanguage);
        Button btnUpdateLang = (Button) dialog.findViewById(R.id.btnUpdateLang);


        System.out.println("langPos===load=="+langPos);
        if (langPos.equals("1")) {
            spinLanguage.setSelection(1);
        }else if (langPos.equals("0")) {
            spinLanguage.setSelection(0);
        }else{

        }


        btnUpdateLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String langPos= String.valueOf(spinLanguage.getSelectedItemPosition());
                System.out.println("langPos==="+langPos);

                if (langPos.equals("1"))
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("langPos",langPos);

                    editor.putString("Medicine_Name","藥名");
                    editor.putString("AM","上午");
                    editor.putString("PM","下午");
                    editor.putString("Set_Alarm","用藥提示");
                    editor.putString("Graph","健康指數");
                    editor.putString("Time_is_set","時間已設定");
                    editor.putString("Health_data_section","健康指數");
                    editor.putString("Alarm_Section","用藥提示");
                    editor.putString("Camera","照相區");


                    editor.putString("Blood_Presure","血壓");
                    editor.putString("Pulse","脈搏");
                    editor.putString("Weight","重量");
                    editor.putString("Sugar","血糖");
                    editor.putString("Blood_Sugar","血糖");
                    editor.putString("UBP","上壓");
                    editor.putString("LBP","下壓");
                    editor.putString("Time/Min","時間/每分鐘");
                    editor.putString("Pound","磅");
                    editor.putString("Date","日期");
                    editor.putString("btnAdd","加");
                    editor.putString("btnShowGraph","顯示圖表");
                    editor.putString("Action","刪除");
                    editor.putString("SelectCategory","選擇類別");
                    editor.putString("UpperLimit","上限");
                    editor.putString("LowerLimit","下限 ");
                    editor.apply();
                }

                else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("langPos",langPos);

                    editor.putString("Medicine_Name","Medicine Name");
                    editor.putString("AM","AM");
                    editor.putString("PM","PM");
                    editor.putString("Graph","Health Data Section");
                    editor.putString("Set_Alarm","Set Alarm");
                    editor.putString("Time_is_set","Time_is_set");
                    editor.putString("Health_data_section","Health data section");
                    editor.putString("Alarm_Section","Alarm_Section");
                    editor.putString("Camera","Camera");


                    editor.putString("Blood_Presure","Blood Presure");
                    editor.putString("Pulse","Pulse");
                    editor.putString("Weight","Weight");
                    editor.putString("Blood_Sugar","Blood Sugar");
                    editor.putString("Sugar","Sugar");
                    editor.putString("UBP","Upper BP");
                    editor.putString("LBP","Lower BP");
                    editor.putString("Time/Min","Time/Min");
                    editor.putString("Pound","Pound");
                    editor.putString("Date","Date");
                    editor.apply();
                }


                startActivity(new Intent(MainActivity.this,MainActivity.class));

                dialog.dismiss();
                finish();
            }
        });


        dialog.show();
    }


    public void AlertShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(
                MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Exit");

        // Setting Dialog Message
        alertDialog.setMessage("You want to Exit");

        // Setting Icon to Dialog
//            alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
//                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
            }
        });


        // Showing Alert Message
        alertDialog.show();

    }

}
