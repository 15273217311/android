package com.example.notebooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String id=null;
    public static final String content="content://com.example.notebook.provider/List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add=(Button)findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(content);//创建url
                ContentValues values=new ContentValues();
                values.put("word","people");
                values.put("sentence","there are many people");
                Uri newUri=getContentResolver().insert(uri,values);
                id=newUri.getPathSegments().get(1);
            }
        });

        Button del=(Button)findViewById(R.id.btn_del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(content+"/"+id);
                getContentResolver().delete(uri,null,null);
            }
        });

        Button query=(Button)findViewById(R.id.btn_query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(content);
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null)
                {
                    while (cursor.moveToNext())
                    {
                        String w=cursor.getString(cursor.getColumnIndex("word"));
                        String s=cursor.getString(cursor.getColumnIndex("sentence"));
                        Log.d("activity",w);
                        Log.d("activity",s);

                    }
                    cursor.close();
                }
            }
        });

        Button update=(Button)findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(content+"/"+id);
                ContentValues values=new ContentValues();
                values.put("sentence","no people");
                getContentResolver().update(uri,values,null,null);
            }
        });

    }
}
