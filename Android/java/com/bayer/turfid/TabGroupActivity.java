package com.bayer.turfid;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TabGroupActivity extends ActivityGroup {

    private ArrayList<String> mIdList;
    Animation animLeft, animRight;
    public int currentPosofList;
    Activity pare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mIdList == null) mIdList = new ArrayList<String>();
        animLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
    }

    /**
     * This is called when a child activity of this one calls its finish method.
     * This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
     * and starts the previous activity.
     * If the last child activity just called finish(),this activity (the parent),
     * calls finish to finish the entire group.
     */
    @Override
    public void finishFromChild(Activity child) {
        LocalActivityManager manager = getLocalActivityManager();
        int index = mIdList.size() - 1;

        if (index < 1) {
            finish();
            return;
        }

        manager.destroyActivity(mIdList.get(index), true);
        mIdList.remove(index);
        index--;
        String lastId = mIdList.get(index);
        //if(lastId!=null) {


        Intent lastIntent = manager.getActivity(lastId).getIntent();

        Window newWindow = manager.startActivity(lastId, lastIntent);
        // newWindow.getDecorView().startAnimation(animRight);
        setContentView(newWindow.getDecorView());

        // }
    }

    /**
     * Starts an Activity as a child Activity to this.
     *
     * @param Id     Unique identifier of the activity to be started.
     * @param intent The Intent describing the activity to be started.
     */
    public void startChildActivity(String Id, Intent intent) {
        Window window = getLocalActivityManager().startActivity(Id, intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
        mIdList.add(Id);


        if (window != null) {
            //window.getDecorView().startAnimation(animLeft);
            if (Id.equalsIgnoreCase("WeedsListActivity") || Id.equalsIgnoreCase("InsectActivity") || Id.equalsIgnoreCase("ProductActivity") || (Id.equalsIgnoreCase("WeatherActivity")) || (Id.equalsIgnoreCase("WeatherlistActivity")) || (Id.equalsIgnoreCase("HomeActivity"))) {
                Activity current = getLocalActivityManager().getActivity(Id);
//        	  if(current.getLocalClassName().equalsIgnoreCase("TurfListActivity"))
//              {
//        		  currentPosofList = 0;
//              }
            }
            setContentView(window.getDecorView());
        }

    }

    /**
     * The primary purpose is to prevent systems before android.os.Build.VERSION_CODES.ECLAIR
     * from calling their default KeyEvent.KEYCODE_BACK during onKeyDown.
     */
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
            onBackPressed();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * If a Child Activity handles KeyEvent.KEYCODE_BACK.
     * Simply override and add this method.
     */
    @Override
    public void onBackPressed() {
        int length = mIdList.size();
        Log.d("tag", "Back" + length);
        if (length > 0) {

            Activity current = getLocalActivityManager().getActivity(mIdList.get(length - 1));
            if (current != null) {
                if (current.getLocalClassName().equalsIgnoreCase("ItemDescriptionActivity")) {

                    ItemDescriptionActivity work = (ItemDescriptionActivity) current;
                    currentPosofList = work.selectionPos;
                    work.resetBitmap();
                }
                if (current.getLocalClassName().equals("ImageSlideViewActivity")) {
                    ImageSlideViewActivity work = (ImageSlideViewActivity) current;
                    work.resetBitmap();
                }

                if (current.getLocalClassName().equals("TurfListActivity")) {
                    currentPosofList = 0;
                }
            }
            current.finish();

        }

    }
}

