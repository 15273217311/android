package com.example.weatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.prefs.Preferences;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout weatherInfoLayout;
    private String cityCode;
    private String selectProvince;
    private String selectCity;
    private String  TAG="tag";
    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private TextView publishText;
    /**
     * 用于显示天气描述信息
     */
    private TextView weatherDespText;
    /**
     * 用于显示气温1
     */
    private TextView tempText;

    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;
    /**
     * 切换城市按钮
     */
    private Button switchCity;
    /**
     * 更新天气按钮
     */
    private Button refreshWeather;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.weather_layout);

        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        tempText = (TextView) findViewById(R.id.temp1);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

        selectCity=getIntent().getStringExtra("city");
        selectProvince=getIntent().getStringExtra("province");
        cityCode=getIntent().getStringExtra("id");

        cityNameText.setText(selectProvince+","+selectCity);

        //解析服务器天气
        final String address="https://free-api.heweather.net/s6/weather/now?location=CN"+cityCode+"&key=e5ccc165ed7a4814885591a58888934f";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    SharedPreferences sharedPreferences=getSharedPreferences("weather",WeatherActivity.MODE_PRIVATE);
                    SharedPreferences.Editor prefs=sharedPreferences.edit();
                    JSONObject jsonObject2=jsonObject1.getJSONObject("update");
                    Log.d(TAG, jsonObject2.getString("loc"));
                    prefs.putString("update",jsonObject2.getString("loc"));
                    jsonObject2=jsonObject1.getJSONObject("now");
                    prefs.putString("now","天气"+jsonObject2.getString("cond_txt")+","+"相对湿度"+jsonObject2.getString("hum")
                    +",降水量"+jsonObject2.getString("pcpn")+"...");
                    prefs.putString("temp",jsonObject2.getString("tmp"));
                    prefs.commit();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences=getSharedPreferences("weather",WeatherActivity.MODE_PRIVATE);
                        publishText.setText("更新时间"+sharedPreferences.getString("update",""));
                        Date date=new Date();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                        currentDateText.setText("此时时间为"+simpleDateFormat.format(date));
                        weatherDespText.setText(sharedPreferences.getString("now",""));
                        tempText.setText(sharedPreferences.getString("temp",""));
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                break;
            case R.id.refresh_weather:
                //解析服务器天气
                final String address="https://free-api.heweather.net/s6/weather/now?location=CN"+cityCode+"&key=e5ccc165ed7a4814885591a58888934f";
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            SharedPreferences sharedPreferences=getSharedPreferences("weather",WeatherActivity.MODE_PRIVATE);
                            SharedPreferences.Editor prefs=sharedPreferences.edit();
                            JSONObject jsonObject2=jsonObject1.getJSONObject("update");
                            Log.d(TAG, jsonObject2.getString("loc"));
                            prefs.putString("update",jsonObject2.getString("loc"));
                            jsonObject2=jsonObject1.getJSONObject("now");
                            prefs.putString("now","天气"+jsonObject2.getString("cond_txt")+","+"相对湿度"+jsonObject2.getString("hum")
                                    +",降水量"+jsonObject2.getString("pcpn")+"...");
                            prefs.putString("temp",jsonObject2.getString("tmp"));
                            prefs.commit();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPreferences=getSharedPreferences("weather",WeatherActivity.MODE_PRIVATE);
                                publishText.setText("更新时间"+sharedPreferences.getString("update",""));
                                Date date=new Date();
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                                currentDateText.setText("此时时间为"+simpleDateFormat.format(date));
                                weatherDespText.setText(sharedPreferences.getString("now",""));
                                tempText.setText(sharedPreferences.getString("temp",""));
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                publishText.setText("同步失败");
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
    }
}
