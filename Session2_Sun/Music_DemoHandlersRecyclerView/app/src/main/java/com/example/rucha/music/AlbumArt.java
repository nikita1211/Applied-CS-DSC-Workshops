package com.example.rucha.music;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AlbumArt extends AppCompatActivity{
    private ArrayList<Uri> au;
    private ArrayList<String> names;
    private int total;
    private Button prev,play,next;
    private int pos=0;
    private TextView tv;
    private SeekBar seek;
    private MediaPlayer mp;
    private android.os.Handler handler = new android.os.Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_art);
        Intent in=getIntent();
        au=new ArrayList<Uri>();
        au=in.getParcelableArrayListExtra("Uri_String");       //imp
        total = au.size();
        pos=in.getIntExtra("pos",0);
        names=in.getStringArrayListExtra("name_list");  //imp
        prev=(Button)findViewById(R.id.prev);
        play=(Button)findViewById(R.id.play);
        next=(Button)findViewById(R.id.next);
        tv=(TextView)findViewById(R.id.song_name);
        seek=(SeekBar)findViewById(R.id.seekBar);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    int i = seek.getProgress();
                    mp.seekTo(i);
                    mp.start();
                }catch(Exception e){
                    //do nothing
                }

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp==null){
                    play();
                }
                else if(mp.isPlaying()){
                    mp.pause();
                }
                else{
                    int p=mp.getCurrentPosition();
                    mp.seekTo(p);
                    mp.start();
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(pos<total)
                    pos=(pos+1)%total;
                    if(mp!=null){
                        mp.release();
                        mp=null;
                    }
                    play();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos>0)
                    pos=pos-1;
                else if(pos==0){
                    pos=total-1;
                }
                if(mp!=null){
                    mp.release();
                    mp=null;
                }
                play();
            }
        });

    }


    private Runnable updateSeek = new Runnable() {
        @Override
        public void run() {
            try {
                int timeelapsed = mp.getCurrentPosition();
                seek.setProgress((int) timeelapsed);
                handler.postDelayed(this, 1000);
            }catch(Exception e){
                //do nothing
            }
        }
    };
    public void play(){
        if(mp!=null){
            if(mp.isPlaying())
                mp.release();
            //dont do anything
        }
        else{
            Uri uri=Uri.parse(au.get(pos).toString());
            mp=MediaPlayer.create(getApplicationContext(),uri);
            tv.setText(names.get(pos));
            seek.setMax(mp.getDuration());
            seek.setProgress(0);
            mp.start();
            ThreadClass th=new ThreadClass();
            th.start();


            //   handler.post(updateSeek);
        }


        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    /*  int position=mp.getCurrentPosition();
        mp.seekTo(position);
        mp.start();*/
    }

    class ThreadClass extends Thread{
        @Override
        public void run() {
            handler.post(updateSeek);
        }
    }
}
