package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;

public class ScrollView extends android.widget.HorizontalScrollView {

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 100;
    private static final String TAG = "MyScrollView";

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

    private OnScrollStoppedListener onScrollStoppedListener;

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollerTask = new Runnable() {

            public void run() {

                int newPosition = getScrollX();
                if (initialPosition - newPosition == 0) {//has stopped

                    if (onScrollStoppedListener != null) {

                        onScrollStoppedListener.onScrollStopped();
                    }
                } else {
                    initialPosition = getScrollX();
                    ScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };


    }


    public void setOnScrollStoppedListener(OnScrollStoppedListener listener) {
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask() {

        initialPosition = getScrollX();
        ScrollView.this.postDelayed(scrollerTask, newCheck);
    }


}