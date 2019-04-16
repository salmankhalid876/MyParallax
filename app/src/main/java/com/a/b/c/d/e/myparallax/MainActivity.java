package com.a.b.c.d.e.myparallax;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {

            Intent intent = new Intent(
                    WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(this, WP_Clock.class));
            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(this, "Error: Device No Compatible", Toast.LENGTH_SHORT).show();

        }
    }

    public void start(View view) {

        try {

            Intent intent = new Intent(
                    WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(this, WP_Clock.class));
            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(this, "Error: Device No Compatible", Toast.LENGTH_SHORT).show();

        }

    }
}
