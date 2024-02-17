package com.example.memory;

import android.content.Context;
import android.media.MediaPlayer;

public class Musica {

    int id;
    Context context;
    MediaPlayer mediaPlayer;

    public Musica(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context,id);
            mediaPlayer.start();
        } else if(!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void liberarRecursos() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public boolean isActivada(){
        return mediaPlayer.isPlaying();
    }
}
