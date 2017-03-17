package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Admin on 25-Jan-17.
 */

public class CustomTextViewThin extends TextView {
    public CustomTextViewThin(Context context) {
        super(context);
        setTypeface(context);
    }

    public CustomTextViewThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CustomTextViewThin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getHelvetica_thin());
        }
    }
}
