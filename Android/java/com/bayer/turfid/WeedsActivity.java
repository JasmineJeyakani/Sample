package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeedsActivity extends Activity {

    private TextView titleTxt;
    private Button btnGrassWeed;
    private Button btnBroadWeed;
    private Button btnWeedIdentifier;
    ArrayList<TIWeed> weedList = new ArrayList<TIWeed>();
    private String type;
    private String title;
    private ImageView imgLogo;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Window window=getWindow();
//        window.setWindowAnimations(R.anim.grow_from_bottomleft_to_topright);

//        View currentView =getWindow().getDecorView().getRootView();
//        currentView.setAnimation(outToLeftAnimation());
        setContentView(R.layout.weeds);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString(this.getPackageName() + ".type");
            title = extras.getString(this.getPackageName() + ".title");
        }

        titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText(title);

        btnGrassWeed = (Button) findViewById(R.id.grassWeed);
        if (type.equals("Weeds")) {
            btnGrassWeed.setText("Grass Weeds A-Z");
        } else if (type.equals("Diseases")) {
            btnGrassWeed.setText("Diseases A-Z");
        }

        btnGrassWeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent weedsListActivity = new Intent(getParent(), TurfListActivity.class).addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);//FLAG_ACTIVITY_CLEAR_TOP);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (type.equals("Weeds")) {
                    weedsListActivity.putExtra(getPackageName() + ".type", "GrassWeed");
                } else if (type.equals("Diseases")) {
                    weedsListActivity.putExtra(getPackageName() + ".type", "Diseases A-Z");
                }
                weedsListActivity.putExtra(getPackageName() + ".title", btnGrassWeed.getText());
                if (android.os.Build.VERSION.SDK_INT <= 10) {
                    parentActivity.startChildActivity("WeedsListActivity", weedsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else {
                    parentActivity.startChildActivity("WeedsListActivity", weedsListActivity);
                }
            }
        });

        btnBroadWeed = (Button) findViewById(R.id.broadWeed);
        if (type.equals("Weeds")) {
            btnBroadWeed.setText("Broad Leaved Weeds A-Z");
            btnBroadWeed.setBackgroundResource(R.drawable.table_center);
        } else if (type.equals("Diseases")) {
            btnBroadWeed.setText("Disease Identifier");
            btnBroadWeed.setBackgroundResource(R.drawable.table_bottom);
        }
        btnBroadWeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (type.equals("Weeds")) {
                    Intent weedsListActivity = new Intent(getParent(), TurfListActivity.class);
                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    weedsListActivity.putExtra(getPackageName() + ".type", "BroadLeaved");
                    weedsListActivity.putExtra(getPackageName() + ".title", btnBroadWeed.getText());
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("WeedsListActivity", weedsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("WeedsListActivity", weedsListActivity);
                    }
                } else if (type.equals("Diseases")) {
                    Intent diseasesListActivity = new Intent(getParent(), GeneralSymptomsActivity.class);
                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("GeneralSymptomsActivity", diseasesListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("GeneralSymptomsActivity", diseasesListActivity);
                    }
                }
            }
        });

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeedsActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        btnWeedIdentifier = (Button) findViewById(R.id.identifier);
        if (type.equals("Weeds")) {
            btnWeedIdentifier.setText("Weed Identifier");
        } else if (type.equals("Diseases")) {
            btnWeedIdentifier.setVisibility(View.GONE);
        }
        btnWeedIdentifier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent weedsListActivity = new Intent(getParent(), LeafIdentifierActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("LeafIdentifierActivity", weedsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("LeafIdentifierActivity", weedsListActivity);
            }
        });
    }


    public Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

        }
        return super.dispatchKeyEvent(event);
    }
}
