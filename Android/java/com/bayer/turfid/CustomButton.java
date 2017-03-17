package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Jasmine Jeyakani on 20-Sep-16.
 */
public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
        setTypeface(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getHelvetica_bold());
        }
    }
}
