package com.example.appcenter.companion.courses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.vending.billing.IInAppBillingService;
import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;
import com.example.appcenter.companion.util.IabHelper;
import com.example.appcenter.companion.util.IabResult;
import com.example.appcenter.companion.util.Inventory;
import com.example.appcenter.companion.util.Purchase;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;


public class CourseChaptersList extends Fragment implements AdapterView.OnItemClickListener,IabHelper.OnIabPurchaseFinishedListener,IabHelper.QueryInventoryFinishedListener,IabHelper.OnIabSetupFinishedListener{
    List<String[]> courseChapterInformation;
    private ListView listView;
    private ChaptersListArrayAdapter chaptersListArrayAdapter;
    final int PROGRESS_UPDATE_REQUEST = 1245;
    public static final String KEY_SELECTED_CHAPTER_DATA = "com.example.appcenter.companion.courses.CHAPTER_DATA";

    boolean isPurchased  = false;
    ProgressDialog progressDialog;
    String productID ="android.test.purchased";
    boolean isInAppPurchasesInitialized = false;
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
        if(isPurchased||position==0) {
            String chapterLink = ((String[]) courseChapterInformation.get(position))[3];
            Intent intent = new Intent(getActivity(), CourseWebView.class);
            intent.putExtra(KEY_SELECTED_CHAPTER_DATA, chapterLink);
            startActivityForResult(intent, PROGRESS_UPDATE_REQUEST);
        }else {
            showAlertDialog(getActivity(), this);
        }
    }
    private void showAlertDialog(final Activity activity, final IabHelper.OnIabPurchaseFinishedListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton("Unlock All Chapters?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHelper.launchPurchaseFlow(activity,productID,1001,listener);
            }
        }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((ImageView)dialogLayout.findViewById(R.id.goProDialogImage)).setImageResource(R.drawable.go_pro_couse_dialog_image);
        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.go_pro_couse_dialog_image);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PROGRESS_UPDATE_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                updateProgressPercentage();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }else if (!mHelper.handleActivityResult(requestCode, resultCode, data))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...(Please ensure Internet connection is active.)");
        progressDialog.show();
        courseChapterInformation=getChaptersInformation();
        chaptersListArrayAdapter = new ChaptersListArrayAdapter(getContext(),R.layout.courses_chapters_list_item,courseChapterInformation,getActivity());
        updateProgressPercentage();
        progressDialog.hide();

        //setup In-App Billing
        String base64 ="<YOUR_KEY_HERE>";
        //mHelper setup is started later see onPrepareOptionsMenu
        mHelper = new IabHelper(getActivity(),base64);
        mHelper.startSetup(this);
        //  mainTabActivity= ((MainTabActivity)getActivity());
       // mainTabActivity.queryPurchase(this);
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
        if(isInAppPurchasesInitialized)
        listView.setOnItemClickListener(this);
        return v;
    }


    @Override
    public void onIabSetupFinished(IabResult result) {
        if(!result.isSuccess())
        {
            Log.e(MainTabActivity.TAG,"In-app billing setup Failed"+result);
            mHelper=null;
        }else {
            mHelper.queryInventoryAsync(this);
            Log.e(MainTabActivity.TAG,"setup Success\t"+result);
        }
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if(result.isFailure()) {
            /*
            //If item is already owned
            //Currently not required because we are already querying the purchases.
            if(result.getResponse()!=BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED) {
            }*/
            Log.e(MainTabActivity.TAG, "Purchase Failure\t" + result);
        }else if(info.getSku().equals(productID))
        {
            isPurchased=true;
            Log.e(MainTabActivity.TAG,"Purchase Success\t"+result);
            chaptersListArrayAdapter.setIsProductPurchased(isPurchased);
            chaptersListArrayAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isFailure()) {
            // Handle failure
            Log.e(MainTabActivity.TAG,"FAILED TO QUERY PURCHASE");
        } else if( inv.hasPurchase(productID))
           {
               isPurchased = true;
               chaptersListArrayAdapter.setIsProductPurchased(true);
               chaptersListArrayAdapter.notifyDataSetChanged();
               Log.e(MainTabActivity.TAG,"QUERY SUCCESS ITEM PURCHASED");
           }
        isInAppPurchasesInitialized=true;
        listView.setOnItemClickListener(this);
        progressDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(MainTabActivity.TAG,"In app billing object destoryed");
        if(mHelper!=null)
            mHelper.dispose();
        mHelper=null;
    }
}
