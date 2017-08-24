package com.example.appcenter.companion.videos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.appcenter.companion.R;
import com.vimeo.networking.Configuration;
import com.vimeo.networking.VimeoClient;
import com.vimeo.networking.callbacks.ModelCallback;
import com.vimeo.networking.model.Video;
import com.vimeo.networking.model.VideoFile;
import com.vimeo.networking.model.error.VimeoError;
import java.util.ArrayList;



public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener,OnCompletionListener,OnPreparedListener{
    ImageButton bookmarkImageButton;
    ImageButton fullScreenButton;
    Context context ;
    String[] videoData;
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

    @Override
    public void onPrepared() {
        if(videoView.isPlaying())
            videoView.pause();
    }

    @Override
    public void onCompletion() {
        videoView.restart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getSupportActionBar().setTitle(R.string.title_videos);
        context = getApplicationContext();

        Intent intent = getIntent();
        videoData = intent.getStringArrayExtra(VideoListActivity.KEY_SELECTED_VIDEO_DATA);

        videoViewWrapper = (FrameLayout) findViewById(R.id.video_view_wrapper);
        videoView = (VideoView)findViewById(R.id.video_view);
        bookmarkImageButton = (ImageButton)findViewById(R.id.bookmark);
        TextView chapterNumber = (TextView)findViewById(R.id.chapter_number);
        TextView chapterTitle = (TextView)findViewById(R.id.chapter_title);
        TextView stepsDescription = (TextView)findViewById(R.id.steps_description);
        chapterNumber.setText("Chapter "+videoData[0]);
        chapterTitle.setText(videoData[1]);
        /*
         See Database table VIDEO_DATA in assets.
         Just truncating the text from the string(Warning message).
          */
        videoData[2]=videoData[2].substring(videoData[2].indexOf("<br><b>Step 1"),videoData[2].length());
        stepsDescription.setText(Html.fromHtml(videoData[2]));
        bookmarkImageButton.setOnClickListener(this);

        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);

        VideoControls videoControls=videoView.getVideoControls();
        fullScreenButton =(ImageButton)videoControls.findViewById(R.id.exomedia_controls_next_btn);
        fullScreenButton.setVisibility(View.VISIBLE);
        fullScreenButton.setImageResource(R.drawable.ic_fullscreen_media_stretch);
        fullScreenButton.setOnClickListener(this);
        fullScreenButton.setScaleType(ImageView.ScaleType.FIT_XY);
        setBookmark();
        getVideoUri(videoView,videoData[5]);

    }

    @Override
    protected void onPause() {
        super.onPause();
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
            case R.id.exomedia_controls_next_btn:
                if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    fullScreenButton.setImageResource(R.drawable.ic_fullscreen_media_shrink);
                }
                else {
                    fullScreenButton.setImageResource(R.drawable.ic_fullscreen_media_stretch);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;




        }
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LinearLayout.LayoutParams param;
        if(newConfig.orientation== android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            param  = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                    , 2.0f
            );
        }else
        {
            getSupportActionBar().show();
            param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0, 1.0f
            );
        }
        videoViewWrapper.setLayoutParams(param);

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

                ArrayList<VideoFile> videoFiles = o.getDownload();
                if(videoFiles != null && !videoFiles.isEmpty()) {
                    VideoFile videoFile = videoFiles.get(0); // you could sort these files by size, fps, width/height
                    String link = videoFile.getLink();

                    videoView.setVideoURI(Uri.parse(link));
                }
            }

            @Override
            public void failure(VimeoError error) {
                Toast.makeText(context,R.string.video_loading_error,Toast.LENGTH_SHORT).show();
            }
        });
    }


}