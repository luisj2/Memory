package com.example.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityResumenPartida extends AppCompatActivity {

    Musica musica;
    Button btnMute;
    Musica sonido;
    Button mute;
    float puntos;
    Intent i;
    Intent intent;
    TextView textViewPuntos;
    TextView textViewAciertos;
    TextView textViewTiempo;


    int REQUEST_IMAGE_CAPTURE = 0;
    ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resumen_partida);



        //VARIABLES
        musica = new Musica(this, R.raw.muisca_resumen);
        musica.play();
        sonido = new Musica(this,R.raw.sonido_mute_btn);


        btnMute = findViewById(R.id.btnMute);
        textViewAciertos = findViewById(R.id.textViewAciertos);
        textViewTiempo = findViewById(R.id.textViewTiempo);
        textViewPuntos = findViewById(R.id.textViewPuntosTotal);

        i = getIntent();

        double intentos = Double.parseDouble(String.valueOf(i.getIntExtra("intentos",0)));
        int correctos = i.getIntExtra("correctos",0);
        double tiempo = Double.parseDouble(String.valueOf(i.getIntExtra("tiempo",0)));

        double totalPuntos = intentos * correctos + tiempo;

        textViewTiempo.setText("Tiempo: "+tiempo+"");
        textViewAciertos.setText("Correctos: "+correctos+"");
        textViewPuntos.setText("Total Puntos: "+totalPuntos+"");

    }



    /*
    GESTIONAR LA FOTO ( procesa la foto en una secuencia de bytes )

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
    String currentPhotoPath;


    //GUARDAR LA FOTO
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,   fotoCapturada
                ".jpg",          jpg
                storageDir       ?¿
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;

     */


    public void IrAInicio(View view) {
        musica.stop();
        musica.liberarRecursos();

        sonido.play();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sonido.liberarRecursos();


        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void silenciar(View view) {
        sonido.play();
        if(musica.isActivada()){
            btnMute.setText("Musica off");
            musica.stop();
        }else  {
            btnMute.setText("Musica on");
            musica.play();
        }

    }
}