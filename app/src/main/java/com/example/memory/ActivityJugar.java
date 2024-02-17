package com.example.memory;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityJugar extends AppCompatActivity {

    Cronometro cronometro;
    int segundos = 60;
    TextView txtCronometro;
    ImageButton carta00,carta01,carta02,carta03,carta10,carta11,carta12,carta13,carta20,carta21,carta22,carta23;

    ImageButton cartaSeleccionada = null;
    ImageButton [][] totalCartas = new ImageButton[3][4];
    int [] imagenes = new int[6];
    Handler handler = new Handler();

    boolean bloquearCartas = false;

    int imagenPrimeraCarta,imagenSegunda;

    int correctos = 0;

    int cartaPredeterminada = R.drawable.cartapredeterminada;


    ArrayList <Integer> cartasBarajadas = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);



        txtCronometro = findViewById(R.id.textViewCronometro);
        cronometro = new Cronometro(txtCronometro);
        cartasBarajadas = barajarCartas(imagenes.length);

        cronometro.iniciarCronometro();
        inicializarCartas();
        rellenarImagenes();
        cargarImagenes();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cargarCartaDefecto();
            }
        },1200);

        darFuncionalidadCartas();

    }



    public void inicializarCartas (){
        //primera fila
        carta00 = findViewById(R.id.carta00);
        carta01 = findViewById(R.id.carta01);
        carta02 = findViewById(R.id.carta02);
        carta03 = findViewById(R.id.carta03);
        //segunda fila
        carta10 = findViewById(R.id.carta10);
        carta11 = findViewById(R.id.carta11);
        carta12 = findViewById(R.id.carta12);
        carta13 = findViewById(R.id.carta13);
        //tercera fila
        carta20 = findViewById(R.id.carta20);
        carta21 = findViewById(R.id.carta21);
        carta22 = findViewById(R.id.carta22);
        carta23 = findViewById(R.id.carta23);

        totalCartas[0][0] = carta00;
        totalCartas[0][1] = carta01;
        totalCartas[0][2] = carta02;
        totalCartas[0][3] = carta03;

        totalCartas[1][0] = carta10;
        totalCartas[1][1] = carta11;
        totalCartas[1][2] = carta12;
        totalCartas[1][3] = carta13;

        totalCartas[2][0] = carta20;
        totalCartas[2][1] = carta21;
        totalCartas[2][2] = carta22;
        totalCartas[2][3] = carta23;

    }



    private void darFuncionalidadCartas() {

        for (int i = 0; i < totalCartas.length; i++) {
            for (int j = 0; j < totalCartas[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                //totalCartas[finalI][finalJ].setEnabled(true);
                totalCartas[finalI][finalJ].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!bloquearCartas){
                            comprobarCarta(finalI,finalJ,totalCartas[finalI][finalJ]);
                        }
                    }
                });
            }
        }
    }


    public ArrayList<Integer> barajarCartas (int tamanio){

        ArrayList <Integer> resultado = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < tamanio; j++) {
                resultado.add(j);
            }
        }
        Collections.shuffle(resultado);
       // Log.i("Informacion Array", Arrays.toString(resultado.toArray()));

        return resultado;
    }

    public void rellenarImagenes (){
        imagenes[0] = R.drawable.carta1;
        imagenes[1] = R.drawable.carta2;
        imagenes[2] = R.drawable.carta3;
        imagenes[3] = R.drawable.carta4;
        imagenes[4] = R.drawable.carta5;
        imagenes[5] = R.drawable.carta6;
        //imagenes[6] = R.drawable.carta7;
       // imagenes[7] = R.drawable.carta8;
    }

    public void cargarImagenes() {

        int iterable = 0;

        for (int i = 0; i < totalCartas.length; i++) {
            for (int j = 0; j < totalCartas[i].length; j++) {
                int indiceImagen = cartasBarajadas.get(iterable);
                totalCartas[i][j].setImageResource(imagenes[indiceImagen]);
                iterable++;

            }
        }
    }
    public void cargarCartaDefecto(){

        for (int i = 0; i < totalCartas.length; i++) {
            for (int j = 0; j < totalCartas[i].length; j++) {
                totalCartas[i][j].setImageResource(cartaPredeterminada);

            }
        }
    }

    public void comprobarCarta(int i,int j,ImageButton boton){
        int posicion = i*totalCartas[i].length+j;
        Handler tiempoVuelta = new Handler();
        if(cartaSeleccionada == null){
        boton.setImageResource(imagenes[cartasBarajadas.get(posicion)]);
        boton.setTag(imagenes[cartasBarajadas.get(posicion)]);
        boton.setEnabled(false);
        cartaSeleccionada = boton;
           
        }else{
            
        bloquearCartas = true;
        boton.setImageResource(imagenes[cartasBarajadas.get(posicion)]);
        boton.setTag(imagenes[cartasBarajadas.get(posicion)]);
        boton.setEnabled(false);



            if (boton.getTag() != null && cartaSeleccionada.getTag() != null //Las cartas son iguales
                    && boton.getTag().equals(cartaSeleccionada.getTag())) {
                //Toast.makeText(this, "Son iguales", Toast.LENGTH_SHORT).show();
                cartaSeleccionada = null;
                correctos++;
                if(correctos == imagenes.length){
                    Toast.makeText(this, "Has ganado!", Toast.LENGTH_SHORT).show();
                }else{
                    tiempoVuelta.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bloquearCartas = false;
                            cartaSeleccionada = null;

                        }
                    },1000);
                }

            } else { //Las cartas no son iguales

                //Toast.makeText(this, "No son iguales", Toast.LENGTH_SHORT).show();

                tiempoVuelta.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boton.setImageResource(cartaPredeterminada);
                        boton.setEnabled(true);
                        cartaSeleccionada.setImageResource(cartaPredeterminada);
                        cartaSeleccionada.setEnabled(true);
                        bloquearCartas = false;
                        cartaSeleccionada = null;

                    }
                },1000);
            }
        }
    }
}