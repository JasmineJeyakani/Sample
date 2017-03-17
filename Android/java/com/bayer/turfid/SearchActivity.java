//package com.bayer.turfid;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class SearchActivity extends Activity {
//
//	TIDatabase TIdb;
//	private ListView list;
//	private EditText searchTxt;
//	private String backupSearchTxt;
//	ArrayList<TISearch> searchList = new ArrayList<TISearch>();
//	private SearchListAdapter searchAdapter;
//	
//	 @Override
//	 public void onCreate(Bundle savedInstanceState) {
//		 super.onCreate(savedInstanceState);
//		 setContentView(R.layout.search);
//
//        searchTxt = (EditText) findViewById(R.id.searchTxt);
//		list = (ListView)findViewById(R.id.list);
//		
//		searchTxt.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				if(searchTxt.getText().toString().equals("")){
//					searchTxt.setHint("Search");
//					//searchList.clear();
//					//searchAdapter.notifyDataSetChanged();
//				} else {
//			    	try {
//			    		searchAdapter = new SearchListAdapter(getBaseContext(), getSearchResult(searchTxt.getText().toString()));
//			    		searchAdapter.notifyDataSetChanged();
//			    		list.setAdapter(searchAdapter);
//			    		list.setItemsCanFocus(true);			    		
//					} catch (Exception e) {
//					}
//				}
//			}
//		});
//	}
//
//	 @Override
//	 public void onResume() {
//		 super.onResume();
//	     //searchTxt.requestFocus();
//	
//		 searchTxt.setText(backupSearchTxt);
//	     searchTxt.postDelayed(new Runnable() {
//	
//	         @Override
//	         public void run() {
//	             InputMethodManager keyboard = (InputMethodManager)
//	             getSystemService(Context.INPUT_METHOD_SERVICE);
//	             keyboard.showSoftInput(searchTxt, 0);
//	         }
//	     },200);	 
//	}
//	 
//	 public ArrayList<TISearch> getSearchResult(String searchString){
//		String query;
//		searchList.clear();
//
//		TIdb = new TIDatabase(getBaseContext());
//		TIdb.open();
//		query = "select WeedName, WeedFamily, WeedDescription, FlowerImage,YoungImage,'Weeds' TurfType from tblWeed where WeedName like '%" + searchString + "%' union all select DiseaseName, DiseaseFamily, DiseaseDescription, DiseaseImage,'null' YoungImage,'Diseases' TurfType from tblDisease where DiseaseName like '%" + searchString + "%' union all select PestName, PestFamily, PestDescription, PestImage,'null' YoungImage,'Pest' TurfType from tblPest where PestName like '%" + searchString + "%' union all select ProductName, UseArea, KeyFeatures, ProductPack,'null' YoungImage,'Products' TurfType from tblProduct where ProductName like '%" + searchString + "%'";
//		Cursor c = TIdb.FetchAllRecordsForRawQuery(query); 
//		while(c.moveToNext()){
//			searchList.add(new TISearch(c.getString(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5)));
//		}
//		c.close();
//		TIdb.close();
//		return searchList;
//	}
//	    
//	private class SearchListAdapter extends ArrayAdapter<TISearch> {
//
//		private ArrayList<TISearch> searchItem;
//	    
//		public SearchListAdapter(Context context, ArrayList<TISearch> searchList) {
//			super(context, R.layout.search, searchList);
//			this.searchItem = searchList;
//		}
//
//	    @Override
//	    public View getView(int position, View convertView, ViewGroup parent) {
//
//	    	LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        	View v = inflater.inflate(R.layout.searchresult, parent, false);
//        	
//        	final TISearch search = searchItem.get(position);
//	        v.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//
//					InputMethodManager keyboard = (InputMethodManager)
//		            getSystemService(Context.INPUT_METHOD_SERVICE);
//		            keyboard.hideSoftInputFromWindow(searchTxt.getWindowToken(), 0);
//		            backupSearchTxt = searchTxt.getText().toString();
//					
//		            Log.d("TAG::search.Img", search.Img);
//					Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
//					itemDescActivity.putExtra(getPackageName()+".title", search.Name);
//					itemDescActivity.putExtra(getPackageName()+".type", search.categoryType);
//					itemDescActivity.putExtra(getPackageName()+".name", search.Name);
//					itemDescActivity.putExtra(getPackageName()+".family", search.Family);
//					itemDescActivity.putExtra(getPackageName()+".desc", search.Description);
//					itemDescActivity.putExtra(getPackageName()+".image", search.Image);
//					itemDescActivity.putExtra(getPackageName()+".img", search.Img);
//
//					TabGroupActivity parentActivity = (TabGroupActivity)getParent();
//					parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);
//				}
//			});
//	        
//	        if(search != null) {
//
//	        	TextView resultTxt = (TextView)v.findViewById(R.id.result);
//	        	resultTxt.setClickable(false);
//	        	resultTxt.setFocusable(false);
//	            if(search.getName() != null) {
//	            	resultTxt.setText( search.getName() );
//	            }
//	        }
//			return v;
//	    }
//	}
//}

package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//import com.bayer.turfid.TurfListActivity.ProductsListAdapter;

public class SearchActivity extends Activity {

    TIDatabase TIdb;
    private ListView list;
    private EditText searchTxt;
    private String backupSearchTxt;
    ArrayList<TISearch> searchList = new ArrayList<TISearch>();
    ArrayList<TIProduct> productList = new ArrayList<TIProduct>();
    private SearchListAdapter searchAdapter;
    private ImageView imgLogo;

    static class ViewHolder {

        TextView resultText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchTxt = (EditText) findViewById(R.id.searchTxt);
        list = (ListView) findViewById(R.id.list);
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        searchTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchTxt.getText().toString().equals("")) {
                    searchTxt.setHint("Search");
                } else {
                    try {
                        searchAdapter = new SearchListAdapter(getBaseContext(), getSearchResult(searchTxt.getText().toString()));
                        searchAdapter.notifyDataSetChanged();
                        Parcelable state = list.onSaveInstanceState();
                        list.setAdapter(searchAdapter);
                        list.setItemsCanFocus(true);
                        list.onRestoreInstanceState(state);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        searchTxt.setText(backupSearchTxt);
        searchTxt.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(searchTxt, 0);
            }
        }, 200);
    }

    public ArrayList<TISearch> getSearchResult(String searchString) {
        String query;
        searchList.clear();

        TIdb = new TIDatabase(getBaseContext());
        TIdb.open();
        query = "select WeedName, WeedFamily, WeedDescription, FlowerImage ,YoungImage,'Weeds' TurfType,'Weeds' CulturalWeeds from tblWeed where WeedName like '%" + searchString + "%' union all select DiseaseName, DiseaseFamily, DiseaseDescription, DiseaseImage,'null' YoungImage,'Diseases' TurfType,DiseaseCultural from tblDisease where DiseaseName like '%" + searchString + "%' union all select PestName, PestFamily, PestDescription, PestImage,'null' YoungImage,'Insects' TurfType,PestCultural from tblPest where PestName like '%" + searchString + "%' union all select ProductName, ProductType, ProductOrder, ProductPack,'null' YoungImage,'Products' TurfType,'Products' CulturalProducts from tblProduct where ProductName like '%" + searchString + "%'";
//		
        Cursor c = TIdb.FetchAllRecordsForRawQuery(query);
        while (c.moveToNext()) {
            searchList.add(new TISearch(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }
        c.close();
        TIdb.close();
        return searchList;
    }

    private class SearchListAdapter extends ArrayAdapter<TISearch> {

        private ArrayList<TISearch> searchItem;

        public SearchListAdapter(Context context, ArrayList<TISearch> searchList) {
            super(context, R.layout.search, searchList);
            this.searchItem = searchList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.searchresult, parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.resultText = (TextView) v.findViewById(R.id.result);

                v.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) v.getTag();


            final TISearch search = searchItem.get(position);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    InputMethodManager keyboard = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(searchTxt.getWindowToken(), 0);
                    backupSearchTxt = searchTxt.getText().toString();
                    Intent itemDescActivity = new Intent(getParent(), ItemDescriptionActivity.class);
                    itemDescActivity.putExtra(getPackageName() + ".title", search.Name);
                    itemDescActivity.putExtra(getPackageName() + ".name", search.Name);
                    itemDescActivity.putExtra(getPackageName() + ".family", search.Family);
                    itemDescActivity.putExtra(getPackageName() + ".desc", search.Description);
                    itemDescActivity.putExtra(getPackageName() + ".image", search.Image);
                    itemDescActivity.putExtra(getPackageName() + ".img", search.Img);
                    itemDescActivity.putExtra(getPackageName() + ".type", search.CategoryType);
                    itemDescActivity.putExtra(getPackageName() + ".cultural", search.Cultural);
                    Log.d("TAG", "search.CategoryType" + search.CategoryType);

                    if (search.CategoryType.equals("Products")) {
                        int myNum = Integer.parseInt(search.Description.toString());
                        String query = "Select * from tblProduct where ProductOrder = " + myNum;
                        TIdb.open();
                        Cursor c = TIdb.FetchAllRecordsForRawQuery(query);

                        if ((c != null) && ((c.getCount()) > 0)) {
                            c.moveToFirst();
                            do {
                                productList.add(new TIProduct(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9)));
                            } while (c.moveToNext());
                        }

                        c.close();
                        TIdb.close();

                        ArrayList<TIProduct> productItem = productList;
                        final TIProduct product = productItem.get(0);
                        String desc;
                        desc = "<b>Overview</b>\n" + product.ProductOverview + "\n" + "<b>Use Area</b>\n" + product.UseArea + "<b>Key Features</b>\n" + product.KeyFeatures;
                        itemDescActivity.putExtra(getPackageName() + ".desc", desc);

                    }

                    TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                    if (android.os.Build.VERSION.SDK_INT <= 10)
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    else
                        parentActivity.startChildActivity("ItemDescriptionActivity", itemDescActivity);


                }
            });

            if (search != null) {
                holder.resultText.setClickable(false);
                holder.resultText.setFocusable(false);
                holder.resultText.setGravity(Gravity.CENTER_VERTICAL);
                if (search.getName() != null) {
                    holder.resultText.setText(search.getName());
                }
            }
            return v;
        }
    }
}