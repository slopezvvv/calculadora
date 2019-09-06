package cl.tuserver.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    protected static final Usuario sesion = new Usuario("Anonimo", new char[]{'1','2','3'});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        startActivity(new Intent(MainActivity.this, ActivityCalculadora.class));
    }
}