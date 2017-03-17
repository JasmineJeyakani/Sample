package com.bayer.turfid;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Jasmine Jeyakani on 15-Oct-16.
 */

public interface ConvexHullAlgorithm {
    ArrayList<LatLng> execute(ArrayList<LatLng> points);
}
