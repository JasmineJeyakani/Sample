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
import android.widget.Toast;

import java.text.DecimalFormat;

public class SprayActivity_Result4 extends Activity {
    Button result4Btn;
    TextView totalPesticide;
    TextView numberOfTanks;
    Double tankCapacityCalc;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double waterVolumeCalc;
    Double tankCapacity;
    Double area;
    Double numberroftanks;
    double totalPesticideVal;
    Double doseRate;
    Double pesticidePerTank;
    DecimalFormat df = new DecimalFormat("####0.00");
    // double val1=0.43758875;
    DecimalFormat df2 = new DecimalFormat("####0.00");
    String nooftanks;
    ImageView backArrow, imgLogo;
    String clubName, areaName, areaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result4);
        backArrow = (ImageView) findViewById(R.id.leftproduct);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        df2.setMaximumFractionDigits(8);
        result4Btn = (Button) findViewById(R.id.result4Btn);
        totalPesticide = (TextView) findViewById(R.id.literstxt);
        numberOfTanks = (TextView) findViewById(R.id.tanksTxt);
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);

        editor = Prefrence.edit();
        editor.putBoolean("in3", false);
        editor.commit();

        waterVolumeCalc = Double.valueOf(Prefrence.getString("waterVolumeCalc1", String.valueOf(0)));
        tankCapacity = Double.valueOf(Prefrence.getString("sprayTankValue1", String.valueOf(0)));
        area = Double.valueOf(Prefrence.getString("areaVal", String.valueOf(0)));
        doseRate = Double.valueOf(Prefrence.getString("productRateVal", String.valueOf(0)));
        clubName = Prefrence.getString("golfName", "gf");
        areaName = Prefrence.getString("areaName", "an");
        areaValue = String.valueOf(area);

        tankCapacityCalc = waterVolumeCalc / tankCapacity;

        pesticidePerTank = doseRate / tankCapacityCalc;
        Log.d("sree doseRate", String.valueOf(doseRate));
        Log.d("sree tankCapCal", String.valueOf(tankCapacityCalc));

        Log.d("sree pesticidePerTank", String.valueOf(pesticidePerTank));

        numberroftanks = tankCapacityCalc * (area / 10000);
        totalPesticideVal = numberroftanks * pesticidePerTank;
        numberOfTanks.setText(df.format(numberroftanks));
        nooftanks = numberOfTanks.getText().toString();

        DatabaseHandler db1 = new DatabaseHandler(getApplicationContext());
        db1.insertAreaTable(clubName, areaName, areaValue);

        imgLogo = (ImageView) findViewById(R.id.logo_img);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity_Result4.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        String type = Prefrence.getString("sprayerType", "default");
        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step2);
            t.setText(R.string.boom_step_13);
        }
        totalPesticide.setText(Double.toString(Double.parseDouble(df.format(totalPesticideVal))));
        Log.d("res4", String.valueOf(totalPesticideVal));
        Log.d("res41", String.valueOf(numberroftanks));
        result4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result5 = new Intent(SprayActivity_Result4.this, SprayActivity_Result5.class);
                editor = Prefrence.edit();
                editor.putString("TQpesticide", String.valueOf(totalPesticideVal));
                editor.putString("numberOfTanks", String.valueOf(numberroftanks));
                editor.putString("pesticidePerTank", String.valueOf(pesticidePerTank));
                editor.putString("nof", nooftanks);
                editor.commit();
                startActivity(result5);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent step8Sprayertank = new Intent(SprayActivity_Result4.this, SprayActivity12_Areamap.class);
        editor = Prefrence.edit();
        editor.putBoolean("in8", false);
        editor.commit();
        startActivity(step8Sprayertank);
    }
}
