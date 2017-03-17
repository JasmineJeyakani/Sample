package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Admin on 16-Jan-17.
 */

public class Choose_CalcActivity extends Activity {
    Button knapsack, vehicle;
    ImageView bayer_logo;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_calc);
        knapsack = (Button) findViewById(R.id.knapSack);
        vehicle = (Button) findViewById(R.id.boom);
        bayer_logo = (ImageView) findViewById(R.id.logo_img);
        bayer_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose_CalcActivity.this, BayerTurfManagementActivity.class);
                startActivity(i);
            }
        });
        knapsack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose_CalcActivity.this, SprayCalculationActivity.class);
                Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
                editor = Prefrence.edit();
                editor.putString("sprayerType", "knap");
                editor.putBoolean("in3", true);
                editor.putBoolean("in3", true);
                editor.putBoolean("in4", true);
                editor.putBoolean("in5", true);
                editor.putBoolean("in6", true);
                editor.putBoolean("in7", true);
                editor.putBoolean("in11", true);
                editor.putBoolean("inres1", true);
                editor.commit();


                startActivity(i);
            }
        });
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose_CalcActivity.this, SprayCalculationActivity.class);
                Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
                editor = Prefrence.edit();
                editor.putString("sprayerType", "boom");
                editor.putBoolean("in3", true);
                editor.putBoolean("in3", true);
                editor.putBoolean("in4", true);
                editor.putBoolean("in5", true);
                editor.putBoolean("in6", true);
                editor.putBoolean("in7", true);
                editor.putBoolean("in11", true);
                editor.putBoolean("inres1", true);
                editor.commit();
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Choose_CalcActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
