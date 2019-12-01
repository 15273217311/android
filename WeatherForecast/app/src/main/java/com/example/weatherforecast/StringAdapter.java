package com.example.weatherforecast;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StringAdapter<String> extends BaseAdapter {
    private List<String> list;
    private Context context;

    public StringAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHoder hoder;
        if (convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.stringadapter,null);
            hoder=new ViewHoder();
            hoder.textView= (TextView) view.findViewById(R.id.string_adapter);
            view.setTag(hoder);
        }else {
            view=convertView;
            hoder= (ViewHoder) view.getTag();

        }
        hoder.textView.setText(list.get(position)+"");

        //设置view的从右往左的动画效果
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"translationX",view.getWidth(),0);
        animator.setDuration(1000);
        animator.start();
        return view;
    }
    private class ViewHoder{
        TextView textView;
    }
}

