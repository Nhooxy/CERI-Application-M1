package com.example.nhooxy.listenator;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Classe qui surround le MediaPlayer d'android pour facilité avec le streaming.
 */
public class Player implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private Context context;

    /**
     * Constructeur avec le context de l'application.
     * @param context
     */
    public Player(Context context) {
        this.context = context;
    }

    /**
     * Permet de lire la musique via une url, demarre la lecture une fois que c'est un peu bufferisé.
     * @param url
     */
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

    /**
     * Permet de mettre en pause la lecture.
     */
    void pause() {
        mediaPlayer.pause();
    }

    /**
     * Permet de reprendre la lecture ou de demarrer la lecture
     * (un media doit etre charger au préalable).
     */
    void play() {
        mediaPlayer.start();
    }

    /**
     * Permet d'arreter la lecture.
     */
    void stop() {
        mediaPlayer.stop();
    }

    /**
     * Appeller automatiquement par play(url) quand tout est pret.
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i("mp3Player", "duration=" + mediaPlayer.getDuration());
        mediaPlayer.start();
    }

    /**
     * Une fois que la musique est fini.
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
    }
}
