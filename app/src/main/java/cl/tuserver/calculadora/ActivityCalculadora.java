package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ActivityCalculadora extends AppCompatActivity {

    // Video Animacion
    private VideoView animVideo;
    private MediaController mediaController;

    // Texto animacion
    private TextView animTexto;
    private String animNuevoTexto;

    // Vista del resultado
    private TextView tvResultado;

    // Input de numeros recibidos
    private TextView tvInput;

    // botones de control
    private Button btnLimpiar;
    private Button btnResetear;

    // Operaciones aritmeticas botones
    private Button btnSuma, btnResta, btnMulti,
                   btnDivi, btnPow, btnPowPorDos;

    // Numpad botones
    private Button btnUno, btnDos, btnTres, btnCuatro, btnCinco,
                   btnSeis, btnSiete, btnOcho, btnNueve, btnCero;

    // Historial de operaciones
    //private static final HashMap<Integer, String> historial = new HashMap<>();

    // Ultima operacion realizada
    //private OperadoresAritmeticos ultimaOperacion = OperadoresAritmeticos.NONE;

    // Ultimo resultado obtenido
    private double ultimoResultado = .0;

    // Esta sonando musica ?
    private boolean isPlayingMusic = false;

    // Media Player
    private MediaPlayer mp;

    /**
     * Metodo onCreate() de la clase Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        // Video Animacion
        animVideo = findViewById(R.id.animVideo);

        // Texto animacion
        animTexto = findViewById(R.id.animTexto);
        animNuevoTexto = getResources().getString(R.string.animTexto);

        // Input / Output textViews
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
        btnResetear = findViewById(R.id.btnResetear);

        animBackground();
        controlListeners();
        aritmeticaListeners();
        numpadListeners();
        if(!isPlayingMusic) playMusic();
    }

    @Override
    protected void onResume(){
        super.onResume();
        animTexto();
    }


    /**
     * Metodo que produce la animacion del fondo.
     */
    private void animBackground(){
        // Looping Animacion
        animVideo.setOnPreparedListener(mp -> mp.setLooping(true));

        mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        animVideo.setMediaController(mediaController);

        // Direccion del recurso audiovisual
        String pathAnim = "android.resource://"+getPackageName()+"/raw/anim";
        Uri uri = Uri.parse(pathAnim);
        animVideo.setVideoURI(uri);

        // Iniciar video
        animVideo.start();
    }

    private void animTexto(){
        // Animacion del texto
        new Thread(
            () -> {
                try {
                    int x = 0;
                    int z = 14;
                    while(true){
                        runOnUiThread(
                            () -> {
                                animTexto.setText(animNuevoTexto.substring(x, z));
                                animNuevoTexto = animNuevoTexto.concat(String.valueOf(animNuevoTexto.charAt(0))).substring(1);
                            }
                        );
                        Thread.sleep(100);
                    }
                }
                catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        ).start();
    }

    private void playMusic(){
        mp = MediaPlayer.create(this, R.raw.bs);
        mp.setLooping(true);
        mp.start();
        isPlayingMusic = true;
    }

    // Metodo que vuelve los valores de las variables usadas a su valor por defecto.
    private void clear(){
        tvResultado.setText("0");
        tvInput.setText("");
        ultimoResultado = .0;
    }

    // Metodos que instancian los eventos de boton
    private void controlListeners(){
        btnLimpiar.setOnClickListener(v -> {
            if(tvInput.getText().toString().length() <= 1)
                tvInput.setText("");
            else
                tvInput.setText(tvInput.getText().toString().substring(0, tvInput.getText().toString().length()-1));
        });
        btnResetear.setOnClickListener(v -> clear());
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

    /**
     * Metodo que valida si hay un 0 como primer termino, se elimina dejando el text vacio,
     * seguido de la instruccion que se encarga de concatenar el nuemo numero ingresado por un boton
     * numerico. Recibe el numero como String.
     * @param num
     */
    private void sacarCeroYPonerDigito(String num){
        if(tvInput.getText().toString().length() == 1 && tvInput.getText().toString().charAt(0) == '0')
            tvInput.setText("");
        tvInput.setText(tvInput.getText().toString().concat(num));
    }

    /**
     * Realiza la operacion aritmetica que se le pase como parametro.
     * Utiliza el resultado anterior y el valor en la caja de input para
     * los terminos A y B que se usan en la operacion (SUMA, RESTA, MULTI, DIVI, etc).
     * @param op
     */
    private void doOperacion(OperadoresAritmeticos op){
        try {
            double resultado = .0;
            double[] valores = new double[2];

            valores[0] = ultimoResultado;
            valores[1] = Double.parseDouble(
                tvInput.getText().toString().isEmpty() ?
                String.valueOf(0) : tvInput.getText().toString()
            );
            // Si es elevado al cuadrado, entonces se ignora el segundo termino
            String input = op != OperadoresAritmeticos.POW_2 ?
                                valores[0]+op.toString()+valores[1] :
                                valores[0]+op.toString();

            if(MainActivity.sesion.historialContainsKey(input.hashCode())){
                tvResultado.setText(MainActivity.sesion.getHistorial(input.hashCode()));
                tvInput.setText("");
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

            ultimoResultado = resultado;
            String output = String.valueOf(resultado);
            // Compruebo si el Double es un numero entero, eliminando el cero,
            // sino se muestra como numero flotante, es decir, sin cambios.
            if((resultado % 1) == 0)
                output = output.substring(0, output.length()-2);

            // Se muestra el resultado y se guarda en el historial
            tvResultado.setText(output);
            MainActivity.sesion.addHistorial(input.hashCode(), output);
        }
        catch (IndexOutOfBoundsException | NumberFormatException ex){
            doToast("La operacion no es valida");
        }
        tvInput.setText("");
    }

    /**
     * Imprime mensaje de alerta en pantalla al usuario.
     * @param msg
     */
    private void doToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // Impide que el boton atras se use
    // sobrescribiendo el metodo y haciendo 'nada' en el

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        mp.stop();
        mp.release();
    }
}
