package com.example.memory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> launcherVideo;
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
        musica.liberarRecursos();

        sonido = new Musica(this, R.raw.boton_pulsado);
        //SONIDO PULSAR BOTON
        sonido.play();
        Thread.sleep(3000);
        sonido.liberarRecursos();

        Intent i = new Intent(this, MainActivityTomarFoto.class);
        startActivity(i);

    }
}