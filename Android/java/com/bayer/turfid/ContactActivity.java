package com.bayer.turfid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

public class ContactActivity extends Activity {

    private WebView contactView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.contact);

        contactView = (WebView) findViewById(R.id.contactView);
        contactView.getSettings().setJavaScriptEnabled(true);
        contactView.getBackground().setAlpha(130);
        contactView.loadUrl("file:///android_asset/contactus.html");
        contactView.setBackgroundColor(Color.TRANSPARENT);
    }
}
