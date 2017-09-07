package com.example.appcenter.companion.examprep;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;
import com.example.appcenter.companion.examprep.ExamPrepChaptersList;
import com.example.appcenter.companion.examprep.ExamPrepReportsActivity;
import com.example.appcenter.companion.examprep.ExamPrepSettings;

import java.util.ArrayList;
import java.util.List;


public class ExamPrepFragment extends Fragment implements AdapterView.OnItemClickListener{
    String listAdapterItems[] = new String[]{"Take Practice Exam",
            "Review My Study Deck",
            "View Reports",
            "Settings"};
    public static final String SELECTED_OPTION_IN_EXAM_PREP_LIST = "com.example.appcenter.companion.SELECTED_OPTIONS_IN_LIST";
    public static final int TAKE_PRACTICE_EXAM_OPTION =0,REVIEW_MY_STUDY_DESK=1;
    private Class listAdapterNavigationClasses[] = {ExamPrepChaptersList.class, ExamPrepChaptersList.class,ExamPrepReportsActivity.class,ExamPrepSettings.class};
    /*This is a bad practice, should work on other way for sending the
       parent activity reference. Must be removed in the next version.
    */
    public ExamPrepFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar=((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.title_exam_prep);

        ArrayAdapter<String> x = new ArrayAdapter<String>(getContext(), R.layout.exam_prep_list_item_view,R.id.exam_prep_list_item_text_view,listAdapterItems);
        View v = inflater.inflate(R.layout.fragment_exam_prep, container, false);
        ListView examPrepListItems = (ListView)v.findViewById(R.id.exam_prep_list_items);
        examPrepListItems.setAdapter(x);
        examPrepListItems.setOnItemClickListener(this);
        return v;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),listAdapterNavigationClasses[position]);
        intent.putExtra(SELECTED_OPTION_IN_EXAM_PREP_LIST,position);
        startActivity(intent);
    }



}
