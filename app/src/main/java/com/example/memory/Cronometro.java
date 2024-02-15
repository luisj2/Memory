package com.example.memory;

import android.widget.TextView;

public class Cronometro extends Thread{

  private int segundos;
  private TextView txtCronometro;

  public Cronometro (int segundos, TextView txtCronometro){
      this.segundos = segundos;
      this.txtCronometro = txtCronometro;
  }
    @Override
    public void run() {

        for (int i = segundos; i >= 0; i--) {
            txtCronometro.setText(String.valueOf(i));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }

    }
}
