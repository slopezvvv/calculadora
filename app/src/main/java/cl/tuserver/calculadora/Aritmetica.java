package cl.tuserver.calculadora;

public class Aritmetica implements InterfaceAritmetica{
    private static final int NUM_DOS = 2;

    @Override
    public double adicion(double a, double b){
        return a + b;
    }

    @Override
    public double sustraccion(double a, double b){
        return a - b;
    }

    @Override
    public double multiplicacion(double a, double b){
        return a * b;
    }

    @Override
    public double divicion(double a, double b){
        if(b <= 0) throw new NumberFormatException("La division por cero no es posible");
        return a / b;
    }

    @Override
    public double potencia(double a, double b){
        return Math.pow(a, b);
    }

    @Override
    public double potenciaPorDos(double a){
        return Math.pow(a, NUM_DOS);
    }
}
