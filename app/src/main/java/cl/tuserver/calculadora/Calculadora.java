package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Calculadora extends AppCompatActivity implements InterfaceCalculadora{

    private static final int NUM_DOS = 2;
    private EditText txtInput;
    private Button btnRealizarOperacion;
    private Button btnSuma, btnResta, btnMulti, btnDivi, btnPow, btnPowPorDos;
    private static final HashMap<Integer, Double> historial = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        txtInput = findViewById(R.id.txtInput);
        btnSuma = findViewById(R.id.btnAdicionar);
        btnResta = findViewById(R.id.btnSustraer);
        btnMulti = findViewById(R.id.btnMultiplicar);
        btnDivi = findViewById(R.id.btnDividir);
        btnPow = findViewById(R.id.btnPotencia);
        btnPowPorDos = findViewById(R.id.btnPotenciaPorDos);
        btnRealizarOperacion = findViewById(R.id.btnRealizarOperacion);
        btnRealizarOperacion.setOnClickListener(v -> doOperacion(txtInput.getText().toString()));
        btnSuma.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"+");
            txtInput.setSelection(txtInput.getText().length());
        });
        btnResta.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"-");
            txtInput.setSelection(txtInput.getText().length());
        });
        btnMulti.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"*");
            txtInput.setSelection(txtInput.getText().length());
        });
        btnDivi.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"/");
            txtInput.setSelection(txtInput.getText().length());
        });
        btnPow.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"^");
            txtInput.setSelection(txtInput.getText().length());
        });
        btnPowPorDos.setOnClickListener(v -> {
            txtInput.setText(txtInput.getText().toString()+"^2");
            txtInput.setSelection(txtInput.getText().length());
        });
    }

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

    private void doToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Matcher extraerOperacion(String input){
        Pattern patron = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher match = patron.matcher(input);
        return match;
    }

    private void doOperacion(String input){
        try {
            double resultado = .0;
            if(historial.containsKey(input.hashCode())){
                doToast("Resultado: " + historial.get(input.hashCode()));
                return;
            }
            Matcher m = extraerOperacion(input);
            while(m.find()){
                String op = m.group();
                op = op.replaceAll("\\(", "");
                op = op.replaceAll("\\)", "");
                System.out.println(op);
            }
            historial.put(input.hashCode(), resultado);
            doToast("Resultado: " + resultado);
        }
        catch (IndexOutOfBoundsException ex){
            doToast("La operacion no es valida");
            txtInput.setText("");
        }
    }
}
