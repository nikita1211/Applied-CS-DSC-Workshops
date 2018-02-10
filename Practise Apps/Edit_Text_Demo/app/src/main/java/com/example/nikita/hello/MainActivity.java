package com.example.nikita.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txtv;
    EditText edt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //edt= (EditText) findViewById(R.id.edittext);
        btn = (Button)findViewById(R.id.button);
        txtv = (TextView) findViewById(R.id.textview);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int maxLength = 6;
        //InputFilter[] FilterArray = new InputFilter[1];
        //FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        edt= (EditText) findViewById(R.id.edittext);
        //edt.setFilters(FilterArray);
        //edt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength), new InputFilter.AllCaps()});
    }



    public void methodclick(View view)
    {
        edt= (EditText) findViewById(R.id.edittext);
        String text= edt.getText().toString();
        Log.d("string is",text);
        Toast.makeText(getApplicationContext(), "String is "+text,
                Toast.LENGTH_SHORT).show();
    }
}
