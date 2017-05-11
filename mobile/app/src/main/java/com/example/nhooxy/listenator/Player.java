package com.example.nhooxy.listenator;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class Player implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private Context context;

    public Player(Context context) {
        this.context = context;
    }

    public void play(String url) {
        mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.prepareAsync();
    }

    void pause() {
        mediaPlayer.pause();
    }

    void play() {
        mediaPlayer.start();
    }

    void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i("mp3Player", "duration=" + mediaPlayer.getDuration());
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
    }
}