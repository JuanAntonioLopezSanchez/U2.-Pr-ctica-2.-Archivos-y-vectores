package mx.edu.iitepic.dadm_practica_u2_archivo_vectores_juanantoniolopezsanchez;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText titulo, materia, fecha, descripcion;
    Button guardar;
    String[] vector;
    String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        titulo = findViewById(R.id.titulo);
        materia = findViewById(R.id.materia);
        fecha = findViewById(R.id.fecha);
        descripcion = findViewById(R.id.descripcion);
        guardar = findViewById(R.id.guardar);
        vector = new String[20];

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preguntarGuardar();


            }
        });
    }

    private void preguntarGuardar() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        final EditText posicion = new EditText(this);
        if (titulo.length()==0 || fecha.length()==0 || materia.length()==0|| descripcion.length()==0) {
            Toast.makeText(Main2Activity.this,"Debes llenar todos los campos",Toast.LENGTH_LONG).show();
        }
        else {
            posicion.setInputType(InputType.TYPE_CLASS_NUMBER);
            posicion.setHint("Rango entre 1 y 20");
            alerta.setTitle("Capturando...")
                    .setMessage("Escriba la posicion donde se guardará el dato")
                    .setView(posicion)
                    .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            guardarEnVector(posicion.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }
    }

    private void guardarEnVector(String posicion) {
        int pos = Integer.parseInt(posicion);
        pos--;
        if (pos>=0 && pos<=19){
            vector [pos] = titulo.getText().toString()+"&"+fecha.getText().toString()+"&"+materia.getText().toString()+"&"+descripcion.getText().toString();
            titulo.setText(null);
            materia.setText(null);
            fecha.setText(null);
            descripcion.setText(null);
        }
        else {
            Toast.makeText(Main2Activity.this, "El valor "+posicion+" no es valido", Toast.LENGTH_LONG).show();
        }
        cadena=vector[pos];
        Intent datosRetorno = new Intent();
        datosRetorno.putExtra("pos",pos);
        datosRetorno.putExtra("cadena",cadena);
        setResult(3,datosRetorno);
        finish();
    }
}
