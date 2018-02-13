package com.example.rucha.music;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> item;
    ArrayList<Uri> mysongs;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mysongs=new ArrayList<Uri>();
        item=new ArrayList<String>();
        lv=(ListView)findViewById(R.id.song_list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,item);
        lv.setAdapter(adapter);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            mysongs = findsongs();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this,AlbumArt.class);
                intent.putExtra("Uri_String",mysongs);
                intent.putExtra("name_list",item);
                intent.putExtra("pos",i);

                startActivity(intent);
                }


        });



    }
    public ArrayList<Uri> findsongs(){
        ArrayList<Uri>songs=new ArrayList<Uri>();
        ContentResolver contentResolver= getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if(cursor==null){
            Toast.makeText(this,"Invalid Error",Toast.LENGTH_SHORT).show();
        }
        else if(!cursor.moveToFirst()){
            Toast.makeText(this,"No song found",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int titleColumn=cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn=cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            do{
                long id=cursor.getLong(idColumn);
                String title=cursor.getString(titleColumn);
                Uri songUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,id);
                songs.add(songUri);
                item.add(title);
            }while (cursor.moveToNext());
        }
        return songs;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"granted",Toast.LENGTH_LONG).show();
                    mysongs= findsongs();
                    adapter.notifyDataSetChanged();
                }
        }
    }
}
