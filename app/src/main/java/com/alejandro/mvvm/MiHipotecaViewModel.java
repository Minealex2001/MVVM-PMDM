package com.alejandro.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * MiHipotecaViewModel
 * Clase que representa el ViewModel de la aplicación.
 * Esta clase se encarga de realizar las operaciones de negocio de la aplicación.
 */
public class MiHipotecaViewModel extends AndroidViewModel {

    // Variables
    MutableLiveData<Double> cuota = new MutableLiveData<>();
    MutableLiveData<Double> errorCapital = new MutableLiveData<>();
    MutableLiveData<Integer> errorPlazos = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    // Instancia de la clase SimuladorHipoteca.
    SimuladorHipoteca simulador;

    // Instancia de la clase Executor.
    Executor executor;


    // Constructor de la clase.
    public MiHipotecaViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorHipoteca();
    }

    // Método que se encarga de realizar el cálculo de la cuota.
    public void calcular(double capital, int plazo) {

        // Instanciamos la clase Solicitud.
        final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(capital, plazo);

        /**
         * Ejecutamos el cálculo de la cuota en un hilo secundario.
         */
        executor.execute(new Runnable() {

            // Método que se ejecuta cuando se inicia el cálculo de la cuota.
            @Override
            public void run() {
                simulador.calcular(solicitud, new SimuladorHipoteca.Callback() {
                    // Método que se ejecuta cuando se ha calculado la cuota.
                    @Override
                    public void cuandoEsteCalculadaLaCuota(double cuotaResultante) {
                        errorCapital.postValue(null);
                        errorPlazos.postValue(null);
                        cuota.postValue(cuotaResultante);
                    }

                    // Método que se ejecuta cuando se ha producido un error.
                    @Override
                    public void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo) {
                        errorCapital.postValue(capitalMinimo);
                    }

                    // Método que se ejecuta cuando se ha producido un error.
                    @Override
                    public void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo) {
                        errorPlazos.postValue(plazoMinimo);
                    }

                    // Método que se ejecuta cuando se inicia el cálculo de la cuota.
                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }
                    // Método que se ejecuta cuando se ha finalizado el cálculo de la cuota.
                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            }
        });
    }

}