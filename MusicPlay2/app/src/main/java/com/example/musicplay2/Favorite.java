package com.example.musicplay2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Favorite extends AppCompatActivity implements View.OnClickListener {

    static final String TAG="bug";
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private int style_flag=1;
    Button button_random;
    final List<File> files=new ArrayList<>();
    private SeekBar seekBar;
    private int position=0;
    TextView musicName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        Button button_playstop=(Button)findViewById(R.id.btn_palystop);
        Button button_next=(Button)findViewById(R.id.btn_next);
        Button button_previous=(Button)findViewById(R.id.btn_previous);
        button_random=(Button)findViewById(R.id.play_style);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        musicName=(TextView)findViewById(R.id.palyingmusic);

        button_next.setOnClickListener(this);
        button_playstop.setOnClickListener(this);
        button_previous.setOnClickListener(this);
        button_random.setOnClickListener(this);

        //初始化歌单数组
        Intent i=getIntent();
        String[] files_list=i.getStringArrayExtra("favorite");
        for(String s:files_list)
        {
            if(s!=null){
                files.add(new File(s));
            }
        }

        //显示列表
        final MusicListAdapter musicListAdapter=new MusicListAdapter(getLayoutInflater(),files);
        ListView listView=(ListView)findViewById(R.id.music_list);
        listView.setAdapter(musicListAdapter);

        //设置点击事件
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
        timer.schedule(timerTask,500,500);

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
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:
                mediaPlayer.reset();
                initMediaPlayer(files.get(position + 1));
                musicName.setText(files.get(position + 1).getName());
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
            case R.id.play_style:
                style_flag++;
                mediaPlayer.reset();
                if(style_flag%3==1)
                {


//                    while (true)
//                    {
//                        if(!mediaPlayer.isPlaying())
//                        {
//                            int size=files.size();
//                            Random random=new Random(10);
//                            int num_random=random.nextInt(size);
//                            mediaPlayer.reset();
//                            initMediaPlayer(files.get(num_random));
//                            mediaPlayer.start();
//                            button_random.setText("order");
//                            style_flag=2;
//                        }
//                    }
                }
                else if (style_flag%3==2)
                {

//                    while (true)
//                    {
//                        if(!mediaPlayer.isPlaying())
//                        {
//                            int size=0;
//                            if(size==files.size()-1)
//                            {
//                                size=0;
//                            }
//                            mediaPlayer.reset();
//                            initMediaPlayer(files.get(size));
//                            mediaPlayer.start();
//                            button_random.setText("stop");
//                            style_flag=3;
//                        }
//                    }
                }
                else if (style_flag%3==0)
                {

                }

                break;
            default:
        }
    }




    public void initMediaPlayer(File file)
    {
        try {
            mediaPlayer.setDataSource(file.getPath());
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


