package com.starrynight.tourapiproject.myPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starrynight.tourapiproject.R;

import java.util.List;

public class MyHashTagAdapter extends BaseAdapter {
    private static List<String> items;
    Context context;

    public MyHashTagAdapter(List<String> items){
        this.items = items;
    }

    public void addItem(String item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        String item = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hashtags_empty3, parent, false);
        }

        TextView hashTagName = convertView.findViewById(R.id.recycler_hashTagName);
        hashTagName.setText("#" + item);

        return convertView;
    }
}