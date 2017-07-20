package com.example.appcenter.companion;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

/**
 * Created by appcenter on 5/16/17.
 */

public class BottomNavigationGridAdapter extends BaseAdapter {

    private Context context;
    private String[] gridNames;
    private int[] gridImages;
    int widthOfGrid=0;

    public BottomNavigationGridAdapter(Context context,String[] gridNames,int[] gridImages)
    {
        this.context = context;
        this.gridNames = gridNames;
        this.gridImages = gridImages;
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        widthOfGrid = (display.getWidth()/gridImages.length)+45;

    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridNames.length
        return gridNames.length;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }



    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {

        // LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            // get layout from bottom_navigation_item.xml ( Defined Below )

            gridView = inflater.inflate( R.layout.bottom_navigation_item , null);
            gridView.setLayoutParams(new AbsListView.LayoutParams(widthOfGrid,widthOfGrid));
            // set value into text view

            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);

            textView.setText(gridNames[position]);
            textView.setSelected(true);
            // set image based on selected text

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            imageView.setImageResource(gridImages[position]);
        } else {

            gridView =  convertView;
        }

        return gridView;
    }

}
