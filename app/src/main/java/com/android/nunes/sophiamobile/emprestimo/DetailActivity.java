package com.android.nunes.sophiamobile.emprestimo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nunes.sophiamobile.R;
import com.android.nunes.sophiamobile.model.Emprestimo;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        mToolbar = (Toolbar) findViewById(R.id.dToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


            Intent intent = this.getIntent();

            if(intent != null && intent.hasExtra(MainActivityFragment.PAR_KEY)){
                final Emprestimo mEmprestimo = (Emprestimo)intent.getParcelableExtra(MainActivityFragment.PAR_KEY);

                popular(mEmprestimo);



                // FAB
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Boolean.valueOf(mEmprestimo.getRenovavel()) && (mEmprestimo.getValorMulta() == null  || Double.valueOf(mEmprestimo.getValorMulta()) <= 0)){
                            Toast.makeText(DetailActivity.this, getString(R.string.renovavel_true) + " " + renovarData() , Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("dv"+mEmprestimo.getLivro(), renovarData());
                            editor.commit();
                            popular(mEmprestimo);

                        }else{
                            Toast.makeText(DetailActivity.this, R.string.renovavel_false, Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }

    }


    public void popular(Emprestimo mEmprestimo){
        TextView livro = (TextView)findViewById(R.id.livroNome);
        TextView dataDevolucao = (TextView)findViewById(R.id.dataDevolucao);
        TextView valorMulta = (TextView)findViewById(R.id.valorMulta);
        ImageView imagemLivro = (ImageView)findViewById(R.id.capa);
        TextView dataEmprestimo = (TextView)findViewById(R.id.dataEmprestimo);


        UrlImageViewHelper.setUrlDrawable(imagemLivro, mEmprestimo.getImagemLivro());
        livro.setText(mEmprestimo.getLivro());
        dataDevolucao.setText(prefs.getString("dv"+mEmprestimo.getLivro(), mEmprestimo.getDataDevolucao()));
        dataEmprestimo.setText(mEmprestimo.getDataEmprestimo());
        valorMulta.setText(mEmprestimo.getValorMulta());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public String renovarData(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //String dateInString = dataDevolucao;

                Date date = new Date();
                    formatter.format(date);

            Calendar a = Calendar.getInstance();
            a.setTime(new Date());

            Calendar b = Calendar.getInstance();
            b.setTime(date);
            a.add(Calendar.DAY_OF_MONTH, 7);

            return formatter.format(a.getTime()).toString();

    }

}
