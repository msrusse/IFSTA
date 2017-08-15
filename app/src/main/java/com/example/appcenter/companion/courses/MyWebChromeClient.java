package com.example.appcenter.companion.courses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by appcenter on 7/14/17.
 */

public class MyWebChromeClient extends WebChromeClient{

    WebView mWebView;
    ProgressDialog mProgressDialog;
    public MyWebChromeClient(WebView webView,ProgressDialog dialog)
    {
        mWebView = webView;
        mProgressDialog=dialog;

    }



    private   String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);

        if (extension != null) {

            switch (extension) {
                case "js":
                    return "text/javascript";
                case "woff":
                    return "application/font-woff";
                case "woff2":
                    return "application/font-woff2";
                case "ttf":
                    return "application/x-font-ttf";
                case "eot":
                    return "application/vnd.ms-fontobject";
                case "svg":
                    return "image/svg+xml";
            }

            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        return type;
    }
    private WebResourceResponse getResponse(WebResourceRequest request)
    {
        String url = request.getUrl().toString();
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request requestBuilder = new Request.Builder()
                    .url(url.trim())
                    .addHeader("x-appdata-key","w1rSBt11JJl05CmBXfarn5ib2xfx9VWF") // Example header
                    .build();

            Response response = httpClient.newCall(requestBuilder).execute();

            return new WebResourceResponse(
                    getMimeType(url),
                    response.header("content-encoding", "utf-8"),
                    response.body().byteStream()
            );

        } catch (Exception e) {
            return null;
        }
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

        mWebView.getSettings().setCacheMode(settings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.clearFormData();
        settings.setSaveFormData(false);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressDialog.hide();
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                String ext = MimeTypeMap.getFileExtensionFromUrl(request.getUrl().toString());
                String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
                        try {
                        HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl().toString()).openConnection();
                        connection.addRequestProperty("x-appdata-key","w1rSBt11JJl05CmBXfarn5ib2xfx9VWF");
                        return new WebResourceResponse(mime,"utf-8",connection.getInputStream());
                    }catch (Exception e)
                    {
                    }

                return super.shouldInterceptRequest(view,request);

            }


        });
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setInitialScale(1);

        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(mWebView);
        resultMsg.sendToTarget();
        return true;
    }



}
