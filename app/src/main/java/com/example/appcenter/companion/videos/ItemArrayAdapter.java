package com.example.appcenter.companion.videos;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcenter.companion.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Pack200;

/**
 * Created by appcenter on 5/22/17.
 */
public class ItemArrayAdapter extends ArrayAdapter implements Filterable{
    private List totalItemsList = new ArrayList();
    private List displayList = new ArrayList();
    private boolean isBookmarksEnabled = false,isSearchViewCollapsed=true;
    CustomFilter filter;

    private class ItemViewHolder {
        TextView chapterNumber,chapterTitle,chapterPlayTime;
        ImageView bookmarkImage;
    }
    public void setItemAsBookmarked(String qID,int value)
    {
        for(int itemPosition = 0;itemPosition<totalItemsList.size();++itemPosition) {
            String[] listItem = (String[]) totalItemsList.get(itemPosition);
            if(listItem[0].equals(qID)) {
                listItem[4] = value + "";
                totalItemsList.set(itemPosition, listItem);

                break;
            }
        }
    }

    public void setSearchViewCollapsed(boolean value)
    {
        isSearchViewCollapsed = value;
    }
    public boolean getSearchViewCollapsed()
    {
        return  isSearchViewCollapsed;
    }

    public void setBookmarksEnabled(boolean value)
    {
            isBookmarksEnabled = value;
    }
    public boolean getIsBookmarkEnabled()
    {
        return isBookmarksEnabled;
    }
    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    @Override
    public void add(Object object) {
        displayList.add(object);
        totalItemsList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
            return this.displayList.size();
    }

    public void showBookmarks(boolean isEnabled)
    {
        isBookmarksEnabled = isEnabled;
        if(isBookmarksEnabled) {
            List bookmarksList = new ArrayList();
            for (int position = 0; position < totalItemsList.size(); ++position) {
                String[] item =(String[]) totalItemsList.get(position);
                boolean isBookmarked = Integer.parseInt(item[4])==1;
                if (isBookmarked) {
                    bookmarksList.add(item);
                }
            }
            displayList = bookmarksList;
            //Notify user if there are no bookmarks to show.
            if (bookmarksList.isEmpty()) {
                Toast.makeText(getContext(), R.string.no_bookmarks_description, Toast.LENGTH_SHORT).show();
            }
        }else
        {
            displayList = totalItemsList;
        }
    }


    @Override
    public Object getItem(int index) {
        return this.displayList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        String[] stat = (String[])getItem(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.chapterNumber = (TextView) row.findViewById(R.id.chapter_number);
            viewHolder.chapterTitle = (TextView) row.findViewById(R.id.chapter_title);
            viewHolder.chapterPlayTime = (TextView) row.findViewById(R.id.chapter_play_time);
            viewHolder.bookmarkImage = (ImageView)row.findViewById(R.id.bookmark);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }

        viewHolder.chapterNumber.setText("Chapter "+stat[0]);
        viewHolder.chapterTitle.setText(stat[1]);
        viewHolder.chapterPlayTime.setText(stat[6]);
        boolean isBookmarked = Integer.parseInt(stat[4])==1;
        //store to display only bookmarks

        if(isBookmarked)
        {
            viewHolder.bookmarkImage.setImageResource(R.mipmap.ic_bookmark);
            //viewHolder.chapterTitle.setTypeface(null, Typeface.BOLD);

        }
        else {
            viewHolder.bookmarkImage.setImageBitmap(null);
            viewHolder.chapterTitle.setTypeface(null, Typeface.NORMAL);
        }
        return row;
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
    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            showBookmarks(isBookmarksEnabled);
            if(constraint!=null&&constraint.length()>0)
            {
                constraint = constraint.toString().toUpperCase();
                //If there is only one word
                if(constraint.toString().split("\\s").length ==1)
                    constraint = constraint.toString().trim();

                List filters = new ArrayList();
                for(int position =0;position<getCount();position++)
                {
                    Object listItem = getItem(position);
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
                results.count = displayList.size();
                results.values = displayList;
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