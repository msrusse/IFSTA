package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appcenter.companion.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by appcenter on 6/23/17.
 */

public class ExamPrepScoreReviewListAdapter extends ArrayAdapter{
        int choiceOffset =2;
        private List<String[]> questionsData;
        private List<Integer> choiceSelectedIndex;
         private LayoutInflater inflater=null;
        public static class ViewHolder
        {
            public TextView question,correctAnswer,wrongAnswer,questionNumber;
        }

    public ExamPrepScoreReviewListAdapter(@NonNull Context context, List<String[]> questionsData, List<Integer> choiceSelectedIndex) {
            super(context, R.layout.wrong_question_review_list_item);
           this.questionsData = questionsData;
        this.choiceSelectedIndex=choiceSelectedIndex;
            inflater= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questionsData.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return questionsData.get(position);
    }

    @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vi = convertView;
            ViewHolder holder;
            if(convertView==null)
            {
                vi= inflater.inflate(R.layout.wrong_question_review_list_item,null);
                holder = new ViewHolder();
                LinearLayout linearLayout=(LinearLayout)vi.findViewById(R.id.wrong_questions_review_listview_layout);
                if(position%2==0)
                    linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.darkWhite));
                else
                    linearLayout.setBackgroundColor(getContext().getResources().getColor(R.color.lightWhite));

                holder.question =(TextView) vi.findViewById(R.id.question);
                holder.correctAnswer =(TextView) vi.findViewById(R.id.correct_answer);
                holder.wrongAnswer =(TextView) vi.findViewById(R.id.wrong_answer);
                holder.questionNumber=(TextView)vi.findViewById(R.id.question_number);
                holder.questionNumber.setPaintFlags(holder.questionNumber.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                holder.wrongAnswer.setPaintFlags(holder.wrongAnswer.getPaintFlags() |   Paint.STRIKE_THRU_TEXT_FLAG);
                vi.setTag(holder);
            }else
                holder = (ViewHolder)vi.getTag();
            //_id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers
             String[] questionData =(String[]) getItem(position);
             String chapterNumber = "ch. " + (questionData[0].split("-"))[0] + "- pg.";
             chapterNumber+=questionData[7];
              holder.questionNumber.setText(chapterNumber);
            holder.question.setText(questionData[6]);
            holder.correctAnswer.setText(questionData[Integer.parseInt(questionData[8])+choiceOffset]);
        if(choiceSelectedIndex.get(position)!=-1)
            holder.wrongAnswer.setText(questionData[choiceSelectedIndex.get(position)+choiceOffset]);
        else
            holder.wrongAnswer.setText("");
            return vi;
        }

}
