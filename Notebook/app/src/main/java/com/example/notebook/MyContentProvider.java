package com.example.notebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.InputType;

public class MyContentProvider extends ContentProvider {

    public static final int DIR=0;
    public static final int ITEM=1;
    public static final String AUTHORITY="com.example.notebook.provider";
    private static UriMatcher uriMatcher;
    private MyDatebaseHelper dbHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"List",DIR);
        uriMatcher.addURI(AUTHORITY,"List/#", ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int del=0;
        switch (uriMatcher.match(uri)){
            case DIR:
            {
                del=db.delete("List",selection,selectionArgs);
            }
            break;
            case ITEM:
            {
                String id=uri.getPathSegments().get(1);
                del=db.delete("List","id=?",new String[]{id});

            }
            break;
            default:
                break;
        }
        return del;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri))
        {
            case DIR:
                return "vnd.android.cursor.dir/vnd.com.example.notebook.provider.List";
            case ITEM:
                return "vnd.android.cursor.item/vnd.com.example.provider.List";
                default:
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriReturn=null;
        long newId=db.insert("List",null,values);
        uriReturn=Uri.parse("content://"+AUTHORITY+"/List/"+newId);
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper=new MyDatebaseHelper(getContext(),"NoteBook.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri))
        {
            case DIR:
                cursor=db.query("List",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ITEM:
                String id=uri.getPathSegments().get(1);
                cursor=db.query("List",projection,"id=?",new String[]{id},null,null,sortOrder);
                break;
                default:
                    break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int update=0;
        switch (uriMatcher.match(uri))
        {
            case DIR:
                update=db.update("List",values,selection,selectionArgs);
                break;
            case ITEM:
                String id=uri.getPathSegments().get(1);
                update=db.update("List",values,"id=?",new String[]{id});
                break;
                default:
                    break;
        }
        return update;
    }
}
