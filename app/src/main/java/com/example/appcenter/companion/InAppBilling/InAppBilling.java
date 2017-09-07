package com.example.appcenter.companion.InAppBilling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.appcenter.companion.util.IabHelper;
import com.example.appcenter.companion.util.IabResult;
import com.example.appcenter.companion.util.Inventory;
import com.example.appcenter.companion.util.Purchase;

import static com.example.appcenter.companion.util.IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED;

/**
 * Created by appcenter on 8/31/17.
 */

public class InAppBilling implements IabHelper.OnIabPurchaseFinishedListener,IabHelper.OnIabSetupFinishedListener {
    private String productID;
    public static final String TAG = "InAppBilling";
    private static String base64;
    private IabHelper mHelper;
    InAppBilling currentContext;
    Activity activity;
    boolean alreadyPurchased=false;

    public InAppBilling(Context context,Activity activity,String productID)
    {

        currentContext=this;
        this.productID=productID;
        this.activity=activity;

        base64 ="<YOUR_KEY_HERE>";
        mHelper = new IabHelper(context,base64);
        mHelper.startSetup(this);

    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if(!result.isSuccess())
        {
            Log.e(TAG,"In-app billing setup Failed"+result);
            mHelper=null;
        }else {
            Log.e(TAG,"setup Success\t"+result);
            launchPurchase();
        }
    }
/*
    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if(result.isFailure())
        {
            Log.e(TAG,"Query FAILURE\t"+result);
        }else {
            if(inv.hasPurchase(productID))
                Log.e(TAG,"Query SUCCESS\t"+result);
        }
    }
*/

    public void launchPurchase()
    {

       if(mHelper!=null)
             mHelper.launchPurchaseFlow(activity,productID,10001,this,"myPurchaseToken");

    }
    public boolean handleActivityResult(int requestCode,int resultCode,Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            return true;
        }
        return false;
    }
    /*
    public boolean handleActivityResult(requestCode, resultCode, data)
    {
        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/
    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        Log.e(TAG,"UNKNOWN\t"+result);
        if(result.isFailure()) {
            //If item is aleready owned
            if(result.getResponse()!=BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED) {
            } else {
                Log.e(TAG, "Purchase Failure\t" + result);

            }
        }else if(info.getSku().equals(productID))
        {
            Log.e(TAG,"Purchase Success\t"+result);
        }
    }
    public void destroy()
    {
        if(mHelper!=null)
            mHelper.dispose();
        mHelper=null;
    }
}
