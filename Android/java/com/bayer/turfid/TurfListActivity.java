package com.bayer.turfid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TurfListActivity extends ListActivity {

    private ListView weedsListView;

    private TextView titleTxt;
    ArrayList<TIWeed> weedList = new ArrayList<TIWeed>();
    ArrayList<TIDisease> diseaseList = new ArrayList<TIDisease>();
    ArrayList<TIPest> insectList = new ArrayList<TIPest>();
    ArrayList<TIProduct> productList = new ArrayList<TIProduct>();
    ArrayList<TIDistributor> distributorList = new ArrayList<TIDistributor>();
    private TIDatabase TIdb;
    private String type;
    private String title;
    private String leaf;
    private String flower;
    private String general;
    private String foliar;
    private String weather;
    private String sward;
    private String country;
    private Bitmap d = null;
    private int currentSelection;
    ImageView imgLogo;

    static class ViewHolder {
        ImageView thumbImage;
        TextView familyName;
        TextView titleName;
        ImageView imgLogo;
    }

    static class DistViewHolder {
        TextView distName;
        TextView distEmail;
        TextView distAddress;
        TextView distTelephone;
        TextView distFax;
        TextView distWebsite;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "thiruthiru");
        // getWindow().setWindowAnimations(R.anim.grow_from_bottomleft_to_topright);

        //imgLogo = (ImageView) findViewById(R.id.img_logo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString(this.getPackageName() + ".type");
            title = extras.getString(this.getPackageName() + ".title");
            if (type.equals("WeedIdentifier")) {
                leaf = extras.getString(this.getPackageName() + ".leaf");
                flower = extras.getString(this.getPackageName() + ".flower");
            } else if (type.equals("Disease Identifier")) {
                general = extras.getString(this.getPackageName() + ".general");
                foliar = extras.getString(this.getPackageName() + ".foliar");

                // 04_OCT_12
                // Remove Weather condition from Disease ID
                //weather = extras.getString(this.getPackageName()+".weather");
                weather = "0";

                sward = extras.getString(this.getPackageName() + ".sward");
            } else if (type.equals("Distributors")) {
                country = extras.getString(this.getPackageName() + ".country");
            }
        }

        if (type.equals("Distributors")) {
            setContentView(R.layout.distributors_list);
        } else {
            setContentView(R.layout.weeds_list);
        }

        titleTxt = (TextView) findViewById(R.id.title1);
        titleTxt.setText(title);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TurfListActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });


        Cursor c = null;
        weedsListView = (ListView) findViewById(android.R.id.list);

//        int index = weedsListView.getFirstVisiblePosition();
//    	View v1 = weedsListView.getChildAt(0);
//    	int top = (v1 == null) ? 0 : v1.getTop();
//    	weedsListView.setSelectionFromTop(index, top);

        TIdb = new TIDatabase(getBaseContext());
        String query;

        if ((type.equals("GrassWeed")) || (type.equals("BroadLeaved")) || (type.equals("WeedIdentifier"))) {
            TIdb.open();
            if (type.equals("GrassWeed")) {
                c = TIdb.FetchAllRecords("tblWeed", "IsGrassWeed='Y'", "WeedName");
            } else if (type.equals("BroadLeaved")) {
                c = TIdb.FetchAllRecords("tblWeed", "IsGrassWeed='N'", "WeedName");
            } else if (type.equals("WeedIdentifier")) {
                if ((leaf.equals("0")) && (flower.equals("0"))) {
                    query = "select * from tblWeed order by WeedName";
                } else {
                    if (leaf.equals("0")) {
                        leaf = "'%'";
                    }

                    if (flower.equals("0")) {
                        flower = "'%'";
                    }

                    query = "Select * from tblWeed where WeedId in ( Select A.WeedId from tblWeedCategoryIdentificationRel A where WeedCategoryIdentificationId like " + leaf + " intersect select A.WeedId from tblWeedCategoryIdentificationRel A where WeedCategoryIdentificationId like " + flower + ") order by WeedName";
                }
                c = TIdb.FetchAllRecordsForRawQuery(query);
            }

            if ((c != null) && ((c.getCount()) > 0)) {
                c.moveToFirst();
                do {
                    weedList.add(new TIWeed(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                } while (c.moveToNext());
            }
            weedsListView.setAdapter(new WeedsListAdapter(getBaseContext(), weedList));
            c.close();
            TIdb.close();
        }

        if ((type.equals("Diseases A-Z")) || (type.equals("Disease Identifier"))) {
            TIdb.open();
            if (type.equals("Diseases A-Z")) {
                c = TIdb.FetchAllRecords("tblDisease", "", "DiseaseName");
            } else if (type.equals("Disease Identifier")) {
                if ((general.equals("0")) && (foliar.equals("0")) && (weather.equals("0")) && (sward.equals("0"))) {
                    query = "select * from tblDisease order by DiseaseName";
                } else {
                    if (general.equals("0")) {
                        general = "'%'";
                    }
                    if (foliar.equals("0")) {
                        foliar = "'%'";
                    }
                    if (weather.equals("0")) {
                        weather = "'%'";
                    }
                    if (sward.equals("0")) {
                        sward = "'%'";
                    }
                    query = "Select * from tblDisease where DiseaseID in ( Select A.DiseaseID from tblDiseaseCategoryIdentificationRel A where DiseaseCategoryIdentificationId like " + general + " intersect Select A.DiseaseID from tblDiseaseCategoryIdentificationRel A where DiseaseCategoryIdentificationId like " + foliar + " intersect Select A.DiseaseID from tblDiseaseCategoryIdentificationRel A where DiseaseCategoryIdentificationId like " + weather + " intersect Select A.DiseaseID from tblDiseaseCategoryIdentificationRel A where DiseaseCategoryIdentificationId like " + sward + ") order by DiseaseName";

                }
                c = TIdb.FetchAllRecordsForRawQuery(query);
            }

            if ((c != null) && ((c.getCount()) > 0)) {
                c.moveToFirst();
                do {
                    diseaseList.add(new TIDisease(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
                } while (c.moveToNext());
            }
            weedsListView.setAdapter(new DiseasesListAdapter(getBaseContext(), diseaseList));
            c.close();
            TIdb.close();
        }

        if (type.equals("Insects")) {
            TIdb.open();
            c = TIdb.FetchAllRecords("tblPest", "", "PestName");

            if ((c != null) && ((c.getCount()) > 0)) {
                c.moveToFirst();
                do {
                    insectList.add(new TIPest(c.getInt(0), c.getString(1), c.getString(4), c.getString(2), c.getString(3), c.getString(5)));
                } while (c.moveToNext());
            }
            weedsListView.setAdapter(new InsectsListAdapter(getBaseContext(), insectList));
            c.close();
            TIdb.close();
        }

        if (type.equals("Products")) {
            TIdb.open();
            c = TIdb.FetchAllRecords("tblProduct", "", "ProductOrder");

            if ((c != null) && ((c.getCount()) > 0)) {
                c.moveToFirst();
                do {
                    productList.add(new TIProduct(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9)));
                } while (c.moveToNext());
            }
            weedsListView.setAdapter(new ProductsListAdapter(getBaseContext(), productList));
            c.close();
            TIdb.close();
        }


        if (type.equals("Distributors")) {
            TIdb.open();
            if (country.equals("All")) {
                c = TIdb.FetchAllRecords("tblDistributor", "", "DistributorName");
            } else {
                c = TIdb.FetchAllRecords("tblDistributor", "DistributorCountry='" + country + "'", "DistributorName");
            }
            if ((c != null) && ((c.getCount()) > 0)) {
                c.moveToFirst();
                do {
                    distributorList.add(new TIDistributor(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7)));
                } while (c.moveToNext());
            }
            weedsListView.setAdapter(new DistributorsListAdapter(getBaseContext(), distributorList));
            c.close();
            TIdb.close();
        }

        weedsListView.setItemsCanFocus(true);

        if (android.os.Build.VERSION.SDK_INT <= 10) {
            TabGroupActivity parentActivity = (TabGroupActivity) getParent();
            Log.d("tag", "selectionPosparent" + parentActivity.currentPosofList);
            weedsListView.setSelection(parentActivity.currentPosofList);
        }


//    	int index = weedsListView.getFirstVisiblePosition();
//    	View v1 = weedsListView.getChildAt(0);
//    	int top = (v1 == null) ? 0 : v1.getTop();
//    	weedsListView.setSelectionFromTop(index, top);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Overrides the default implementation for KeyEvent.KEYCODE_BACK
     * so that all systems call onBackPressed().
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TabGroupActivity parentActivity = (TabGroupActivity) getParent();
            //  parentActivity.currentPosofList = currentSelection;
            parentActivity.onBackPressed();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private class WeedsListAdapter extends ArrayAdapter<TIWeed> {

        private ArrayList<TIWeed> weeds;

        public WeedsListAdapter(Context context, ArrayList<TIWeed> weedsList) {
            super(context, R.layout.weeds_list, weedsList);
            this.weeds = weedsList;
            TextView textView = (TextView) findViewById(R.id.empty_title);

            if (weedsList.isEmpty()) {
                textView.setText("No results found.  Please contact us on 00800 1214 9451 for further information. ");
            } else
                textView.setVisibility(View.GONE);
        }

        @SuppressWarnings("unused")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.weeds_row, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.titleName = (TextView) v.findViewById(R.id.weedName);
                viewHolder.familyName = (TextView) v.findViewById(R.id.weedFamily);
                viewHolder.thumbImage = (ImageView) v.findViewById(R.id.weedImage);
                v.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) v.getTag();
            final TIWeed weed = weeds.get(position);


            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    getListView().setSelectionFromTop(getListView().getFirstVisiblePosition(), 0);
                    Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
                    itemDescActivity.putExtra(getPackageName() + ".title", weed.WeedName);
                    itemDescActivity.putExtra(getPackageName() + ".type", "Weeds");
                    itemDescActivity.putExtra(getPackageName() + ".name", weed.WeedName);
                    itemDescActivity.putExtra(getPackageName() + ".family", weed.WeedFamily);
                    itemDescActivity.putExtra(getPackageName() + ".desc", weed.WeedDescription);
                    itemDescActivity.putExtra(getPackageName() + ".flowerimage", weed.FlowerImage);
                    itemDescActivity.putExtra(getPackageName() + ".youngimage", weed.YoungImage);
                    itemDescActivity.putExtra(getPackageName() + ".image", weed.FlowerImage);
                    itemDescActivity.putExtra(getPackageName() + ".img", weed.YoungImage);
                    itemDescActivity.putExtra(getPackageName() + ".selectionPos", position);
                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);
                    }

                }
            });

            if (weed != null) {

//	        	TextView weedName = (TextView)v.findViewById(R.id.weedName);
//	        	weedName.setClickable(false);
//	        	weedName.setFocusable(false);
//	            if(weed.getWeedName() != null) {
//	            	weedName.setText( weed.getWeedName() );
//	            }

                holder.titleName.setClickable(false);
                holder.titleName.setFocusable(false);
                if (weed.getWeedName() != null) {
                    holder.titleName.setText(weed.getWeedName());
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.titleName.getLayoutParams();
                params.setMargins(0, 15, 30, 10);
                holder.titleName.setLayoutParams(params);

//	        	TextView weedFamily = (TextView)v.findViewById(R.id.weedFamily);
//	        	weedFamily.setClickable(false);
//	        	weedFamily.setFocusable(false);
//	            if(weed.getWeedFamily() != null) {
//	            	weedFamily.setText( weed.getWeedFamily());
//	            } 


                holder.familyName.setClickable(false);
                holder.familyName.setFocusable(false);
                if (weed.getWeedFamily() != null) {
                    holder.familyName.setText(weed.getWeedFamily());
                }


                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder.familyName.getLayoutParams();
                params1.setMargins(0, 10, 30, 10);
                holder.familyName.setLayoutParams(params1);

                holder.thumbImage.setClickable(false);
                holder.thumbImage.setFocusable(false);

                if (android.os.Build.VERSION.SDK_INT > 10) {
                    int index = weedsListView.getFirstVisiblePosition();
                    View v1 = weedsListView.getChildAt(0);
                    int top = (v1 == null) ? 0 : v1.getTop();
                    weedsListView.setSelectionFromTop(index, top);
                }
//		            Drawable d = null;

                try {
//		        		d = getResources().getDrawable(getResources().getIdentifier(weed.FlowerImage + "_small", "drawable", getPackageName()));
                    d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(weed.FlowerImage + "_small", "drawable", getPackageName()));
                    //Log.i(log,"FlowerImage : " + weed.FlowerImage + "_small");

                    if (d != null) {
//			    	    	weedImage.setImageDrawable(d);
                        holder.thumbImage.setImageBitmap(d);
                    } else {
                        try {
                            //Log.i(log,"Young Image : " + weed.YoungImage + "_small");

//				    	    	d = getResources().getDrawable(getResources().getIdentifier(weed.YoungImage + "_small", "drawable", getPackageName()));
                            d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(weed.YoungImage + "_small", "drawable", getPackageName()));

                            if (d != null) {
//				    	    		weedImage.setImageDrawable(d);
                                holder.thumbImage.setImageBitmap(d);
                            } else {
                                holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                            }
                        } catch (Exception e) {
                            holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                        }
                    }
                } catch (Exception e) {
                    try {
                        //Log.i(log,"Young Image : " + weed.YoungImage + "_small");
//			    	    	d = getResources().getDrawable(getResources().getIdentifier(weed.YoungImage + "_small", "drawable", getPackageName()));
                        d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(weed.YoungImage + "_small", "drawable", getPackageName()));

                        if (d != null) {
//			    	    		weedImage.setImageDrawable(d);
                            holder.thumbImage.setImageBitmap(d);
                        } else {
                            holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                        }
                    } catch (Exception e1) {
                        holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                    }
                }
            }
            return v;
        }
    }

    private class DiseasesListAdapter extends ArrayAdapter<TIDisease> {

        private ArrayList<TIDisease> diseases;

        public DiseasesListAdapter(Context context, ArrayList<TIDisease> diseasesList) {
            super(context, R.layout.weeds_list, diseasesList);
            this.diseases = diseasesList;
            TextView textView = (TextView) findViewById(R.id.empty_title);
            if (diseasesList.isEmpty()) {
                textView.setText("No results found.  Please contact us on 00800 1214 9451 for further information. ");

            } else
                textView.setVisibility(View.GONE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.weeds_row, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.titleName = (TextView) v.findViewById(R.id.weedName);
                viewHolder.familyName = (TextView) v.findViewById(R.id.weedFamily);
                viewHolder.thumbImage = (ImageView) v.findViewById(R.id.weedImage);
                v.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) v.getTag();

            final TIDisease disease = diseases.get(position);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getListView().setSelectionFromTop(getListView().getFirstVisiblePosition(), 0);
                    Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
                    itemDescActivity.putExtra(getPackageName() + ".title", disease.DiseaseName);
                    itemDescActivity.putExtra(getPackageName() + ".type", "Diseases");
                    itemDescActivity.putExtra(getPackageName() + ".name", disease.DiseaseName);
                    itemDescActivity.putExtra(getPackageName() + ".family", disease.DiseaseFamily);
                    itemDescActivity.putExtra(getPackageName() + ".desc", disease.DiseaseDescription);
                    itemDescActivity.putExtra(getPackageName() + ".image", disease.DiseaseImage);
                    itemDescActivity.putExtra(getPackageName() + ".cultural", disease.DiseaseCultural);
                    itemDescActivity.putExtra(getPackageName() + ".selectionPos", position);
                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);
                    }
                }
            });

            if (disease != null) {

//	        	TextView diseaseName = (TextView)v.findViewById(R.id.weedName);
//	        	diseaseName.setClickable(false);
//	        	diseaseName.setFocusable(false);
//	            if(disease.getDiseaseName() != null) {
//	            	diseaseName.setText( disease.getDiseaseName());
//	            }

                holder.titleName.setClickable(false);
                holder.titleName.setFocusable(false);
                if (disease.getDiseaseName() != null) {
                    holder.titleName.setText(disease.getDiseaseName());
                }
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.titleName.getLayoutParams();
                params.setMargins(0, 15, 30, 10);
                holder.titleName.setLayoutParams(params);

//	        	TextView diseaseFamily = (TextView)v.findViewById(R.id.weedFamily);
//	        	diseaseFamily.setClickable(false);
//	        	diseaseFamily.setFocusable(false);
//	            if(disease.getDiseaseFamily() != null) {
//	            	diseaseFamily.setText( disease.getDiseaseFamily());
//	            } 

                holder.familyName.setClickable(false);
                holder.familyName.setFocusable(false);
                if (disease.getDiseaseFamily() != null) {
                    holder.familyName.setText(disease.getDiseaseFamily());
                }

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder.familyName.getLayoutParams();
                params1.setMargins(0, 40, 30, 10);
                holder.familyName.setLayoutParams(params1);

//	        	ImageView diseaseImage = (ImageView)v.findViewById(R.id.weedImage);
//	        	diseaseImage.setClickable(false);
//	        	diseaseImage.setFocusable(false);

                holder.thumbImage.setClickable(false);
                holder.thumbImage.setFocusable(false);

                if (android.os.Build.VERSION.SDK_INT > 10) {
                    int index = weedsListView.getFirstVisiblePosition();
                    View v1 = weedsListView.getChildAt(0);
                    int top = (v1 == null) ? 0 : v1.getTop();
                    weedsListView.setSelectionFromTop(index, top);
                }
//	            Drawable d = null;
                Bitmap d = null;
                try {
//	        		d = getResources().getDrawable(getResources().getIdentifier(disease.DiseaseImage + "_small", "drawable", getPackageName()));
                    d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(disease.DiseaseImage + "_small", "drawable", getPackageName()));

                    if (d != null) {
//	    	    		diseaseImage.setImageDrawable(d);
                        holder.thumbImage.setImageBitmap(d);
                    } else {
                        holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                    }
                } catch (Exception e) {
                    holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                }
            }

            return v;
        }
    }

    private class InsectsListAdapter extends ArrayAdapter<TIPest> {

        private ArrayList<TIPest> insects;

        public InsectsListAdapter(Context context, ArrayList<TIPest> insectsList) {
            super(context, R.layout.weeds_list, insectsList);
            this.insects = insectsList;
            TextView textView = (TextView) findViewById(R.id.empty_title);
            ;
            if (insectsList.isEmpty()) {
                textView.setText("No results found.  Please contact us on 00800 1214 9451 for further information. ");

            } else
                textView.setVisibility(View.GONE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.weeds_row, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.titleName = (TextView) v.findViewById(R.id.weedName);
                viewHolder.familyName = (TextView) v.findViewById(R.id.weedFamily);
                viewHolder.thumbImage = (ImageView) v.findViewById(R.id.weedImage);
                v.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) v.getTag();

            final TIPest insect = insects.get(position);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getListView().setSelectionFromTop(getListView().getFirstVisiblePosition(), 0);
                    Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
                    itemDescActivity.putExtra(getPackageName() + ".title", insect.PestName);
                    itemDescActivity.putExtra(getPackageName() + ".type", "Insects");
                    itemDescActivity.putExtra(getPackageName() + ".name", insect.PestName);
                    itemDescActivity.putExtra(getPackageName() + ".family", insect.PestFamily);
                    itemDescActivity.putExtra(getPackageName() + ".desc", insect.PestDescription);
                    itemDescActivity.putExtra(getPackageName() + ".image", insect.PestImage);
                    itemDescActivity.putExtra(getPackageName() + ".cultural", insect.PestCultural);
                    itemDescActivity.putExtra(getPackageName() + ".selectionPos", position);
                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);
                    }
                }
            });

            if (insect != null) {

//	        	TextView insectName = (TextView)v.findViewById(R.id.weedName);
//	        	insectName.setClickable(false);
//	        	insectName.setFocusable(false);
//	            if(insect.getPestName() != null) {
//	            	insectName.setText( insect.getPestName());
//	            }

                holder.titleName.setClickable(false);
                holder.titleName.setFocusable(false);
                if (insect.getPestName() != null) {
                    holder.titleName.setText(insect.getPestName());
                }

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.titleName.getLayoutParams();
                params.setMargins(0, 15, 30, 10);
                holder.titleName.setLayoutParams(params);

//	        	TextView insectFamily = (TextView)v.findViewById(R.id.weedFamily);
//	        	insectFamily.setClickable(false);
//	        	insectFamily.setFocusable(false);
//	            if(insect.getPestFamily() != null) {
//	            	insectFamily.setText( insect.getPestFamily());
//	            } 

                holder.familyName.setClickable(false);
                holder.familyName.setFocusable(false);
                if (insect.getPestFamily() != null) {
                    holder.familyName.setText(insect.getPestFamily());
                }

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder.familyName.getLayoutParams();
                params1.setMargins(0, 10, 30, 10);
                holder.familyName.setLayoutParams(params1);

//	        	ImageView insectImage = (ImageView)v.findViewById(R.id.weedImage);
//	        	insectImage.setClickable(false);
//	        	insectImage.setFocusable(false);

                holder.thumbImage.setClickable(false);
                holder.thumbImage.setFocusable(false);

                if (android.os.Build.VERSION.SDK_INT > 10) {
                    int index = weedsListView.getFirstVisiblePosition();
                    View v1 = weedsListView.getChildAt(0);
                    int top = (v1 == null) ? 0 : v1.getTop();
                    weedsListView.setSelectionFromTop(index, top);
                }
//	            Drawable d = null;
                Bitmap d = null;
                try {
//	        		d = getResources().getDrawable(getResources().getIdentifier(insect.PestImage + "_small", "drawable", getPackageName()));
                    d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(insect.PestImage + "_small", "drawable", getPackageName()));

                    if (d != null) {
//	    	    		insectImage.setImageDrawable(d);
                        holder.thumbImage.setImageBitmap(d);
                    } else {
                        holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                    }
                } catch (Exception e) {
                    holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                }
            }
            return v;
        }
    }

    private class ProductsListAdapter extends ArrayAdapter<TIProduct> {

        private ArrayList<TIProduct> products;

        public ProductsListAdapter(Context context, ArrayList<TIProduct> productsList) {
            super(context, R.layout.weeds_list, productsList);
            this.products = productsList;
            TextView textView = (TextView) findViewById(R.id.empty_title);
            ;
            if (productsList.isEmpty()) {
                textView.setText("No results found.  Please contact us on 00800 1214 9451 for further information. ");

            } else
                textView.setVisibility(View.GONE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.weeds_row, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.titleName = (TextView) v.findViewById(R.id.productName);
//		        	viewHolder.familyName = (TextView)v.findViewById(R.id.weedFamily);
                viewHolder.thumbImage = (ImageView) v.findViewById(R.id.weedImage);

                v.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) v.getTag();

            final TIProduct product = products.get(position);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getListView().setSelectionFromTop(getListView().getFirstVisiblePosition(), 0);
                    Intent itemDescActivity = new Intent(getBaseContext(), ItemDescriptionActivity.class);
                    itemDescActivity.putExtra(getPackageName() + ".title", product.ProductName);
                    itemDescActivity.putExtra(getPackageName() + ".type", "Products");
                    itemDescActivity.putExtra(getPackageName() + ".name", product.ProductName);
                    itemDescActivity.putExtra(getPackageName() + ".family", product.ProductType);
                    String desc;
                    desc = "<b>Overview</b>\n" + product.ProductOverview + "\n" + "<b>Use Area</b>\n" + product.UseArea + "<b>Key Features</b>\n" + product.KeyFeatures;
                    itemDescActivity.putExtra(getPackageName() + ".desc", desc);
                    itemDescActivity.putExtra(getPackageName() + ".image", product.ProductPack);
                    itemDescActivity.putExtra(getPackageName() + ".selectionPos", position);

                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10) {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);
                    }
                }
            });

            if (product != null) {

	        	/*TextView productName = (TextView)v.findViewById(R.id.weedName);
                productName.setClickable(false);
	        	productName.setFocusable(false);
	            if(product.getProductName() != null) {
	            	productName.setText( product.getProductName());
	            }
	        	LayoutParams imgLayout = productName.getLayoutParams();
        		imgLayout.width = LayoutParams.WRAP_CONTENT;
        		imgLayout.height = LayoutParams.WRAP_CONTENT;
        		productName.setLayoutParams(imgLayout);*/


//disable by suresh	            

//	        	TextView productType = (TextView)v.findViewById(R.id.productName);
//	        	productType.setClickable(false);
//	        	productType.setFocusable(false);
//	        	productType.setTextAppearance(getBaseContext(), R.style.prodText);
//	            if(product.getProductType() != null) {
//	            	productType.setText( product.getProductType());
//	            }

                holder.titleName.setClickable(false);
                holder.titleName.setFocusable(false);
                holder.titleName.setTextAppearance(getBaseContext(), R.style.prodText);
                if (product.getProductType() != null) {
                    holder.titleName.setText(product.getProductType());
                }

                // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)productType1.getLayoutParams();
//	            params.setMargins(10, 25, 30, 10);
                //productType1.setLayoutParams(params);

//	        	ImageView productImage = (ImageView)v.findViewById(R.id.weedImage);
//	        	productImage.setClickable(false);
//	        	productImage.setFocusable(false);
//        		productImage.getBackground().setAlpha(0);
//        		productImage.setScaleType(ScaleType.CENTER_INSIDE);

                holder.thumbImage.setClickable(false);
                holder.thumbImage.setFocusable(false);
                holder.thumbImage.getBackground().setAlpha(0);
                holder.thumbImage.setScaleType(ScaleType.CENTER_INSIDE);

                if (android.os.Build.VERSION.SDK_INT > 10) {
                    int index = weedsListView.getFirstVisiblePosition();
                    View v1 = weedsListView.getChildAt(0);
                    int top = (v1 == null) ? 0 : v1.getTop();
                    weedsListView.setSelectionFromTop(index, top);
                }
//	            Drawable d = null;
                Bitmap d = null;
                try {
//	        		d = getResources().getDrawable(getResources().getIdentifier(product.ProductImage, "drawable", getPackageName()));
                    d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(product.ProductImage, "drawable", getPackageName()));

                    if (d == null) {
                        holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                    } else {
//	    	    		productImage.setImageDrawable(d);
                        holder.thumbImage.setImageBitmap(d);
                        LayoutParams imgLayout = holder.thumbImage.getLayoutParams();
                        imgLayout.width = LayoutParams.WRAP_CONTENT;
                        imgLayout.height = LayoutParams.WRAP_CONTENT;
                        holder.thumbImage.setLayoutParams(imgLayout);
                    }
                } catch (Exception e) {
                    holder.thumbImage.setImageResource(R.drawable.bayer_default_small);
                }
            }
            return v;
        }
    }

    private class DistributorsListAdapter extends ArrayAdapter<TIDistributor> {

        private ArrayList<TIDistributor> distributors;

        public DistributorsListAdapter(Context context, ArrayList<TIDistributor> distributorsList) {
            super(context, R.layout.distributors_list, distributorsList);
            this.distributors = distributorsList;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.distributor_row, parent, false);

                DistViewHolder viewHolder = new DistViewHolder();
                viewHolder.distName = (TextView) v.findViewById(R.id.name);
                viewHolder.distAddress = (TextView) v.findViewById(R.id.address);
                viewHolder.distTelephone = (TextView) v.findViewById(R.id.telephone);
                viewHolder.distFax = (TextView) v.findViewById(R.id.fax);
                viewHolder.distEmail = (TextView) v.findViewById(R.id.email);
                viewHolder.distWebsite = (TextView) v.findViewById(R.id.website);
                v.setTag(viewHolder);
            }

            DistViewHolder holder = (DistViewHolder) v.getTag();


            final TIDistributor distributor = distributors.get(position);

            if (distributor != null) {

                holder.distName.setText(distributor.DistributorName);
                holder.distAddress.setText(distributor.DistributorAddress);
                holder.distTelephone.setText(distributor.DistributorTelephone);
                holder.distFax.setText(distributor.DistributorFax);
                holder.distEmail.setText(distributor.DistributorEmail);
                holder.distWebsite.setText(distributor.DistributorWebsite);

            }
            return v;
        }

    }


}
