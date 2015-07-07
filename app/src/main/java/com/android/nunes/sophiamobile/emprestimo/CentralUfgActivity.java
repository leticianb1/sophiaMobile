package com.android.nunes.sophiamobile.emprestimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.android.nunes.sophiamobile.R;

public class CentralUfgActivity extends AppCompatActivity implements OnClickListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_ufg);

        mToolbar = (Toolbar) findViewById(R.id.toolbarCentral);
        mToolbar.setTitle("Central UFG");
        setSupportActionBar(mToolbar);

      //  TextView texto = (TextView) findViewById(R.id.textBiblio);

        ImageView imageView = (ImageView) findViewById(R.id.iconCentral);

        imageView.setOnClickListener(this);


    }

    @Override public void onClick(View v) {
        Log.d("MR.bool", "Button1 was clicked ");

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_central_ufg, menu);
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


}
