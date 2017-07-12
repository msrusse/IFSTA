package com.example.appcenter.companion.videos;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appcenter on 6/1/17.
 */

public class VideoListActivity extends Fragment implements AdapterView.OnItemClickListener,CompoundButton.OnCheckedChangeListener{
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    private ToggleButton bookmarkToggle;
    public static final String KEY_SELECTED_VIDEO_DATA = "com.example.appcenter.companion.VIDEO_DATA";
    public final int KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_REQUEST_CODE= 1;
    public static final String KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_VIDEO_ID= "com.example.appcenter.companion.BOOKMARK_CHANGED_DATA_IN_VIDEO_PLAY_ACTIVITY_VIDEO_ID";
    public static final String KEY_IS_BOOKMARKED_IN_VIDEO_PLAY_ACTIVITY= "com.example.appcenter.companion.BOOKMARKED_IN_VIDEO_PLAY_ACTIVITY";
    private SearchView searchView;
    private DataBaseHelper myDbHelper ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemArrayAdapter = new ItemArrayAdapter(getActivity(), R.layout.list_item_layout);
        List csvFileContents = new ArrayList();
        try {
            myDbHelper = new DataBaseHelper(getActivity());
            myDbHelper.openDataBase();
            csvFileContents = myDbHelper.getVideoData();

        }catch(SQLException sqle){
            throw sqle;
        }
        for(Object scoreData:csvFileContents ) {
            itemArrayAdapter.add(scoreData);
        }
        //must enable in the fragments to handle the menu items
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myDbHelper.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_videos_list,container,false);
        bookmarkToggle = (ToggleButton)v.findViewById(R.id.toggleButton);
        bookmarkToggle.setOnCheckedChangeListener(this);
        listView = (ListView) v.findViewById(R.id.listView);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);
        listView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.videolist_action_bar_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String query) {
                itemArrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });


        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                itemArrayAdapter.setSearchViewCollapsed(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                itemArrayAdapter.setSearchViewCollapsed(true);
                itemArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] selectedVideoData =(String[]) itemArrayAdapter.getItem(position);
        Intent intent = new Intent(getActivity(),VideoPlayActivity.class);
        intent.putExtra(KEY_SELECTED_VIDEO_DATA,selectedVideoData);
        startActivityForResult(intent,KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_REQUEST_CODE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                String videoID = data.getStringExtra(KEY_IS_BOOKMARK_CHANGED_IN_VIDEO_PLAY_ACTIVITY_VIDEO_ID);
                int value = data.getBooleanExtra(KEY_IS_BOOKMARKED_IN_VIDEO_PLAY_ACTIVITY,false)?1:0;
                myDbHelper.setVideoAsBookmarked(videoID,value);
                itemArrayAdapter.setItemAsBookmarked(videoID,value);

                if (itemArrayAdapter.getSearchViewCollapsed()) {
                    itemArrayAdapter.showBookmarks(itemArrayAdapter.getIsBookmarkEnabled());
                    itemArrayAdapter.notifyDataSetChanged();
                }else
                {
                    itemArrayAdapter.getFilter().filter(searchView.getQuery());
                }

            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (itemArrayAdapter.getSearchViewCollapsed()) {
            itemArrayAdapter.showBookmarks(isChecked);
            itemArrayAdapter.notifyDataSetChanged();
        }else
        {
            itemArrayAdapter.setBookmarksEnabled(isChecked);
            itemArrayAdapter.getFilter().filter(searchView.getQuery());
        }
    }


}
