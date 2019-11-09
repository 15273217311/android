package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatebaseHelper dbHelp;
    private SQLiteDatabase db=null;
    private static List<Items> list=new ArrayList<Items>();
    private Button btn_search,btn_add,btn_del,btn_renew,btn_update;
    private EditText word_ed,sentence_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setContentView(R.layout.activity_main);

        btn_search=(Button)findViewById(R.id.btn_search);
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_del=(Button)findViewById(R.id.btn_del);
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_renew=(Button)findViewById(R.id.btn_renew);
        btn_add.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_renew.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        /**
        word_ed=(EditText)findViewById(R.id.word_ed);
        sentence_ed=(EditText)findViewById(R.id.sentence_ed);
        word_ed.setInputType(InputType.TYPE_NULL);
        sentence_ed.setInputType(0);
        **/
        dbHelp= new MyDatebaseHelper(this,"NoteBook.db",null,1);
        db=dbHelp.getWritableDatabase();
        show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
            {
                word_ed=(EditText)findViewById(R.id.word_ed);
                sentence_ed=(EditText)findViewById(R.id.sentence_ed);
                String w=word_ed.getText().toString();
                String s=sentence_ed.getText().toString();
                add(w,s);
                show();
            }
                break;
            case R.id.btn_del:
            {
                word_ed=(EditText)findViewById(R.id.word_ed);
                String w=word_ed.getText().toString();
                String[] args=new String[1];
                args[0]=w;
                db.delete("List","word = ?",args);
                show();

            }
                break;
            case R.id.btn_update:
            {
                word_ed=(EditText)findViewById(R.id.word_ed);
                sentence_ed=(EditText)findViewById(R.id.sentence_ed);
                String w=word_ed.getText().toString();
                String s=sentence_ed.getText().toString();
                db=dbHelp.getReadableDatabase();
                ContentValues values=new ContentValues();
                values.put("sentence",s);
                db.update("List",values,"word = ?",new String[]{w});
                show();
            }
            break;
            case R.id.btn_renew:
            {
                add("apple","this is a apple");
                add("before","he could be taken before a magistrate for punishment");
                add("you","are you listening");
                show();
            }
                break;
            case R.id.btn_search:
            {
                word_ed=(EditText)findViewById(R.id.word_ed);
                String w=word_ed.getText().toString();
                String[] columns=new String[]{"word","sentence"};
                String[] args=new String[1];
                args[0]=w;
                Cursor c=db.query("List",columns,"word = ?",args,null,null,null);
                list.clear();
                while (c.moveToNext())
                {
                    String word=c.getString(c.getColumnIndex("word"));
                    String sentence=c.getString(c.getColumnIndex("sentence"));
                    list.add(new Items(word,sentence));
                }
                c.close();
                ItemsAdapter adapter=new ItemsAdapter(this,R.layout.item,list);
                ListView listView=(ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);
            }
                break;
                default:
        }
    }

    public void show()
    {
        initList();
        ItemsAdapter adapter=new ItemsAdapter(this,R.layout.item,list);
        ListView listView=(ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }

    public void add(String w,String s)
    {

        ContentValues values=new ContentValues();
        values.put("word",w);
        values.put("sentence",s);
        db.insert("List",null,values);
    }

    
    public void initDateBase()
    {
        add("apple","this is a apple");
        add("before","he could be taken before a magistrate for punishment");
        add("you","are you listening");
    }

    public void initList()
    {
        list.clear();
        Cursor c=db.rawQuery("select * from List",null);
        while (c.moveToNext())
        {
            String word=c.getString(c.getColumnIndex("word"));
            String sentence=c.getString(c.getColumnIndex("sentence"));
            list.add(new Items(word,sentence));

        }
        c.close();
    }


}

