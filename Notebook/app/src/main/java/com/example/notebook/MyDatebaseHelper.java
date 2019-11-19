package com.example.notebook;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatebaseHelper extends SQLiteOpenHelper {
    public static final String creat_list="create table List ("
            + "id integer primary key autoincrement,"
            + "word text,"
            + "sentence text)";

    private Context mContext;

    public MyDatebaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version)//（内容，数据库名，null，版本int）
    {
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creat_list);//执行数据库语句
        //Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


}
