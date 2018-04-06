package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

import java.lang.reflect.Field;

public class ExamPrepSettings extends AppCompatActivity implements NumberPicker.OnValueChangeListener,View.OnClickListener{
    SharedPreferences sharedPreferences;
    DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_settings);
        getSupportActionBar().setTitle(R.string.title_exam_prep);
        ((Button)findViewById(R.id.clear_test_results_button)).setOnClickListener(this);
        ((Button)findViewById(R.id.clear_study_deck_button)).setOnClickListener(this);
        NumberPicker picker = (NumberPicker)findViewById(R.id.max_number_of_attempts_number_picker);
        picker.setMaxValue(4);
        picker.setMinValue(1);
        sharedPreferences=getSharedPreferences(getString(R.string.exam_prep_test_options_preference_file_key), Context.MODE_PRIVATE);
        picker.setValue(sharedPreferences.getInt(getString(R.string.max_number_of_attempts),1));
        picker.setOnValueChangedListener(this);
        setNumberPickerTextColor(picker);

        myDbHelper = new DataBaseHelper(this);
        myDbHelper.openDataBase();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(getString(R.string.max_number_of_attempts),newVal);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
           case R.id.clear_study_deck_button:
               myDbHelper.getSQLiteDatabaseObject().execSQL("update EXAM_PREP_DATA SET isAnswered=0 where isAnswered=1;");
               break;
           case R.id.clear_test_results_button:
               myDbHelper.getSQLiteDatabaseObject().execSQL("update EXAM_PREP_CHAPTER_TITLES SET correctAnswersCount=0,questionsAttemptedCount=0 where questionsAttemptedCount>=0;");
               break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbHelper.close();
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(Color.WHITE);
                    ((EditText)child).setTextColor(Color.WHITE);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumPickerTextColor", e);
                }
            }
        }
        return false;
    }
}
