package com.bayer.turfid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyClubsActivity extends Activity {
    GridView gridView;
    SharedPreferences knapsackval;
    SharedPreferences.Editor editor;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_clubs);
        imgLogo = (ImageView) findViewById(R.id.imglogo);
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyClubsActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        knapsackval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        gridView = (GridView) findViewById(R.id.gridView);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> clubNamelist = db.selectClubName();
        List<String> addresslist = db.selectAddressName();
        List<String> spraymakelist = db.selectGolfSprayMake();
        List<String> sprayModellist = db.selectSprayerModel();
        String[] clubName = new String[clubNamelist.size()];
        clubName = clubNamelist.toArray(clubName);
        String[] address = new String[addresslist.size()];
        address = addresslist.toArray(address);
        String[] spraymake = new String[spraymakelist.size()];
        spraymake = spraymakelist.toArray(spraymake);
        String[] sprayModel = new String[sprayModellist.size()];
        sprayModel = sprayModellist.toArray(sprayModel);
        CustomGrid adapter = new CustomGrid(MyClubsActivity.this, clubName, address, spraymake, sprayModel);
        gridView.setAdapter(adapter);
        final String[] finalClubName = clubName;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == finalClubName.length) {


                    Intent newclub = new Intent(MyClubsActivity.this, NewClubActivity.class);
                    editor = knapsackval.edit();
                    editor.putString("newclubVal", "myclubintent");
                    editor.commit();
                    startActivity(newclub);

                }
            }
        });
//

    }

    private class CustomGrid extends BaseAdapter {


        private Context mcontext;
        private final String[] clubName;
        private final String[] address;
        private final String[] sprayMake;
        private final String[] sprayModel;


        public CustomGrid(Context context, String[] clubName, String[] address, String[] sprayMake, String[] sprayModel) {
            mcontext = context;
            this.clubName = clubName;
            this.address = address;
            this.sprayMake = sprayMake;
            this.sprayModel = sprayModel;

        }

        @Override
        public int getCount() {
            return clubName.length + 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View grid;


            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            if (i == clubName.length) {

                grid = inflater.inflate(R.layout.club_new, null);
                grid.setBackgroundColor(Color.parseColor("#BF000000"));

            } else {

                grid = inflater.inflate(R.layout.club_items, null);
                if (clubName.length > 0) {
                    if (i == 0) {
                        TextView de = (TextView) grid.findViewById(R.id.defaultClub);
                        ImageView img = (ImageView) grid.findViewById(R.id.imageView11);
                        de.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);
                    }
                }

                final TextView cname = (TextView) grid.findViewById(R.id.clubName);
                final TextView cadd = (TextView) grid.findViewById(R.id.address);
                final TextView make = (TextView) grid.findViewById(R.id.sprayMake);
                final TextView model = (TextView) grid.findViewById(R.id.sprayModel);
                ImageView pen = (ImageView) grid.findViewById(R.id.imageView10);
                pen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        knapsackval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
                        editor = knapsackval.edit();
                        editor.putString("updateclub", cname.getText().toString());
                        editor.commit();

                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        List<String> email = db.selectEmail(cname.getText().toString());
                        List<String> fname = db.selectFirstName(cname.getText().toString());
                        List<String> lname = db.selectLastName(cname.getText().toString());
                        Intent i = new Intent(MyClubsActivity.this, UpdateClassActivity.class);
                        i.putExtra("clubname", cname.getText().toString());
                        i.putExtra("addname", cadd.getText().toString());
                        i.putExtra("makename", make.getText().toString());
                        i.putExtra("modelname", model.getText().toString());
                        i.putExtra("mail", email.get(0));
                        i.putExtra("fname", fname.get(0));
                        i.putExtra("lname", lname.get(0));
                        startActivity(i);

                    }
                });

                cname.setText(clubName[i]);
                cadd.setText(address[i]);
                make.setText(sprayMake[i]);
                model.setText(sprayModel[i]);
                grid.setBackgroundColor(Color.parseColor("#ebebeb"));
            }

            return grid;
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(MyClubsActivity.this, BayerTurfManagementActivity.class);
        startActivity(i);
    }
}
