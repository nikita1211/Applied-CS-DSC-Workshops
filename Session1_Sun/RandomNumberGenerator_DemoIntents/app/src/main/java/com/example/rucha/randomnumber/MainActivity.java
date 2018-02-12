package com.example.rucha.randomnumber;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button toast,count,random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast=(Button)findViewById(R.id.toast);
        count=(Button)findViewById(R.id.count);
        random=(Button)findViewById(R.id.random);

           }


    public void countMe(View view) {
        TextView showCountTextView=(TextView)findViewById(R.id.textview);
        String countString=showCountTextView.getText().toString();
        Integer count=Integer.parseInt(countString);
        count++;
        showCountTextView.setText(Integer.toString(count));

    }


    public void randomMe(View view) {
        TextView showCountTextView=(TextView)findViewById(R.id.textview);
        String countString=showCountTextView.getText().toString();
        Integer maxCount=Integer.parseInt(countString);
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtra("Maxcount",maxCount);
        startActivity(intent);
     /*   Uri uri = Uri.parse("http://www.google.com");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);*/

    }

    public void toastMe(View view) {
        Toast.makeText(MainActivity.this,"Here's a Toast!",Toast.LENGTH_SHORT).show();
    }
}
