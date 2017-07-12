package com.example.appcenter.companion.identify;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class IdentifyTestActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    final int maxOptions = 4;
    RadioButton options[] = new RadioButton[maxOptions];
    int radioButtonId[] = {R.id.option_1, R.id.option_2, R.id.option_3, R.id.option_4};
    int currentQuestionInList = 0, correctOptionId;
    List<String[]> unansweredQuestions = new ArrayList();
    ImageView containerImage;
    TextView questionNumber, imageReferences;
    RadioGroup radioGroup;
    DataBaseHelper myDbHelper;
    Button skipButton;

    private void setQuestionInformation() {
        if (currentQuestionInList < unansweredQuestions.size()) {
            skipButton.setText("Skip");
            String questionInformation[] = unansweredQuestions.get(currentQuestionInList);
            //schema of information ID,qPicture,qPictureCourtesy,correctAnswers,options1,2,3,4

            questionNumber.setText("Q: " + questionInformation[0]);

            int resId = getResources().getIdentifier("identify_" + questionInformation[1].replace(".jpg", ""), "drawable", getPackageName());
            containerImage.setImageResource(resId);

            String imageRef = questionInformation[2];
            if (imageRef.length() != 0) {
                imageReferences.setText("Photo courtesy of: " + imageRef);
            }
            correctOptionId = Integer.parseInt(questionInformation[3]);

            int color = ContextCompat.getColor(this, android.R.color.background_light);
            for (int i = 0; i < options.length; i++) {
                options[i].setText(questionInformation[i + 4]);
                options[i].setBackgroundColor(color);
                options[i].setClickable(true);
            }
            //go to next question.
            currentQuestionInList++;
        } else {
            testEndOptions();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_test);

        radioGroup = (RadioGroup) findViewById(R.id.container_radio_group);
        options[0] = (RadioButton) findViewById(radioButtonId[0]);
        options[1] = (RadioButton) findViewById(radioButtonId[1]);
        options[2] = (RadioButton) findViewById(radioButtonId[2]);
        options[3] = (RadioButton) findViewById(radioButtonId[3]);
        containerImage = (ImageView) findViewById(R.id.container_image);
        questionNumber = (TextView) findViewById(R.id.questionNumber);
        imageReferences = (TextView) findViewById(R.id.imageReferences);
        skipButton = (Button) findViewById(R.id.skip);
        (skipButton).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        try {
            myDbHelper = new DataBaseHelper(this);
            myDbHelper.openDataBase();
            unansweredQuestions = myDbHelper.getIdentifyUnansweredQuestions();

        } catch (SQLException sqle) {
            throw sqle;
        }
        setQuestionInformation();
    }


    private int getRadioButtonIndexByID(int radioButtonId) {
        switch (radioButtonId) {
            case R.id.option_1:
                return 0;
            case R.id.option_2:
                return 1;
            case R.id.option_3:
                return 2;
            case R.id.option_4:
                return 3;
        }
        return -1;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        if (checkedId == View.NO_ID)
            return;

        //Freeze all the radio buttons
        for (int i = 0; i < group.getChildCount(); i++)
            options[i].setClickable(false);

        skipButton.setText("Next Question");
        options[correctOptionId].setBackgroundColor(Color.GREEN);

        if (radioButtonId[correctOptionId] == checkedId) {
            String qID = questionNumber.getText().toString().replace("Q: ", "");
            myDbHelper.setQuestionAsAnswered(qID);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                radioGroup.setOnCheckedChangeListener(null);
                radioGroup.clearCheck();
                radioGroup.setOnCheckedChangeListener(this);
                setQuestionInformation();
                break;

        }
    }

    @Override
    public void finish() {
        super.finish();
        myDbHelper.close();
    }

    private void testEndOptions() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Test Finished (Reset progress).\n");
        builder1.setIcon(android.R.drawable.ic_delete);
        builder1.setMessage(getResources().getString(R.string.identify_delete_preferences_description));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDbHelper.clearAllAnsweredQuestions();
                        dialog.cancel();
                        //Recreating the activity again by defaulting the values.
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }
}
