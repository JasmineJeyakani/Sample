package com.bayer.turfid;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Admin on 20-Dec-16.
 */

public class AboutUsGroupActivity extends TabGroupActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("DistributorActivity", new Intent(this, AboutUsActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AboutUsGroupActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
