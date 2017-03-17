package com.bayer.turfid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class FirstActivity extends Activity {

    protected static final String TAG = null;
    public static final String PREFS_NAME = "MyPrefsFile";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.acceptance);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            Intent intent = new Intent(FirstActivity.this, AcceptanceActivity.class);
            startActivity(intent);


        } else {
            Intent intent = new Intent(FirstActivity.this, BayerTurfManagementActivity.class);
            startActivity(intent);
        }

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(FirstActivity.this)
                .setMessage(message)
                .setPositiveButton("Allow", okListener)
                .create()
                .show();
    }

}
