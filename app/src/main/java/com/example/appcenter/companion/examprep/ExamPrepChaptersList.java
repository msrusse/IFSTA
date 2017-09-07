package com.example.appcenter.companion.examprep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;
import com.example.appcenter.companion.util.IabHelper;
import com.example.appcenter.companion.util.IabResult;
import com.example.appcenter.companion.util.Inventory;
import com.example.appcenter.companion.util.Purchase;

import java.util.ArrayList;
import java.util.List;


public class ExamPrepChaptersList extends AppCompatActivity implements AdapterView.OnItemClickListener,IabHelper.OnIabPurchaseFinishedListener,IabHelper.QueryInventoryFinishedListener,IabHelper.OnIabSetupFinishedListener {

    ExamPrepChaptersListAdapter adapter;
    public static final String KEY_SELECTED_CHAPTERS = "com.example.appcenter.companion.SELECTED_CHAPTERS_DATA_KEY";
    int optionSelected;
    boolean isPurchased  = false;
    String productID ="android.test.purchased";
    MenuItem searchItem;
    ProgressDialog mProgressDialog;
    TextView toogleTextView;
    ToggleButton selectAllChaptersToogle;
    ListView listView;
    private IabHelper mHelper;
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

    private void showAlertDialog(final Activity activity, final IabHelper.OnIabPurchaseFinishedListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Get Pro", new DialogInterface.OnClickListener() {
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
        ((ImageView)dialogLayout.findViewById(R.id.goProDialogImage)).setImageResource(R.drawable.go_pro_exam_prep_dialog_image);
        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.go_pro_exam_prep_dialog_image);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle("Loading..(Please ensure Internet connection is active.)");
        mProgressDialog.show();

        //setup In-App Billing
        String base64 ="<YOUR_KEY_HERE>";
        //mHelper setup is started later see onPrepareOptionsMenu
        mHelper = new IabHelper(this,base64);


        //This is used to clear back stack at the final test stage
        getSupportActionBar().setTitle(R.string.title_exam_prep);
        Intent intent = getIntent();
        optionSelected = intent.getIntExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST,-1);
        List<String[]> listViewData;
        if(optionSelected==ExamPrepFragment.TAKE_PRACTICE_EXAM_OPTION)
            listViewData  = getChaptersInformation();
        else
            listViewData  = getStudyDeckChaptersInformation();

        adapter = new ExamPrepChaptersListAdapter(this,R.layout.practice_exam_chapters_list_item,listViewData);

        setContentView(R.layout.activity_videos_list);

        toogleTextView = (TextView)findViewById(R.id.toogle_text);
        toogleTextView.setText(R.string.title_select_all_chapters);

        selectAllChaptersToogle = (ToggleButton)findViewById(R.id.toggleButton);
        selectAllChaptersToogle.setClickable(false);
        selectAllChaptersToogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setAllAsChecked(isChecked);
            }
        });
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.done).setVisible(true);
        menu.findItem(R.id.settings).setVisible(false);
        /*This needs to be called here because we need to fetch search item before calling,
          Otherwise it invokes null pointer exception.
        */
        mHelper.startSetup(this);
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
        searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(false);
        SearchView searchView = (SearchView)searchItem.getActionView();
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
        if(isPurchased||position==0) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.chapter_selection);
            boolean value = checkBox.isChecked();
            checkBox.setChecked(!value);
            adapter.setChecked(position, !value);
        } else {
            showAlertDialog(this,this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateUI()
    {
        selectAllChaptersToogle.setClickable(isPurchased);
        listView.setOnItemClickListener(this);
        adapter.setIsProductPurchased(isPurchased);
        searchItem.setVisible(isPurchased);
        adapter.notifyDataSetChanged();
    }
    //In-app purchases methods
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
            Log.e(MainTabActivity.TAG, "Purchase Failure\t" + result);
        }else if(info.getSku().equals(productID))
        {
            isPurchased=true;
            Log.e(MainTabActivity.TAG,"Purchase Success\t"+result);
            updateUI();
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
            Log.e(MainTabActivity.TAG,"QUERY SUCCESS ITEM PURCHASED");
        }
        updateUI();
        mProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MainTabActivity.TAG,"In app billing object destoryed");
        if(mHelper!=null)
            mHelper.dispose();
        mHelper=null;
    }
}
