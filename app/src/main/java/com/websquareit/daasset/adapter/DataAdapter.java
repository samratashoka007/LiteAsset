package com.websquareit.daasset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.websquareit.daasset.DataModel;
import com.websquareit.daasset.R;

import java.util.List;

public class DataAdapter extends ArrayAdapter<DataModel> {
    private Context context;
    private List<DataModel> names;

    public DataAdapter(Context context2, int resource, List<DataModel> names2) {
        super(context2, resource, names2);
        this.context = context2;
        this.names = names2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
     //   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.name, null, true);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);
        DataModel name = (DataModel) this.names.get(position);
        ((TextView) listViewItem.findViewById(R.id.textViewName)).setText(name.getSiteName());
        if (name.getStatus() == 0) {
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        } else {
            imageViewStatus.setBackgroundResource(R.drawable.success);
        }
        return listViewItem;
    }
}
