package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accept_ChoiceActivity extends Activity {
    Button yesBtn, noBtn;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept__choice);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        noBtn = (Button) findViewById(R.id.noBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accept_ChoiceActivity.this, NewClubActivity.class);
                startActivity(i);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Accept_ChoiceActivity.this, BayerTurfManagementActivity.class);
                Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
                editor = Prefrence.edit();
                editor.putBoolean("firstAccept", false);
                startActivity(i);
            }
        });
    }
}
