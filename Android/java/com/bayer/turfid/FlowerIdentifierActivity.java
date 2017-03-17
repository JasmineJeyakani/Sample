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

public class FlowerIdentifierActivity extends Activity {

    private TextView titleTxt;
    private Button submit;
    private String weedList[];
    private String weedIDList[];
    private String leaf;
    private ImageView imgLogo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weed_identifier);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlowerIdentifierActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        weedList = new String[]{"Red", "Yellow", "Pink", "Green", "Blue", "White", "Purple", "Not known"};
        weedIDList = new String[]{"7", "8", "9", "10", "11", "12", "13", "0"};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            leaf = extras.getString(this.getPackageName() + ".leaf");
        }

        titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText("Flower Colour ID");

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new FlowerAdapter(this, weedList));
        itemList.setCurrentItem(0);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent weedsListActivity = new Intent(getParent(), TurfListActivity.class);
                weedsListActivity.putExtra(getPackageName() + ".type", "WeedIdentifier");
                weedsListActivity.putExtra(getPackageName() + ".title", "Weeds");
                weedsListActivity.putExtra(getPackageName() + ".leaf", leaf);
                weedsListActivity.putExtra(getPackageName() + ".flower", weedIDList[itemList.getCurrentItem()]);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("TurfListActivity", weedsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("TurfListActivity", weedsListActivity);
            }
        });
    }

    private class FlowerAdapter extends AbstractWheelTextAdapter {

        private String weedList[];

        /**
         * Constructor
         */
        protected FlowerAdapter(Context context, String[] list) {
            super(context, R.layout.weed_identifier_list, NO_RESOURCE);

            this.weedList = list;
            setItemTextResource(R.id.itmTxt);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            TextView itemTxt = (TextView) view.findViewById(R.id.itmTxt);
            itemTxt.setText(weedList[index]);
            return view;
        }

        @Override
        public int getItemsCount() {
            return weedList.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return weedList[index];
        }
    }
}
