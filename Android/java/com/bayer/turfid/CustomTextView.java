package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Jasmine Jeyakani on 19-Sep-16.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getHelvetica_font());
        }
    }
}
