package com.example.appcenter.companion.courses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.appcenter.companion.R;

import java.util.HashMap;
import java.util.Map;


public class CourseWebView extends AppCompatActivity {
    private WebView mwebView;
    SharedPreferences sharedPreferences;
    private MyWebChromeClient myWebChromeClient;
    WebView scomWebView;
    static Map<String,String> extraHeaders=new HashMap<String, String>();
    ProgressDialog mProgressDialog=null;
    Button startCourseButton;
    String chapterURL="";
    String chapterLocation ="javascript:this.initialState[\"cmi.core.lesson_location\"]=";
    String launchFile = "javascript:this.launchFile=";
    String launchSCO = "javascript:Utils.launchSCO()";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_web_view);
        Intent intent = getIntent();
        chapterURL = intent.getStringExtra(CourseChaptersList.KEY_SELECTED_CHAPTER_DATA);
        launchFile+="\'";
        launchFile+=chapterURL;
        launchFile+="\'";
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        startCourseButton=(Button)findViewById(R.id.start_course_button);
        mwebView = (WebView) findViewById(R.id.web_view);
        scomWebView = (WebView)findViewById(R.id.scom_view);
        extraHeaders.put("x-appdata-key","w1rSBt11JJl05CmBXfarn5ib2xfx9VWF");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        loadUrl();

    }


    public void startCourse(View v)
    {
        scomWebView.evaluateJavascript(launchSCO,null);
        v.setVisibility(View.GONE);
    }
    private void setBrowserSettings()
    {

        WebSettings settings = scomWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(false);  // normally set true
        settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);

        CookieManager.getInstance().setAcceptCookie(false);
        scomWebView.getSettings().setCacheMode(settings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        scomWebView.clearHistory();
        scomWebView.clearCache(true);
        scomWebView.clearFormData();
        settings.setSaveFormData(false);

    }
    private void loadUrl() {

        setBrowserSettings();
        myWebChromeClient = new MyWebChromeClient(mwebView,mProgressDialog,scomWebView,chapterURL,this);
        scomWebView.setWebChromeClient(myWebChromeClient);
        scomWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(!mProgressDialog.isShowing())
                {
                    mProgressDialog.show();
                }
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    scomWebView.evaluateJavascript(launchFile,null);
                    String chapterTitle = sharedPreferences.getString(chapterURL,"DEFAULT");
                    if(!chapterTitle.equals("DEFAULT")&&(chapterTitle.length()!=0)) {
                        chapterLocation += chapterTitle;
                        scomWebView.evaluateJavascript(chapterLocation,null);
                    }
                    startCourseButton.setVisibility(View.VISIBLE);
                }
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };

        scomWebView.loadUrl("https://appdata.ifsta.org/hmfr5/a.htm",extraHeaders);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
        Note:Destroy both webviews to load correctly next time.
             Not destroying may cause webpage to load only for first time.
        */
        scomWebView.evaluateJavascript("(function() { return API.LMSGetValue(\'cmi.core.lesson_location\')})();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String chapterTitle) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(chapterURL,chapterTitle);
                editor.commit();
                 mwebView.destroy();
                 scomWebView.destroy();
            }
        });
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();

    }


}
