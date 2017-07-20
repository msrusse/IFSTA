package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by appcenter on 6/13/17.
 */

public class ExamPrepChaptersListAdapter extends ArrayAdapter {
    private List totalItemsList ;
    private List displayList;
    private boolean[] isChecked;
    CustomFilter filter;

    public class ItemViewHolder {
        TextView chapterNumber,chapterTitle,chapterQuestionsCount;
        CheckBox checkBox;
    }
    public ExamPrepChaptersListAdapter(Context context,int textViewResourceId,List<String[]> chaptersData)
    {
        super(context,textViewResourceId,chaptersData);
        totalItemsList=new ArrayList<String[]>();
        displayList=new ArrayList<String[]>();
        this.totalItemsList.addAll(chaptersData);
        this.displayList.addAll(chaptersData);
        isChecked = new boolean[totalItemsList.size()];
        Arrays.fill(isChecked,false);
    }
    public void setChecked(int position,boolean value)
    {
        int indexInOriginalArray = totalItemsList.indexOf(displayList.get(position));
        isChecked[indexInOriginalArray]=value;
    }
    public void setAllAsChecked(boolean value)
    {
        for(int position=0;position<isChecked.length;position++)
        {
            isChecked[position]=value;
        }
        notifyDataSetChanged();
    }
    public String getAllSelectedChapterNumbers() {
        String chapterSelection="";
        int questionCount=0;
        for (int position = 0; position < isChecked.length; position++) {
            if (isChecked[position]) {
                String[] itemData = (String[]) totalItemsList.get(position);
                chapterSelection += itemData[0]+",";
                questionCount+=Integer.parseInt(itemData[2]);
            }
        }
        if(chapterSelection.length()==0)
            return chapterSelection;
        else
            return chapterSelection+questionCount;
    }

    @Override
    public int getCount() {
        return displayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return displayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ItemViewHolder viewHolder=null;
        if(convertView==null)
        {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.practice_exam_chapters_list_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.chapterNumber = (TextView)convertView.findViewById(R.id.chapter_number);
            viewHolder.chapterTitle = (TextView)convertView.findViewById(R.id.chapter_title);
            viewHolder.chapterQuestionsCount = (TextView)convertView.findViewById(R.id.chapter_play_time);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.chapter_selection);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ItemViewHolder)convertView.getTag();
        }
        String[] itemData =(String[]) displayList.get(position);
        viewHolder.chapterNumber.setText("Chapter "+itemData[0]);
        viewHolder.chapterTitle.setText(itemData[1]);
        viewHolder.chapterQuestionsCount.setText(itemData[2]+" Questions");
        int checkItemIndex=totalItemsList.indexOf(displayList.get(position));
        if(isChecked[checkItemIndex])
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);

        return convertView;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter = new CustomFilter();
        }
        return filter;
    }

    //Inner Class
    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null&&constraint.length()>0)
            {
                constraint = constraint.toString().toUpperCase();
                //If there is only one word
                if(constraint.toString().split("\\s").length ==1)
                    constraint = constraint.toString().trim();

                List filters = new ArrayList();
                for(int position =0;position<totalItemsList.size();position++)
                {
                    Object listItem = totalItemsList.get(position);
                    String titleFiledString = ((String[])listItem)[1];
                    titleFiledString = titleFiledString.toUpperCase();
                    if(titleFiledString.contains(constraint))
                    {
                        filters.add(listItem);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else
            {
                results.count = totalItemsList.size();
                results.values = totalItemsList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            displayList =(ArrayList)results.values;
            notifyDataSetChanged();
        }
    }
}
