package com.example.musicplay2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    Button button_style;
    private final int NO=1;
    Random random;
    private final int RANDOM=2;
    private final int ORDER=3;
    private int flag=NO;
    final List<File> files=new ArrayList<>();
    private SeekBar seekBar;
    private int position=0;
    TextView musicName;
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        Button button_playstop=(Button)findViewById(R.id.btn_palystop);
        final Button button_next=(Button)findViewById(R.id.btn_next);
        Button button_previous=(Button)findViewById(R.id.btn_previous);
        button_style=(Button)findViewById(R.id.play_style);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        musicName=(TextView)findViewById(R.id.palyingmusic);

        button_next.setOnClickListener(this);
        button_playstop.setOnClickListener(this);
        button_previous.setOnClickListener(this);
        button_style.setOnClickListener(this);
        //设置随机数
        random=new Random(1);

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
        //设置seekbar监听事件

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switch (flag)
                {
                    case NO:
                        break;
                    case RANDOM:
                        mediaPlayer.reset();
                        int size=files.size();
                        int r=random.nextInt(size);

                        initMediaPlayer(files.get(r));
                        musicName.setText(files.get(r).getName());
                        position=r;
                        mediaPlayer.start();
                        break;
                    case ORDER:
                        mediaPlayer.reset();
                        initMediaPlayer(files.get(position + 1));
                        musicName.setText(files.get(position + 1).getName());
                        position++;
                        if (position==files.size()-1) position=-1;
                        mediaPlayer.start();
                        break;
                        default:
                }
            }
        });

        //设置一个主线程
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what)
                {
                    case 1:
                        button_style.setText("order");
                        break;
                    case 2:
                        button_style.setText("random");
                        break;
                    case 3:
                        button_style.setText("stop");
                        break;
                    default:
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.reset();
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
                mediaPlayer.reset();
                if(style_flag%3==1)
                {
                    flag=ORDER;
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
                else if (style_flag%3==2)
                {
                    flag=RANDOM;
                    Message message=new Message();
                    message.what=2;
                    handler.sendMessage(message);
                }
                else if (style_flag%3==0)
                {
                    flag=NO;
                    Message message=new Message();
                    message.what=3;
                    handler.sendMessage(message);
                }
                style_flag++;
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


