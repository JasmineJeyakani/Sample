package com.bayer.turfid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductDescriptionActivity extends ListActivity {

    private ListView weedsListView;
    private String weedName;
    private String diseaseName;
    private String pestName;
    private String type;
    private TextView titleTxt;
    ArrayList<TIProduct> productList = new ArrayList<TIProduct>();
    private TIDatabase TIdb;
    private ImageView imgLogo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString(this.getPackageName() + ".type");
            if (type.equals("Turf") || type.equals("NonTurf")) {
                weedName = extras.getString(this.getPackageName() + ".weed");
            } else if (type.equals("Diseases")) {
                diseaseName = extras.getString(this.getPackageName() + ".disease");
                Log.d("TAG", "diseaseName" + diseaseName);
            } else if (type.equals("Insects")) {
                pestName = extras.getString(this.getPackageName() + ".insect");
            }
        }

        Cursor c = null;
        String query = null;
        Integer id = 0;

        if (type.equals("Turf") || type.equals("NonTurf")) {
            TIdb = new TIDatabase(getBaseContext());
            TIdb.open();

            query = "Select WeedId from tblWeed where weedName = '" + weedName + "'";
            c = TIdb.FetchAllRecordsForRawQuery(query);
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
            c = null;
            query = "";
            if (type.equals("Turf")) {
                query = "Select distinct A.* from tblProduct A, tblWeedProduct B on A.ProductId = B.ProductId where B.WeedId = " + id + " and B.ISTurf = 1 order by ProductName";
            } else {
                query = "Select distinct A.* from tblProduct A, tblWeedProduct B on A.ProductId = B.ProductId where B.WeedId = " + id + " and B.ISTurf = 0 order by ProductName";
            }

            c = TIdb.FetchAllRecordsForRawQuery(query);
            if ((c != null) && ((c.getCount()) > 0)) {
                setContentView(R.layout.weeds_list);

                imgLogo = (ImageView) findViewById(R.id.img_logo);
                imgLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductDescriptionActivity.this, BayerTurfManagementActivity.class);
                        startActivity(intent);
                    }
                });
                titleTxt = (TextView) findViewById(R.id.title1);
                titleTxt.setText("Products");
                weedsListView = (ListView) findViewById(android.R.id.list);

                c.moveToFirst();
                do {
                    productList.add(new TIProduct(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9)));
                } while (c.moveToNext());
                weedsListView.setAdapter(new ProductsListAdapter(getBaseContext(), productList));
                weedsListView.setItemsCanFocus(true);
            }
            c.close();
            TIdb.close();
        } else if (type.equals("Diseases")) {

            TIdb = new TIDatabase(getBaseContext());
            TIdb.open();
            query = "Select DiseaseId from tblDisease where DiseaseName = '" + diseaseName + "'";
            c = TIdb.FetchAllRecordsForRawQuery(query);
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
            c = null;
            query = "";
            query = "Select distinct A.* from tblProduct A, tblDiseaseProduct B on A.ProductId = B.ProductId where B.DiseaseId = " + id + " order by ProductOrder";
            c = TIdb.FetchAllRecordsForRawQuery(query);

            if ((c != null) && ((c.getCount()) > 0)) {
                setContentView(R.layout.weeds_list);
                imgLogo = (ImageView) findViewById(R.id.img_logo);
                imgLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductDescriptionActivity.this, BayerTurfManagementActivity.class);
                        startActivity(intent);
                    }
                });
                titleTxt = (TextView) findViewById(R.id.title1);
                titleTxt.setText("Products");
                weedsListView = (ListView) findViewById(android.R.id.list);

                c.moveToFirst();
                do {
                    productList.add(new TIProduct(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9)));
                } while (c.moveToNext());
                weedsListView.setAdapter(new ProductsListAdapter(getBaseContext(), productList));
                weedsListView.setItemsCanFocus(true);
            }
            c.close();
            TIdb.close();
        } else if (type.equals("Insects")) {

            TIdb = new TIDatabase(getBaseContext());
            TIdb.open();
            query = "Select PestId from tblPest where PestName = '" + pestName + "'";
            c = TIdb.FetchAllRecordsForRawQuery(query);

            c.moveToFirst();
            id = c.getInt(0);
            c.close();
            c = null;
            query = "";
            query = "Select distinct A.* from tblProduct A, tblPestProduct B on A.ProductId = B.ProductId where B.PestId = " + id;
            c = TIdb.FetchAllRecordsForRawQuery(query);
            if ((c != null) && ((c.getCount()) > 0)) {
                setContentView(R.layout.weeds_list);

                titleTxt = (TextView) findViewById(R.id.title1);
                titleTxt.setText("Products");
                weedsListView = (ListView) findViewById(android.R.id.list);

                c.moveToFirst();
                do {
                    productList.add(new TIProduct(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9)));
                } while (c.moveToNext());
                weedsListView.setAdapter(new ProductsListAdapter(getBaseContext(), productList));
                weedsListView.setItemsCanFocus(true);
            }
            c.close();
            TIdb.close();
        }
    }


    private class ProductsListAdapter extends ArrayAdapter<TIProduct> {

        private ArrayList<TIProduct> products;

        public ProductsListAdapter(Context context, ArrayList<TIProduct> productsList) {
            super(context, R.layout.weeds_list, productsList);

            this.products = productsList;
            TextView textView = (TextView) findViewById(R.id.empty_title);
            textView.setVisibility(View.GONE);

        }

        @SuppressWarnings("unused")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.weeds_row, parent, false);
            final TIProduct product = products.get(position);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

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
                    if (android.os.Build.VERSION.SDK_INT <= 10)
                        parentActivity.startChildActivity("ProdItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    else
                        parentActivity.startChildActivity("ProdItemDescriptionActivity", itemDescActivity);
                }
            });

            if (product != null) {
                TextView productType = (TextView) v.findViewById(R.id.productName);
                productType.setClickable(false);
                productType.setFocusable(false);
                productType.setTextAppearance(getBaseContext(), R.style.prodText);
                if (product.getProductType() != null) {
                    productType.setText(product.getProductType());
                }
                ImageView productImage = (ImageView) v.findViewById(R.id.weedImage);
                productImage.setClickable(false);
                productImage.setFocusable(false);
                productImage.getBackground().setAlpha(0);
                LayoutParams imgLayout = productImage.getLayoutParams();
                imgLayout.width = LayoutParams.WRAP_CONTENT;
                productImage.setLayoutParams(imgLayout);
                Bitmap d = null;
                try {
                    if (d != null) {
                        d.recycle();
                        d = null;
                    }
                    d = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(product.ProductImage, "drawable", getPackageName()));

                    if (d == null) {
                        productImage.setImageResource(R.drawable.bayer_default_small);
                    } else {
//	    	    		productImage.setImageDrawable(d);
                        productImage.setImageBitmap(d);
                    }
                } catch (Exception e) {
                    productImage.setImageResource(R.drawable.bayer_default_small);
                }
            }
            return v;
        }
    }
}
