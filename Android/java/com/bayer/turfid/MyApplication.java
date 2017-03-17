package com.bayer.turfid;

import android.app.Application;
import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Jasmine Jeyakani on 19-Sep-16.
 */
public class MyApplication extends MultiDexApplication {
    private static Typeface helvetica_font;
    private static Typeface helvetica_bold;
    private static Typeface helvetica_thin;

    public static Typeface getHelvetica_font() {
        return helvetica_font;
    }

    public static Typeface getHelvetica_bold() {
        return helvetica_bold;
    }

    public static Typeface getHelvetica_thin() {
        return helvetica_thin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        helvetica_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica_font.otf");
        helvetica_thin = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica_thin.ttf");
        helvetica_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica_bold.otf");

    }
}

