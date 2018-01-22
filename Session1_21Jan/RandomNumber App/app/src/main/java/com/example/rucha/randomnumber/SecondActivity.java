package com.example.rucha.randomnumber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class SecondActivity extends AppCompatActivity{
    TextView randomText,randomNumber;
    int randomInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        randomText=(TextView)findViewById(R.id.text);
        randomNumber=(TextView)findViewById(R.id.randomnum);
        getRandomNumber();
    }

    private void getRandomNumber() {
        int count= getIntent().getIntExtra("Maxcount",0);
        randomText.setText(getString(R.string.random_heading,count));

        Random random=new Random();
        if(count>0)
        randomInt=random.nextInt(count);
        randomNumber.setText(Integer.toString(randomInt));
    }
}
