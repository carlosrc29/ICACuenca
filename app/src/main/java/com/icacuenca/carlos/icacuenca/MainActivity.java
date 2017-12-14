package com.icacuenca.carlos.icacuenca;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.icacuenca.carlos.icacuenca.lawyers.LawyersActivity;
import com.icacuenca.carlos.icacuenca.lawyers.LawyersCursorAdapter;
import com.icacuenca.carlos.icacuenca.lawyers.LawyersFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private EditText txtCodigo;
    private EditText txtNombre;
    private TextView txtResultado;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;

    private SQLiteDatabase db;



    Button b1;
    Button buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//Obtenemos las referencias a los controles
        txtCodigo = (EditText)findViewById(R.id.txtReg);
        txtNombre = (EditText)findViewById(R.id.txtVal);
        txtResultado = (TextView)findViewById(R.id.txtResultado);

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);

        bbdd bd= new bbdd(this);



    //    UsuariosSQLiteHelper usdbh =
      //          new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);


         db = bd.getWritableDatabase();

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Alternativa 1: método rawQuery()
                Cursor c = db.rawQuery("SELECT * FROM abogados", null);

                //Alternativa 2: método delete()
                //String[] campos = new String[] {"codigo", "nombre"};
                //Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

                //Recorremos los resultados para mostrarlos en pantalla
                txtResultado.setText("");
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String nom = c.getString(0);
                        String ape = c.getString(1);
                        int index1 = c.getColumnIndex("numCol");// hay que buscarlos en la consulta si queremos mostrarlooo
                        String nCol = c.getString(index1);
                        String id= c.getColumnName(9);
                       // String numCol= c.getString(2); " - " + numCol +

                        txtResultado.append(" " + nom + " - " + ape + " - " + nCol +" - " + id +  "\n");

                    } while(c.moveToNext());
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String nCol = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: método update()
                ContentValues valores = new ContentValues();
                valores.put("nombre", nom);
                db.update("abogados", valores, "numCol=" + nCol, null);

            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String nCol = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
                //db.execSQL(sql);

                //Alternativa 2: método insert()
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("numCol", nCol);
                nuevoRegistro.put("nombre", nom);
                db.insert("abogados", null, nuevoRegistro);

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String nCol = txtCodigo.getText().toString();

                //Alternativa 1: método sqlExec()
                //String sql = "DELETE FROM Usuarios WHERE numCol=" + nCol;
                //db.execSQL(sql);

                //Alternativa 2: método delete()
                db.delete("abogados", "numCol=" + nCol, null);

            }
        });


    b1= (Button) findViewById(R.id.button1);
        buscar=(Button) findViewById(R.id.button2);

        //muestra todos los abogados
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LawyersActivity.class);
                startActivity(i);
                //Login.this.finish();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Buscador.class); //rellenar tabla
                startActivity(i);
                //Login.this.finish();
            }
        });


    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

//poner el buscador en el lawyeer fragment y asi se pueda buscar arriba y abajo salgan todos los abogados

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(MainActivity.this, LawyersActivity.class)));
        searchView.setIconifiedByDefault(false);





        return true;
    }

/*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }
    */

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }
}
