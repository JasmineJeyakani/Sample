package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class WeatherConditionsActivity extends Activity {

    private TextView titleTxt;
    private ImageButton submit;
    private String diseaseList[];
    private String diseaseIDList[];
    private String general;
    private String foliar;
    private ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weed_identifier);

        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeatherConditionsActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        diseaseList = new String[]{"Cool,humid/Wet weather", "Warm/humid", "Mild", "Not known"};
        diseaseIDList = new String[]{"14", "15", "16", "0"};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            general = extras.getString(this.getPackageName() + ".general");
            foliar = extras.getString(this.getPackageName() + ".foliar");
        }

        titleTxt = (TextView) findViewById(R.id.title);
        titleTxt.setText("Weather conditions");

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new LeafAdapter(this, diseaseList));
        itemList.setCurrentItem(0);

        submit = (ImageButton) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent diseasesListActivity = new Intent(getParent(), SwardCompositionActivity.class);
                diseasesListActivity.putExtra(getPackageName() + ".general", general);
                diseasesListActivity.putExtra(getPackageName() + ".foliar", foliar);
                diseasesListActivity.putExtra(getPackageName() + ".weather", diseaseIDList[itemList.getCurrentItem()]);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("SwardCompositionActivity", diseasesListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("SwardCompositionActivity", diseasesListActivity);
            }
        });

    }

    private class LeafAdapter extends AbstractWheelTextAdapter {

        private String diseaseList[];

        /**
         * Constructor
         */
        protected LeafAdapter(Context context, String[] list) {
            super(context, R.layout.weed_identifier_list, NO_RESOURCE);

            this.diseaseList = list;
            setItemTextResource(R.id.itmTxt);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            TextView itemTxt = (TextView) view.findViewById(R.id.itmTxt);
            itemTxt.setText(diseaseList[index]);
            return view;
        }

        @Override
        public int getItemsCount() {
            return diseaseList.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return diseaseList[index];
        }
    }
}
