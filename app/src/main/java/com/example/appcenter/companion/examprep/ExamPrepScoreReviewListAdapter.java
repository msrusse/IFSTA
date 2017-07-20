package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appcenter.companion.R;

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
            public TextView question,correctAnswer,wrongAnswer;
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
                holder.question =(TextView) vi.findViewById(R.id.question);
                holder.correctAnswer =(TextView) vi.findViewById(R.id.correct_answer);
                holder.wrongAnswer =(TextView) vi.findViewById(R.id.wrong_answer);
                vi.setTag(holder);
            }else
                holder = (ViewHolder)vi.getTag();
            //_id,qType,possible0,possible1,possible2,possible3,question,RefPage,correctAnswers
             String[] questionData =(String[]) getItem(position);
            holder.question.setText("Q: "+questionData[0]+":"+questionData[6]);
            holder.correctAnswer.setText("Right: "+questionData[Integer.parseInt(questionData[8])+choiceOffset]);
            holder.wrongAnswer.setText("Wrong: "+questionData[choiceSelectedIndex.get(position)+choiceOffset]);

            return vi;
        }

}
