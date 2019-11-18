package com.example.dialogue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button help;
    Button dialogue;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        help=(Button)findViewById(R.id.btn_help);
        dialogue=(Button)findViewById(R.id.btn_dialogue);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Help.class);
                startActivity(intent);
            }
        });
        dialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogue= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogue,null);
                username=(EditText)dialogue.findViewById(R.id.username);
                password=(EditText)dialogue.findViewById(R.id.password);
                Button submit=(Button)dialogue.findViewById(R.id.btn_submit);
                AlertDialog.Builder layoutDialog=new AlertDialog.Builder(MainActivity.this);
                layoutDialog.setTitle("Dialogue");
                layoutDialog.setView(dialogue);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String get_username= username.getText().toString();
                        String get_password=password.getText().toString();
                        if(get_username=="abc" && get_password=="123")
                        {
                            Toast.makeText(MainActivity.this,"succession",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            password.setText("");
                            username.setText("");
                            Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                layoutDialog.show();


            }
        });
    }
}
