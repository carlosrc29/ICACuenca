package com.icacuenca.carlos.icacuenca;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;

/**
 * Created by Carlos on 04/09/2017.
 */

public class Buscador extends AppCompatActivity implements SearchView.OnQueryTextListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       rellenarTablaUsuarios();
      // mostrarDatos();

    }

    private void mostrarDatos(){
        bbdd bd = new bbdd(this);
        bd.mostrarDatos2();
    }

    private void rellenarTablaUsuarios(){
        bbdd bd = new bbdd(this);
        bd.existe();


        bd.insertarUsuario("Carlos", "Risueño Jimenez", "969307008", "658435053","Arco, 16", "San Clemente", "16600", "3548", "carlosrisueno@gmail.com");
        bd.insertarUsuario("Gema",  "Rubio Ortíz", "969307008", "658435053","Arco, 16", "San Clemente", "16600", "3549", "gemarubio@gmail.com");

        //bd.insertarUsuario4("pruueba","risu","3470","San Clemente");

       // bd.insertarUsuario(null, "Risueño Jimenez", "969307008","658435053"," Arco 16","San Clemente","16600","3548","carlosrisueno@gmail.com");
       // bd.insertarUsuario("Prueba ", "Risueño Jimenez", "969307008",null," Arco 16","San Clemente","16600","3548","carlosrisueno@gmail.com");

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
