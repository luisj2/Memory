package com.example.memory;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivityTomarFoto extends AppCompatActivity {

    ImageView imagen;
    private ActivityResultLauncher<Intent> launcher;
    static int REQUEST_PERMISSIONS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tomar_foto);

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
                                imagen.setImageBitmap(imgBitmap);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                // Iniciar la siguiente actividad y pasar la imagen como array de bytes
                                Intent intent = new Intent(this, MainActivityJugar.class);
                                intent.putExtra("imagen", byteArray);
                                startActivity(intent);
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
        Intent hacerFot = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(hacerFot);
    }

    public void IrAJugar(View view) {
        Intent i = new Intent(this, MainActivityJugar.class);
        startActivity(i);
    }
}
