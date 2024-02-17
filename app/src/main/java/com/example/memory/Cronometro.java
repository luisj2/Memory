package com.example.memory;


import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class Cronometro {

        private Handler hilo;
        private TextView miTextView;
        private int segundosRestantes = 60;
        private boolean isTiempoTerminado;


        public Cronometro(TextView textView) {
            this.miTextView = textView;
            this.hilo = new Handler(Looper.getMainLooper());
            this.isTiempoTerminado = false;
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
                    if (!isTiempoTerminado) {
                        hilo.postDelayed(this, 1000);  // Contador cada segundo
                        segundosRestantes--;
                        pararSiTiempoTerminadoYactualizarTxtView();
                        comprobarTiempo();
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
            if(segundosRestantes < 0){
                isTiempoTerminado = true;
            }
    }

    public boolean isTiempoTerminado() {
        return isTiempoTerminado;
    }

    public void setTiempoTerminado(boolean tiempoTerminado) {
        isTiempoTerminado = tiempoTerminado;
    }
}
