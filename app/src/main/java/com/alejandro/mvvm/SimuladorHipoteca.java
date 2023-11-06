package com.alejandro.mvvm;

public class SimuladorHipoteca {

    public static class Solicitud {
        public double capital;
        public int plazo;

        public Solicitud(double capital, int plazo) {
            this.capital = capital;
            this.plazo = plazo;
        }

        public static double calcular(Solicitud solicitud){
            double interes = 0.00;
            try{
                Thread.sleep(10000);
                interes = 0.01605;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return solicitud.capital * interes / 12 / (1 - Math.pow(1 + interes / 12, -solicitud.plazo * 12));
        }
    }
}
