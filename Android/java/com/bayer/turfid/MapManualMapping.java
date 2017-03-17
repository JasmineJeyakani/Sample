package com.bayer.turfid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapManualMapping extends FragmentActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback, LocationListener {
    private static final String TAG = null;
    private GoogleMap mMap;
    private ArrayList<LatLng> arrayPoints = null;
    private ArrayList<Double> angle = null;
    PolygonOptions polygonOptions;
    Polygon polygon;
    ImageView markerButton;
    SharedPreferences areaval;
    SharedPreferences.Editor editor;
    private boolean checkClick = false;
    View mapView;
    TextView areaView;
    ImageView img;
    ImageView close;
    RelativeLayout save_btn;
    Float areavar;
    DecimalFormat df = new DecimalFormat(".####");
    MarkerOptions markerOptions;
    Marker marker;
    List<Marker> markerList;
    double areaPoly;
    String ClubName, AreaName;
    List<MarkerWithTag> markersWithTags;
    static int tag = 0;
    String maptype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_mapping_activity);
        areaView = (TextView) findViewById(R.id.areaVal);
        markerButton = (ImageView) findViewById(R.id.list_image);
        close = (ImageView) findViewById(R.id.close);
        save_btn = (RelativeLayout) findViewById(R.id.save1);
        img = (ImageView) findViewById(R.id.img_logo);
        arrayPoints = new ArrayList<LatLng>();
        markersWithTags = new ArrayList<MarkerWithTag>();
        angle = new ArrayList<>();
        markerList = new ArrayList<Marker>();
        areaval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        maptype = areaval.getString("mapping", "default");

        areaval = getApplicationContext().getSharedPreferences("SprayCalc", MODE_PRIVATE);
        ClubName = areaval.getString("mapClubName", "default");
        AreaName = areaval.getString("mapAreaName", "default");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapManualMapping.this, BayerTurfManagementActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = fm.getView();
        fm.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Gpstracker gpstracker = new Gpstracker(MapManualMapping.this);
        Location location = gpstracker.getLocation();

        if (location != null) {

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(20.0f)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 1);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            locationButton.setLayoutParams(layoutParams);
            layoutParams.setMargins(0, 0, 0, 20);

        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                areaView.setText("0");
                remove(marker.getTag().toString());
                marker.remove();
                if (polygon != null) {
                    polygon.remove();
                    polygon = null;
                }
                drawPolgon();
                return true;
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                arrayPoints.clear();
                markerList.clear();
                markersWithTags.clear();
                checkClick = false;
                angle.clear();
                areaView.setText("0");
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                if (marker.getTag() != null)
                    remove(marker.getTag().toString());
                /*
                    NOTE: this is not working. The postion of the marker has been already
                    changed and removing it from the arraypoints doesn't work
                * */
                arrayPoints.remove(marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                addMarker(marker.getPosition());

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (maptype.equals("markmyarea")) {
                    Intent i = new Intent(MapManualMapping.this, BayerMappingTurf.class);
                    editor = areaval.edit();
                    editor.putBoolean("prepop", false);
                    editor.putString("clubNameRE", ClubName);
                    editor.putString("areaNameRE", AreaName);
                    editor.commit();

                    startActivity(i);
                } else if (maptype.equals("markmyareacalc")) {
                    Intent i = new Intent(MapManualMapping.this, MappingActivityCalc.class);
                    startActivity(i);
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (areaView.getText().toString().equals("") || areaView.getText().toString().equalsIgnoreCase("m2") || areaView.getText().toString().equals("0")) {
                    Toast.makeText(MapManualMapping.this, "Select Area first", Toast.LENGTH_SHORT).show();
                } else {

                    if (maptype.equals("markmyarea")) {
                        Intent i1 = new Intent(MapManualMapping.this, BayerMappingTurf.class);
                        areavar = Float.parseFloat(areaView.getText().toString());

                        ClubName = areaval.getString("mapClubName", "default");
                        AreaName = areaval.getString("mapAreaName", "default");
                        DatabaseHandler db = new DatabaseHandler(MapManualMapping.this);
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
                        editor.putString("setArea", AreaName);
                        editor.putString("setSprayMake", sprayMake);
                        editor.putString("setSprayModel", sprayModel);
                        editor.putBoolean("prepop", false);
                        editor.putString("clubNameRE", ClubName);
                        editor.putString("areaNameRE", AreaName);
                        editor.commit();

                        db.insertMapTable(ClubName, AreaName, String.valueOf(areavar));
                        db.insertAreaTable(ClubName, AreaName, String.valueOf(areavar));

                        startActivity(i1);
                    } else if (maptype.equals("markmyareacalc")) {

                        Intent i1 = new Intent(MapManualMapping.this, SprayActivity12_Areamap.class);
                        areavar = Float.parseFloat(areaView.getText().toString());
                        i1.putExtra("areaValue", areavar);
                        startActivity(i1);

                    }
                }

            }
        });

    }

    private void addMarker(LatLng point) {
        markerOptions = new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.pindrop)).draggable(true);
        marker = mMap.addMarker(markerOptions);
        if (checkClick == false) {
            marker.setTag(tag);
            markersWithTags.add(new MarkerWithTag(marker, marker.getPosition()));
            tag++;
            arrayPoints.add(new LatLng(point.latitude, point.longitude));
            Log.d("sreeninin", arrayPoints.toString());
            drawPolgon();
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        addMarker(point);

    }

    @Override
    public void onMapLongClick(LatLng point) {
//        arrayPoints.clear();
//        checkClick = false;
//        areaView.setText("0");
    }

    @Override
    public void onBackPressed() {

        if (maptype.equals("markmyarea")) {
            Intent i = new Intent(MapManualMapping.this, BayerMappingTurf.class);
            editor = areaval.edit();
            editor.putBoolean("prepop", false);
            editor.putString("clubNameRE", ClubName);
            editor.putString("areaNameRE", AreaName);
            editor.commit();

            startActivity(i);
        } else if (maptype.equals("markmyareacalc")) {

            Intent i = new Intent(MapManualMapping.this, MappingActivityCalc.class);
            startActivity(i);
        }

    }

    public void drawPolgon() {

        if (polygon != null) {
            polygon.remove();
            polygon = null;
        }

        if (arrayPoints.size() < 3) return;

        if (arrayPoints.size() >= 3) {
            calculateAngles();

            arrayPoints = sortByAngle(arrayPoints, angle);
            polygonOptions = new PolygonOptions();
            polygonOptions.addAll(arrayPoints);
            polygonOptions.strokeColor(Color.parseColor("#0cacc6"));
            polygonOptions.strokeWidth(7);
            polygonOptions.fillColor(Color.parseColor("#340cacc6"));

            polygon = mMap.addPolygon(polygonOptions);
            areaPoly = SphericalUtil.computeArea(arrayPoints);
            String areaString = String.valueOf(df.format(areaPoly));
            areaView.setText(areaString);

        }
    }

    private void calculateAngles() {
        angle.clear();
        double medianLat = 0.0;
        double medianLng = 0.0;
        ArrayList<Double> concave = new ArrayList<>();
        for (int i = 0; i < arrayPoints.size(); i++) {
            double lat = arrayPoints.get(i).latitude;
            double lng = arrayPoints.get(i).longitude;
            medianLat = medianLat + lat;
            medianLng = medianLng + lng;
        }
        medianLat = medianLat / arrayPoints.size();
        medianLng = medianLng / arrayPoints.size();

        for (int i = 0; i < arrayPoints.size(); i++) {
            double lat = arrayPoints.get(i).latitude;
            double lng = arrayPoints.get(i).longitude;
            double eachAngle = Math.atan2((lng - medianLng), (lat - medianLat));
            angle.add(eachAngle);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(20.0f).build());
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.animateCamera(cameraUpdate);
        mMap.moveCamera(cameraUpdate);


    }

    private ArrayList<LatLng> sortByAngle(ArrayList<LatLng> latLng, ArrayList<Double> degree) {

        List<SortByAngle> tuples = new ArrayList<>();

        for (int i = 0; i < latLng.size(); i++) {
            tuples.add(new SortByAngle(latLng.get(i).latitude, latLng.get(i).longitude, degree.get(i)));
        }

        Collections.sort(tuples, new Comparator<SortByAngle>() {
            @Override
            public int compare(SortByAngle c1, SortByAngle c2) {
                return Double.compare(c1.angle, c2.angle);
            }
        });

        for (int i = 0; i < tuples.size(); i++) {
            latLng.set(i, new LatLng(tuples.get(i).latitude, tuples.get(i).longitude));
        }

        return latLng;
    }

    private class SortByAngle {

        private double latitude;
        private double longitude;
        private double angle;

        public SortByAngle(double latitude, double longitude, double angle) {

            this.latitude = latitude;
            this.longitude = longitude;
            this.angle = angle;
        }
    }

    public void remove(String toSearch) {
        for (MarkerWithTag each : markersWithTags) {
            if (each.m.getTag() != null) {
                if (each.m.getTag().toString() == toSearch) {
                    arrayPoints.remove(each.s);
                    markersWithTags.remove(each);
                    return;
                }
            }
        }
    }

    public class MarkerWithTag {
        private Marker m;
        private LatLng s;

        public MarkerWithTag(Marker m, LatLng s) {
            this.m = m;
            this.s = s;
        }
    }
}