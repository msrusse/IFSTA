package com.example.appcenter.companion.examprep;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class ExamPrepReportsActivity extends AppCompatActivity {
    HorizontalBarChart horizontalBarChart;
    float spaceForBar = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_reports);
        List<Float> chartData = getData();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        horizontalBarChart = (HorizontalBarChart) findViewById(R.id.horizontal_bar_chart);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.setDrawGridBackground(false);
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.setDragEnabled(true);
        horizontalBarChart.setPinchZoom(false);
        horizontalBarChart.setScaleEnabled(true);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "Ch "+(int)(1+value/spaceForBar);
            }


        };
        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawGridLines(false);
        xl.setDrawAxisLine(true);
        //restrict number of rows
        xl.setLabelCount(chartData.size(),false);
        xl.setValueFormatter(formatter);

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);
        yl.setAxisMaximum(100f);
        yl.setLabelCount(10,false);

        //Bottom of chart labels
        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
        yr.setDrawLabels(false);
        yr.setAxisMaximum(100f);

        setData(chartData);

        horizontalBarChart.setFitBars(true);
        horizontalBarChart.animateY(1200);
    }
    private List<Float> getData()
    {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        myDbHelper.openDataBase();
        SQLiteDatabase myDataBase = myDbHelper.getSQLiteDatabaseObject();
        String databaseQuery = "select correctAnswersCount,questionsAttemptedCount from EXAM_PREP_CHAPTER_TITLES;";
        Cursor c = myDataBase.rawQuery(databaseQuery, null);
        List<Float> correctAnswersPercentage = new ArrayList<>();
        if(c.moveToFirst())
        {
            while (!c.isAfterLast())
            {
                float correctAnswers =(float) c.getInt(0);
                float attemptedCount =(float) c.getInt(1);
                float percentageCorrect;
                if(attemptedCount>0.0f)
                    percentageCorrect = (correctAnswers/attemptedCount)*100.0f;
                else
                    percentageCorrect = 0.0f;
                correctAnswersPercentage.add(percentageCorrect);
                c.moveToNext();
            }
        }
        c.close();
        myDbHelper.close();
        return correctAnswersPercentage;
    }

    private void setData(List<Float> chartData) {

        float barWidth = 9f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < chartData.size(); i++) {
            float val = chartData.get(i);
            yVals1.add(new BarEntry(i * spaceForBar, val));
        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, " % of correctly answered questions.");
        set1.setDrawIcons(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(barWidth);
        horizontalBarChart.setData(data);


    }
}