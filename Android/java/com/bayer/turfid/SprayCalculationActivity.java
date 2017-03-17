package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

public class SprayCalculationActivity extends Activity {
    Button continuebtn;
    ImageView imgLogo;
    TextView clubName;
    TextView dateofCalib,createClub;
    TextView sprayModel;
    EditText clubArea,sprayMake,pesticideView;
    final ArrayList<String> sprayList = new ArrayList<String>();
    SharedPreferences sprayCalc;
    ImageView img;
    int waterVol;
    String appRate;
    String watervolString;
    Boolean valueResult;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    TabWidget tabWidget;
    Activity parent;
    Context context;
     DateFormat dateFormatter;
    DatePickerDialog fromDatePickerDialog;
    private static Activity parentac;
    int tempPos;
    int pos;
    Boolean model=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_calculation);
        img=(ImageView)findViewById(R.id.logo_img);
        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        valueResult=false;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SprayCalculationActivity.this, BayerTurfManagementActivity.class);
                startActivity (intent);
            }
        });
        Prefrence=getApplicationContext().getSharedPreferences("SprayCalc",MODE_PRIVATE);
        editor=Prefrence.edit();

        clubName= (TextView) findViewById(R.id.clubname);
        clubArea= (EditText) findViewById(R.id.clubarea);
        dateofCalib=(TextView) findViewById(R.id.date);
        sprayMake= (EditText) findViewById(R.id.sprayermake);
        sprayModel= (TextView) findViewById(R.id.spraymodel);
        pesticideView= (EditText) findViewById(R.id.pesticide);
        createClub=(TextView)findViewById(R.id.createClub);
        continuebtn=(Button)findViewById(R.id.continuebtn);


        sprayCalc = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);


        clubName.setText("");
        clubArea.setText("");
        dateofCalib.setText("");
        sprayMake.setText("");
        sprayModel.setText("");
        pesticideView.setText("");
        if(!sprayCalc.getBoolean("in10",false))
        {

            String clnme=sprayCalc.getString("golfName","club");
            String arnme=sprayCalc.getString("areaName","club");
            String dtenme=sprayCalc.getString("dateofcalibration","date");
            String sprmkenme=sprayCalc.getString("sprmake","sprmke");
            String sprmodlnme=sprayCalc.getString("sprmodel","sprmodel");
            String pesnme=sprayCalc.getString("pes","pesti");
            if(clnme.equals("club"))
            {
                clubName.setText("");
            }
            else if(arnme.equals("club"))
            {
                clubArea.setText("");
            }
            else if(dtenme.equals("date"))
            {
                dateofCalib.setText("");
            }
            else if(sprmkenme.equals("sprmke"))
            {
                sprayMake.setText("");
            }
            else if(sprmodlnme.equals("sprmodel"))
            {
                sprayModel.setText("");
            }
            else if(pesnme.equals("pesti"))
            {
                pesticideView.setText("");
            }
            else {
                clubName.setText(clnme);
                clubArea.setText(arnme);
                dateofCalib.setText(dtenme);
                sprayMake.setText(sprmkenme);
                sprayModel.setText(sprmodlnme);
                pesticideView.setText(pesnme);
            }



            SharedPreferences.Editor editor = sprayCalc.edit();
            editor.putBoolean("in10", true);
            editor.commit();
        }
        else
        {
            String clnme=sprayCalc.getString("setClub","clubName");
            String sprMake=sprayCalc.getString("setSprayMake","sprayMake");
            String sprModel=sprayCalc.getString("setSprayModel","sprayModel");
            if(clnme.equals("clubName"))
            {
                clubName.setText("");
            }
            else if(sprMake.equals("sprayMake"))
            {
                sprayMake.setText("");
            }
            else if(sprModel.equals("sprayModel"))
            {
                sprayModel.setText("");
            }
            else {

                clubName.setText(clnme);
                dateofCalib.setText("");
                sprayMake.setText(sprMake);
                sprayModel.setText(sprModel);
                pesticideView.setText("");
            }
//
        }

        createClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(SprayCalculationActivity.this,NewClubActivity.class);
                editor=Prefrence.edit();
                editor.putString("newclubVal","sprayCalc");
                editor.commit();

                startActivity(i);
            }
        });



        try {
            JSONObject jObj = new JSONObject(loadJSONFromAsset());
            final JSONArray jArray;
            String type = Prefrence.getString("sprayerType","default");
            Log.d("sree type check", type);
//                    if(!Prefrence.getBoolean("firstAccept",false)){
            if(type.equals("boom")){
//                calc2=new Intent(SprayCalculationActivity.this,BoomStep2Activity.class);
                jArray=jObj.getJSONArray( "name_of_productBoom");
            }else{
//                calc2=new Intent(SprayCalculationActivity.this,SprayActivity2_knapsack.class);
                jArray = jObj.getJSONArray("name_of_product");
            }

            HashMap<String, String> spray;
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject spr = jArray.getJSONObject(i);
                String sprName = spr.getString("name");

                sprayList.add(sprName);

            }
            sprayList.add("Others");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
            final String pesticidelist[]=sprayList.toArray(new String[sprayList.size()]);
            final ArrayAdapter<String> myAdapternew= new ArrayAdapter<String>(SprayCalculationActivity.this, android.R.layout.simple_dropdown_item_1line,
                    pesticidelist);
        pesticideView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    pesticideView.requestFocus();

                    final AlertDialog.Builder pesticideSpin = new AlertDialog.Builder(SprayCalculationActivity.this);
                    pesticideSpin.setTitle("Select Pesticide");
                    pesticideSpin.setAdapter(myAdapternew, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (pesticidelist[which].toString().equals("Others")) {

                                pesticideView.setText("");
                                pesticideView.setError(null);
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(pesticideView, InputMethodManager.SHOW_IMPLICIT);
                                editor = Prefrence.edit();
                                editor.putBoolean("prePopulate", false);
                                editor.putBoolean("prePopulate1", false);
                                editor.commit();
                                pesticideView.requestFocus();


                            }
                            else {
                                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(pesticideView.getWindowToken(), 0);
                                pesticideView.setError(null);
                                pesticideView.setText(pesticidelist[which].toString());
                                editor = Prefrence.edit();
                                editor.putInt("tempPosition", which);
                                editor.putBoolean("prePopulate", true);
                                editor.putBoolean("prePopulate1", true);

                                editor.commit();
                            }
//
                            dialog.dismiss();
                        }
                    });

                    pesticideSpin.create().show();

                }
                return true;
            }


        });







        clubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clubName.setError(null);
                final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                List<String> golfVal=db.selectGolfName();
                List<String>golfSprayMake=db.selectSprayerMake();
                List<String>golfSprayModel=db.selectSprayerModel();
                final String [] listString=golfVal.toArray(new String[golfVal.size()]);
                final String [] sprayMakeVal=golfSprayMake.toArray(new String[golfSprayMake.size()]);
                final String [] sprayModelVal=golfSprayModel.toArray(new String[golfSprayModel.size()]);
                final ArrayAdapter<String> myAdapternew= new ArrayAdapter<String>(SprayCalculationActivity.this, android.R.layout.simple_dropdown_item_1line,
                        listString);
                AlertDialog.Builder mBuider= new AlertDialog.Builder(SprayCalculationActivity.this);
                      mBuider  .setTitle("Select Golf Name");
                    mBuider.setAdapter(myAdapternew, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clubName.setText(listString[which].toString());
                                clubArea.setText("");
                                sprayMake.setText(sprayMakeVal[which].toString());
                                sprayModel.setText(sprayModelVal[which].toString());
                                dialog.dismiss();
                            }
                        });

                 mBuider.create().show();

            }
        });

        clubArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clubArea.requestFocus();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    final DatabaseHandler db1 = new DatabaseHandler(getApplicationContext());
                    List<String> areaValName = db1.selectAreaName(clubName.getText().toString());
                    areaValName = new ArrayList<String>(new LinkedHashSet<String>(areaValName));
                    final String[] listString = areaValName.toArray(new String[areaValName.size()]);
                    final ArrayAdapter<String> myAdapternew1 = new ArrayAdapter<String>(SprayCalculationActivity.this, android.R.layout.simple_dropdown_item_1line,
                            listString);
                    new AlertDialog.Builder(SprayCalculationActivity.this)
                            .setTitle("Select Area Name")
                            .setAdapter(myAdapternew1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                            clubArea.setError(null);
                            if (listString[which].toString().equalsIgnoreCase("Create a New Area")) {
                                valueResult = true;
                                clubArea.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(clubArea, InputMethodManager.SHOW_IMPLICIT);
                                clubArea.requestFocus();
                            } else {
                                clubArea.setText(listString[which].toString());
                                valueResult = false;
                                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(clubArea.getWindowToken(), 0);

                            }
                            dialog.dismiss();
                        }
                    }).create().show();



                }
                return true;
            }
        });

        try {
            JSONObject jObj = new JSONObject(loadJSONFromAsset());
            final JSONArray jArray = jObj.getJSONArray("sprayer_make");
            final ArrayList<HashMap<String, String>> sprayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> spray;
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject spr = jArray.getJSONObject(i);
                String sprName = spr.getString("name");
                spray = new HashMap<>();
                spray.put("name", sprName);
                sprayList.add(spray);
            }
            spray = new HashMap<>();
            spray.put("name","Others");
            sprayList.add(spray);


            final String[] listString1 = new String[sprayList.size()];
            int j = 0;
            for (int index = 0; index < sprayList.size(); index++) {
                HashMap<String, String> map = sprayList.get(index);
                listString1[j++] = map.get("name");
            }

            final ArrayAdapter<String> myAdapternew12= new ArrayAdapter<String>(SprayCalculationActivity.this, android.R.layout.simple_dropdown_item_1line,
                    listString1);

            sprayMake.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        new AlertDialog.Builder(SprayCalculationActivity.this)
                                .setTitle("Select Sprayer Make")
                                .setAdapter(myAdapternew12, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        sprayMake.setError(null);
                                        if (listString1[which].toString().equals("Others")) {
                                            sprayMake.setText("");
                                            sprayModel.setText("");
                                            model=true;
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.showSoftInput(sprayMake, InputMethodManager.SHOW_IMPLICIT);
                                            sprayMake.requestFocus();
                                        } else {
                                            model=false;
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(sprayMake.getWindowToken(), 0);
                                            sprayMake.setText(listString1[which].toString());
                                           sprayModel.setText("");

                                            pos = which;

                                            final List<String> val = new ArrayList<String>();
                                            JSONObject spr = null;
                                            try {
                                                spr = jArray.getJSONObject(which);
                                                JSONArray sprayModelarr = spr.getJSONArray("sprayer_model");
                                                for (int j1 = 0; j1 < sprayModelarr.length(); j1++)

                                                {
                                                    val.add(sprayModelarr.getString(j1));

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            final String[] valArray = val.toArray(new String[val.size()]);

                                            sprayModel.setText(valArray[0]);



                                        }
                                        //Toast.makeText(NewClubActivity.this, ""+pos, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        final List<String> val = new ArrayList<String>();
                                        JSONObject spr = null;
                                        try {
                                            spr = jArray.getJSONObject(which);
                                            JSONArray sprayModelarr = spr.getJSONArray("sprayer_model");
                                            for (int j1 = 0; j1 < sprayModelarr.length(); j1++)

                                            {
                                                val.add(sprayModelarr.getString(j1));

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        final String[] valArray = val.toArray(new String[val.size()]);
                                        final ArrayAdapter<String> myAdapternew1 = new ArrayAdapter<String>(SprayCalculationActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                valArray);
                                        sprayModel.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {

//
                                                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                                    if(model==true)
                                                    {
                                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                        imm.showSoftInput(sprayModel, InputMethodManager.SHOW_IMPLICIT);
                                                        sprayModel.requestFocus();
                                                    }
                                                    else {
                                                        new AlertDialog.Builder(SprayCalculationActivity.this)
                                                                .setTitle("Select Sprayer Model")
                                                                .setAdapter(myAdapternew1, new DialogInterface.OnClickListener() {

                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        sprayModel.setError(null);
                                                                        sprayModel.setText(val.get(which).toString());
                                                                        dialog.dismiss();
                                                                    }

                                                                }).create().show();
                                                    }
                                                    return false;
                                                }
                                                return true;
                                            }
                                        });
                                    }
                                }).create().show();
                    }
                    return false;
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        dateofCalib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
                dateofCalib.setError(null);
//                setDateTimeField();
//                fromDatePickerDialog.show();

               DialogFragment dtp=new  DateTimePicker();
                dtp.show(getFragmentManager(),"Date Picker");
            }
        });
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = Prefrence.edit();
//                editor.putBoolean("in3", true);
//                editor.commit();

                try {

                    JSONObject jObj=new JSONObject(loadJSONFromAsset());

                    final JSONArray jArray;
                    String type = Prefrence.getString("sprayerType","default");
                    Log.d("sree type check", type);
                    if(type.equals("boom")){
                        jArray=jObj.getJSONArray( "name_of_productBoom");
                    }else{
//                calc2=new Intent(SprayCalculationActivity.this,SprayActivity2_knapsack.class);
                        jArray = jObj.getJSONArray("name_of_product");
                    }
                    tempPos=sprayCalc.getInt("tempPosition",1);
                    JSONObject spr = jArray.getJSONObject(tempPos);
                    watervolString = spr.getString("water_volume_range");
                    appRate = spr.getString("application_rate");
                    if (watervolString.contains("-")) {
                        waterVol = Integer.parseInt(watervolString.substring(watervolString.lastIndexOf("-") + 1));
                    } else {
                        waterVol = Integer.parseInt(watervolString);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                editor = Prefrence.edit();
                editor.putBoolean("inres1",true);
                editor.commit();


                if(clubName.getText().toString().equals(""))
                {
                    clubName.setError("Choose your ClubName");
                }
                else if(dateofCalib.getText().toString().equals(""))
                {
                    dateofCalib.setError("Select Date");
                }
                else if(pesticideView.getText().toString().equals(""))
                {
                    pesticideView.setError("Select Pesticide");
                }
                else if(clubArea.getText().toString().equals(""))
                {
                    clubArea.setError("Enter Area");
                }
                else
                {
                    String type = Prefrence.getString("sprayerType","default");
                    Log.d("sree type check", type);
                    Intent calc2;
//                    if(!Prefrence.getBoolean("firstAccept",false)){
                    if(type.equals("boom")){
                         calc2=new Intent(SprayCalculationActivity.this,BoomStep2Activity.class);
                    }else{
                         calc2=new Intent(SprayCalculationActivity.this,SprayActivity2_knapsack.class);
                    }

//                    Intent calc2 = new Intent(getParent(), SprayActivity2_knapsack.class);
//                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                  //  if (android.os.Build.VERSION.SDK_INT <= 10) {

                        String areaNameTxt = clubArea.getText().toString();
                        String clubNameTxt=clubName.getText().toString();
                        String  dateTxt=dateofCalib.getText().toString();

                        String sprmke=sprayMake.getText().toString();
                        String sprmodel=sprayModel.getText().toString();
                        String pestici=pesticideView.getText().toString();

                        final DatabaseHandler db1 = new DatabaseHandler(getApplicationContext());
                        List<String> areaVal=db1.selectAreaName(clubName.getText().toString());
                        if(areaVal.contains(areaNameTxt)&&valueResult==true)
                        {
                            clubArea.setError("Area Name Already Available");
                        }
                        else {

                            editor = sprayCalc.edit();
                            editor.putString("golfName", clubNameTxt);
                            editor.putString("areaName", areaNameTxt);
                            editor.putString("dateofcalibration", dateTxt);
                            editor.putString("sprmake",sprmke);
                            editor.putString("sprmodel",sprmodel);
                            editor.putString("pes",pestici);
                            editor.putInt("prewaterVol", waterVol);
                            editor.putString("appRateknap", String.valueOf(appRate));
                            editor.commit();

                            startActivity(calc2);

                           // parentActivity.startChildActivity("calcac", calc2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                       // }


                    }
                }
            }
        });


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("sprayer_config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static class DateTimePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar= Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);
            return datePickerDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView ed=(TextView) getActivity().findViewById(R.id.date);
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate=cal.getTime();
            DateFormat df=new SimpleDateFormat("dd-MMM-yyyy");
           // DateFormat df=DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String formatedDate=df.format(chosenDate);
            ed.setText(formatedDate);

        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(clubArea.getWindowToken(), 0);


        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(SprayCalculationActivity.this,Choose_CalcActivity.class);
        startActivity(i);
    }
}