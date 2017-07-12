package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appcenter.companion.MainActivity;
import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.List;


public class ExamPrepScoreReview extends AppCompatActivity {

    ExamPrepScoreReviewListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_score_review);
        Intent intent= getIntent();
        int wrongAnswers = intent.getIntExtra(ExamPrepTestActivity.KEY_TOTAL_WRONG_ANSWERS,-1);
        int correctAnswers = intent.getIntExtra(ExamPrepTestActivity.KEY_TOTAL_CORRECT_ANSWERS,-1);
        float scoreInPercentage = ((float)correctAnswers/(float)(correctAnswers+wrongAnswers))*100.0f;

        ArrayList<String[]> questionsData =(ArrayList<String[]>)intent.getSerializableExtra(ExamPrepTestActivity.KEY_INCORRECT_QUESTIONS_DATA);
        ArrayList<Integer> incorrectAnsweredChoiceIndex = intent.getIntegerArrayListExtra(ExamPrepTestActivity.KEY_INCORRECT_CHOICES_SELECTED);
        ((TextView)findViewById(R.id.correct_answers_text_view)).setText(correctAnswers+"");
        ((TextView)findViewById(R.id.wrong_answers_text_view)).setText(wrongAnswers+"");
        ((TextView)findViewById(R.id.score_text_view)).setText(String.format("%.1f",scoreInPercentage));

        ListView listView = (ListView)findViewById(R.id.wrong_questions_review_listview);
        adapter = new ExamPrepScoreReviewListAdapter(this,questionsData,incorrectAnsweredChoiceIndex);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainTabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.KEY_SELECTED_TAB,ExamPrepTestActivity.EXAM_PREP_BOTTOM_NAVIGATION_POSTION);
        startActivity(intent);
        finish();
    }
}
