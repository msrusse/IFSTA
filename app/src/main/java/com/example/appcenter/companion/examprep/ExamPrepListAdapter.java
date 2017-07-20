package com.example.appcenter.companion.examprep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appcenter.companion.R;

import org.w3c.dom.Text;

/**
 * Created by appcenter on 6/12/17.
 */

public class ExamPrepListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    public ExamPrepListAdapter(Context context, String[] values) {
        super(context, R.layout.exam_prep_list_item_view,values);
        this.values= values;
       this.context = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exam_prep_list_item_view, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.exam_prep_list_item_text_view);
            textView.setText(values[position]);
        }
        return convertView;
    }
}
