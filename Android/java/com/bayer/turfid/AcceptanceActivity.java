package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AcceptanceActivity extends Activity {

    protected static final String TAG = null;
    private Button submit;
    private WebView contactView;
    private TextView textView;
    public static final String PREFS_NAME = "MyPrefsFile";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.acceptance);
        TextView txtView = (TextView) findViewById(R.id.title);
        txtView.setMovementMethod(new ScrollingMovementMethod());
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        textView = (TextView) findViewById(R.id.title);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Log.i(TAG, "dfggggb");
                Intent intent = new Intent(AcceptanceActivity.this, Accept_ChoiceActivity.class);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstTime", true);
                editor.commit();
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.insertSprayTablle("12-Dec-2012", "12/12/2012", "example record", "golf", "1.0", "19.0", "20.0", "1000", "0.0", "0.0", "0.0", "0.0", "1.0");
                startActivity(intent);

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1) {
            SharedPreferences settings = getSharedPreferences("prefs", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", true);
            editor.commit();
        }
    }


}
