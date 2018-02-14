package com.example.rucha.audioplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SeekBar seekbar;
    MediaPlayer mp;
    Button play,pause;
    Handler mHandler;
    TextView tv;
    int i=0;
    ArrayList<Integer> names;
    ArrayList<String>titles;
    //completion listener object
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer m) {
            //  mp.release();
            // i++;
            play();

            //mp.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(Button)findViewById(R.id.play);
        pause=(Button)findViewById(R.id.pause);
        seekbar=(SeekBar) findViewById(R.id.seekbar);
        mp=MediaPlayer.create(MainActivity.this,R.raw.s1);
        //set completion listener to mp
        mp.setOnCompletionListener(completionListener);
        tv=(TextView)findViewById(R.id.tv);
        names=new ArrayList<Integer>();
        titles=new ArrayList<String>();
        names.add(R.raw.s1);
        names.add(R.raw.s2);
        names.add(R.raw.s3);

        titles.add("It's Time");
        titles.add("Song2");
        titles.add("Song3");

        mHandler=new Handler();
        // uri=MediaStore.getDocumentUri(MainActivity.this,R.raw.s1);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar.setMax(mp.getDuration());
                seekbar.setProgress(0);
                mp.start();
                ThreadClass th=new ThreadClass();
                th.start();

//                mHandler.post(th);

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    int i = seekbar.getProgress();
                    mp.seekTo(i);
                    mp.start();
                }catch(Exception e){
                    //do nothing
                }

            }
        });

    }
    public void play(){
        if(i<2) {
            i++;
        }else{
            i=0;
        }
        mp = MediaPlayer.create(MainActivity.this, names.get(i));
        //set completion listener to mp
        mp.setOnCompletionListener(completionListener);
        seekbar.setProgress(0);
        seekbar.setMax(mp.getDuration());
        tv.setText(titles.get(i));
        mp.start();
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
            mHandler.post(th);

        };
    }

}
