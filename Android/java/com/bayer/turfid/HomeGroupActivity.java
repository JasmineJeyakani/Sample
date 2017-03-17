package com.bayer.turfid;

import android.content.Intent;
import android.os.Bundle;

public class HomeGroupActivity extends TabGroupActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("HomeActivity", new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }


}
