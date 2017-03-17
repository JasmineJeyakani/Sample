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

public class LeafIdentifierActivity extends Activity {

    private TextView titleTxt;
    private Button submit;
    private String weedList[];
    private String weedIDList[];
    private ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weed_identifier);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeafIdentifierActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        weedList = new String[]{"Oblong or Oval", "Round", "Needle", "Wavy Edged", "Fern/feathery", "Trifoliate", "Not known"};
        weedIDList = new String[]{"1", "2", "3", "4", "5", "6", "0"};

        titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText("Leaf Shape ID");

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new LeafAdapter(this, weedList));
        itemList.setCurrentItem(0);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent weedsListActivity = new Intent(getParent(), FlowerIdentifierActivity.class);
                weedsListActivity.putExtra(getPackageName() + ".leaf", weedIDList[itemList.getCurrentItem()]);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("FlowerIdentifierActivity", weedsListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("FlowerIdentifierActivity", weedsListActivity);
            }
        });
    }

    private class LeafAdapter extends AbstractWheelTextAdapter {

        private String weedList[];

        /**
         * Constructor
         */
        protected LeafAdapter(Context context, String[] list) {
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
