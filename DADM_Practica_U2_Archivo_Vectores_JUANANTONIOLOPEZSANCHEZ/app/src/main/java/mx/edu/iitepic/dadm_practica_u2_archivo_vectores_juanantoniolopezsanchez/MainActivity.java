package mx.edu.iitepic.dadm_practica_u2_archivo_vectores_juanantoniolopezsanchez;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    String[] listaItems, vector;
    TextView texto;
    LinearLayout layo;
    String cadena;
    int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista=findViewById(R.id.listaTareas);
        layo=findViewById(R.id.layo);
        texto=findViewById(R.id.texto);
        listaItems = new String[20];
        vector = new String[20];
        for (int i=0; i<listaItems.length;i++) {
            listaItems[i] = "";
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaCrear = new Intent (MainActivity.this, Main2Activity.class);
                startActivityForResult(ventanaCrear,1);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int posicion = data.getIntExtra("pos", 10);
        String cadena = data.getStringExtra("cadena");
        if (resultCode != 10){
            guardarEnVector(posicion, cadena);
            guardarEnMemoria();
        }
        if(listaItems[posicion]==""){
            for(int i=0; i<20;i++){
                if(listaItems[i]==""){
                    contador++;
                }
            }
        }
        if(contador==20){
            Intent regresar = new Intent(MainActivity.this, MainActivity.class);
            startActivity(regresar);
        }
        contador=0;
    }

    private void guardarEnVector(int posicion, String cadena) {
        layo.removeView(texto);
        String datos[] = cadena.split("&");
        listaItems[posicion] = datos[0];
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,listaItems);
        lista.setAdapter(adaptador);
        vector[posicion] = cadena;
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String totalCadena = vector[position];
                if (listaItems[position]!=""){
                    Intent ventana3 = new Intent(MainActivity.this, Main3Activity.class);
                    ventana3.putExtra("cadena", totalCadena);
                    ventana3.putExtra("pos", position);
                    startActivityForResult(ventana3, 2);
                }
                else {
                    Toast.makeText(MainActivity.this,"Está vacío",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void guardarEnMemoria() {
        try{
            cadena="";
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("archivo.txt", MODE_PRIVATE));
            for(int i=0; i<listaItems.length; i++){
                if(vector[i]==null){
                    cadena+=""+"#";
                }else {
                    cadena += vector[i] + '#';
                }
            }
            archivo.write(cadena);
            archivo.close();
            Toast.makeText(this,"Se almacenaron los datos en memoria",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void cargarEnMemoria() {
        try {
            layo.removeView(texto);
            BufferedReader archivo = new BufferedReader(new InputStreamReader(openFileInput("archivo.txt")));
            String datosArchivo = archivo.readLine();
            archivo.close();
            String[] vectorDataSplit = datosArchivo.split("#");
            for(int i=0; i<20; i++) {
                String datos[] = vectorDataSplit[i].split("&");
                listaItems[i] = datos[0];
                vector[i] = vectorDataSplit[i];
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listaItems);
                lista.setAdapter(adaptador);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String totalCadena = vector[position];
                        if (listaItems[position] != "") {
                            Intent ventana3 = new Intent(MainActivity.this, Main3Activity.class);
                            ventana3.putExtra("cadena", totalCadena);
                            ventana3.putExtra("pos", position);
                            startActivityForResult(ventana3, 15);
                        } else {
                            Toast.makeText(MainActivity.this, "Está vacío", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }catch (Exception e){

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.cargar) {
            cargarEnMemoria();
        }

        return super.onOptionsItemSelected(item);
    }
}
