package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SprayActivity6_CalibratedJug extends Activity {
Button continue6calibrate;
    ImageView backArrow,imgLogo;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    TextView productlabel1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity6__calibrated_jug);
        backArrow=(ImageView)findViewById(R.id.leftarrowknapsack);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        continue6calibrate=(Button)findViewById(R.id.calibratedjugcontinue);
        imgLogo=(ImageView)findViewById(R.id.img_logo);
        productlabel1=(TextView)findViewById(R.id.productlabel);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity6_CalibratedJug.this, BayerTurfManagementActivity.class);
                startActivity (intent);
            }
        });
        Prefrence=getApplicationContext().getSharedPreferences("SprayCalc",MODE_PRIVATE);
        String type = Prefrence.getString("sprayerType","default");
        if(type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step3);
            t.setText(R.string.boom_step_6);
            String sourceString = " Using a calibrated jug and stopwatch," +
                    "        check the output from a minimum of four" +
                    "        nozzles and at least one from each boom section." +

                    "        <br>If time permits, check the output from all nozzles." +

                    "       <br><br> <b>Keep a note of the actual<br></b>" +
                    "            nozzle output";
            productlabel1.setText(Html.fromHtml(sourceString));

        }
        continue6calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = Prefrence.getString("sprayerType","default");
                Intent totalOutput;
                if(type.equals("boom")){
                    totalOutput = new Intent(SprayActivity6_CalibratedJug.this, BoomStep7Activity.class);
                }
                else {
                     totalOutput = new Intent(SprayActivity6_CalibratedJug.this, SprayActivity7_TotalOutput.class);
                }
                startActivity(totalOutput);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(SprayActivity6_CalibratedJug.this,SprayActivity5_correctnozzle.class);
        editor = Prefrence.edit();
        editor.putBoolean("in5",false);
        editor.commit();
        startActivity(i);
    }
}
