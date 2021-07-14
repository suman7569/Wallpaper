package com.appdevelopersumankr.api_demo_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class Fullscreenwallpaper extends AppCompatActivity {
    String originalurl="";
    PhotoView photoView;
    AppCompatButton setwallpaperbutton,downloadbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_fullscreenwallpaper );

        getSupportActionBar ().hide ();
        photoView=findViewById ( R.id.photoviewid);
        setwallpaperbutton=findViewById ( R.id.setwallpaperid);
        downloadbutton=findViewById ( R.id.downloadwallpaperid);

        Intent intent=getIntent ();
        originalurl=intent.getStringExtra ( "originalurl" );

        Glide.with ( this ).load ( originalurl ).into ( photoView );


        downloadbutton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager=(DownloadManager)getSystemService ( Context.DOWNLOAD_SERVICE);
                Uri uri=Uri.parse ( originalurl);
                DownloadManager.Request request=new DownloadManager.Request ( uri );
                request.setNotificationVisibility ( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
                downloadManager.enqueue ( request);
                Toast.makeText ( Fullscreenwallpaper.this, "Download Start", Toast.LENGTH_SHORT ).show ();
            }
        } );



        setwallpaperbutton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager;
                wallpaperManager = WallpaperManager.getInstance (getApplicationContext ());
                Bitmap bitmap=((BitmapDrawable)photoView.getDrawable ()).getBitmap ();

                try {
                    wallpaperManager.setBitmap ( bitmap );
                    Toast.makeText ( Fullscreenwallpaper.this, "Successful", Toast.LENGTH_SHORT ).show ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }

            }
        } );

    }
}