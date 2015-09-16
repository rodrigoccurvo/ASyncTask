package com.example.rodrigo.asynctask;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rodrigo.asynctask.data.ContratoDB;
import com.example.rodrigo.asynctask.data.NoticiaDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> mAdapter;
    private Map<Integer, Long> mapaIds = new HashMap<Integer, Long>();

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
        atualizaInterface();
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

    private void atualizaInterface() {
        SQLiteOpenHelper dbHelper = new NoticiaDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                ContratoDB.Noticia.NOME_TABELA, // Tabela
                null, // colunas (todas)
                null, // colunas para o where
                null, // valores para o where
                null, // group by
                null, // having
                null  // sort by
        );

        mAdapter.clear();
        mapaIds.clear();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(ContratoDB.Noticia._ID));
            long data = cursor.getLong(cursor.getColumnIndex(ContratoDB.Noticia.COLUNA_DATA));
            String titulo = cursor.getString(cursor.getColumnIndex(ContratoDB.Noticia.COLUNA_TITULO));
            String texto = cursor.getString(cursor.getColumnIndex(ContratoDB.Noticia.COLUNA_TEXTO));

            String dataBonita = new SimpleDateFormat("dd/MM/yyyy").format(new Date(data * 1000));

            // Associa a posição do item ao id dele
            mapaIds.put(mAdapter.getCount(), id);

            // Não estou usando o texto, mas poderia
            mAdapter.add(id + " - " + dataBonita + " - " + titulo);
        }

        db.close();
        dbHelper.close();
    }

    public class PegaDadosDoServidor extends AsyncTask<Void, Void, String[][]> {

        @Override
        protected String[][] doInBackground(Void... voids) {
            ServidorFalso servidor = new ServidorFalso();
            return servidor.pegaDados();
        }

        @Override
        protected void onPostExecute(String[][] result) {
            if (result != null) {
                SQLiteOpenHelper dbHelper = new NoticiaDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // LIMPA A TABELA INTEIRA!!!!
                db.delete(ContratoDB.Noticia.NOME_TABELA, null, null);

                for(String linha[] : result) {
                    ContentValues entrada = new ContentValues();
                    entrada.put(ContratoDB.Noticia.COLUNA_DATA, linha[0]);
                    entrada.put(ContratoDB.Noticia.COLUNA_TITULO, linha[1]);
                    entrada.put(ContratoDB.Noticia.COLUNA_TEXTO, linha[2]);

                    db.insert(ContratoDB.Noticia.NOME_TABELA, null, entrada);

                }

                db.close();
                dbHelper.close();
            }

            atualizaInterface();
        }
    }
}
