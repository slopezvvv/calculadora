package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.StringTokenizer;


public class ActivityCalculadora extends AppCompatActivity {
    private TextView tvResultado;
    private TextView tvInput;
    private Button btnRealizarOperacion;
    private Button btnSuma, btnResta, btnMulti, btnDivi, btnPow, btnPowPorDos;

    private static final HashMap<Integer, Double> historial = new HashMap<>();
    OperadoresAritmeticos ultimaOperacion = null;
    double ultimoResultado = .0;
    boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        tvResultado = findViewById(R.id.tvResultado);
        tvInput = findViewById(R.id.tvInput);
        btnSuma = findViewById(R.id.btnAdicionar);
        btnResta = findViewById(R.id.btnSustraer);
        btnMulti = findViewById(R.id.btnMultiplicar);
        btnDivi = findViewById(R.id.btnDividir);
        btnPow = findViewById(R.id.btnPotencia);
        btnPowPorDos = findViewById(R.id.btnPotenciaPorDos);
        btnRealizarOperacion = findViewById(R.id.btnRealizarOperacion);
        btnRealizarOperacion.setOnClickListener(v -> accionBotonAritmetica(ultimaOperacion));

        btnSuma.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.SUMA)
        );
        btnResta.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.RESTA)
        );
        btnMulti.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.MULTI)
        );
        btnDivi.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.DIVI)
        );
        btnPow.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.POW)
        );
        btnPowPorDos.setOnClickListener(v ->
            accionBotonAritmetica(OperadoresAritmeticos.POW_2)
        );
    }

    private void accionBotonAritmetica(OperadoresAritmeticos op){
        if(!isClick) {
            tvInput.setText(tvInput.getText().toString() + op.toString());
            //tvInput.setSelection(tvInput.getText().length());
            ultimaOperacion = op;
        }
        else {
            doOperacion(tvInput.getText().toString());
        }
        isClick = !isClick;
    }

    private void doToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void doOperacion(String input){
        try {
            double resultado = .0;
            if(historial.containsKey(input.hashCode())){
                tvResultado.setText(String.valueOf(historial.get(input.hashCode())));
                return;
            }
            InterfaceAritmetica aritmetica = new Aritmetica();
            StringTokenizer filtrarVariables = new StringTokenizer(input, ultimaOperacion.toString());
            double[] valores = new double[2];
            if(ultimoResultado == .0) {
                int i = 0;
                while (filtrarVariables.hasMoreTokens()) {
                    valores[i] = Double.parseDouble(filtrarVariables.nextToken());
                    i++;
                }
            }
            switch (ultimaOperacion){
                case SUMA:
                    resultado = aritmetica.adicion(valores[0], valores[1]);
                    break;
                case RESTA:
                    resultado = aritmetica.sustraccion(valores[0], valores[1]);
                    break;
                case MULTI:
                    resultado = aritmetica.multiplicacion(valores[0], valores[1]);
                    break;
                case DIVI:
                    resultado = aritmetica.divicion(valores[0], valores[1]);
                    break;
                case POW:
                    resultado = aritmetica.potencia(valores[0], valores[1]);
                    break;
                case POW_2:
                    resultado = aritmetica.potenciaPorDos(ultimoResultado);
                    break;
            }
            historial.put(input.hashCode(), resultado);
            ultimoResultado = resultado;
            tvResultado.setText(String.valueOf(resultado));
        }
        catch (IndexOutOfBoundsException | NumberFormatException ex){
            doToast("La operacion no es valida");
        }
        tvInput.setText("");
    }
}
