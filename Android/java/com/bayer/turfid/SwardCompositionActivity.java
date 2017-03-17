package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class SwardCompositionActivity extends Activity {

    private TextView titleTxt;
    private Button submit;
    private String diseaseList[];
    private String diseaseIDList[];
    private String general;
    private String foliar;
    private String weather;
    private ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weed_identifier);

        diseaseList = new String[]{"Annual meadow grasses", "Annual meadow grasses and Bentgrass", "Bentgrass", "Fescue", "Ryegrass", "Not known"};
        diseaseIDList = new String[]{"17", "18", "19", "20", "21", "0"};
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwardCompositionActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            general = extras.getString(this.getPackageName() + ".general");
            foliar = extras.getString(this.getPackageName() + ".foliar");
            // 04_OCT_12
            // Remove Weather condition from Disease ID
            // weather = extras.getString(this.getPackageName()+".weather");
        }

        titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText("Sward composition");

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new LeafAdapter(this, diseaseList));
        itemList.setCurrentItem(0);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent diseasesListActivity = new Intent(getParent(), TurfListActivity.class);
                diseasesListActivity.putExtra(getPackageName() + ".type", "Disease Identifier");
                diseasesListActivity.putExtra(getPackageName() + ".title", "Diseases");
                diseasesListActivity.putExtra(getPackageName() + ".general", general);
                diseasesListActivity.putExtra(getPackageName() + ".foliar", foliar);

                // 04_OCT_12
                // Remove Weather condition from Disease ID
                //diseasesListActivity.putExtra(getPackageName()+".weather", weather);

                diseasesListActivity.putExtra(getPackageName() + ".sward", diseaseIDList[itemList.getCurrentItem()]);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("TurfListActivity", diseasesListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("TurfListActivity", diseasesListActivity);
//				Log.d("tag","parentActivity.startChildActivity");
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
