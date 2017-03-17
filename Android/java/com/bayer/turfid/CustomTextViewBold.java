package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Jasmine Jeyakani on 03-Oct-16.
 */

public class CustomTextViewBold extends TextView {
    public CustomTextViewBold(Context context) {
        super(context);
        setTypeface(context);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getHelvetica_bold());
        }
    }
}
