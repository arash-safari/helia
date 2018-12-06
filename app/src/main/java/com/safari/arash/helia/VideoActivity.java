package com.safari.arash.helia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.safari.arash.helia.utils.CustomMediaPlayerAssertFolder;

import java.io.IOException;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        int number = getIntent().getIntExtra("number",1);
        JZDataSource jzDataSource = null;
        try {
            jzDataSource = new JZDataSource(getAssets().openFd("v"+number+".mp4"));
            jzDataSource.title = "video";
        } catch (IOException e) {
            e.printStackTrace();
        }
        jzvdStd.setUp(jzDataSource, Jzvd.SCREEN_WINDOW_NORMAL);
        Jzvd.setMediaInterface(new CustomMediaPlayerAssertFolder());
//        jzvdStd.thumbImageView.setImageURI();

    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
