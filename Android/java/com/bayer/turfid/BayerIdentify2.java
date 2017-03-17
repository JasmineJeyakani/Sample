package com.bayer.turfid;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BayerIdentify2 extends TabActivity {
    private Integer setCurrTab = 1;
    SharedPreferences Prefrence;
    public TabWidget tabWidget;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);

        tabWidget = getTabWidget();
        tabWidget.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setCurrTab = extras.getInt(this.getPackageName() + ".tab");
        }

        final TabHost tabHost = getTabHost();
        tabHost.setBackgroundColor(Color.LTGRAY);
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, MyClubsActivity.class);

        View searchView = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView searchImgView = (ImageView) searchView.findViewById(R.id.TabImageView);
        TextView searchTxtView = (TextView) searchView.findViewById(R.id.TabTextView);
        searchImgView.setImageResource(R.drawable.settings_copy);
        searchTxtView.setText("My Clubs");

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("clubs").setIndicator(searchView).setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, IdentifyGroupActivity.class);

        View identifyView = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView identifyImgView = (ImageView) identifyView.findViewById(R.id.TabImageView);
        TextView identifyTxtView = (TextView) identifyView.findViewById(R.id.TabTextView);
        identifyImgView.setImageResource(R.drawable.identify_copy);
        identifyTxtView.setText("Identify");

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("identify").setIndicator(identifyView).setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, HomeGroupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        View homeView = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView homeImgView = (ImageView) homeView.findViewById(R.id.TabImageView);
        TextView homeTxtView = (TextView) homeView.findViewById(R.id.TabTextView);
        homeImgView.setImageResource(R.drawable.home_copy);
        homeTxtView.setText("Home");
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("home").setIndicator(homeView).setContent(intent);
        tabHost.addTab(spec);
        //intent = new Intent().setClass(this, ContactActivity.class);
        intent = new Intent().setClass(this, AboutUsGroupActivity.class);

        View contactView = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView contactImgView = (ImageView) contactView.findViewById(R.id.TabImageView);
        TextView contactTxtView = (TextView) contactView.findViewById(R.id.TabTextView);
        contactImgView.setImageResource(R.drawable.aboutus_copy);
        contactTxtView.setText("About Us");

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("aboutus").setIndicator(contactView).setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, DistributorGroupActivity.class);

        View distView = LayoutInflater.from(this).inflate(R.layout.tabs, null);
        ImageView distImgView = (ImageView) distView.findViewById(R.id.TabImageView);
        TextView distTxtView = (TextView) distView.findViewById(R.id.TabTextView);
        distImgView.setImageResource(R.drawable.distributors_copy);
        distTxtView.setText("Distributors");

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("distributor").setIndicator(distView).setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(setCurrTab);
        //  tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.LTGRAY);
//        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setAlpha(50);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.WHITE);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setAlpha(50);

        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                //tabHost.setAnimation(inFromRightAnimation());
                setTabColor(tabHost);
            }
        });

        int numberOfTabs = tabHost.getTabWidget().getChildCount();
        for (int t = 0; t < numberOfTabs; t++) {
            tabHost.getTabWidget().getChildAt(t).setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {


                    // use getTabHost().getCurrentTabView to decide if the current tab is
                    // touched again
                    if (event.getAction() == MotionEvent.ACTION_DOWN
                            && v.equals(getTabHost().getCurrentTabView())) {
                        // use getTabHost().getCurrentView() to get a handle to the view
                        // which is displayed in the tab - and to get this views context
                        Intent intent = null;
                        Integer currentTab;
                        currentTab = getTabHost().getCurrentTab();
                        if (currentTab == 2) {
                            intent = new Intent(getBaseContext(), BayerTurfManagementActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            getLocalActivityManager().removeAllActivities();
                            getCurrentActivity().finish();
                            intent.putExtra(getPackageName() + ".tab", currentTab);
                            startActivity(intent);
                            getTabHost().setCurrentTab(currentTab);
                        }
                    }
                    return false;
                }
            });
        }
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("TurfId.db");
            File databaseDir = new File("/data/data/" + this.getPackageName() + "/databases/");
            databaseDir.mkdir();
            String newFileName = "/data/data/" + this.getPackageName() + "/databases/" + "TurfId.db";
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }


    public Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(1000);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
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

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

    public void setTabColor(TabHost tabhost) {
        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.LTGRAY); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.WHITE); // selected
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).getBackground().setAlpha(50);

    }


}

