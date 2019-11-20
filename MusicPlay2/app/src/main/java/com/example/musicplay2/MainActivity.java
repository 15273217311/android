package com.example.musicplay2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG="bug";
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private final List<File> files=new ArrayList<>();
    private File[] favorite_list=new File[50];
    public static boolean[] favorite=new boolean[50];
    private SeekBar seekBar;//创建进度条
    private int position=0;
    TextView musicName;


    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list,menu);//inflate（子布局，父布局），getMenuInflater().inflate为加载布局
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){

            case R.id.menu_favorite:
                Intent intent=new Intent(this,Favorite.class);
                String[] s=new String[50];
                for(int i=0;i<50;i++)
                {
                    if(favorite_list[i]!=null){
                        s[i]=""+favorite_list[i];
                    }
                }
                intent.putExtra("favorite",s);
                startActivity(intent);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_playstop=(Button)findViewById(R.id.btn_palystop);
        Button button_next=(Button)findViewById(R.id.btn_next);
        Button button_previous=(Button)findViewById(R.id.btn_previous);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        musicName=(TextView)findViewById(R.id.palyingmusic);


        button_next.setOnClickListener(this);
        button_playstop.setOnClickListener(this);
        button_previous.setOnClickListener(this);
        //设置权限

        try {
            int permission= ActivityCompat.checkSelfPermission(this,"android.permission.READ_EXTERNAL_STORAGE");
            if(permission!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{"android.permission.READ_EXTERNAL_STORAGE"},1);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //初始化列表
        File musicDir= new File("/sdcard/music");
        Log.d("bug", ""+musicDir);
        File [] arrayFile=musicDir.listFiles();

        for(File k:arrayFile)
        {
            if(accept(""+k))
            {
                files.add(k);
            }
        }

        //初始化歌单数组
        for(boolean l:favorite)
        {
            l=false;
        }
        //设置listview点击事件

        final MusicListAdapter musicListAdapter=new MusicListAdapter(getLayoutInflater(),files);
        ListView listView=(ListView)findViewById(R.id.music_list);
        listView.setAdapter(musicListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mediaPlayer.reset();
                initMediaPlayer(files.get(i));
                musicName.setText(files.get(i).getName());
                position=i;
                playstop();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(!favorite[i]){
                            favorite_list[i]=files.get(i);
                            Toast.makeText(MainActivity.this,"Add favorite",Toast.LENGTH_SHORT).show();
                            favorite[i]=true;
                            musicListAdapter.notifyDataSetChanged();
                        }
                        else {
                            favorite_list[i]=null;
                            Toast.makeText(MainActivity.this,"Remove favorite",Toast.LENGTH_SHORT).show();
                            favorite[i]=false;
                            musicListAdapter.notifyDataSetChanged();
                        }
                        return true;
            }
        });
        //设置seekbar


        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    int progress=mediaPlayer.getCurrentPosition();
                    int total=mediaPlayer.getDuration();
                    seekBar.setMax(total);
                    seekBar.setProgress(progress);
                }
            }
        };
        timer.schedule(timerTask,500,500);//每隔一定的时间执行一次

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int position=seekBar.getProgress();
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(position);
                }
            }
        });

    }
    //活动停止状态

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.reset();
    }

    public boolean accept(String s)
    {
        return s.endsWith(".mp3");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_next:
                mediaPlayer.reset();
                initMediaPlayer(files.get(position+1));
                musicName.setText(files.get(position+1).getName());
                position++;
                mediaPlayer.start();
                break;
            case R.id.btn_palystop:
                playstop();
                break;
            case R.id.btn_previous:
                mediaPlayer.reset();
                if(position-1>0)
                {
                    initMediaPlayer(files.get(position - 1));
                    musicName.setText(files.get(position - 1).getName());
                    position--;
                    mediaPlayer.start();
                }
                break;
            default:
        }
    }

    public void initMediaPlayer(File file)
    {
        try {
            mediaPlayer.setDataSource(file.getPath());//指定音乐路径
            mediaPlayer.prepare();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void playstop()
    {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        else if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
}
