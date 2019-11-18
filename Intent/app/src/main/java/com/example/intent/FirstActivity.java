package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        button=(Button)findViewById(R.id.btn_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                Intent intent1=new Intent();
                intent1.putExtra("first","FirstActivity");
                setResult(RESULT_OK,intent1);
                Log.d("pig", intent.getStringExtra("name"));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("first","FirstActivity");
        setResult(RESULT_OK,intent);
    }
}
