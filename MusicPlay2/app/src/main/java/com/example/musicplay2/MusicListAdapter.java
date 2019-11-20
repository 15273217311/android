package com.example.musicplay2;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MusicListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    protected List<File> files;

    MusicListAdapter(LayoutInflater layoutInflater,List<File> files) {
        this.files=files;
        this.layoutInflater=layoutInflater;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int i) {
        return files.get(i);
    }//返回指定的列表项

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        View views;
        if (view==null)
        {
            viewHolder=new ViewHolder();
            views=layoutInflater.inflate(R.layout.item,null);
            viewHolder.musicAuthor=(TextView)views.findViewById(R.id.tx_authou);
            viewHolder.musicName=(TextView)views.findViewById(R.id.tx_name);
            viewHolder.favorite_signal=(TextView)views.findViewById(R.id.signal_favorite);
            views.setTag(viewHolder);
        }
        else {
            views=view;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.musicName.setText(files.get(i).getName());
        //获取歌曲中的指定信息
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(files.get(i).getAbsolutePath());
        String author=mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if(author!=null)
        {
            viewHolder.musicAuthor.setText(author);
        }
        else
        {
            viewHolder.musicAuthor.setText("null");
        }
        //根据是否被加入歌单 设置textview
        if(MainActivity.favorite[i])
        {
            viewHolder.favorite_signal.setText("added");
        }
        else {
            viewHolder.favorite_signal.setText("");
        }

        return views;
    }

}

class ViewHolder//列表的可改变的项
{
    TextView musicName;
    TextView musicAuthor;
    TextView favorite_signal;
}

