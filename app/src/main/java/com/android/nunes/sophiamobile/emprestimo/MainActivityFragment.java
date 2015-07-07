package com.android.nunes.sophiamobile.emprestimo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.nunes.sophiamobile.R;
import com.android.nunes.sophiamobile.login.LoginActivity;
import com.android.nunes.sophiamobile.model.Emprestimo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EmprestimoAdapter.ClickListener{

    private RecyclerView mRecyclerView;
    private EmprestimoAdapter mAdapter;

    public  final static String PAR_KEY = "com.android.nunes.objectpass.par";

    private String mUserStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.emprestimo_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateList();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList() {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mUserStr = intent.getStringExtra(Intent.EXTRA_TEXT);
        }else{
            mUserStr = LoginActivity.usuarioLogado;
        }
            PegarEmprestimosTask pegarEmprestimosTask = new PegarEmprestimosTask();
            pegarEmprestimosTask.execute(mUserStr);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.rview_emprestimos);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new EmprestimoAdapter(new ArrayList<Emprestimo>());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateList();
    }

    @Override
    public void itemClicked(View view, int position) {
       // String id =  mAdapter.getEmprestimos().get(position).get;
        //   String forecast = String.valueOf(getActivity().find(R.id.serieId));

        Emprestimo mEmprestimo =  mAdapter.getEmprestimos().get(position);
        Toast.makeText(getActivity(), mEmprestimo.getLivro(), Toast.LENGTH_SHORT).show();
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable(PAR_KEY, mEmprestimo );
        detailIntent.putExtras(mBundle);

        startActivity(detailIntent);
    }


    public class PegarEmprestimosTask extends AsyncTask<String, Void, List<Emprestimo>> {
        public final String LOG_TAG = MainActivityFragment.class.getSimpleName();
        private String user;
        private final String CONNECTION_URL = "https://www.dropbox.com/s/mn3sj7tgk4x7bn6/emprestimos.json?dl=1";


        @Override
        protected List<Emprestimo> doInBackground(String... params) {


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

                Log.v(LOG_TAG, "Usuarios JSON: " + userJsonStr);

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

        private List<Emprestimo> getDataFromJson(String usersJsonStr) throws JSONException {
            final String JSON_RESULT = "users";
            final String JSON_MULTA = "multa";
            final String JSON_USER = "user";
            final String JSON_EMPRESTIMOS = "emprestimos";
            final String JSON_LIVRO = "livro";
            final String JSON_DATA_EMPRESTIMO = "dataEmprestimo";
            final String JSON_DATA_DEVOLUCAO = "dataDevolucao";
            final String JSON_IMAGEM = "imagemLivro";
            final String JSON_RENOVAVEL = "renovavel";


            double multa;

            JSONObject listaJsonObject = new JSONObject(usersJsonStr);
            multa = listaJsonObject.getDouble(JSON_MULTA);
            JSONArray usuariosArray = listaJsonObject.getJSONArray(JSON_RESULT);

            List<Emprestimo> resultStrs = new ArrayList<Emprestimo>();
            for (int posicao = 0; posicao < usuariosArray.length(); posicao++) {

                String usuario;

                JSONObject usuarioJson = usuariosArray.getJSONObject(posicao);
                usuario = usuarioJson.getString(JSON_USER);


                if (user.equals(usuario)) {
                    JSONArray emprestimoArray = usuarioJson.getJSONArray(JSON_EMPRESTIMOS);
                    for (int contador = 0; contador < emprestimoArray.length(); contador++) {
                        JSONObject emprestimoJson = emprestimoArray.getJSONObject(contador);

                        String livro;
                        String dataDevolucao;
                        String dataEmprestimo;
                        String imagemLivro;
                        String renovavel;

                        Log.v(LOG_TAG, "Emprestimos JSON: " + emprestimoJson.toString());

                        livro = emprestimoJson.getString(JSON_LIVRO);
                        dataDevolucao = emprestimoJson.getString(JSON_DATA_DEVOLUCAO);
                        dataEmprestimo = emprestimoJson.getString(JSON_DATA_EMPRESTIMO);
                        imagemLivro = emprestimoJson.getString(JSON_IMAGEM);
                        renovavel = emprestimoJson.getString(JSON_RENOVAVEL);

                        double valorMulta = 0;

                        if(computarDias(dataDevolucao) >= 0){
                            valorMulta = computarDias(dataDevolucao) * multa;
                        }


                       Emprestimo emprestimo = new Emprestimo(livro, dataDevolucao, dataEmprestimo, imagemLivro, String.valueOf(valorMulta), renovavel);
                        resultStrs.add(emprestimo);
                    }

                }
            }

            for (Emprestimo u : resultStrs) {
                Log.v(LOG_TAG, "Livro" + u.getLivro());
                Log.v(LOG_TAG, "Data Emprestimo: " + u.getDataEmprestimo());
                Log.v(LOG_TAG, "Data Devolucao: " + u.getDataDevolucao());
                Log.v(LOG_TAG, "Imagem: " + u.getImagemLivro());
                //    Log.v(LOG_TAG, "Id: " + u.getId());

            }

            return resultStrs;
        }


        @Override
        protected void onPostExecute(List<Emprestimo> response) {
            if (response != null) {

                mAdapter = new EmprestimoAdapter(response);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setClickListener(MainActivityFragment.this);
                //  mAdapter.
            }
        }

        public int computarDias(String dataDevolucao){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateInString = dataDevolucao;

            try {

                Date date = formatter.parse(dateInString);
                 formatter.format(date);

                Calendar a = Calendar.getInstance();
                a.setTime(new Date());

                Calendar b = Calendar.getInstance();
                b.setTime(date);

                if(formatter.format(a.getTime()).equals(formatter.format(b.getTime()))){
                    Log.i("Data", formatter.format(a.getTime()) +"||"+ formatter.format(a.getTime()));
                   return -1;
                }else{
                    a.add(Calendar.DATE, -b.get(Calendar.DAY_OF_MONTH));
                    return a.get(Calendar.DAY_OF_MONTH);
                }



            } catch (ParseException e) {
                e.printStackTrace();
                return -1;
            }

        }
    }
}
