package com.example.appcenter.companion.courses;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appcenter.companion.R;
import com.example.appcenter.companion.examprep.ExamPrepChaptersListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by krishna on 7/21/2017.
 */

public class ChaptersListArrayAdapter extends ArrayAdapter {
    private List totalItemsList = new ArrayList();
    public class ItemViewHolder
    {
        TextView chapterNumber,chapterTitle,pageCount,chapterProgressPercentage;
    }
    public ChaptersListArrayAdapter(@NonNull Context context, @LayoutRes int resource,List<String[]> coursesData) {
        super(context, resource);
        totalItemsList=coursesData;
    }

    @Override
    public int getCount() {
        return totalItemsList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return totalItemsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemViewHolder viewHolder=null;
        if(convertView==null)
        {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.courses_chapters_list_item, null);
            viewHolder = new ItemViewHolder();
            viewHolder.chapterNumber = (TextView)convertView.findViewById(R.id.chapter_number);
            viewHolder.chapterTitle = (TextView)convertView.findViewById(R.id.chapter_title);
            viewHolder.pageCount=(TextView)convertView.findViewById(R.id.page_count);
            viewHolder.chapterProgressPercentage=(TextView)convertView.findViewById(R.id.chapter_progress_percentage);
            convertView.setTag(viewHolder);
        }else
            viewHolder=(ItemViewHolder)convertView.getTag();
        String[] chapterData = (String[])getItem(position);
        viewHolder.chapterNumber.setText(chapterData[0]);
        viewHolder.pageCount.setText("Pages 1-"+chapterData[1]);
        viewHolder.chapterTitle.setText(chapterData[2]);
        viewHolder.chapterProgressPercentage.setText((new Random().nextInt(101))+"%");
        int width = convertView.getWidth()/4-10;
        viewHolder.chapterProgressPercentage.setWidth(width);
        return convertView;
    }
}
