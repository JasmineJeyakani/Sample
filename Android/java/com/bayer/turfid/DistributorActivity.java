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

public class DistributorActivity extends Activity {

    private Button submit;
    private String distributorList[];
    ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.distributor_identifier);
        distributorList = new String[]{"England & Wales", "N. Ireland", "Republic of Ireland", "Scotland", "All"};

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new DistributorAdapter(this, distributorList));
        itemList.setCurrentItem(0);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DistributorActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent distributorsListActivity = new Intent(getParent(), TurfListActivity.class);
                distributorsListActivity.putExtra(getPackageName() + ".type", "Distributors");
                distributorsListActivity.putExtra(getPackageName() + ".title", "Distributors");
                distributorsListActivity.putExtra(getPackageName() + ".country", distributorList[itemList.getCurrentItem()]);

                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("TurfListActivity", distributorsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("TurfListActivity", distributorsListActivity);
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(DistributorActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }

    private class DistributorAdapter extends AbstractWheelTextAdapter {

        private String distributorList[];

        /**
         * Constructor
         */
        protected DistributorAdapter(Context context, String[] list) {
            super(context, R.layout.distributors, NO_RESOURCE);

            this.distributorList = list;
            setItemTextResource(R.id.distributorTxt);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            TextView itemTxt = (TextView) view.findViewById(R.id.distributorTxt);
            itemTxt.setText(distributorList[index]);
            return view;
        }

        @Override
        public int getItemsCount() {
            return distributorList.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return distributorList[index];
        }
    }
}
