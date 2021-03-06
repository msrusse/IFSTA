package com.example.appcenter.companion.identify;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;
import com.example.appcenter.companion.util.IabHelper;
import com.example.appcenter.companion.util.IabResult;
import com.example.appcenter.companion.util.Purchase;

import static com.example.appcenter.companion.util.IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED;

public class IdentifyActivity extends Fragment implements View.OnClickListener{


    TextView answeredQuestions;
    DataBaseHelper myDbHelper;
    long totalQuestions;


    public void setAnsweredQuestionsCount()
    {
        long remainingQuestions =totalQuestions-myDbHelper.getAnsweredQuestionsCount();
        answeredQuestions.setText(remainingQuestions+" of "+totalQuestions);

    }

    private void clearAllAnsweredQuestions()
    {
        final Context context = getActivity();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("WARNING!");
        builder1.setIcon(android.R.drawable.ic_delete);
        builder1.setMessage(context.getResources().getString(R.string.identify_delete_preferences_description));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDbHelper.clearAllAnsweredQuestions();
                        setAnsweredQuestionsCount();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.identify_action_bar_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.settings: clearAllAnsweredQuestions();
                                break;
            default:
                    return false;
        }

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            myDbHelper = new DataBaseHelper(getContext());
            myDbHelper.openDataBase();
            totalQuestions = myDbHelper.getTotalQuestionsCount();
        }catch(SQLException sqle){
            throw sqle;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setTitle(R.string.title_identify);
        View v = inflater.inflate(R.layout.activity_identify, container, false);
        LinearLayout layout = (LinearLayout)v.findViewById(R.id.identify_layout);
        answeredQuestions = (TextView)v.findViewById(R.id.answeredQuestions);
        answeredQuestions.setSelected(true);
        layout.setOnClickListener(this);
        // Toast.makeText(getContext(),R.string.toast_identify_notification,Toast.LENGTH_LONG).show();
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        setAnsweredQuestionsCount();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myDbHelper.close();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.identify_layout :
                    Intent intent = new Intent(getActivity(),IdentifyTestActivity.class);
                    startActivity(intent);
                    break;
            }
    }
}
