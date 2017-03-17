package com.bayer.turfid;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


public class HomeActivity extends Activity {

    private RelativeLayout btnWeeds;
    private RelativeLayout btnDiseases;
    private RelativeLayout btnInsects;
    private RelativeLayout btnProducts;
    private RelativeLayout btnWeather;
    private RelativeLayout btnCalculators;
    private RelativeLayout btnSprayReports;
    private RelativeLayout btnMapArea;
    TextView txt;
    ImageView searchBtn;
    SharedPreferences Prefrence;
    SharedPreferences.Editor editor;
    Boolean val;
    Activity parent;
    boolean gps_enabled = false;
    boolean network_enabled = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Prefrence = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        editor = Prefrence.edit();
        editor.putInt("tabval", 1);
        editor.commit();

        searchBtn = (ImageView) findViewById(R.id.searchbtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent diseaseActivity = new Intent(getParent(), SearchActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();

                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("DiseaseActivity", diseaseActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("DiseaseActivity", diseaseActivity);

            }
        });


        btnWeather = (RelativeLayout) findViewById(R.id.weatherl);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                } catch (Exception e) {

                }

                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {

                    if (!gps_enabled && !network_enabled) {
                        Toast.makeText(HomeActivity.this, "Enable location", Toast.LENGTH_SHORT).show();
                    } else {


                        Intent weatherActivity = new Intent(getParent(), WeatherMainActivity.class);
                        TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                        weatherActivity.putExtra(getPackageName() + ".type", "Weather");
                        weatherActivity.putExtra(getPackageName() + ".title", "Weather");
                        if (android.os.Build.VERSION.SDK_INT <= 10)
                            parentActivity.startChildActivity("WeatherMainActivity", weatherActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        else
                            parentActivity.startChildActivity("WeatherMainActivity", weatherActivity);
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();

                }


            }
        });

        btnWeeds = (RelativeLayout) findViewById(R.id.weedsTxtViewl);
        btnWeeds.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent weedsActivity = new Intent(getParent(), WeedsActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                weedsActivity.putExtra(getPackageName() + ".type", "Weeds");
                weedsActivity.putExtra(getPackageName() + ".title", "Weeds");
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("WeedsActivity", weedsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("WeedsActivity", weedsActivity);
            }
        });

        btnDiseases = (RelativeLayout) findViewById(R.id.diseasesTxtViewl);
        btnDiseases.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent diseaseActivity = new Intent(getParent(), WeedsActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                diseaseActivity.putExtra(getPackageName() + ".type", "Diseases");
                diseaseActivity.putExtra(getPackageName() + ".title", "Diseases");
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("DiseaseActivity", diseaseActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("DiseaseActivity", diseaseActivity);
            }
        });

        btnInsects = (RelativeLayout) findViewById(R.id.InsectsTxtViewl);
        btnInsects.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent insectsActivity = new Intent(getParent(), TurfListActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                insectsActivity.putExtra(getPackageName() + ".type", "Insects");
                insectsActivity.putExtra(getPackageName() + ".title", "Insects");
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("InsectActivity", insectsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("InsectActivity", insectsActivity);
            }
        });

        btnProducts = (RelativeLayout) findViewById(R.id.productsTxtViewl);
        btnProducts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent productsActivity = new Intent(getParent(), TurfListActivity.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();
                productsActivity.putExtra(getPackageName() + ".type", "Products");
                productsActivity.putExtra(getPackageName() + ".title", "Products");
                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("ProductActivity", productsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("ProductActivity", productsActivity);
            }
        });

        btnCalculators = (RelativeLayout) findViewById(R.id.calculatorl);
        btnCalculators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Choose_CalcActivity.class);

                startActivity(intent);

            }
        });
        btnSprayReports = (RelativeLayout) findViewById(R.id.sprayreportsl);
        btnSprayReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reports = new Intent(getParent(), SprayReprts.class);
                TabGroupActivity parentActivity = (TabGroupActivity) getParent();

                if (android.os.Build.VERSION.SDK_INT <= 10)
                    parentActivity.startChildActivity("SprayActivity", reports.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else
                    parentActivity.startChildActivity("SprayActivity", reports);

            }
        });
        btnMapArea = (RelativeLayout) findViewById(R.id.map);
        btnMapArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    Intent i = new Intent(HomeActivity.this, BayerMappingTurf.class);
                    editor = Prefrence.edit();
                    editor.putBoolean("prepop", true);
                    editor.commit();
                    startActivity(i);

                } else {
                    Toast.makeText(HomeActivity.this, "Make sure you have connected to internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void refresh() {          //refresh is onClick name given to the button
        onRestart();
    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(HomeActivity.this, BayerTurfManagementActivity.class); //your class
        startActivity(i);
        finish();

    }

}