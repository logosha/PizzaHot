package com.google.pizzahot.adapters;

import android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.pizzahot.DB.tables.VenueData;
import com.paging.listview.PagingBaseAdapter;

import java.util.List;


/**
 * Created by Алексей on 31.10.2016.
 */

public class MyPagingAdaper extends PagingBaseAdapter<VenueData> {

    public MyPagingAdaper(List<VenueData> list){
        this.items.addAll(list);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public VenueData getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void addItems(List<VenueData> list){
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        VenueData vd = getItem(position);

        if (convertView != null) {
            textView = (TextView) convertView;
        } else {
            textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1, null, false);
        }
        textView.setText(vd.getName()+", "+ vd.getDistance()+"m");
        return textView;
    }
}