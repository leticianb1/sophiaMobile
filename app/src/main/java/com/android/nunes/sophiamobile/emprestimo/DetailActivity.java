package com.android.nunes.sophiamobile.emprestimo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = (Toolbar) findViewById(R.id.dToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


            Intent intent = this.getIntent();

            if(intent != null && intent.hasExtra(MainActivityFragment.PAR_KEY)){
                final Emprestimo mEmprestimo = (Emprestimo)intent.getParcelableExtra(MainActivityFragment.PAR_KEY);

                TextView livro = (TextView)findViewById(R.id.livroNome);
                TextView dataDevolucao = (TextView)findViewById(R.id.dataDevolucao);
                TextView valorMulta = (TextView)findViewById(R.id.valorMulta);
                ImageView imagemLivro = (ImageView)findViewById(R.id.imagemLivro);
                TextView dataEmprestimo = (TextView)findViewById(R.id.dataEmprestimo);


                UrlImageViewHelper.setUrlDrawable(imagemLivro, mEmprestimo.getImagemLivro());
                livro.setText(mEmprestimo.getLivro());
                dataDevolucao.setText(mEmprestimo.getDataDevolucao());
                dataEmprestimo.setText(mEmprestimo.getDataEmprestimo());
               valorMulta.setText(mEmprestimo.getValorMulta());


                mCollapsingToolbarLayout.setTitle(mEmprestimo.getLivro());
               // Glide.with(this).load(mEmprestimo.getImagemLivro()).fitCenter().into(imagemLivro);

                // FAB
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Boolean.valueOf(mEmprestimo.getRenovavel())){
                            Toast.makeText(DetailActivity.this, R.string.renovavel_true , Toast.LENGTH_SHORT).show();
                           // SharedPreferences
                        }else{
                            Toast.makeText(DetailActivity.this, R.string.renovavel_false, Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }

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


}
