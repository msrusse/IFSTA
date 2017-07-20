package com.example.appcenter.companion;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.appcenter.companion.courses.CourseChaptersList;
import com.example.appcenter.companion.examprep.ExamPrepFragment;
import com.example.appcenter.companion.identify.IdentifyActivity;
import com.example.appcenter.companion.videos.VideoListActivity;


/**
 * Created by appcenter on 6/1/17.
 */

public class MainTabActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost mTabHost;
    private Class tabNavigationClasses[] = {null,VideoListActivity.class,IdentifyActivity.class,ExamPrepFragment.class,CourseChaptersList.class};
    private String tabNames[] = new String[5];
    private int tabImages[]= new int[5];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_view);

        //Get all the tab names and images.
        tabNames[0]="Home";
        tabImages[0]=R.mipmap.ic_home_white;

        System.arraycopy(MainActivity.bottomNavigationGridNames,0,tabNames,1,4);
        System.arraycopy(MainActivity.bottomNavigationGridImages,0,tabImages,1,4);

        Intent intent = getIntent();
        int selectedTab = intent.getIntExtra(MainActivity.KEY_SELECTED_TAB,0)+1;
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        setupTab(new TextView(this), 0);
        setupTab(new TextView(this), 1);
        setupTab(new TextView(this), 2);
        setupTab(new TextView(this), 3);
        setupTab(new TextView(this), 4);

        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(selectedTab);
    }
    private void setupTab(final View view, final int position) {
        String tag = tabNames[position];
        View tabview = createTabView(mTabHost.getContext(),position);
        FragmentTabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new FragmentTabHost.TabContentFactory() {
            public View createTabContent(String tag) {return view;}
        });
        //If home is selected then it should not navigate.
        if(tabNavigationClasses[position]!=null)
            mTabHost.addTab(setContent,tabNavigationClasses[position],null);
        else
            mTabHost.addTab(setContent);
    }

    private View createTabView(final Context context, final int position) {
        String text = tabNames[position];
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_tab, null);
        ImageView iv = (ImageView)view.findViewById(R.id.tabs_image);
        TextView tv = (TextView) view.findViewById(R.id.tabs_text);
        iv.setImageResource(tabImages[position]);
        tv.setText(text);
        //For older versions supporting tint mode on Image views
        ColorStateList csl = ContextCompat.getColorStateList(this,R.color.navigation_item_selector);
        Drawable drawable = DrawableCompat.wrap(iv.getDrawable());
        DrawableCompat.setTintList(drawable, csl);
        iv.setImageDrawable(drawable);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        if(tabId.equals(tabNames[0]) )
            finish();
        }

}
