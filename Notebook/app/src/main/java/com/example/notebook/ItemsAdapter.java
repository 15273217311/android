package com.example.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends ArrayAdapter<Items> {
    private int resourceId;
    public ItemsAdapter(Context context, int textViewResourceId, List<Items> object)
    {
        super(context,textViewResourceId,object);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Items items=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView word=(TextView) view.findViewById(R.id.word_tx);
        TextView sentence=(TextView)view.findViewById(R.id.sentense_tx);
        word.setText(items.getWord());
        sentence.setText(items.getSentence());
        return view;
    }
}
