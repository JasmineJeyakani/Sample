package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CulturalActivity extends Activity {

    private String cultural;
    private String title;
    ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cultural);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CulturalActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cultural = extras.getString(this.getPackageName() + ".cultural");
            title = extras.getString(this.getPackageName() + ".title");

        }
        if (cultural == null) {
            Log.d("tag", "CulturalActivity null");

        } else if (title == null) {
            Log.d("tag", "title null");


        }

        TextView tv = (TextView) findViewById(R.id.culturalDesc);
        tv.setText(Html.fromHtml(cultural));

        TextView titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText(title);
    }

}
