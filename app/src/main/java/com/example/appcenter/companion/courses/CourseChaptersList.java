package com.example.appcenter.companion.courses;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.vending.billing.IInAppBillingService;
import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;
import com.example.appcenter.companion.util.IabHelper;
import com.example.appcenter.companion.util.IabResult;
import com.example.appcenter.companion.util.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class CourseChaptersList extends Fragment implements AdapterView.OnItemClickListener,IabHelper.OnIabPurchaseFinishedListener{
    List<String[]> courseChapterInformation;
    private ListView listView;
    private ChaptersListArrayAdapter chaptersListArrayAdapter;
    final int PROGRESS_UPDATE_REQUEST = 1;
    public static final String KEY_SELECTED_CHAPTER_DATA = "com.example.appcenter.companion.courses.CHAPTER_DATA";

    private static final String TAG = "InAppBilling";
    static final String ITEM_SKU = "android.test.purchased";
    private IabHelper mHelper;
    private List<String[]> getChaptersInformation()
    {

        DataBaseHelper  myDbHelper = new DataBaseHelper(getContext());
        myDbHelper.openDataBase();
        SQLiteDatabase myDataBase = myDbHelper.getSQLiteDatabaseObject();

        Cursor c = myDataBase.rawQuery("select * from COURSE_CHAPTER_DATA;",null);
        List<String[]> courseChaptersDataList = new ArrayList<String[]>();

        if(c.moveToFirst())
        {
            while (!c.isAfterLast())
            {
                //Look schema of database for this
                String identifyDataListItem[] = {
                        //_id,name,questionsCount,selection
                        c.getString(0),c.getInt(1)+"",c.getString(2),c.getString(3)+"",
                };
                String[] chapterIdFromatting= identifyDataListItem[0].split("_");

                identifyDataListItem[0]="Chapter "+chapterIdFromatting[1];
                courseChaptersDataList.add(identifyDataListItem);
                c.moveToNext();
            }
        }
        c.close();
        myDbHelper.close();
        return  courseChaptersDataList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String chapterLink= ((String[])courseChapterInformation.get(position))[3];
        Intent intent = new Intent(getActivity(),CourseWebView.class);
        intent.putExtra(KEY_SELECTED_CHAPTER_DATA,chapterLink);
        //startActivity(intent);
        startActivityForResult(intent, PROGRESS_UPDATE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PROGRESS_UPDATE_REQUEST)
            if(resultCode==RESULT_CANCELED) {
                updateProgressPercentage();
            }

    }

    private void setupInAppBillingConvinienceHelper()
    {
        String base64EncodedPublicKey ="<YOUR_KEY_HERE>";
        mHelper = new IabHelper(getContext(),base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if(result.isSuccess())
                {
                    Log.e(TAG,"In-app billing Success"+result);
                }else {
                    Log.e(TAG,"In-app billing Failed"+result);

                }
            }
        });
    }

    private boolean queryIfAlreadyPurchased()
    {
        List additionalSkuList = new ArrayList();
        additionalSkuList.add(ITEM_SKU);
        mHelper.queryInventoryAsync(true,additionalSkuList,mQueryFinishedListener);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseChapterInformation=getChaptersInformation();
        chaptersListArrayAdapter = new ChaptersListArrayAdapter(getContext(),R.layout.courses_chapters_list_item,courseChapterInformation);
        updateProgressPercentage();
        setupInAppBillingConvinienceHelper();
        queryIfAlreadyPurchased();
        mHelper.launchPurchaseFlow(getActivity(),ITEM_SKU,10001,this,"mypurchasetoken");

    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if(result.isFailure()){
            return;

        }else if(info.getSku().equals(ITEM_SKU))
        {

        }
    }

    private void updateProgressPercentage() {
        SharedPreferences sharedPreferences;
        List<Integer> stringBooleanMap=new ArrayList<>();
        stringBooleanMap.clear();
        for(String[] chapter:courseChapterInformation){
            String chapterLink= chapter[3].substring(0,chapter[3].indexOf("/"));
            sharedPreferences = getContext().getSharedPreferences(chapterLink,Context.MODE_PRIVATE);
            stringBooleanMap.add(sharedPreferences.getAll().size());
        }
        chaptersListArrayAdapter.updateChapterProgressInformation(stringBooleanMap);
        chaptersListArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_course);
        View v = inflater.inflate(R.layout.fragment_course_chapters_list, container, false);
        listView = (ListView)v.findViewById(R.id.course_chapters_list);
        listView.setAdapter(chaptersListArrayAdapter);
        listView.setOnItemClickListener(this);
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHelper!=null) mHelper.dispose();
        mHelper=null;

    }

}
