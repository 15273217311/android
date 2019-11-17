package com.example.calculator4;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnPoint;    //.
    private Button btnPlus;     //+
    private Button btnMinus;    //-
    private Button btnMultiply; //*
    private Button btnDivide;   //  /
    private Button btnEqual;    // =
    private Button btnClear;     //清平
    private Button btnDel;      //删除
    private Button btnRight; //  )
    private Button  btnLift; //  (
    private EditText etInput;   //输入
    private Button btnSin;
    private Button btnCos;
    private Button btnTan;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){

            case R.id.menuConversion:
                Toast.makeText(this,"转换",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,ConvertActivity.class);
                startActivity(intent);
                break;
            case R.id.menuPor:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case R.id.menuLands:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case R.id.help:
                Intent i=new Intent(this,Help.class);
                startActivity(i);
            default:
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        int orientation=getResources().getConfiguration().orientation;
        setContentView(R.layout.activity_main);


        findView();
        setListener();

    }
    /**  找到View  **/
    private void findView(){
        btn0=(Button) findViewById(R.id.txt0);
        btn1=(Button) findViewById(R.id.txt1);
        btn2=(Button) findViewById(R.id.txt2);
        btn3=(Button) findViewById(R.id.txt3);
        btn4=(Button) findViewById(R.id.txt4);
        btn5=(Button) findViewById(R.id.txt5);
        btn6=(Button) findViewById(R.id.txt6);
        btn7=(Button) findViewById(R.id.txt7);
        btn8=(Button) findViewById(R.id.txt8);
        btn9=(Button) findViewById(R.id.txt9);
        btnPlus=(Button) findViewById(R.id.txtPlus);
        btnMinus=(Button) findViewById(R.id.txtMinus);
        btnMultiply=(Button) findViewById(R.id.txtMul);
        btnDivide=(Button) findViewById(R.id.txtDiv);
        btnEqual=(Button) findViewById(R.id.txtEqual);
        btnPoint=(Button) findViewById(R.id.txtPoint);
        btnClear=(Button) findViewById(R.id.txtClear);
        btnDel=(Button) findViewById(R.id.txtDel);
        btnLift=(Button) findViewById(R.id.txtLeft);
        btnRight=(Button) findViewById(R.id.txtRight);
        btnSin=(Button) findViewById(R.id.txtSin);
        btnCos=(Button) findViewById(R.id.txtCos);
        btnTan=(Button) findViewById(R.id.txtTan);
        etInput=(EditText) findViewById(R.id.input);


    }
    /**  设置监听 **/
    private void setListener(){
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnLift.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
        btnTan.setOnClickListener(this);

    }

    @Override
    public  void onClick(View v){
        TextView outputTV=(TextView) findViewById(R.id.output);
        String str=etInput.getText().toString();
        Button btn=(Button) v;
        switch (v.getId()){
            case R.id.txtPlus:
            case R.id.txtMinus:
            case R.id.txtMul:
            case R.id.txtDiv:
                str+=" "+btn.getText()+" ";
                break;
            case R.id.txtRight:
                str+=" "+btn.getText();
                break;
            case R.id.txtLeft:
                str+=btn.getText()+" ";
                break;
            case R.id.txtSin:
            case R.id.txtCos:
            case R.id.txtTan:
                str+=btn.getText()+" ( ";
                break;
            case R.id.txtEqual:
                outputTV.setText(btnEqualEvent(str));
                break;
            case R.id.txtDel:
                str=btnDelEvent(str);
                break;
            case R.id.txtClear:
                str ="";
                outputTV.setText("");
                break;

            default:
                str+=btn.getText();
        }
        etInput.setText(str);
    }
    /**    ***/
    private String btnDelEvent(String str){
        if(str!=null&&!"".equals(str)){
            if(str.endsWith(" ")){
                str=str.substring(0,str.length()-3);
            }
            else if(str.endsWith(")")){
                str=str.substring(0,str.length()-2);
            }
            else str=str.substring(0,str.length()-1);

        }
        return str;
    }
    /** btnEqual **/
    private  String btnEqualEvent(String str){
        return Calculator.operate(str);
    }
}
