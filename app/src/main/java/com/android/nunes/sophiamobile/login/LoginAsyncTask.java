package com.android.nunes.sophiamobile.login;

import android.os.AsyncTask;
import android.util.Log;

import com.android.nunes.sophiamobile.model.User;
import com.android.nunes.sophiamobile.utils.WebInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Letícia on 28/06/2015.
 */
public class LoginAsyncTask extends AsyncTask<User, Void, HashMap<String, String>> {
    public final String LOG_TAG = LoginAsyncTask.class.getSimpleName();
    private User user;
    private final String CONNECTION_URL = "https://www.dropbox.com/s/d89zdr78j1iilsx/users.json?dl=1";
    private int responseCode;
    private String returnToken;
    private WebInterface handler;


    public LoginAsyncTask(WebInterface handler){
        this.handler = handler;
    }

    @Override
    protected HashMap<String, String> doInBackground(User... params) {


        if (params.length == 0) {
            return null;
        }

        user = params[0];
        InputStream is = null;
        int len = 500;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String userJsonStr = null;

        try {

            URL url = new URL(CONNECTION_URL.toString());
            Log.v(LOG_TAG, CONNECTION_URL.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            userJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Usuario JSON: " + userJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            userJsonStr = null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);

                }
            }
        }
        try {
            return getDataFromJson(userJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<String, String> getDataFromJson(String usersJsonStr) throws JSONException {
        final String JSON_RESULT = "users";
        final String JSON_ID = "id";
        final String JSON_NAME = "user";
        final String JSON_PASSWORD = "password";

        JSONObject listaJsonObject = new JSONObject(usersJsonStr);
        JSONArray usuariosArray = listaJsonObject.getJSONArray(JSON_RESULT);

        HashMap<String, String> resultStrs = new HashMap<String, String>();
        for (int posicao = 0; posicao < usuariosArray.length(); posicao++) {

            String id;
            String user;
            String password;

            JSONObject usuarioJson = usuariosArray.getJSONObject(posicao);
            user = usuarioJson.getString(JSON_NAME);
            password = usuarioJson.getString(JSON_PASSWORD);
      //      id = usuarioJson.getString(JSON_ID);

            //User usuario = new User(user, password, id);
            resultStrs.put(user, password);
        }

        for (String u : resultStrs.keySet()){
            Log.v(LOG_TAG, "Usuario: " + u);
            Log.v(LOG_TAG, "Senha: " + resultStrs.get(u));
        //    Log.v(LOG_TAG, "Id: " + u.getId());

        }

        return resultStrs;
    }


    @Override
    protected void onPostExecute(HashMap<String, String> response) {
        if (response != null) {

            if(response.containsKey(user.getName())){
                if(response.get(user.getName()).equals(user.getPassword())){
                    handler.handleResponse(response);
                }
                else{
                    handler.handleError("Combinação Usuário/Senha inválida");
                }

            }
            else{
                handler.handleError("Usuário não Cadastrado");
            }

        }else{
            handler.handleError("Usuário não Cadastrado");
        }
    }
}