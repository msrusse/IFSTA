package com.example.appcenter.companion.videos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.appcenter.companion.R;
import com.example.appcenter.companion.videos.CustomMediaController;
import com.example.appcenter.companion.videos.VideoListActivity;
import com.vimeo.networking.Configuration;
import com.vimeo.networking.VimeoClient;
import com.vimeo.networking.callbacks.ModelCallback;
import com.vimeo.networking.model.Video;
import com.vimeo.networking.model.VideoFile;
import com.vimeo.networking.model.error.VimeoError;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import static java.net.Authenticator.RequestorType.SERVER;


public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
    ImageButton bookmarkImageButton;
    Context context ;
    String[] videoData;
    MediaController mc;
    private ProgressDialog mProgressDialog;
    private VideoView videoView;
    boolean isBookmarkChanged;
    private FrameLayout videoViewWrapper;
    VimeoClient mApiClient;
    private void setBookmark()
    {
        isBookmarkChanged = Integer.parseInt(videoData[4])!=0;
        if(isBookmarkChanged) {
            bookmarkImageButton.setImageResource(R.mipmap.ic_bookmark);
            bookmarkImageButton.setTag(new Boolean(true));
        }else
        {
            bookmarkImageButton.setTag(new Boolean(false));
        }
    }
    private void hideMediaController()
    {
        if(mc.getVisibility()==View.GONE)
        {
            mc.show();
            mc.setVisibility(View.VISIBLE);
        }

    }

    private void setMediaController(VideoView videoView)
    {
        mc = new CustomMediaController(this);
        mc.setMediaPlayer(videoView);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        mc.setLayoutParams(lp);
        ((ViewGroup) mc.getParent()).removeView(mc);
        videoViewWrapper.addView(mc);
        videoView.setMediaController(mc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(R.string.progress_dialog_description);
        mProgressDialog.show();

        Intent intent = getIntent();
        videoData = intent.getStringArrayExtra(VideoListActivity.KEY_SELECTED_VIDEO_DATA);

        context = getApplicationContext();
        videoViewWrapper = (FrameLayout) findViewById(R.id.video_view_wrapper);
        videoView = (VideoView)findViewById(R.id.video_view);
        bookmarkImageButton = (ImageButton)findViewById(R.id.bookmark);
        TextView chapterNumber = (TextView)findViewById(R.id.chapter_number);
        TextView chapterNumberRed = (TextView)findViewById(R.id.chapter_number_red);
        TextView chapterTitle = (TextView)findViewById(R.id.chapter_title);
        TextView chapterTitleRed = (TextView)findViewById(R.id.chapter_title_red);
        TextView stepsDescription = (TextView)findViewById(R.id.steps_description);
        chapterNumber.setText("Chapter "+videoData[0]);
        chapterNumberRed.setText("Chapter "+videoData[0]);
        chapterTitle.setText(videoData[1]);
        chapterTitleRed.setText(videoData[1]);
        stepsDescription.setText(Html.fromHtml(videoData[2]));
        bookmarkImageButton.setOnClickListener(this);

        videoView.setOnTouchListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        videoView.setOnCompletionListener(this);

        setBookmark();
        setMediaController(videoView);
        getVideoUri(videoView,"206631738");

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Pause video playing if user shuts off the screen
        videoView.pause();

    }

    @Override
    public void onBackPressed() {
        boolean check = (Boolean)bookmarkImageButton.getTag();
        if(check!=isBookmarkChanged)
        {
            Intent intent = new Intent();
            intent.putExtra(VideoListActivity.KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_VIDEO_ID,videoData[0]);
            intent.putExtra(VideoListActivity.KEY_IS_BOOKMARKED_IN_VIDEO_PLAY_ACTIVITY,check);
            setResult(RESULT_OK,intent);
            finish();
        }
        super.onBackPressed();

    }

    private  void toogleFullScreen()
    {
        int currentOrientation = getRequestedOrientation();

        LinearLayout.LayoutParams param;
        if(currentOrientation==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getSupportActionBar().hide();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            param  = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                    , 2.0f
            );
        }else
        {
            getSupportActionBar().show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1.0f
            );
        }
        videoViewWrapper.setLayoutParams(param);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.bookmark: boolean check = (Boolean)bookmarkImageButton.getTag();
                if(check ==false)
                {
                    bookmarkImageButton.setImageResource(R.mipmap.ic_bookmark);
                }else
                {
                    bookmarkImageButton.setImageResource(R.mipmap.ic_bookmark_unchecked);
                }
                bookmarkImageButton.setTag(!check);
                break;
            case CustomMediaController.fullScreenButtonId:
                toogleFullScreen();
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideMediaController();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mProgressDialog.hide();
        mProgressDialog.dismiss();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("HTML",what+"--"+extra);
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public Configuration.Builder getAccessTokenBuilder(Context context)
    {
        String accessToken = context.getString(R.string.vimeo_access_token);
        return new Configuration.Builder(accessToken);
    }

    public void getVideoUri(final VideoView videoView, final String videoID)
    {
        String VIDEO_URI = "/videos/"+videoID;
        Configuration.Builder configBuilder= getAccessTokenBuilder(context);
        VimeoClient.initialize(configBuilder.build());
        mApiClient = VimeoClient.getInstance();
        mApiClient.fetchNetworkContent(VIDEO_URI, new ModelCallback<Video>(Video.class) {

            @Override
            public void success(Video o) {

                ArrayList<VideoFile> videoFiles = o.files;
                if(videoFiles != null && !videoFiles.isEmpty()) {
                    VideoFile videoFile = videoFiles.get(0); // you could sort these files by size, fps, width/height
                    String link = videoFile.getLink();
                    videoView.setVideoURI(Uri.parse(link));

                }
            }

            @Override
            public void failure(VimeoError error) {
                Toast.makeText(context,R.string.video_loading_error,Toast.LENGTH_SHORT).show();
                mProgressDialog.hide();
            }
        });
    }


}