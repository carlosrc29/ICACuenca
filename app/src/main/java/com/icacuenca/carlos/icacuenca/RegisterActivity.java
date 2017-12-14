package com.icacuenca.carlos.icacuenca;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Carlos on 23/11/2017.
 */

public class RegisterActivity extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private View mProgressView;
    private View mRegisterFormView;
    private EditText numCol;
    private Button mRegister;
    private EditText nombre;
    private EditText apellidos;

    private UserRegisterTask mAuthTask = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegister = (Button) findViewById(R.id.register_button);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        numCol = (EditText) findViewById(R.id.numCol);
        nombre = (EditText) findViewById(R.id.nombre);
        apellidos = (EditText) findViewById(R.id.apellidos);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView2 = (EditText) findViewById(R.id.password2);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    private void attemptLogin() {//comprueba que los campos estan bien
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        String name = nombre.getText().toString();
        String subName = apellidos.getText().toString();
        String numeroColegiado = numCol.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if ( !isPasswordValid(password)) {
            mPasswordView2.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView2;
            cancel = true;
        }
        if ( !isPasswordValid(password2)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(name)){
            nombre.setError(getString(R.string.error_field_required));
            focusView=nombre;
            cancel = true;

        }

        if (TextUtils.isEmpty(numeroColegiado) ){
            numCol.setError(getString(R.string.error_field_required));
            focusView=numCol;
            cancel = true;

        }
        if (TextUtils.isEmpty(subName)){
            apellidos.setError(getString(R.string.error_field_required));
            focusView=apellidos;
            cancel = true;

        }
        if (!isPassword2Valid(password, password2)){
            mPasswordView2.setError(getString(R.string.error_notSame_password));
            focusView=mPasswordView2;
            cancel=true;

        }
        if (!isEmailValid(email)){
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView=mEmailView;
            cancel=true;

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } /*else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        */

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            new UserRegisterTask().execute("http://192.168.1.11/prueba/registro.php?nombre=" + nombre.getText().toString() + "&apellidos=" + apellidos.getText().toString() + "&numCol=" + numCol.getText().toString() + "&email=" + mEmailView.getText().toString() + "&password=" + mPasswordView.getText().toString() + "&password2=" + mPasswordView2.getText().toString());
       //http://192.168.1.11/prueba/registro.php?nombre=carlos&apellidos=risu carni&numCol=526&email=carlos@&password=prueba&password2=prueba
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPassword2Valid (String pass1, String pass2){
        return pass1.equals(pass2);
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        //el formulario desaparece y el progressview aparece
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            int animationTime = 1200;
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(animationTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(animationTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserRegisterTask extends AsyncTask<String, Void, String> {




        protected String doInBackground(String...urls) {
            // TODO: attempt authentication against a network service.

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String resulta = result.trim();

            switch (resulta) {
                case "false":
                    Toast.makeText(RegisterActivity.this, "Error al registrar", Toast.LENGTH_LONG).show();
                    showProgress(false);
                    break;

                case "noid":
                    Toast.makeText(RegisterActivity.this, "NumCol no existente no registrado", Toast.LENGTH_LONG).show();
                    showProgress(false);
                    break;

                case "yaExiste":
                    Toast.makeText(RegisterActivity.this, "Numero de colegiado ya registrado", Toast.LENGTH_LONG).show();
                    showProgress(false);
                    break;

                case "error":
                    Toast.makeText(RegisterActivity.this, "email o password en blanco", Toast.LENGTH_LONG).show();
                    showProgress(false);
                    break;
                case "idDisponible":
                    String datosEmail = mEmailView.getText().toString();
                    //Instanciamos un objeto del SharedPreferences.Editor
                    //el cual nos permite almacenar con su metodo putString
                    //los 4 valores del perfil profesional asociandolos a una
                    // clave la cual definimos como un string en el fichero strings.xml

                    Toast.makeText(RegisterActivity.this, "NumCol disponigle Registro realizado correctamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    //intent.putExtra("email", datosEmail);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                    break;


                default:


                    Toast.makeText(RegisterActivity.this, "Error desconocido", Toast.LENGTH_LONG).show();
                    showProgress(false);

            }

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        URLEncoder.encode(myurl, "utf-8");
        myurl = myurl.replace(" ","%20");
        Log.i("URL reemplazada",""+myurl);
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
