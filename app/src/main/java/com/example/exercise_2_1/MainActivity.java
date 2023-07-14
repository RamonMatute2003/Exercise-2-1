package com.example.exercise_2_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int TOMA_VEDEO=1;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private VideoView video;
    Button btn_save_android, btn_save_sqlite, btn_take_video;
    AssetFileDescriptor video_asset;
    FileInputStream file;
    FileOutputStream url_file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video=findViewById(R.id.videoView);
        btn_take_video=findViewById(R.id.btn_take_video);
        btn_save_android=findViewById(R.id.btn_save_android);
        btn_save_sqlite=findViewById(R.id.btn_save_sqlite);

        btn_take_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                take_video();
            }
        });

        btn_save_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                save_android();
            }
        });
    }

    private void take_video(){
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, TOMA_VEDEO);
    }

    private void save_android() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==TOMA_VEDEO && resultCode==RESULT_OK){
            Uri video_uri=data.getData();
            video.setVideoURI(video_uri);
            video.start();

            try{
                video_asset=getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                file=video_asset.createInputStream();
                url_file=openFileOutput(create_name(), Context.MODE_PRIVATE);
                byte[] buffer=new byte[1024];
                int len;

                while((len=file.read())>0){
                    url_file.write(buffer, 0, len);
                }

            }catch(IOException e){
                Log.e("Error: ", ""+e);
            }

        }
    }

    private String create_name(){
        String date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name=date+".mp4";

        return name;
    }

}