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

public class GeneralSymptomsActivity extends Activity {

	private TextView titleTxt;
	private Button submit;
	private String diseaseList[];
	private String diseaseIDList[];
    ImageView imgLogo;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weed_identifier);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralSymptomsActivity.this, BayerTurfManagementActivity.class);
                startActivity (intent);
            }
        });
        diseaseList = new String[] {"Circle", "Irregular", "Patch", "Ring", "Spot", "Smear", "Wilted", "Not known"};
    	diseaseIDList = new String[] {"1", "2", "3", "4", "5", "6", "7", "0"};	
        
    	
	    titleTxt = (TextView) findViewById(R.id.title1);
	    titleTxt.setText("General Symptoms");

        final WheelView itemList = (WheelView) findViewById(R.id.item);
        itemList.setVisibleItems(3);
        itemList.setViewAdapter(new LeafAdapter(this, diseaseList));
        itemList.setCurrentItem(0);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent diseasesListActivity = new Intent(getParent(), FoliarSymptomsActivity.class);
				diseasesListActivity.putExtra(getPackageName()+".general", diseaseIDList[itemList.getCurrentItem()]);
				TabGroupActivity parentActivity = (TabGroupActivity)getParent();
				if(android.os.Build.VERSION.SDK_INT <= 10)
					parentActivity.startChildActivity("FoliarSymptomsActivity", diseasesListActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				else
					parentActivity.startChildActivity("FoliarSymptomsActivity", diseasesListActivity);
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
