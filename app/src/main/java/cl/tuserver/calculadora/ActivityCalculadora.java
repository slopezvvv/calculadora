package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class ActivityCalculadora extends AppCompatActivity {
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
        btnSuma.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.SUMA.toString())
        );
        btnResta.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.RESTA.toString())
        );
        btnMulti.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.MULTI.toString())
        );
        btnDivi.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.DIVI.toString())
        );
        btnPow.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.POW.toString())
        );
        btnPowPorDos.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.POW_2.toString())
        );
    }

    private void accionBotonAritmetica(String input){
        txtInput.setText(txtInput.getText().toString()+input);
        txtInput.setSelection(txtInput.getText().length());
    }

    private void doToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void doOperacion(String input){
        try {
            double resultado = .0;
            if(historial.containsKey(input.hashCode())){
                doToast("Resultado: " + historial.get(input.hashCode()));
                return;
            }
            InterfaceAritmetica aritmetica = new Aritmetica();

            historial.put(input.hashCode(), resultado);
            doToast("Resultado: " + resultado);
        }
        catch (IndexOutOfBoundsException | NumberFormatException ex){
            doToast("La operacion no es valida");
            txtInput.setText("");
        }
    }
}
