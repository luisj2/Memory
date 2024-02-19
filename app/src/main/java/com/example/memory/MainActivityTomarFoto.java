package com.example.memory;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivityTomarFoto extends AppCompatActivity {
    Musica musica;
    Musica sonido;
    Button musicaMute;
    boolean pulsdo = false;
    byte[] byteArray;
    ImageView imagen;
    private ActivityResultLauncher<Intent> launcher;
    static int REQUEST_PERMISSIONS = 1000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tomar_foto);

        sonido = new Musica(this,R.raw.sonido_mute_btn);
        musicaMute = findViewById(R.id.musicaFoto);
        musica = new Musica(this,R.raw.musica_foto);
        musica.play();
        imagen = findViewById(R.id.fotoTomada);


        // PEDIR PERMISOS PARA CAMARA Y ESCRIBIR EN EL MEMORIA
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivityTomarFoto.this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_PERMISSIONS
            );
        }
        initializeLauncher();
    }

    // Método para inicializar el launcher
    private void initializeLauncher() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null) {
                            Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
                            if (imgBitmap != null) {

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byteArray = stream.toByteArray();
                                imagen.setImageBitmap(imgBitmap);

                            } else {
                                Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    // Manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                initializeLauncher();
            }
        }
    }

    public void Fotomat(View view) {
        sonido.play();
        Intent hacerFot = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(hacerFot);
    }

    public void mute(View view) {
        sonido.play();
        if(musica.isActivada()){
            musica.stop();
            musicaMute.setText("Musica off");
        } else {
            musica.play();
            musicaMute.setText("Musica on");
        }
    }
    public void IrAJugar(View view) {

        if(!pulsdo) {

            pulsdo = true;
            musica.liberarRecursos();

            sonido = new Musica(this,R.raw.tono_pulsar_boton);
            sonido.play();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sonido.liberarRecursos();

            Intent i = new Intent(this, MainActivityJugar.class);
            i.putExtra("imagen", byteArray);
            startActivity(i);
        }
    }
}
