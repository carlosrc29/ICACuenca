package com.icacuenca.carlos.icacuenca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.icacuenca.carlos.icacuenca.lawyers.LawyersActivity;

/**
 * Created by Carlos on 15/11/2017.
 */

public class InterfazPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ImageButton btnBuscar;
    ImageButton btnArea;
    TextView t1;
    TextView t2;
    Button b4;
    private SharedPreferences sharedPref; //login -> token y email





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        t1= (TextView)findViewById(R.id.textView);
        t2= (TextView)findViewById(R.id.textView5);
        b4= (Button) findViewById(R.id.button4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InterfazPrincipal.this, Pruebatoolbar.class);
                startActivity(i);
                //Login.this.finish();
            }
        });

        btnBuscar = (ImageButton)findViewById(R.id.imageButton1);
        btnArea = (ImageButton)findViewById(R.id.imageButton2);
/*
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InterfazPrincipal.this, LoginActivity.class);
                startActivity(i);
                //Login.this.finish();
            }
        });
        */
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InterfazPrincipal.this, MainActivity.class);
                startActivity(i);
                //Login.this.finish();
            }
        });

        Context context = this.getApplicationContext();
        //Instanciamos el objeto SharedPreferences y creamos un fichero Privado bajo el
        //nombre definido con la clave preference_file_key en el fichero string.xml
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        String defaultemail = getResources().getString(R.string.email_key);
        String email = sharedPref.getString(getString(R.string.email_key), defaultemail);
        String defaulttoken = getResources().getString(R.string.token_key);
        String token = sharedPref.getString(getString(R.string.token_key), defaulttoken);
        t1.setText(String.valueOf(email));
        t2.setText(String.valueOf(token));





    }

    public void clearProfile(View view) {//llamado en xml
        //Para borrar el registro de algun dato en elfichero compartido
        // sencillamente empleamos el metodo remove(key)
        // del objeto SharedPreferences.Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.token_key));
        editor.remove(getString(R.string.email_key));
        editor.commit();

        Intent intent = new Intent(InterfazPrincipal.this, LoginActivity.class);
        startActivity(intent);
        InterfazPrincipal.this.onDestroy();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
