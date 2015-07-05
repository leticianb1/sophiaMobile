package com.android.nunes.sophiamobile.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.nunes.sophiamobile.MainActivity;
import com.android.nunes.sophiamobile.R;
import com.android.nunes.sophiamobile.model.User;
import com.android.nunes.sophiamobile.utils.WebInterface;

public class LoginActivity extends AppCompatActivity implements WebInterface{


    private ProgressDialog ringProgressDialog;
    private AsyncTask webConnection;
    private User usuarioInformado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View v){
        checkFields();
        if(!isNetworkAvailable()){
            showError(getResources().getString(R.string.warning_internet));
        }else{
            LoginAsyncTask servico = new LoginAsyncTask(this);
            EditText entradaUsuario = (EditText)findViewById(R.id.entrada_nome);
            EditText entradaSenha = (EditText)findViewById(R.id.entrada_password);
            User usuario = new User(entradaUsuario.getText().toString());
            usuario.setPassword(entradaSenha.getText().toString());

            usuarioInformado = usuario;

            ringProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.warning_aguarde),
                    getResources().getString(R.string.warning_fazendo_login), true);
            ringProgressDialog.show();
            webConnection = servico;
            servico.execute(usuario);

        }
    }

    public void checkFields(){
        EditText entradaUsuario = (EditText)
                findViewById(R.id.entrada_nome);
        if(entradaUsuario.getText() == null || "".equals(entradaUsuario.getText().toString())){
            entradaUsuario.setError(getResources().getString(R.string.warning_empty_username));
            return;
        }
        EditText entradaSenha = (EditText) findViewById(R.id.entrada_password);
        if(entradaSenha.getText() == null || "".equals(entradaSenha.getText().toString())){
            entradaSenha.setError(getResources().getString(R.string.warning_empty_password));
            return;
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void showError(String error){
        Toast.makeText(this, error,
                Toast.LENGTH_LONG).show();
    }


    public void handleError(String error) {
        ringProgressDialog.dismiss();
        showError(error);
    }


    public void handleResponse(Object object) {
        ringProgressDialog.dismiss();
//        Log.d("RESPONSE", (String) object);

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(Intent.EXTRA_TEXT, usuarioInformado.getName() );
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ringProgressDialog.isShowing()){
            webConnection.cancel(true);
        }
    }
}
