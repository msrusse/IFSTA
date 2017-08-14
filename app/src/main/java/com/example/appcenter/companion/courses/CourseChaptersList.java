package com.example.appcenter.companion.courses;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.List;


public class CourseChaptersList extends Fragment implements AdapterView.OnItemClickListener{
    List<String[]> courseChapterInformation;
    private ListView listView;
    private ChaptersListArrayAdapter chaptersListArrayAdapter;
    public static final String KEY_SELECTED_CHAPTER_DATA = "com.example.appcenter.companion.courses.CHAPTER_DATA";

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

                identifyDataListItem[0]="Chapter "+chapterIdFromatting[1]+" Lesson "+chapterIdFromatting[2];
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
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseChapterInformation=getChaptersInformation();
        chaptersListArrayAdapter = new ChaptersListArrayAdapter(getContext(),R.layout.courses_chapters_list_item,courseChapterInformation);
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



}
