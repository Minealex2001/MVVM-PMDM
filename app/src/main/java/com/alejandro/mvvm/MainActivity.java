package com.alejandro.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * MainActivity
 * Clase que representa la vista principal de la aplicación.
 * Esta clase se encarga de mostrar la vista principal de la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}