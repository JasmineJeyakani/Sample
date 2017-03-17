package com.bayer.turfid;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jasmine Jeyakani on 9/1/2016.
 */
public class IdentifyGroupActivity extends TabGroupActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("IdentifyCnfrm", new Intent(this, IdentifyConfrm.class));
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(IdentifyGroupActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
