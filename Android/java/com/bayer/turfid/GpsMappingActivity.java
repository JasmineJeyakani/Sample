package com.bayer.turfid;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GpsMappingActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 5 * 1;
    private static final long FASTEST_INTERVAL = 1000 * 5 * 1;
    LocationRequest mLocationRequest;
    private ArrayList<LatLng> arrayPoints = null;
    GoogleMap googleMap;
    PolygonOptions polygonOptions;
    Polygon polygon;
    SharedPreferences areaval;
    SharedPreferences.Editor editor;
    TextView areaView;
    ImageView img;
    ImageView close;
    RelativeLayout save_btn;
    Float areavar;
    DecimalFormat df = new DecimalFormat(".####");
    Marker marker;
    List<Marker> markerList;
    double areaPoly;
    public static int API_STATE_NETWORK_LOST = 0;
    public static int API_STATE_NULL = 0;
    public static int API_STATE_RECREATING = 0;
    public static int API_STATE_SERVICE_DISCONNECTED = 0;
    public static int API_STATE_UP_AND_RUNNING = 0;
    private static LocationRequest REQUEST_BALANCED_POWER_ACCURACY;
    private static LocationRequest REQUEST_HIGH_ACCURACY;
    public static int RESOLUTION_FOR_GOOGLE_API_CLIENT;
    Activity act;
    private int apiState;
    private boolean setVal;
    private Location lastUpdatedLocation;
    private LocationSource.OnLocationChangedListener locationChangedListeners;
    private GoogleApiClient mGoogleApiClient;
    private LocationSource.OnLocationChangedListener mapLocationChangedListener;
    private int retryCount;
    String ClubName, AreaName;
    String maptype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_mapping_activity);
        areaView = (TextView) findViewById(R.id.areaVal);
        markerList = new ArrayList<Marker>();
        close = (ImageView) findViewById(R.id.close);
        save_btn = (RelativeLayout) findViewById(R.id.save1);
        img = (ImageView) findViewById(R.id.img_logo);


        areaval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        maptype = areaval.getString("mapping", "default");


        Log.d(TAG, "onCreate ...............................");
        REQUEST_HIGH_ACCURACY = LocationRequest.create().setInterval(INTERVAL).setFastestInterval(FASTEST_INTERVAL).setSmallestDisplacement(1).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        REQUEST_BALANCED_POWER_ACCURACY = LocationRequest.create().setInterval(INTERVAL).setFastestInterval(FASTEST_INTERVAL).setSmallestDisplacement(1).setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        API_STATE_NULL = 0;
        API_STATE_SERVICE_DISCONNECTED = 1;
        API_STATE_NETWORK_LOST = 2;
        API_STATE_RECREATING = 3;
        API_STATE_UP_AND_RUNNING = 4;
        RESOLUTION_FOR_GOOGLE_API_CLIENT = 51;

        //initMGoogleApiClient();
        arrayPoints = new ArrayList<>();
        apiState = API_STATE_RECREATING;

        ClubName = areaval.getString("mapClubName", "default");
        AreaName = areaval.getString("mapAreaName", "default");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GpsMappingActivity.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fm.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap mgoogleMap) {

        googleMap = mgoogleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (areaView.getText().toString().equals("") || areaView.getText().toString().equalsIgnoreCase("m2") ||
                        String.valueOf(Math.round(Float.parseFloat(areaView.getText().toString()))).equals("0"))


                {
                    Toast.makeText(GpsMappingActivity.this, "Select Area first", Toast.LENGTH_SHORT).show();
                } else {


                    if (maptype.equals("walkmyarea")) {
                        Intent i1 = new Intent(GpsMappingActivity.this, BayerMappingTurf.class);
                        areavar = Float.parseFloat(areaView.getText().toString());
                        DatabaseHandler db = new DatabaseHandler(GpsMappingActivity.this);
                        List<String> sprayMakelist = db.golfSprayMake(ClubName);
                        String sprayMake;
                        if (sprayMakelist.size() == 0) {
                            sprayMake = "SHURflo";
                        } else {
                            sprayMake = sprayMakelist.get(0);
                        }

                        List<String> sprayModellist = db.golfSprayModel(ClubName);
                        String sprayModel;
                        if (sprayModellist.size() == 0) {
                            sprayModel = "SRS-600";
                        } else {
                            sprayModel = sprayModellist.get(0);
                        }


                        editor = areaval.edit();
                        editor.putString("setClub", ClubName);
                        editor.putString("setSprayMake", sprayMake);
                        editor.putString("setSprayModel", sprayModel);
                        editor.putBoolean("prepop", false);
                        editor.putString("clubNameRE", ClubName);
                        editor.putString("areaNameRE", AreaName);
                        editor.commit();
                        db.insertMapTable(ClubName, AreaName, String.valueOf(areavar));
                        db.insertAreaTable(ClubName, AreaName, String.valueOf(areavar));
//
//
                        startActivity(i1);
                    } else if (maptype.equals("walkmyareacalc")) {
                        Intent i1 = new Intent(GpsMappingActivity.this, SprayActivity12_Areamap.class);
                        areavar = Float.parseFloat(areaView.getText().toString());
                        i1.putExtra("areaValue", areavar);
//
                        startActivity(i1);
                    }

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (maptype.equals("walkmyarea")) {
                    Intent i = new Intent(GpsMappingActivity.this, BayerMappingTurf.class);
                    editor = areaval.edit();
                    editor.putBoolean("prepop", false);
                    editor.putString("clubNameRE", ClubName);
                    editor.putString("areaNameRE", AreaName);
                    editor.commit();
                    startActivity(i);
                } else if (maptype.equals("walkmyareacalc")) {
                    Intent i = new Intent(GpsMappingActivity.this, MappingActivityCalc.class);
                    startActivity(i);
                }


            }
        });


    }


    public void onStart() {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        this.mGoogleApiClient.disconnect();
    }

    public void onConnected(Bundle bundle) {
        this.apiState = API_STATE_UP_AND_RUNNING;
        setRequestBalancedPowerAccuracy();
        bindGpsStateIfMeasuringActive();
        if (!this.setVal) {
            this.lastUpdatedLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
            onLocationChanged(this.lastUpdatedLocation);
        }
    }

    public void requestHighAccuracy() {
        if (this.apiState == API_STATE_UP_AND_RUNNING) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, REQUEST_HIGH_ACCURACY, (LocationListener) this);
        } else {
            Toast.makeText(this.act, "Error : Balanced power accuracy failed to set due to suspended or disconnected GoogleApiClient  ", 0).show();
        }
    }

    public void setRequestBalancedPowerAccuracy() {
        if (this.apiState == API_STATE_UP_AND_RUNNING) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, REQUEST_BALANCED_POWER_ACCURACY, (LocationListener) this);
        } else {
            Toast.makeText(this.act, "Error : Balanced power accuracy failed to set due to suspended or disconnected GoogleApiClient  ", 0).show();
        }
    }

    public Location getLastLocation() {
        return this.lastUpdatedLocation;
    }

    public void updateLastMapLocation() {
        if (!this.setVal || this.lastUpdatedLocation == null) {
            this.lastUpdatedLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
            if (this.lastUpdatedLocation != null && this.mapLocationChangedListener != null) {
                this.mapLocationChangedListener.onLocationChanged(this.lastUpdatedLocation);
                return;
            }
            return;
        }
        onLocationChanged(this.lastUpdatedLocation);
    }

    public void onConnectionSuspended(int i) {
        if (i == 1) {
            this.apiState = API_STATE_SERVICE_DISCONNECTED;
        } else if (i == 2) {
            this.apiState = API_STATE_NETWORK_LOST;
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("GoogleLocationApiClient", "  onConnectionFailed  " + connectionResult.getErrorMessage());
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this.act, RESOLUTION_FOR_GOOGLE_API_CLIENT);
                return;
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
                return;
            }
        }

    }


    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        this.mapLocationChangedListener = onLocationChangedListener;
        updateLastMapLocation();
    }

    public void deactivate() {
        this.mapLocationChangedListener = null;
    }

    public void addLocationListener(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        this.locationChangedListeners = onLocationChangedListener;
        Log.i("GoogleLocationApiClient", "  location listener added size ");
    }

    public void deactivate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        this.locationChangedListeners = null;
        Log.i("GoogleLocationApiClient", "  location listener removed ");
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }


    public class FastConvexHull implements ConvexHullAlgorithm {

        @Override
        public ArrayList<LatLng> execute(ArrayList<LatLng> points) {
            ArrayList<LatLng> xSorted = (ArrayList<LatLng>) points.clone();
            Collections.sort(xSorted, new XCompare());

            int n = xSorted.size();

            LatLng[] lUpper = new LatLng[n];

            lUpper[0] = xSorted.get(0);
            lUpper[1] = xSorted.get(1);

            int lUpperSize = 2;

            for (int i = 2; i < n; i++) {
                lUpper[lUpperSize] = xSorted.get(i);
                lUpperSize++;

                while (lUpperSize > 2 && !rightTurn(lUpper[lUpperSize - 3], lUpper[lUpperSize - 2], lUpper[lUpperSize - 1])) {
                    // Remove the middle point of the three last
                    lUpper[lUpperSize - 2] = lUpper[lUpperSize - 1];
                    lUpperSize--;
                }
            }

            LatLng[] lLower = new LatLng[n];

            lLower[0] = xSorted.get(n - 1);
            lLower[1] = xSorted.get(n - 2);

            int lLowerSize = 2;

            for (int i = n - 3; i >= 0; i--) {
                lLower[lLowerSize] = xSorted.get(i);
                lLowerSize++;

                while (lLowerSize > 2 && !rightTurn(lLower[lLowerSize - 3], lLower[lLowerSize - 2], lLower[lLowerSize - 1])) {
                    // Remove the middle point of the three last
                    lLower[lLowerSize - 2] = lLower[lLowerSize - 1];
                    lLowerSize--;
                }
            }

            ArrayList<LatLng> result = new ArrayList<LatLng>();

            for (int i = 0; i < lUpperSize; i++) {
                result.add(lUpper[i]);
            }

            for (int i = 1; i < lLowerSize - 1; i++) {
                result.add(lLower[i]);
            }

            return result;
        }

        private boolean rightTurn(LatLng a, LatLng b, LatLng c) {
            return (b.latitude - a.latitude) * (c.longitude - a.longitude) - (b.longitude - a.longitude) * (c.latitude - a.latitude) > 0;
        }

        private class XCompare implements Comparator<LatLng> {
            @Override
            public int compare(LatLng o1, LatLng o2) {
                return (new Float(o1.latitude)).compareTo(new Float(o2.latitude));
            }
        }
    }


    private void bindGpsStateIfMeasuringActive() {
    }


    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,INTERVAL,DISTANCE, (android.location.LocationListener) locationListener);

        Log.d(TAG, "Location update started ..............: ");
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        for (int i = 0; i < arrayPoints.size(); i++) {
            if (arrayPoints.get(i) == latLng) {

            } else {
                arrayPoints.add(latLng);
                countPolygonPoints();
            }
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(20.0f).build());
        MarkerOptions options = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.manual_direction_icon)).draggable(true);
        for (int i = 0; i < arrayPoints.size(); i++) {
            Log.d("AArrayPoints", String.valueOf(arrayPoints.get(i)));
        }
        Log.d("size", String.valueOf(arrayPoints.size()));

        options.position(latLng);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.animateCamera(cameraUpdate);
        googleMap.moveCamera(cameraUpdate);
        if (marker != null) {
            marker.remove();
        }
        marker = googleMap.addMarker(options);
        arrayPoints.add(latLng);
        countPolygonPoints();
        updateLocation(location);
    }

    public void onExternalLocationChanged(Location location) {
        updateLocation(location);
    }

    public void updateLocation(Location location) {
        if (location != null) {
            // Toast.makeText(this, "loc"+location.getLatitude(), Toast.LENGTH_SHORT).show();
            this.lastUpdatedLocation = location;
            if (this.mapLocationChangedListener != null) {
                this.mapLocationChangedListener.onLocationChanged(location);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                MarkerOptions options = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.manual_direction_icon)).draggable(true);
                if (marker != null) {
                    marker.remove();
                }
                marker = googleMap.addMarker(options);
                arrayPoints.add(latLng);
                countPolygonPoints();

            }
            if (this.locationChangedListeners != null) {
                this.locationChangedListeners.onLocationChanged(location);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                MarkerOptions options = new MarkerOptions().position(latLng);
                if (marker != null) {
                    marker.remove();
                }
                marker = googleMap.addMarker(options);
                arrayPoints.add(latLng);
                countPolygonPoints();
            }
        }
    }

    public GoogleApiClient getmGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }


    public void countPolygonPoints() {
        if (arrayPoints.size() == 3) {
            polygonOptions = new PolygonOptions();
            FastConvexHull fch = new FastConvexHull();
            ArrayList<LatLng> val = fch.execute(arrayPoints);
            double areaPoly = SphericalUtil.computeArea(val);
            String areaString = String.valueOf(df.format(areaPoly));

            if(areaString.startsWith("."))
            {
                areaView.setText("0");
            }
            else {
                areaView.setText("" + areaString);
            }
            polygonOptions.addAll(val);
            polygonOptions.strokeColor(Color.parseColor("#0cacc6"));
            polygonOptions.strokeWidth(7);
            polygonOptions.fillColor(Color.parseColor("#340cacc6"));
            polygon = googleMap.addPolygon(polygonOptions);

        } else if (arrayPoints.size() > 3) {
            polygon.remove();
            polygonOptions = new PolygonOptions();
            FastConvexHull fch = new FastConvexHull();
            ArrayList<LatLng> val = fch.execute(arrayPoints);
            areaPoly = SphericalUtil.computeArea(val);
            String areaString = String.valueOf(df.format(areaPoly));
            //areaView.setText("" + areaString);

            if(areaString.startsWith("."))
            {
                areaView.setText("0");
            }
            else {
                areaView.setText("" + areaString);
            }
            polygonOptions.addAll(val);
            polygonOptions.strokeColor(Color.parseColor("#0cacc6"));
            polygonOptions.strokeWidth(7);
            polygonOptions.fillColor(Color.parseColor("#340cacc6"));
            Set<LatLng> set1 = new HashSet<>();
            Set<LatLng> set2 = new HashSet<>();
            set1.addAll(val);
            set2.addAll(arrayPoints);
            set2.removeAll(set1);
            for (LatLng diff : set2) {
                for (int i = 0; i < markerList.size(); i++) {


                    marker = markerList.get(i);
                    if (marker.getPosition().equals(diff)) {
                        marker.remove();

                    } else {
                    }

                }

            }
            polygon = googleMap.addPolygon(polygonOptions);
        }
    }


    @Override
    public void onBackPressed() {
        if (maptype.equals("walkmyarea")) {
            Intent i = new Intent(GpsMappingActivity.this, BayerMappingTurf.class);
            editor = areaval.edit();
            editor.putBoolean("prepop", false);
            editor.putString("clubNameRE", ClubName);
            editor.putString("areaNameRE", AreaName);
            editor.commit();
            startActivity(i);
        } else if (maptype.equals("walkmyareacalc")) {
            Intent i = new Intent(GpsMappingActivity.this, MappingActivityCalc.class);
            startActivity(i);
        }
    }

}
