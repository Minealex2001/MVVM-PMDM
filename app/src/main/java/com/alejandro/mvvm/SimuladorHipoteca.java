package com.alejandro.mvvm;

/**
 * SimuladorHipoteca
 * Clase que representa el simulador de hipotecas.
 * Esta clase se encarga de realizar el cálculo de la cuota de una hipoteca.
 */
public class SimuladorHipoteca {

    // Clase que representa la solicitud de cálculo de la cuota.
    public static class Solicitud{

        // Variables
        public double capital;
        public int plazo;

        // Constructor de la clase.
        public Solicitud(double capital, int plazo) {
            this.capital = capital;
            this.plazo = plazo;
        }
    }

    // Interfaz que representa el callback del cálculo de la cuota.
    interface Callback {
        void cuandoEsteCalculadaLaCuota(double cuota);
        void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo);
        void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    // Método que se encarga de realizar el cálculo de la cuota.
    public void calcular(Solicitud solicitud, Callback callback) {

        callback.cuandoEmpieceElCalculo();

        double interes = 0;
        double capitalMinimo = 0;
        int plazoMinimo = 0;

        // Simulamos un cálculo de 10 segundos.
        try{
            Thread.sleep(10000);
            interes = 0.01605;
            capitalMinimo = 1000;
            plazoMinimo = 2;
        }catch (InterruptedException e){}

        boolean error = false;
        if (solicitud.capital < capitalMinimo) {
            callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo);
            error = true;
        }

        if (solicitud.plazo < plazoMinimo) {
            callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadaLaCuota(solicitud.capital * interes / 12 / (1 - Math.pow(1 + (interes / 12), -solicitud.plazo * 12)));
        }

        callback.cuandoFinaliceElCalculo();
    }
}