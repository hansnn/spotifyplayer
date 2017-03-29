package com.hansn.spotifyplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.hansn.spotifyplayer.R;
import com.hansn.spotifyplayer.singleton.PlayerSingleton;
import com.hansn.spotifyplayer.view.*;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends Activity {

    private String url;
    private Timer timer;
    private boolean isPlaying;
    private PlayerSingleton player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);
        url = getIntent().getStringExtra("url");
        player = PlayerSingleton.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        PlayButton btn = (PlayButton) findViewById(R.id.play_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayButton btn = (PlayButton) v;
                if (isPlaying) {
                    player.pauseSong();
                    btn.setIsPlaying(false);
                    stopTimer();
                } else {
                    player.playSong(url);
                    btn.setIsPlaying(true);
                    startTimer();
                }
                isPlaying = !isPlaying;
            }
        });

        ProgressBar bar = (ProgressBar)findViewById(R.id.progress_bar);
        bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        double newPos = (player.getSongDuration() / 100) * ((x / v.getWidth()) * 100);
                        player.setSongPos(newPos);
                        break;
                }
                return true;
            }
        });
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateProgressBarProgress();
            }
        }, 0, 100);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void updateProgressBarProgress() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                progressBar.setCurrentPos(player.getCurrentPosAsPercentage());
            }
        });
    }
}
