package com.example.appcenter.companion.courses;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by krishna on 7/22/2017.
 */

public class EqualWidthHeightTextView extends AppCompatTextView {

    public EqualWidthHeightTextView(Context context) {
        super(context);
    }

    public EqualWidthHeightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualWidthHeightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        int padding = 30;
        int r = (metrics.widthPixels/4)-padding;


        setMeasuredDimension(r, r);

    }
}