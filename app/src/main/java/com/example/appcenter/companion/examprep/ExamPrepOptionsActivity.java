package com.example.appcenter.companion.examprep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appcenter.companion.R;

import java.util.Arrays;


public class ExamPrepOptionsActivity extends AppCompatActivity implements View.OnClickListener {
    NumberPicker numberPicker;
    String chapterNumbers[];
    RadioGroup studyDeckRadioGroup,feedbackRadioGroup;
    public static final String KEY_SELECTED_OPTIONS_DATA = "com.example.appcenter.companion.OPTIONS_DATA";
    public static final String KEY_SELECTED_CHAPTERS_DATA = "com.example.appcenter.companion.CHAPTERS_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_options);
        //This is used to clear back stack at the final test stage

        Intent intent = getIntent();
        String data = intent.getStringExtra(ExamPrepChaptersList.KEY_SELECTED_CHAPTERS);
        chapterNumbers=data.split(",");

        studyDeckRadioGroup = (RadioGroup)findViewById(R.id.study_deck_radio_group);
        feedbackRadioGroup = (RadioGroup)findViewById(R.id.feedback_radio_group);
        Button saveButton =(Button)findViewById(R.id.save_button);

        numberPicker=(NumberPicker)findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(Integer.parseInt(chapterNumbers[chapterNumbers.length-1]));
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save_button: int studyGroupIndex = studyDeckRadioGroup.indexOfChild(findViewById(studyDeckRadioGroup.getCheckedRadioButtonId()));
                                   int feedBackIndex = feedbackRadioGroup.indexOfChild(findViewById(feedbackRadioGroup.getCheckedRadioButtonId()));
                                   int numberOfQuestions = numberPicker.getValue();
                                    if(studyGroupIndex==-1||feedBackIndex==-1)
                                        Toast.makeText(this,R.string.toast_if_no_options_selected,Toast.LENGTH_SHORT).show();
                                    else {
                                        Intent intent = new Intent(this,ExamPrepTestActivity.class);
                                        intent.putExtra(KEY_SELECTED_OPTIONS_DATA,new int[]{numberOfQuestions,studyGroupIndex,feedBackIndex});
                                        intent.putExtra(KEY_SELECTED_CHAPTERS_DATA, Arrays.copyOfRange(chapterNumbers,0,chapterNumbers.length-1));
                                        intent.putExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST,getIntent().getIntExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST,-1));
                                        startActivity(intent);
                                    }
                break;
        }
    }
}
