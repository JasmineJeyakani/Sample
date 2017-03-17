package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DecimalFormat;

public class SprayActivity_Result2 extends Activity {
    Button skipResult2;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double nozzlePressure;
    Double nozzleOutput;
    Double nozzleSpacing;
    String speedCalc;
    double waterVolumeCalc;
    TextView result2;
    DecimalFormat df = new DecimalFormat("####0.00");
    ImageView backArrow, imgLogo;
    TextView result2txtview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result2);
        result2 = (TextView) findViewById(R.id.resultlha);
        backArrow = (ImageView) findViewById(R.id.leftarrowknapsack);
        result2txtview1 = (TextView) findViewById(R.id.result2txtview);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        nozzlePressure = Double.valueOf(Prefrence.getString("correctNozzleVal", String.valueOf(0)));
        nozzleOutput = Double.valueOf(Prefrence.getString("actualOutputVal", String.valueOf(0)));
        nozzleSpacing = Double.valueOf(Prefrence.getString("NozzleDistanceVal", String.valueOf(0)));
        speedCalc = String.valueOf(Prefrence.getString("speedCalc", String.valueOf(0)));


        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity_Result2.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        String type = Prefrence.getString("sprayerType", "default");

        if (type.equals("boom")) {
            result2txtview1.setText(" If your actual volume of water is within the limits set by the product label, your sprayer is correctly calibrated.");
        }

        waterVolumeCalc = Double.valueOf(Prefrence.getString("waterVolumeCalc1", String.valueOf(0)));
        Log.d("res2", String.valueOf(waterVolumeCalc));
        result2.setText(String.valueOf(df.format(waterVolumeCalc)) + " L/Ha");
        editor = Prefrence.edit();
        editor.putString("waterVolumeCalc1", String.valueOf(waterVolumeCalc));
        editor.commit();
        skipResult2 = (Button) findViewById(R.id.skipresult2);
        skipResult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent step8Sprayertank = new Intent(SprayActivity_Result2.this, SprayActivity8_Sprayertank.class);
                startActivity(step8Sprayertank);
            }
        });
    }

    @Override
    public void onBackPressed() {
        String type = Prefrence.getString("sprayerType", "default");
        Intent step8Sprayertank;
        if (type.equals("boom")) {
            step8Sprayertank = new Intent(SprayActivity_Result2.this, BoomStep9Activity.class);
        } else {
            step8Sprayertank = new Intent(SprayActivity_Result2.this, SprayActivity7_TotalOutput.class);
        }
        editor = Prefrence.edit();
        editor.putBoolean("in6", false);
        editor.commit();
        startActivity(step8Sprayertank);
    }
}
