package com.example.memory;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class Musica {

    int id;
    Context context;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public Musica(Context context, int id) {
        this.context = context;
        this.id = id;
    }

    public void play() throws IOException {
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            mediaPlayer = new MediaPlayer();
            String uri = ""+id;
            mediaPlayer.setDataSource(context,   Uri.parse(uri));
            mediaPlayer.start();
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        } else
            mediaPlayer.isPlaying();
    }

    public void liberarRecursos() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
