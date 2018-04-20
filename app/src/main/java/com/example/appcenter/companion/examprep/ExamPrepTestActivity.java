package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcenter.companion.DataBaseHelper;
import com.example.appcenter.companion.MainActivity;
import com.example.appcenter.companion.MainTabActivity;
import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ExamPrepTestActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, DialogInterface.OnClickListener {
    public static final String KEY_TOTAL_WRONG_ANSWERS = "com.example.appcenter.companion.TOTAL_WRONG_ANSWERS";
    public static final String KEY_TOTAL_CORRECT_ANSWERS = "com.example.appcenter.companion.TOTAL_CORRECT_ANSWERS";
    public static final String KEY_INCORRECT_QUESTIONS_DATA = "com.example.appcenter.companion.INCORRECT_QUESTIONS_DATA";
    public static final String KEY_INCORRECT_CHOICES_SELECTED = "com.example.appcenter.companion.INCORRECT_CHOICES_SELECTED";
    public static final int EXAM_PREP_BOTTOM_NAVIGATION_POSTION = 2;
    final int maxChoices = 4;
    int currentQuestionNumber = 0, correctAnswer=0;
    Random randomCorrectAnswerShuffler=new Random();
    MenuItem actionBarItem;
    final int MENU_ITEM_ID = 1;
    int NUMBER_OF_QUESTIONS_LIMIT = 0;
    int totalCorrectAnswers = 0;
    boolean ADD_MISSED_QUESTIONS_TO_STUDYDECK, FEEDBACK_STYLE_IMMEDIATE_ANSWERS;
    DataBaseHelper myDbHelper;
    List<String[]> questionsData;
    ArrayList<String[]> incorrectAnsweredQuestions = new ArrayList<String[]>();
    ArrayList<Integer> incorrectAnsweredChoiceIndex = new ArrayList<Integer>();
    RadioGroup examPrepRadioGroup;
    RadioButton options[] = new RadioButton[maxChoices];
    ImageView addToStudyDeck;
    TextView question, pageReference, questionNumber;
    int numberOfAttempts = 0;
    int MAXIMUM_NUMBER_OF_ATTEMPTS = 1;

    private List<String[]> getExamPrepQuestionsInformation(String chaptersList[], int selectedOption) {
        String queryWithAllChapters = "(";
        for (String eachNumber : chaptersList) {
            queryWithAllChapters += "(_id like '" + eachNumber + "-%')OR";
        }
        queryWithAllChapters = queryWithAllChapters.substring(0, queryWithAllChapters.length() - 2) + ")";
        String databaseQuery;
        if (selectedOption == ExamPrepFragment.REVIEW_MY_STUDY_DESK)
            databaseQuery = "select _id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers,isAnswered from EXAM_PREP_DATA where isAnswered =1 AND " + queryWithAllChapters + " LIMIT " + NUMBER_OF_QUESTIONS_LIMIT + ";";
        else
            databaseQuery = "select _id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers,isAnswered from EXAM_PREP_DATA where " + queryWithAllChapters + " LIMIT " + NUMBER_OF_QUESTIONS_LIMIT + ";";

        myDbHelper = new DataBaseHelper(this);
        myDbHelper.openDataBase();
        SQLiteDatabase myDataBase = myDbHelper.getSQLiteDatabaseObject();

        Cursor c = myDataBase.rawQuery(databaseQuery, null);
        List<String[]> examPrepQuestionsList = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //Look schema of database for this
                String identifyDataListItem[] = {
                        //_id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers
                        c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),
                        c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getInt(9) + ""
                };
                examPrepQuestionsList.add(identifyDataListItem);
                c.moveToNext();
            }
        }
        c.close();
        return examPrepQuestionsList;
    }

    private void setQuestionInformation() {
        if (currentQuestionNumber != questionsData.size()) {
            examPrepRadioGroup.clearCheck();
            addToStudyDeck.setVisibility(View.INVISIBLE);
            numberOfAttempts = 0;
            int color = ContextCompat.getColor(this, android.R.color.background_light);
            String[] data = questionsData.get(currentQuestionNumber);
            questionNumber.setText("Question " + (currentQuestionNumber + 1) + " of " + questionsData.size());
            //_id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers
            String chapterNumber = "ch. " + (data[0].split("-"))[0] + "- pg.";
            for (int i = 0; i < options.length; i++) {
                options[i].setText(data[i + 2].replaceAll(" iacute;a", "iá"));
                options[i].setBackgroundColor(color);
                options[i].setClickable(true);
            }
            question.setText(data[6]);
            pageReference.setText(chapterNumber + data[7]);
            correctAnswer = Integer.parseInt(data[8]);
            //Shuffling Options.
            int shuffledAnswer;
            do{
                shuffledAnswer=randomCorrectAnswerShuffler.nextInt(4);
            }while(shuffledAnswer==correctAnswer);
            options[correctAnswer].setText(data[shuffledAnswer+2].replaceAll(" iacute;a", "iá"));
            options[shuffledAnswer].setText(data[correctAnswer+2].replaceAll(" iacute;a", "iá"));
            correctAnswer=shuffledAnswer;

            currentQuestionNumber++;
        } else {
            //calling dialog interface on click method to show reports.
            exitOptions("Test finished.");
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_prep_test);
        getSupportActionBar().setTitle(R.string.title_exam_prep);
        Intent intent = getIntent();
        int selectedOptions[] = intent.getIntArrayExtra(ExamPrepOptionsActivity.KEY_SELECTED_OPTIONS_DATA);
        NUMBER_OF_QUESTIONS_LIMIT = selectedOptions[0];
        ADD_MISSED_QUESTIONS_TO_STUDYDECK = selectedOptions[1] == 0;
        FEEDBACK_STYLE_IMMEDIATE_ANSWERS = selectedOptions[2] == 0;
        SharedPreferences pref = getSharedPreferences(getString(R.string.exam_prep_test_options_preference_file_key), Context.MODE_PRIVATE);
        MAXIMUM_NUMBER_OF_ATTEMPTS = pref.getInt(getString(R.string.max_number_of_attempts), 1);

        int selectedOptionInExamPrepFragmentList = intent.getIntExtra(ExamPrepFragment.SELECTED_OPTION_IN_EXAM_PREP_LIST, -1);
        String selectedChapters[] = intent.getStringArrayExtra(ExamPrepOptionsActivity.KEY_SELECTED_CHAPTERS_DATA);
        questionsData = getExamPrepQuestionsInformation(selectedChapters, selectedOptionInExamPrepFragmentList);

        examPrepRadioGroup = (RadioGroup) findViewById(R.id.exam_prep_test_radio_group);
        options[0] = (RadioButton) findViewById(R.id.option_1);
        options[1] = (RadioButton) findViewById(R.id.option_2);
        options[2] = (RadioButton) findViewById(R.id.option_3);
        options[3] = (RadioButton) findViewById(R.id.option_4);
        addToStudyDeck = (ImageView) findViewById(R.id.add_to_study_deck);
        question = (TextView) findViewById(R.id.question_view);
        pageReference = (TextView) findViewById(R.id.page_number_reference);
        questionNumber = (TextView) findViewById(R.id.current_question_Number);
        examPrepRadioGroup.setOnCheckedChangeListener(this);
        addToStudyDeck.setOnClickListener(this);
        addToStudyDeck.setTag(false);
        Collections.shuffle(questionsData);
        setQuestionInformation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarItem = (MenuItem) menu.add(0, MENU_ITEM_ID, 0, "SKIP");
        actionBarItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    private void showAddToStudyDeckOption(boolean value) {
        addToStudyDeck.setTag(value);

        if (value)
            addToStudyDeck.setImageResource(R.drawable.bitmap_add_to_study_deck_checked);
        else
            addToStudyDeck.setImageResource(R.drawable.bitmap_add_to_study_deck_unchecked);

    }

    private void addQuestionToStudyDeck() {
        String[] data = questionsData.get(currentQuestionNumber - 1);
        markQuestion(data[0], 1);
        showAddToStudyDeckOption(true);
    }

    private void removeQuestionFromStudyDeck() {
        String[] data = questionsData.get(currentQuestionNumber - 1);
        markQuestion(data[0], 0);
        showAddToStudyDeckOption(false);

    }

    private void preserveAttemptedQuestionInformation(int checkedId) {

        int selectedOptionIndex = getRadioButtonIndexByID(checkedId);
        RadioButton selectedOption = options[selectedOptionIndex];
        boolean isCorrect = selectedOption.getId() == options[correctAnswer].getId();
        String[] data = questionsData.get(currentQuestionNumber - 1);
        boolean isAddedToStudyDeck = Integer.parseInt(data[9]) == 1;


        //Freeze all the radio buttons
        for (int i = 0; i < examPrepRadioGroup.getChildCount(); i++)
            options[i].setClickable(false);

        addToStudyDeck.setVisibility(View.VISIBLE);
        showAddToStudyDeckOption(isAddedToStudyDeck);
        actionBarItem.setTitle("NEXT QUESTION");

        //Update attempts in database for question.
        String chapterNumber = (data[0].split("-"))[0];
        myDbHelper.getSQLiteDatabaseObject().execSQL("UPDATE EXAM_PREP_CHAPTER_TITLES SET questionsAttemptedCount= questionsAttemptedCount+1 where _id='" + chapterNumber + "';");

        incorrectAnsweredQuestions.add(data);


        if (isCorrect == false) {
            incorrectAnsweredChoiceIndex.add(selectedOptionIndex);
            if (ADD_MISSED_QUESTIONS_TO_STUDYDECK)
                addQuestionToStudyDeck();
        } else {
            incorrectAnsweredChoiceIndex.add(-1);
            myDbHelper.getSQLiteDatabaseObject().execSQL("UPDATE EXAM_PREP_CHAPTER_TITLES SET correctAnswersCount= correctAnswersCount+1 where _id='" + chapterNumber + "';");
            ++totalCorrectAnswers;
        }
        if (FEEDBACK_STYLE_IMMEDIATE_ANSWERS) {
            selectedOption.setBackgroundColor(getResources().getColor(R.color.identifyIncorrectRed));
            options[correctAnswer].setBackgroundColor(getResources().getColor(R.color.identifyCorrectGreen));
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        if (checkedId == -1)
            return;
        numberOfAttempts++;

        if (numberOfAttempts == MAXIMUM_NUMBER_OF_ATTEMPTS) {
            preserveAttemptedQuestionInformation(checkedId);
        }

        if ((MAXIMUM_NUMBER_OF_ATTEMPTS - numberOfAttempts) > 0)
            Toast.makeText(this, "Remaining Attempts : " + (MAXIMUM_NUMBER_OF_ATTEMPTS - numberOfAttempts), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == MENU_ITEM_ID) {
            setQuestionInformation();
            item.setTitle("SKIP");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_study_deck:
                if (v.getVisibility() == View.VISIBLE)
                    if ((Boolean) addToStudyDeck.getTag()) {
                        removeQuestionFromStudyDeck();
                    } else {
                        addQuestionToStudyDeck();
                    }
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == 0) {
            Intent intent = new Intent(this, ExamPrepScoreReview.class);
            intent.putExtra(KEY_TOTAL_WRONG_ANSWERS, incorrectAnsweredChoiceIndex.size()-totalCorrectAnswers);
            intent.putExtra(KEY_TOTAL_CORRECT_ANSWERS, totalCorrectAnswers);
            intent.putExtra(KEY_INCORRECT_QUESTIONS_DATA, incorrectAnsweredQuestions);
            intent.putExtra(KEY_INCORRECT_CHOICES_SELECTED, incorrectAnsweredChoiceIndex);
            startActivity(intent);
        } else if (which == 1 || which == 3) {
            Intent intent = new Intent(this, MainTabActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MainActivity.KEY_SELECTED_TAB, ExamPrepTestActivity.EXAM_PREP_BOTTOM_NAVIGATION_POSTION);
            startActivity(intent);
            finish();
        }
    }

    private void markQuestion(String qID, int isAnsweredValue) {
        myDbHelper.getSQLiteDatabaseObject().execSQL("update EXAM_PREP_DATA SET isAnswered=" + isAnsweredValue + " where _id='" + qID + "';");
    }

    private void exitOptions(String message) {
        CharSequence exitOptions[] = new CharSequence[]{"Score and exit?", "Exit without Scoring?", "Cancel", "Quit"};
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(message);
        builder1.setItems(exitOptions, this);
        builder1.setCancelable(true);
        builder1.show();
    }

    @Override
    public void onBackPressed() {
        if (currentQuestionNumber == 1 && (examPrepRadioGroup.getCheckedRadioButtonId() == -1))
            super.onBackPressed();
        else
            exitOptions("Exit Options");
    }

    @Override
    public void finish() {
        super.finish();
        myDbHelper.close();
    }

}
