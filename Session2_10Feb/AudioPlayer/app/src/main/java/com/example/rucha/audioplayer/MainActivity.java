package com.example.rucha.audioplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar seekbar;
    MediaPlayer mp;
    Button play,pause;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(Button)findViewById(R.id.play);
        pause=(Button)findViewById(R.id.pause);
        seekbar=(SeekBar) findViewById(R.id.seekbar);
         mp=MediaPlayer.create(MainActivity.this,R.raw.song1);
        mHandler=new Handler();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setMax(mp.getDuration());
                mp.start();
               // ThreadClass th=new ThreadClass();
                //th.start();
                mHandler.post(th);

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });


    }
    Runnable th=new Runnable() {
        @Override
        public void run() {
            seekbar.setProgress(mp.getCurrentPosition());
            mHandler.postDelayed(th,1000);
        }
    };

    class ThreadClass extends  Thread{
        @Override
        public void run() {
           // Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
            mHandler.post(th);

        };

            //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
            //super.run();
        }

    }

