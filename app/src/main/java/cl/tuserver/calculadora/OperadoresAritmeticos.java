package cl.tuserver.calculadora;

public enum OperadoresAritmeticos {
    SUMA("+"), RESTA("-"), MULTI("*"), DIVI("/"), POW("^"), POW_2("^2");

    private final String simbolo;
    OperadoresAritmeticos(String simbolo){
        this.simbolo = simbolo;
    }

    @Override
    public String toString(){
        return simbolo;
    }
}
