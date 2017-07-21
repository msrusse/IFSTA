package com.example.appcenter.companion.courses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.appcenter.companion.R;

public class CourseWebView extends AppCompatActivity {
    private WebView mwebView;
    private MyWebChromeClient myWebChromeClient;
    String sharedPrefKey = "cmi.core.lesson_location";
    WebView scomWebView;
    ProgressDialog mProgressDialog=null;
    LinearLayout linearLayout;
    String chapter_location ="javascript:this.initialState[\"cmi.core.lesson_location\"]=\'analyzing_the_incident_identifying_potential_hazards_analyzing_the_incident_identifying_potential_hazards_chemical_properties_page_5.html\'";
    String launchFile = "javascript:this.launchFile=\'a002index.html\'";
    String launchSCO = "javascript:Utils.launchSCO()";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_web_view);
        chapter_location = retriveChapterLocationFromSharedPreferences();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        mwebView = (WebView) findViewById(R.id.web_view);
        scomWebView = (WebView)findViewById(R.id.scom_view);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        setupBrowser();

 
    }
    private String retriveChapterLocationFromSharedPreferences() {
        String mapKeyInJSFile = "javascript:this.initialState[\"cmi.core.lesson_location\"]=";
        String defaultFile = "\'analyzing_the_incident_identifying_potential_hazards_analyzing_the_incident_identifying_potential_hazards_chemical_properties_page_1.html\'";
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String sharedPrefValue = sharedPreferences.getString(sharedPrefKey, "DEFAULT");
        if (sharedPrefValue.equals("DEFAULT"))
            return mapKeyInJSFile+defaultFile;
        else
            return mapKeyInJSFile+sharedPrefValue;
    }
    public void startCourse(View v)
    {
        scomWebView.evaluateJavascript(launchSCO,null);
        v.setVisibility(View.GONE);
    }
    private void setupBrowser() {

        //scomWebView = new WebView(this);
        WebSettings settings = scomWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setGeolocationEnabled(false);  // normally set true

        CookieManager.getInstance().setAcceptCookie(false);
        settings.setSupportMultipleWindows(true);
        scomWebView.getSettings().setCacheMode(settings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        scomWebView.clearHistory();;
        scomWebView.clearCache(true);
        scomWebView.clearFormData();
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setSaveFormData(false);

        myWebChromeClient = new MyWebChromeClient(mwebView);
        scomWebView.setWebChromeClient(myWebChromeClient);
        scomWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

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
                    scomWebView.evaluateJavascript(chapter_location,null);
                }

            }
        });
        scomWebView.post(new Runnable() {
            @Override
            public void run() {
                scomWebView.loadUrl("https://samples.ifsta.org/hmfr5/a.htm");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String myJsString ="API.LMSGetValue('cmi.core.lesson_location')";
        scomWebView.evaluateJavascript("(function() { return " + myJsString + "; })();", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e("HELLO", s); // Returns the value from the function
                SharedPreferences.Editor sharedPreferencesEditor = getPreferences(Context.MODE_PRIVATE).edit();
                sharedPreferencesEditor.putString(sharedPrefKey,s);
                sharedPreferencesEditor.commit();
            }
        });

        mwebView.destroy();
        scomWebView.destroy();
    }
}
