package com.bayer.turfid;

import android.content.Intent;
import android.os.Bundle;

public class DistributorGroupActivity extends TabGroupActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("DistributorActivity", new Intent(this, DistributorActivity.class));
    }

    @Override
    public void onBackPressed() {

        Intent i=new Intent(DistributorGroupActivity.this,BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
