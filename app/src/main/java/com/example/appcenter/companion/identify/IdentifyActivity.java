package com.example.appcenter.companion.identify;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

public class IdentifyActivity extends Fragment implements View.OnClickListener{


    TextView answeredQuestions;
    DataBaseHelper myDbHelper;
    String totalQuestions="";
    public void setAnsweredQuestionsCount()
    {
        float totalAnswered =(float) myDbHelper.getAnsweredQuestionsCount();
        answeredQuestions.setText((int)totalAnswered+" of "+totalQuestions);
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
            totalQuestions = Long.toString(myDbHelper.getTotalQuestionsCount());
        }catch(SQLException sqle){
            throw sqle;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_identify, container, false);
        LinearLayout layout = (LinearLayout)v.findViewById(R.id.identify_layout);
        answeredQuestions = (TextView)v.findViewById(R.id.answeredQuestions);
        answeredQuestions.setSelected(true);
        layout.setOnClickListener(this);

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
