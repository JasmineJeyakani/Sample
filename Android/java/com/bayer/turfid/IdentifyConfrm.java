package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Jasmine Jeyakani on 8/26/2016.
 */
public class IdentifyConfrm extends Activity {
    Button ok;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify1);

        imgLogo = (ImageView) findViewById(R.id.img_logo);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IdentifyConfrm.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IdentifyConfrm.this, BayerIdentify.class);
                startActivity(i);
//


            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(IdentifyConfrm.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
