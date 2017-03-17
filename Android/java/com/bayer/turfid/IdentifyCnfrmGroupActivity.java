package com.bayer.turfid;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jasmine Jeyakani on 04-Oct-16.
 */

public class IdentifyCnfrmGroupActivity extends TabGroupActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("IdentifyCnfrmAct", new Intent(this, SampIdentify.class));
    }
}
