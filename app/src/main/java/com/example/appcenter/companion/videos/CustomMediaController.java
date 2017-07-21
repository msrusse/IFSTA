package com.example.appcenter.companion.videos;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;

import com.example.appcenter.companion.R;

/**
 * Created by appcenter on 6/1/17.
 */

public class CustomMediaController extends MediaController {
    public final static int fullScreenButtonId = 12345;
    public CustomMediaController(Context context) {
        super(context,true);
    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
        super.hide();
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        ImageButton fullScreen = new ImageButton(getContext());
        fullScreen.setId(fullScreenButtonId);
        fullScreen.setImageResource(R.drawable.ic_fullscreen_media_stretch);
        fullScreen.setBackgroundColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity =  Gravity.RIGHT|Gravity.TOP;
        addView(fullScreen, params);
        fullScreen.setOnClickListener((VideoPlayActivity)getContext());
    }

    public boolean dispatchKeyEvent(KeyEvent event)
    {
        Log.e("HTML",event.getKeyCode()+"---11FROM CUSTOM MEDIA CONTROLLER");

        return super.dispatchKeyEvent(event);
    }

}
