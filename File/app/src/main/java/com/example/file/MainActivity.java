package com.example.file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnW = (Button) findViewById(R.id.btnW);
        btnW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //写文件
                String data="Data to save";
                FileOutputStream out=null;
                BufferedWriter writer=null;
                try{
                    out=openFileOutput("data", Context.MODE_PRIVATE);//默认路径/data/data/《package name》/files
                    writer=new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(data);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    try {
                        if (writer!=null)
                        {
                            writer.close();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button btnR = (Button) findViewById(R.id.btnR);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream in=null;
                BufferedReader reader=null;
                StringBuffer content=new StringBuffer();
                try {
                    in=openFileInput("data");
                    reader=new BufferedReader(new InputStreamReader(in));
                    String line="";
                    while ((line=reader.readLine())!=null){
                        content.append(line);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    if (reader!=null)
                    {
                        try {
                            reader.close();

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                Log.d("file", content.toString());
            }

        });
    }
}
