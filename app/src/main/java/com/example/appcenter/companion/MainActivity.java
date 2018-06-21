package com.example.appcenter.companion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;


public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;
    private LinearLayout videosLayout, identifyLayout, examPrepLayout, coursesLayout;
    static final String bottomNavigationGridNames[] ={"Videos","Identify","Exam Prep","Courses"};
    static final int bottomNavigationGridImages[]= {R.mipmap.ic_video,R.mipmap.ic_identify_white,R.mipmap.ic_examprep_white,R.mipmap.ic_course_white};
    public static final String KEY_SELECTED_TAB = "com.example.appcenter.companion.SELECTED_TAB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper myDbHelper ;
        setTitle(R.string.companion);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        try {
            myDbHelper = new DataBaseHelper(this);
            myDbHelper.createDataBase(sharedPreferences);
            myDbHelper.close();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        videosLayout = findViewById(R.id.videoLayout);
        identifyLayout = findViewById(R.id.identifyLayout);
        examPrepLayout = findViewById(R.id.examPrepLayout);
        coursesLayout = findViewById(R.id.coursesLayout);

        videosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,MainTabActivity.class);
                intent.putExtra(KEY_SELECTED_TAB,0);
                startActivity(intent);
            }
        });

        identifyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,MainTabActivity.class);
                intent.putExtra(KEY_SELECTED_TAB,1);
                startActivity(intent);
            }
        });

        examPrepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,MainTabActivity.class);
                intent.putExtra(KEY_SELECTED_TAB,2);
                startActivity(intent);
            }
        });

        coursesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,MainTabActivity.class);
                intent.putExtra(KEY_SELECTED_TAB,3);
                startActivity(intent);
            }
        });

        mTextMessage = findViewById(R.id.message);
        //mBottomNavigationGridView = (GridView) findViewById(R.id.bottom_navigation_grid_view);

        //new Adapter class for setting the data into the grid view.
        //mBottomNavigationGridView.setAdapter(new BottomNavigationGridAdapter(this,bottomNavigationGridNames,bottomNavigationGridImages));
        //To call interface onItemClick method.
        //mBottomNavigationGridView.setOnItemClickListener(this);


    }
    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(this,MainTabActivity.class);
            intent.putExtra(KEY_SELECTED_TAB,position);
            startActivity(intent);


    }*/



}
