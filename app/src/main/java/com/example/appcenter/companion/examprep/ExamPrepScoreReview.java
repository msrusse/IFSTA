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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class ExamPrepScoreReview extends AppCompatActivity {

    ExamPrepScoreReviewListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_score_review);
        getSupportActionBar().setTitle(R.string.title_exam_prep);
        Intent intent= getIntent();
        int wrongAnswers = intent.getIntExtra(ExamPrepTestActivity.KEY_TOTAL_WRONG_ANSWERS,-1);
        int correctAnswers = intent.getIntExtra(ExamPrepTestActivity.KEY_TOTAL_CORRECT_ANSWERS,-1);
        float scoreInPercentage = ((float)correctAnswers/(float)(correctAnswers+wrongAnswers))*100.0f;

        ArrayList<String[]> questionsData =(ArrayList<String[]>)intent.getSerializableExtra(ExamPrepTestActivity.KEY_INCORRECT_QUESTIONS_DATA);
        ArrayList<Integer> incorrectAnsweredChoiceIndex = intent.getIntegerArrayListExtra(ExamPrepTestActivity.KEY_INCORRECT_CHOICES_SELECTED);
        ((TextView)findViewById(R.id.correct_answers_text_view)).setText(correctAnswers+"");
        ((TextView)findViewById(R.id.wrong_answers_text_view)).setText(wrongAnswers+"");
        ((TextView)findViewById(R.id.completed_questions_text_view)).setText((wrongAnswers+correctAnswers)+"");
        PieChart chart = (PieChart)findViewById(R.id.score_pie_chart);
        setPieChart(chart,scoreInPercentage);
        ListView listView = (ListView)findViewById(R.id.wrong_questions_review_listview);
        adapter = new ExamPrepScoreReviewListAdapter(this,questionsData,incorrectAnsweredChoiceIndex);
        listView.setAdapter(adapter);
    }

    private void setPieChart(PieChart pieChart,float scorePercentage)
    {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(scorePercentage,"Correct %"));
        entries.add(new PieEntry(100.0f-scorePercentage,"Incorrect %"));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{R.color.lightGreen,R.color.lightRed},this);
        Description description=new Description();
        description.setText("");

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setWordWrapEnabled(true);
        PieData data = new PieData(dataSet);
        pieChart.setCenterText(String.format("%.1f",scorePercentage));
        pieChart.setCenterTextColor(R.color.lightGreen);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawSliceText(false);
        pieChart.setTouchEnabled(false);
        pieChart.animateX(1000);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDescription(description);
        pieChart.setData(data);
        pieChart.invalidate();
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
