package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SprayActivity_Result3 extends Activity {

    Button continueResult3Button;
    Button reenterResult3Button;
    TextView adjustedSwath;
    TextView adjustedSpeed;
    SharedPreferences Prefrence;
    Double nozzleOutput1;
    Double speedCalc;
    Double waterVolume1;
    Double nozzleSpacing;
    Double adjustedSwathVal;
    Double adjustedSpeedVal;
    DecimalFormat df = new DecimalFormat("####0.00");
    ImageView backArrow, imgLogo;
    TextView res;
    SharedPreferences.Editor editor;
    double waterVolumeCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result3);
        continueResult3Button = (Button) findViewById(R.id.continueresult3Btn);
        reenterResult3Button = (Button) findViewById(R.id.reenterresult3Btn);
        adjustedSwath = (TextView) findViewById(R.id.metersresult);
        adjustedSpeed = (TextView) findViewById(R.id.adjustedSpeedval);
        res = (TextView) findViewById(R.id.resultlha);

        backArrow = (ImageView) findViewById(R.id.leftproduct);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        nozzleOutput1 = Double.valueOf(Prefrence.getString("actualOutputVal", String.valueOf(0)));
        speedCalc = Double.valueOf(Prefrence.getString("speedCalc", String.valueOf(0)));
        waterVolume1 = Double.valueOf(Prefrence.getString("ProductLabelVal", String.valueOf(0)));
        nozzleSpacing = Double.valueOf(Prefrence.getString("NozzleDistanceVal", String.valueOf(0)));
        waterVolumeCalc = Double.parseDouble(Prefrence.getString("waterVolumeCalc1", String.valueOf(0)));
        res.setText(String.valueOf(df.format(waterVolumeCalc)));
        adjustedSwathVal = 600 * (((nozzleOutput1 / speedCalc) / waterVolume1));
        adjustedSpeedVal = 600 * (((nozzleOutput1 / nozzleSpacing) / waterVolume1));
        adjustedSwath.setText(Float.toString(Float.valueOf(df.format(adjustedSwathVal))));
        adjustedSpeed.setText(Float.toString(Float.valueOf(df.format(adjustedSpeedVal))));
        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity_Result3.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        reenterResult3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sprayerTank1 = new Intent(SprayActivity_Result3.this, SprayActivity2_knapsack.class);
                editor = Prefrence.edit();
                editor.putBoolean("in3", true);
                editor.putBoolean("in4", true);
                editor.putBoolean("in5", true);
                editor.putBoolean("in6", true);
                editor.putBoolean("in7", true);
                editor.commit();

                startActivity(sprayerTank1);
            }
        });
        continueResult3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = Prefrence.edit();
                Intent sprayerTank = new Intent(SprayActivity_Result3.this, SprayActivity8_Sprayertank.class);
                startActivity(sprayerTank);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent step8Sprayertank = new Intent(SprayActivity_Result3.this, SprayActivity7_TotalOutput.class);
        editor = Prefrence.edit();
        editor.putBoolean("in6", false);
        editor.commit();
        startActivity(step8Sprayertank);
    }
}
