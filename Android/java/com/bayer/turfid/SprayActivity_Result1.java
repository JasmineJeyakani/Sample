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

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SprayActivity_Result1 extends Activity {
    Button resultCOntinuBtn;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Double knapSack;
    Double productLabel;
    Double nozzleSpacing;
    Double speedCalcA;
    Double speedCalc;
    Double TargetNozzleop;
    TextView result1Val;
    ImageView imgLogo;
    ImageView backArrow;
    DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spray_activity__result1);
        resultCOntinuBtn = (Button) findViewById(R.id.resultcontinuebtn);
        result1Val = (TextView) findViewById(R.id.result1Val);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SprayActivity_Result1.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        backArrow = (ImageView) findViewById(R.id.leftproduct);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        knapSack = Double.valueOf(Prefrence.getString("knapSackSecVal", String.valueOf(0)));
        productLabel = Double.valueOf(Prefrence.getString("ProductLabelVal", String.valueOf(0)));
        nozzleSpacing = Double.valueOf(Prefrence.getString("NozzleDistanceVal", String.valueOf(0)));
        speedCalcA = Double.valueOf(new BigDecimal(10 / knapSack).toString());
        speedCalc = Double.valueOf(((3.6) * speedCalcA));
        TargetNozzleop = ((productLabel * speedCalc) * nozzleSpacing) / 600;

        String type = Prefrence.getString("sprayerType", "default");

        Double speedCalcADouble;
        final Double speedCalcDouble;
        Double TargetNozzleopDouble;


        if (type.equals("boom")) {
            CustomTextViewBold t = (CustomTextViewBold) findViewById(R.id.step5);
            t.setText(R.string.boom_step_4);

            if (knapSack != 0) {
                speedCalcADouble = Double.valueOf(new BigDecimal(100 / knapSack).toString());
                speedCalcDouble = ((3.6) * speedCalcADouble);
            } else {
                speedCalcDouble = 0.0;
            }
            TargetNozzleopDouble = Double.valueOf(((productLabel * speedCalcDouble) * nozzleSpacing) / 600);

        } else {
            speedCalcADouble = Double.valueOf(new BigDecimal(10 / knapSack).toString());
            speedCalcDouble = ((3.6) * speedCalcADouble);
            TargetNozzleopDouble = Double.valueOf(((productLabel * speedCalcDouble) * nozzleSpacing) / 600);
        }

        result1Val.setText(String.valueOf(df.format(TargetNozzleopDouble)));

        Log.d("speedcalc", String.valueOf(speedCalc));
        Log.d("speedCalcA", String.valueOf(speedCalcA));
        result1Val.setText(String.valueOf(df.format(TargetNozzleopDouble)));

        Log.d("res", String.valueOf(df.format(TargetNozzleopDouble)));
        resultCOntinuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent correctNozzle = new Intent(SprayActivity_Result1.this, SprayActivity5_correctnozzle.class);

                editor = Prefrence.edit();
                editor.putString("speedCalc", String.valueOf(speedCalcDouble));
                editor.commit();
                startActivity(correctNozzle);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SprayActivity_Result1.this, SprayActivity4_holdingspray.class);
        editor = Prefrence.edit();
        editor.putBoolean("in4", false);
        editor.commit();
        startActivity(i);
    }
}
