package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public TextView titleText;
    public StringAdapter adapter;
    public CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<>();
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    private String TAG="tag";
    /*
    省列表
     */
    private List<Province> provinceList;
    /*
   市列表
   */
    private List<City> cityList;
    /*
    选中的省份
     */
    private Province selectProvince;
    /*
    选中的市份
    */
    private City selectCity;
    /*
    当前选中的级别
     */
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_title);
        adapter =new StringAdapter(dataList,this);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        queryProvinces();//加载省级数据
        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectProvince = provinceList.get(position);
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    selectCity = cityList.get(position);
                    Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                    intent.putExtra("id",selectCity.getCityCode());
                    intent.putExtra("province",selectProvince.getProvinceName());
                    intent.putExtra("city",selectCity.getCityName());
                    startActivity(intent);
                }
            }
        });

    }
    /**
     * 查询全国的省，先在数据库去查，没有查询到在从文件上去查询
     */
    public void queryProvinces() {
        provinceList = coolWeatherDB.loadProvinces();
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel=LEVEL_PROVINCE;
        }else {
            StringBuilder stringBuilder=new StringBuilder();
            try {

                AssetManager assetManager=this.getAssets();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(
                        assetManager.open("province.json")));
                String line;
                while ((line= bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            //解析json，使用JsonObject解析json数据
            try {
                JSONArray jsonArray=new JSONArray(stringBuilder.toString());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String provinceName=jsonObject.getString("name");
                    String provinceCode=jsonObject.getString("id");
                    coolWeatherDB.saveProvince(new Province(i,provinceName,provinceCode));
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            //读数据库
            provinceList = coolWeatherDB.loadProvinces();
            dataList.clear();
            for (Province province:provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel=LEVEL_PROVINCE;
        }
    }

    /**
     * 查询全国的省，先在数据库去查，没有查询到在从文件上去查询
     */
    public void queryCities()
    {
        cityList=coolWeatherDB.loadCities(selectProvince.getProvinceName());
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectProvince.getProvinceName());
            currentLevel=LEVEL_CITY;
        }else {
            StringBuilder stringBuilder=new StringBuilder();
            try {
                AssetManager assetManager=this.getAssets();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(
                        assetManager.open("city.json")));
                String line;
                while ((line= bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            //解析json
            try {
                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                JSONArray jsonArray=jsonObject.getJSONArray("城市代码");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsCity=jsonArray.getJSONObject(i);
                    String jsCityName=jsCity.getString("省");
                    if (jsCityName.equals(selectProvince.getProvinceName().substring(0,jsCityName.length())))
                    {
                        JSONArray jsonArray1=jsCity.getJSONArray("市");
                        for (int k=0;k<jsonArray1.length();k++){
                            String cityName=jsonArray1.getJSONObject(k).getString("市名");
                            String cityCode=jsonArray1.getJSONObject(k).getString("编码");
                            coolWeatherDB.saveCity(new City(i,cityName,cityCode,selectProvince.getProvinceName()));
                        }
                        break;
                    }

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            cityList=coolWeatherDB.loadCities(selectProvince.getProvinceName());
            dataList.clear();
            for (City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectProvince.getProvinceName());
            currentLevel=LEVEL_CITY;
        }
    }


}
