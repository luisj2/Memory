package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Musica musica;
    Musica sonido;
    private final int REQUEST_CODE_PERMISSIONS = 1000; //VARIABLE PARA PERMISOS

    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boton = findViewById(R.id.btnMusicaInicio);

    }






    public void paraMusica(View view) {
        boton.setText("Musica off");

    }



    public void redireccionarAlJuego (View view){
        //musicaInicio.stop();
        Intent i = new Intent(this, ActivityJugar.class);
        startActivity(i);

    }
}