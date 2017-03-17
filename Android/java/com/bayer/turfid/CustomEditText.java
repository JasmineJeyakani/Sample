package com.bayer.turfid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Jasmine Jeyakani on 10-Oct-16.
 */

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        setTypeface(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getHelvetica_font());
        }
    }
}
