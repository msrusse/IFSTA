package com.example.appcenter.companion.courses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.appcenter.companion.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 7/21/2017.
 */

public class ChaptersListArrayAdapter extends ArrayAdapter {
    private List totalItemsList = new ArrayList();
    private List<Integer> chapterProgressList = new ArrayList<>();
    private SharedPreferences preferences;
    private boolean isProductPurchased =false;
    private Activity activity;
    public void setIsProductPurchased(boolean isPurchased)
    {
        isProductPurchased=isPurchased;
    }
    public class ItemViewHolder
    {
        TextView chapterNumber,chapterTitle,pageCount,chapterProgressPercentage;
    }
    public ChaptersListArrayAdapter(@NonNull Context context, @LayoutRes int resource,List<String[]> coursesData,Activity activity) {
        super(context, resource);
        totalItemsList=coursesData;
        this.activity = activity;
    }

    public void updateChapterProgressInformation(List<Integer> chapterProgressList)
    {
        this.chapterProgressList=chapterProgressList;
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

        if (!isProductPurchased && position != 0)
        {
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.courses_chapters_list_item_dark, null);
                viewHolder = new ItemViewHolder();
                viewHolder.chapterNumber = convertView.findViewById(R.id.chapter_number);
                viewHolder.chapterTitle = convertView.findViewById(R.id.chapter_title);
                viewHolder.pageCount = convertView.findViewById(R.id.page_count);
                viewHolder.chapterProgressPercentage = convertView.findViewById(R.id.chapter_progress_percentage);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ItemViewHolder) convertView.getTag();
            String[] chapterData = (String[]) getItem(position);
            viewHolder.chapterNumber.setText(chapterData[0]);
            viewHolder.pageCount.setText("Screens: " + chapterData[1]);
            viewHolder.chapterTitle.setText(chapterData[2]);
            int width = convertView.getWidth() / 4 - 10;
            viewHolder.chapterProgressPercentage.setWidth(width);
            viewHolder.chapterProgressPercentage.setText("");
            viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.mipmap.ic_lock_closed));
        }
        else if (position == 0) {
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.courses_chapters_list_item, null);
                viewHolder = new ItemViewHolder();
                viewHolder.chapterNumber = convertView.findViewById(R.id.chapter_number);
                viewHolder.chapterTitle = convertView.findViewById(R.id.chapter_title);
                viewHolder.pageCount = convertView.findViewById(R.id.page_count);
                viewHolder.chapterProgressPercentage = convertView.findViewById(R.id.chapter_progress_percentage);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ItemViewHolder) convertView.getTag();
            String[] chapterData = (String[]) getItem(position);
            viewHolder.chapterNumber.setText(chapterData[0]);
            viewHolder.pageCount.setText("Screens: " + chapterData[1]);
            viewHolder.chapterTitle.setText(chapterData[2]);
            int width = convertView.getWidth() / 4 - 10;
            viewHolder.chapterProgressPercentage.setWidth(width);
            float progressInPercentage = ((float) chapterProgressList.get(position)) / ((float) Integer.parseInt(chapterData[1]));
            float progress = progressInPercentage * 100;
            if (progress == 0) {
                viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable_gray));
            } else if (progress == 100) {
                viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable_green));
            } else if (progressInPercentage < 99) {
                viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable));
            }
        }
        else if (isProductPurchased)
        {
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.courses_chapters_list_item_dark, null);
                viewHolder = new ItemViewHolder();
                viewHolder.chapterNumber = convertView.findViewById(R.id.chapter_number);
                viewHolder.chapterTitle = convertView.findViewById(R.id.chapter_title);
                viewHolder.pageCount = convertView.findViewById(R.id.page_count);
                viewHolder.chapterProgressPercentage = convertView.findViewById(R.id.chapter_progress_percentage);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ItemViewHolder) convertView.getTag();
                String[] chapterData = (String[]) getItem(position);
                viewHolder.chapterNumber.setText(chapterData[0]);
                viewHolder.pageCount.setText("Screens: " + chapterData[1]);
                viewHolder.chapterTitle.setText(chapterData[2]);
                int width = convertView.getWidth() / 4 - 10;
                viewHolder.chapterProgressPercentage.setWidth(width);
                float progressInPercentage = ((float) chapterProgressList.get(position)) / ((float) Integer.parseInt(chapterData[1]));
                float progress = progressInPercentage * 100;
                if (progress == 0) {
                    viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable_gray));
                } else if (progress == 100) {
                    viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable_green));
                } else if (progressInPercentage < 99) {
                    viewHolder.chapterProgressPercentage.setBackground(ContextCompat.getDrawable(activity, R.drawable.circular_textview_drawable));
                }
        }
        return convertView;
    }
}
