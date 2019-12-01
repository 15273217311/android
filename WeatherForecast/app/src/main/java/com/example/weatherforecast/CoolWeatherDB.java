package com.example.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME="cool_weather";
    /*
    数据库版本
     */
    public static final int VERSION=1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME
                ,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例
     * @param context
     * @return
     */
    public static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储到数据库中
     * @param province
     */
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    /**
     * 从数据库中读取所有省信息
     * @return
     */
    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Province province=new Province();
            province.setId(cursor.getInt(cursor.getColumnIndex("id")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            list.add(province);
        }
        return list;
    }
    /**
     * 将City的实例存储到数据库中
     */
    public void saveCity(City city){
        ContentValues values=new ContentValues();
        values.put("city_name",city.getCityName());
        values.put("city_code",city.getCityCode());
        values.put("province_name",city.getProvinceName());
        db.insert("City",null,values);
    }
    /**
     * 获取数据库中的所有市的信息
     *
     */
    public List<City> loadCities(String provinceName){
        List<City> list=new ArrayList<>();
        Cursor cursor=db.query("City",null,"province_name=?"
                ,new String[]{provinceName+""},null,null,null);
        while (cursor.moveToNext()){
            City city=new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceName(provinceName);
            list.add(city);
        }
        return list;
    }
}
