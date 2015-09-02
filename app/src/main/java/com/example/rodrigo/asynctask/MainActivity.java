package com.example.rodrigo.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter =
                new ArrayAdapter<String>(
                        this, // The current context (this activity)
                        R.layout.list_item, // The name of the layout ID.
                        R.id.list_item_textview, // The ID of the textview to populate.
                        new ArrayList<String>());

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        atualizar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
                atualizar();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void atualizar() {
        PegaDadosDoServidor pega = new PegaDadosDoServidor();
        pega.execute();
    }

    public class PegaDadosDoServidor extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            ServidorFalso servidor = new ServidorFalso();
            return servidor.pegaDados();
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mAdapter.clear();
                for(String r : result) {
                    mAdapter.add(r);
                }
            }
        }
    }
}
