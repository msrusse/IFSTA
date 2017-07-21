package com.example.appcenter.companion.courses;

import android.app.Activity;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by appcenter on 7/14/17.
 */

public class MyWebChromeClient extends WebChromeClient{

    WebView mWebView;
    public MyWebChromeClient(WebView webView)
    {
        mWebView = webView;

    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(false);
        settings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setInitialScale(1);
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(mWebView);
        resultMsg.sendToTarget();
        return true;
    }


}
