package com.example.calculator4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculator.R;

public class ConvertActivity extends AppCompatActivity implements View.OnClickListener{
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
    private Button btnEqual;    // =
    private Button btnClear;     //清平
    private Button btnDel;      //删除
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private Button btnE;
    private Button btnF;
    private EditText etFirst;
    private TextView textViewFirst;
    private  TextView textViewSecond;

    @Override   
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.convert_activity,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){

            case R.id.menuCalcultor:
                Toast.makeText(this,"转入计算器",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.menuPor:
                setContentView(R.layout.activity_main);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case R.id.menuLands:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Spinner spinner = (Spinner) findViewById(R.id.spFirst);
        textViewFirst=(TextView)findViewById(R.id.tvFirstUnits);
        Spinner spinner2 = (Spinner) findViewById(R.id.spSecond);
        textViewSecond=(TextView)findViewById(R.id.tvSecondUnits);
        getSpinnerResult(spinner,R.array.volume2,textViewFirst);
        getSpinnerResult(spinner2,R.array.volume,textViewSecond);
        findView();
        setListener();
    }
    public void getSpinnerResult(Spinner spinner, final int postion,final  TextView textView){
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,postion,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                                              @Override
                                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                  textView.setText(adapterView.getItemAtPosition(i).toString());
                                                  Toast.makeText(ConvertActivity.this,adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show();
                                              }
                                              public void onNothingSelected(AdapterView<?> adapterView){

                                              }
                                          }
        );
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
        btnEqual=(Button) findViewById(R.id.txtEqual);
        btnPoint=(Button) findViewById(R.id.txtPoint);
        btnClear=(Button) findViewById(R.id.txtClear);
        btnDel=(Button) findViewById(R.id.txtDel);
        etFirst=(EditText)findViewById(R.id.etFirst);
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
        btnPoint.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
    }

    @Override
    public  void onClick(View v){
        TextView tvSecond=(TextView)findViewById(R.id.tvSecond);
        tvSecond.setText("0");
        String str=etFirst.getText().toString();
        Button btn=(Button) v;
        switch (v.getId()){
            case R.id.txtEqual:
                tvSecond.setText(btnEqualEvent(str,textViewFirst.getText().toString(),textViewSecond.getText().toString()));
                break;
            case R.id.txtDel:
                str=btnDelEvent(str);
                break;
            case R.id.txtClear:
                str ="";
                tvSecond.setText("");
                break;
            default:
                str+=btn.getText();
        }
        etFirst.setText(str);
    }
    /**    ***/
    private String btnDelEvent(String str){
        str=str.substring(0,str.length()-1);
        return str;
    }
    /** btnEqual **/
    private  String btnEqualEvent(String str,String first,String  second){
        String result=null;
        result=Converst.converst(str,first,second);
        return result;
    }
}

