package com.example.appcenter.companion.examprep;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.List;


public class ExamPrepChaptersList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ExamPrepChaptersListAdapter adapter;
    public static final String KEY_SELECTED_CHAPTERS = "com.example.appcenter.companion.SELECTED_CHAPTERS_DATA_KEY";
    int optionSelected;
    private List<String[]> getChaptersInformation()
    {

          DataBaseHelper  myDbHelper = new DataBaseHelper(this);
          myDbHelper.openDataBase();
          SQLiteDatabase myDataBase = myDbHelper.getSQLiteDatabaseObject();

         Cursor c = myDataBase.rawQuery("select * from EXAM_PREP_CHAPTER_TITLES;",null);
         List<String[]> examPrepDataList = new ArrayList<String[]>();

        if(c.moveToFirst())
        {
                while (!c.isAfterLast())
                {
                    //Look schema of database for this
                    String identifyDataListItem[] = {
                        //_id,name,questionsCount,selection
                        c.getString(0),c.getString(1),c.getInt(2)+"",
                                                     };
                    examPrepDataList.add(identifyDataListItem);
                    c.moveToNext();
                }
        }
        c.close();
        myDbHelper.close();
        return  examPrepDataList;
    }

    private List<String[]> getStudyDeckChaptersInformation() {

        List<String[]> examPrepDataList = getChaptersInformation();
        List<String[]> studyDeckDataList = new ArrayList<String[]>();
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        myDbHelper.openDataBase();
        SQLiteDatabase myDataBase = myDbHelper.getSQLiteDatabaseObject();

        for(int i =0;i<examPrepDataList.size();i++) {
            Cursor c = myDataBase.rawQuery("select count(*) from EXAM_PREP_DATA where (_id like '"+(i+1)+"-%') and isAnswered=1;", null);
            if(c.moveToFirst())
            {
                int count = c.getInt(0);
                if(count!=0)
                {
                    String tempListData[]= examPrepDataList.get(i);
                    tempListData[2] = count+"";
                    studyDeckDataList.add(tempListData);
                }
            }
        }
        return studyDeckDataList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This is used to clear back stack at the final test stage
        getSupportActionBar().setTitle(R.string.title_exam_prep);
        Intent intent = getIntent();
        optionSelected = intent.getIntExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST,-1);
        List<String[]> listViewData;
        if(optionSelected==ExamPrepFragment.TAKE_PRACTICE_EXAM_OPTION)
            listViewData  = getChaptersInformation();
        else
            listViewData  = getStudyDeckChaptersInformation();

        setContentView(R.layout.activity_videos_list);
        TextView tv = (TextView)findViewById(R.id.toogle_text);
        tv.setText(R.string.title_select_all_chapters);
        ToggleButton selectAllChaptersToogle = (ToggleButton)findViewById(R.id.toggleButton);
        selectAllChaptersToogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setAllAsChecked(isChecked);
            }
        });

        ListView listView = (ListView)findViewById(R.id.listView);
        adapter = new ExamPrepChaptersListAdapter(this,R.layout.practice_exam_chapters_list_item,listViewData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.done).setVisible(true);
        menu.findItem(R.id.settings).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.done:
                String chapterNumbers =adapter.getAllSelectedChapterNumbers();
                if(chapterNumbers.length()==0)
                     Toast.makeText(this,R.string.toast_if_no_chapters_selected,Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(this,ExamPrepOptionsActivity.class);
                    intent.putExtra(KEY_SELECTED_CHAPTERS,chapterNumbers);
                    intent.putExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST,optionSelected);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.videolist_action_bar_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         CheckBox checkBox =(CheckBox) view.findViewById(R.id.chapter_selection);
         boolean value = checkBox.isChecked();
         checkBox.setChecked(!value);
         adapter.setChecked(position,!value);
    }
}
