package com.example.rucha.audplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button play,pause;
    SeekBar seekBar;
    MediaPlayer mp;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(Button)findViewById(R.id.play);
        pause=(Button)findViewById(R.id.pause);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        mp=MediaPlayer.create(MainActivity.this,R.raw.song1);
        seekBar.setMax(mp.getDuration());
        mHandler=new Handler();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //play song
                mp.start();
                ThreadClass threadClass=new ThreadClass();
                threadClass.start();
                //mHandler.post(th);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pause
                mp.pause();
            }
        });
    }
    Runnable th=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mp.getCurrentPosition());
            mHandler.postDelayed(th,1000);
        }
    };

    class ThreadClass extends Thread{
        @Override
        public void run() {
          //  Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
            mHandler.post(th);
           // super.run();
        }
    }
}
