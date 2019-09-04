package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class ActivityCalculadora extends AppCompatActivity {
    // Vista del resultado
    private TextView tvResultado;

    // Input de numeros recibidos
    private TextView tvInput;

    // botones de control
    private Button btnLimpiar;

    // Operaciones aritmeticas botones
    private Button btnSuma, btnResta, btnMulti,
                   btnDivi, btnPow, btnPowPorDos;

    // Numpad botones
    private Button btnUno, btnDos, btnTres, btnCuatro, btnCinco,
                   btnSeis, btnSiete, btnOcho, btnNueve, btnCero;

    // Historial de operaciones
    private static final HashMap<Integer, Double> historial = new HashMap<>();

    // Ultima operacion realizada
    //private OperadoresAritmeticos ultimaOperacion = OperadoresAritmeticos.NONE;

    // Ultimo resultado obtenido
    private double ultimoResultado = .0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        tvResultado = findViewById(R.id.tvResultado);
        tvInput = findViewById(R.id.tvInput);

        // operaciones aritmeticas, obtencion por ID
        btnSuma = findViewById(R.id.btnAdicionar);
        btnResta = findViewById(R.id.btnSustraer);
        btnMulti = findViewById(R.id.btnMultiplicar);
        btnDivi = findViewById(R.id.btnDividir);
        btnPow = findViewById(R.id.btnPotencia);
        btnPowPorDos = findViewById(R.id.btnPotenciaPorDos);

        // numpad, obtencion por ID
        btnUno = findViewById(R.id.btnUno);
        btnDos = findViewById(R.id.btnDos);
        btnTres = findViewById(R.id.btnTres);
        btnCuatro = findViewById(R.id.btnCuatro);
        btnCinco = findViewById(R.id.btnCinco);
        btnSeis = findViewById(R.id.btnSeis);
        btnSiete = findViewById(R.id.btnSiete);
        btnOcho = findViewById(R.id.btnOcho);
        btnNueve = findViewById(R.id.btnNueve);
        btnCero = findViewById(R.id.btnCero);

        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(v -> {
            if(tvInput.getText().toString().length() <= 1)
                tvInput.setText("0");
            else
                tvInput.setText(tvInput.getText().toString().substring(0, tvInput.getText().toString().length()-1));
        });
        aritmeticaListeners();
        numpadListeners();
    }

    private void aritmeticaListeners(){
        btnSuma.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.SUMA)
        );
        btnResta.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.RESTA)
        );
        btnMulti.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.MULTI)
        );
        btnDivi.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.DIVI)
        );
        btnPow.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.POW)
        );
        btnPowPorDos.setOnClickListener(v ->
            doOperacion(OperadoresAritmeticos.POW_2)
        );
    }

    private void numpadListeners(){
        btnUno.setOnClickListener(v ->
            sacarCeroYPonerDigito("1")
        );
        btnDos.setOnClickListener(v ->
            sacarCeroYPonerDigito("2")
        );
        btnTres.setOnClickListener(v ->
            sacarCeroYPonerDigito("3")
        );
        btnCuatro.setOnClickListener(v ->
            sacarCeroYPonerDigito("4")
        );
        btnCinco.setOnClickListener(v ->
            sacarCeroYPonerDigito("5")
        );
        btnSeis.setOnClickListener(v ->
            sacarCeroYPonerDigito("6")
        );
        btnSiete.setOnClickListener(v ->
            sacarCeroYPonerDigito("7")
        );
        btnOcho.setOnClickListener(v ->
            sacarCeroYPonerDigito("8")
        );
        btnNueve.setOnClickListener(v ->
            sacarCeroYPonerDigito("9")
        );
        btnCero.setOnClickListener(v ->
            sacarCeroYPonerDigito("0")
        );
    }

    private void sacarCeroYPonerDigito(String num){
        if(tvInput.getText().toString().length() == 1 && tvInput.getText().toString().charAt(0) == '0')
            tvInput.setText("");
        tvInput.setText(tvInput.getText().toString().concat(num));
    }

    private void doOperacion(OperadoresAritmeticos op){
        try {
            double resultado = .0;
            double[] valores = new double[2];

            valores[0] = ultimoResultado;
            valores[1] = Double.parseDouble(
                tvInput.getText().toString().isEmpty() ?
                String.valueOf(0) : tvInput.getText().toString()
            );
            String input = valores[0]+op.toString()+valores[1];

            if(historial.containsKey(input.hashCode())){
                tvResultado.setText(String.valueOf(historial.get(input.hashCode())));
                return;
            }
            InterfaceAritmetica aritmetica = new Aritmetica();
            switch (op){
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

    private void doToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
