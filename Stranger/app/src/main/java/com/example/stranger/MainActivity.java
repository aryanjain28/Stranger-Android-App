package com.example.stranger;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        final TextView waitText = findViewById(R.id.waitText);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.aryansImage).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                                findViewById(R.id.existence1).setVisibility(View.INVISIBLE);
                                findViewById(R.id.existence2).setVisibility(View.INVISIBLE);
                                Glide.with(MainActivity.this)
                                        .asBitmap()
                                        .load("http://thispersondoesnotexist.com/image")
                                        .listener(new RequestListener<Bitmap>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                                findViewById(R.id.existence1).setVisibility(View.VISIBLE);
                                                findViewById(R.id.existence2).setVisibility(View.VISIBLE);
                                                waitText.setText("(Takes few seconds to load.)");
                                                Toast.makeText(MainActivity.this, "Image ready", Toast.LENGTH_SHORT).show();
                                                return false;
                                            }
                                        })
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into((ImageView)findViewById(R.id.aryansImage));
                            }
                        }
                );
            }
        };
        Thread aryansThread = new Thread(r);
        aryansThread.start();
    }
}
