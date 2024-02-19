package com.example.memory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcherVideo;
    boolean pulsado = false;
    private final int REQUEST_PERMISSIONS = 1000; //VARIABLE PARA PERMISOS
    Musica musica;
    Musica sonido;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DECLARACION DE OBJETOS
        musica = new Musica(this,R.raw.musica_inicio);
        boton = findViewById(R.id.btnMusicaInicio);


        //INICIAR MUSICA
        musica.play();
    }





    //BOTON SILENCIAR MUSICA
    public void paraMusica(View view) {

        if(musica.isActivada()){
            boton.setText("Musica off");
            musica.stop();
        }else  {
            boton.setText("Musica on");
            musica.play();
        }
    }



    //IR AL JUEGO Y LIBERAR RECURSOS
    public void redireccionarAlJuego (View view) throws InterruptedException {

        if(!pulsado) {
            musica.liberarRecursos();

            pulsado = true;

            sonido = new Musica(this, R.raw.boton_pulsado_inicio);
            //SONIDO PULSAR BOTON
            sonido.play();
            Thread.sleep(3000);
            sonido.liberarRecursos();

            Intent i = new Intent(this, MainActivityTomarFoto.class);
            startActivity(i);

        }
    }
}