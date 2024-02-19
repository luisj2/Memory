package com.example.memory;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

public class Cronometro {

        View view;
        private Handler hilo;
        private TextView miTextView;
        private int segundosRestantes = 60;
        private boolean isTiempoTerminado;


        public Cronometro(TextView textView) {
            this.miTextView = textView;
            this.hilo = new Handler(Looper.getMainLooper());
            this.isTiempoTerminado = false;
            this.view = view;
        }

        public void iniciarCronometro() {
            if (!isTiempoTerminado) {
                actualizarCronometro();
                if(segundosRestantes <= 0)
                    isTiempoTerminado= true;
            }
        }

        public void detenerCronometro() {
            if (isTiempoTerminado) {
                isTiempoTerminado = false;
                hilo.removeCallbacksAndMessages(null);
            }
        }

        private void actualizarCronometro() {
            hilo.post(new Runnable() {
                @Override
                public void run() {
                        comprobarTiempo();
                    if (!isTiempoTerminado) {
                        hilo.postDelayed(this, 1000);  // Contador cada segundo
                        segundosRestantes--;
                        pararSiTiempoTerminadoYactualizarTxtView();
                    } else {

                    }
                }
            });
        }
    public void pararSiTiempoTerminadoYactualizarTxtView(){
        if(isTiempoTerminado)
            detenerCronometro();
        else
            miTextView.setText(""+segundosRestantes);
    }
    public void comprobarTiempo(){
            if(segundosRestantes < 1){
                isTiempoTerminado = true;
            }
    }

    public boolean isTiempoTerminado() {

            return isTiempoTerminado;
    }

    public int getSegundosRestantes() {
        return segundosRestantes;
    }

    public void setTiempoTerminado(boolean tiempoTerminado) {
            isTiempoTerminado = tiempoTerminado;
    }
}
