package com.icacuenca.carlos.icacuenca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;
import android.provider.BaseColumns;

/**
 * Created by Carlos on 04/09/2017.
 */

public class bbdd extends SQLiteOpenHelper implements BaseColumns  {

    private static String name = "Icacuenca.db";
    private static int version = 1;
    private static SQLiteDatabase.CursorFactory cursorFactory = null;

    protected static String TableAbogados = "abogados";
    protected static String ID = bbdd._ID;
    protected static String NOMBRE= "nombre";
    protected static String APELLIDOS= "apellidos";
    protected static String FIJO= "fijo";
    protected static String MOVIL= "movil";
    protected static String DIRECCION= "direccion";
    protected static String LOCALIDAD= "localidad";
    protected static String CP= "cp";
    protected static String NUMCOL= "numCol";
    protected static String EMAIL= "email";
    //private String SQLCreateUsuarios = "CREATE TABLE " + TableAbogados +  " (nombre VARCHAR(20)" + "text not null, apellidos VARCHAR(50)" + "text not null , fijo VARCHAR(13), movil VARCHAR(13), localidad VARCHAR(35), cp VARCHAR(5), numCol VARCHAR(8)" + "text not null, email VARCHAR(1000) ) ";

    private String SQLCreateUsuarios = "CREATE TABLE " + TableAbogados +  " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NOMBRE + " TEXT NOT NULL,"
            + APELLIDOS + " TEXT NOT NULL,"
            + FIJO + " TEXT,"
            + MOVIL + " TEXT,"
            + EMAIL + " TEXT,"
            + DIRECCION + " TEXT,"
            + LOCALIDAD + " TEXT NOT NULL,"
            + CP + " TEXT,"
            + NUMCOL+ " TEXT NOT NULL,"
            + "UNIQUE (" + NUMCOL + "))";


//igual q el de arriba    private String SQLCreateUsuarios = "CREATE TABLE " + TableAbogados +  " (nombre VARCHAR(20), " + "apellidos VARCHAR(50), " + "fijo VARCHAR(13), " + "movil VARCHAR(13), " + "direccion VARCHAR(50), " + "localidad VARCHAR(35), " + "cp VARCHAR(5), " + "numCol VARCHAR(8), " + "email VARCHAR(1000) ) ";

    // private String SQLCreateAbogados = "CREATE TABLE " + TableAbogados +  " (nombre VARCHAR(20) TEXT NOT NULL, " + "apellidos VARCHAR(50) TEXT NOT NULL, " + "fijo VARCHAR(13), " + "movil VARCHAR(13), " + "direccion VARCHAR(50), " + "localidad VARCHAR(35), " + "cp VARCHAR(5), " + "numCol VARCHAR(8) TEXT NOT NULL , " + "email VARCHAR(1000) ) ";
    //añadir columna direccion o crear otra trabla en la base de datos, no se se se han creado como not null o no

    public bbdd(Context context){
        super(context,name,cursorFactory, version);
    }

    public bbdd(Context contexto, String nombre,
                SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLCreateUsuarios);
        Log.d("respuesta", "bd creada" );

    }

    public void insertarUsuario(String nombre, String apellidos, String fijo, String movil, String direccion, String localidad, String cp, String numCol, String email){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableAbogados +
                    " (nombre, apellidos, fijo, movil, direccion, localidad, cp, numCol, email) " +
                    " VALUES('"+ nombre +"', '"+ apellidos +"', '"+ fijo +"','"+ movil +"','"+ direccion +"', '"+ localidad +"', '"+ cp +"', '"+ numCol +"', '"+ email +"') ");
            Log.d("respuesta", "insertado usuario" );
            db.close();
        }
    }
    public void insertarUsuarioNotNull(String nombre, String apellidos, String localidad, String numCol){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            Log.d("respuesta", "insertaaaaaando usuario4" );
            db.execSQL("INSERT INTO " + TableAbogados +
                    " (nombre, apellidos, numCol, email) " +
                    " VALUES('" + nombre + "', '" + apellidos + "', '" + localidad + "', '"  + numCol + "') ");
            Log.d("respuesta", "insertado usuario4" );
            db.close();
        }
    }

    public void insertarUsuario2(){ //no he probado si inserta
// Contenedor de valores
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put(NOMBRE, "Gema");
        values.put(APELLIDOS, "Ruiz Ortiz");
        values.put(FIJO, "969307008");
        values.put(MOVIL, "643908321");
        values.put(EMAIL, "gema@gmail.com");
        values.put(DIRECCION, "Arco,14");
        values.put(LOCALIDAD, "San Clemente");
        values.put(CP, "16600");
        values.put(NUMCOL, "38626");

        // Insertar...
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TableAbogados, null, values);

    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        TableAbogados,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAllLawyers2() {
        return getReadableDatabase().rawQuery("select * FROM "+ TableAbogados ,null);
    }

    public Cursor getLawyerByName2() {
        return getReadableDatabase().rawQuery("select * FROM "+ TableAbogados +" WHERE nombre ='Carlos' ",null);
    }


    //Hay que poner la busqueda igual, incluyendo mayusculas
    public Cursor getLawyerByNameOrCity() {
        return getReadableDatabase().rawQuery("select * FROM "+ TableAbogados +" WHERE nombre LIKE 'carlos' OR localidad LIKE 'carlos' ",null);
    }



    public Cursor getLawyerByNameOrCity2(String busqueda) {
        Log.d("otra respuesta",busqueda);
        return getReadableDatabase().rawQuery("select * FROM "+ TableAbogados +" WHERE nombre LIKE '"+busqueda+"' OR localidad LIKE '"+busqueda+"' ",null);
    }




    public Cursor getLawyerById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                TableAbogados,
                null,
                bbdd.NUMCOL + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public Cursor getLawyerByName(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                TableAbogados,
                null,
                bbdd.NOMBRE+ " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    //cursor para buscar

    public Cursor getCursorBuscador(String textSearch){

        textSearch = textSearch.replace("'", "''");

        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT *" +
                " FROM "+TableAbogados +
                " WHERE nombre LIKE '%" +textSearch+ "%' OR localidad LIKE '%" +textSearch+ "%' OR apellidos LIKE '%" +textSearch+ "%' ", null);
    }



    public void mostrarDatos() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.query(
                TableAbogados,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY

        );


        while(c.moveToNext()){

           // String name= c.getString(1); //muestra todas las filas de la columna 1
           // String name = c.getString(c.getColumnIndex("apellidos"));//es lo mismo que la de arriba
       // Log.d("respuesta", name );

            String name2 = c.getString(c.getColumnIndex(TableAbogados));

            Log.d("otra respuesta", name );


        }





    }


    public void mostrarDatos2 (){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery("select * FROM "+ TableAbogados,null);

        while(c.moveToNext()){

            // String name= c.getString(1); //muestra todas las filas de la columna 1
            // String name = c.getString(c.getColumnIndex("apellidos"));//es lo mismo que la de arriba
            // Log.d("respuesta", name );
            int index1 = c.getColumnIndex("nombre");
            int index2 = c.getColumnIndex("direccion");
            String name = c.getString(index1);
                    //c.getString(index1);
            String apellidos = c.getString(index2);
                    //c.getString(index2);

            Log.d("otra respuesta", name );
            if(apellidos != null) {
                Log.d("otra respuesta", apellidos );
            }
            else Log.d("otra respuesta", "es null" );


        }
    }


    public void existe(){
        SQLiteDatabase db = getWritableDatabase();
        if (db!=null){


            Log.d("respuesta", "Existe la bbdd" );


        }
        else Log.d("respuesta", "no existe la bbdd" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //    db.execSQL("ALTER TABLE "+ TableAbogados + " ADD COLUMN " + ID +  " INTEGER PRIMARY KEY");
    }
}
