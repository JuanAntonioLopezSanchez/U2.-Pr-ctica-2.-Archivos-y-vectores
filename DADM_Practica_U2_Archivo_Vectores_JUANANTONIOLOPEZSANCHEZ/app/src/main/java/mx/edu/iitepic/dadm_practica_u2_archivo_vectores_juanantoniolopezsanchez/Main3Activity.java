package mx.edu.iitepic.dadm_practica_u2_archivo_vectores_juanantoniolopezsanchez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    EditText titulo, fecha, materia, descripcion;
    TextView textito;
    ImageButton regresar, actualizar, borrar;
    int pos, contadorActualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        titulo = findViewById(R.id.titulo);
        fecha = findViewById(R.id.fecha);
        materia = findViewById(R.id.materia);
        descripcion = findViewById(R.id.descripcion);
        regresar = findViewById(R.id.regresar);
        actualizar = findViewById(R.id.actualizar);
        borrar = findViewById(R.id.eliminar);
        textito=findViewById(R.id.txtActualizar);
        pos = getIntent().getExtras().getInt("pos");
        String cadena = getIntent().getExtras().getString("cadena");
        String datos[] = cadena.split("&");
        contadorActualizar=0;
        titulo.setText(datos[0]);
        fecha.setText(datos[1]);
        materia.setText(datos[2]);
        descripcion.setText(datos[3]);

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarEnVector(pos);
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresar = new Intent();
                int p=0;
                String n="";
                regresar.putExtra("pos", p);
                regresar.putExtra("cadena", n);
                setResult(10, regresar);
                finish();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contadorActualizar==0){
                    String a= "Actualizar";
                    actualizar.setImageResource(R.drawable.baseline_save_white_18dp);
                    titulo.setEnabled(true);
                    fecha.setEnabled(true);
                    materia.setEnabled(true);
                    descripcion.setEnabled(true);
                    titulo.setTextColor(getResources().getColor(R.color.colorNegroGuardar));
                    fecha.setTextColor(getResources().getColor(R.color.colorNegroGuardar));
                    materia.setTextColor(getResources().getColor(R.color.colorNegroGuardar));
                    descripcion.setTextColor(getResources().getColor(R.color.colorNegroGuardar));
                    textito.setText(getResources().getString(R.string.guardar));
                    contadorActualizar=1;
                }
                else {
                    String nuevaCadena = titulo.getText().toString() + "&" + fecha.getText().toString() + "&" + materia.getText().toString() + "&" + descripcion.getText().toString();
                    actualizarLista(nuevaCadena);
                    titulo.setEnabled(false);
                    fecha.setEnabled(false);
                    materia.setEnabled(false);
                    descripcion.setEnabled(false);
                    titulo.setTextColor(getResources().getColor(R.color.colorRojoActualizar));
                    fecha.setTextColor(getResources().getColor(R.color.colorRojoActualizar));
                    materia.setTextColor(getResources().getColor(R.color.colorRojoActualizar));
                    descripcion.setTextColor(getResources().getColor(R.color.colorRojoActualizar));
                    textito.setText(getResources().getString(R.string.actualizar));
                    contadorActualizar=0;
                }
            }
        });
    }

    private void actualizarLista(String nuevaCadena) {
        Intent datosRetorno = new Intent();
        datosRetorno.putExtra("pos", pos);
        datosRetorno.putExtra("cadena", nuevaCadena);
        setResult(8, datosRetorno);
        finish();
    }

    private void borrarEnVector(int pos) {
        String s="";
        Intent datosRetorno = new Intent();
        datosRetorno.putExtra("pos", pos);
        datosRetorno.putExtra("cadena", s);
        setResult(6, datosRetorno);
        finish();
    }
}
