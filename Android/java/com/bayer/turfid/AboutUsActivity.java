package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class AboutUsActivity extends Activity {

    private WebView aboutView;
    private ImageView imgLogo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        aboutView = (WebView) findViewById(R.id.aboutView);
        aboutView.getSettings().setJavaScriptEnabled(true);
        aboutView.getBackground().setAlpha(130);
        aboutView.loadUrl("file:///android_asset/about.html");
        aboutView.setBackgroundColor(Color.parseColor("#EBEBEB"));
        // Here, thisActivity is the current activity

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AboutUsActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }

}