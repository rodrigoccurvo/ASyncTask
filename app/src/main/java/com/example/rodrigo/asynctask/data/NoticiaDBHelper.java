package com.example.rodrigo.asynctask.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rodrigo.asynctask.data.ContratoDB.Noticia;

/**
 * Created by rodrigo on 9/9/15.
 */
public class NoticiaDBHelper extends SQLiteOpenHelper {
    private static final int VERSAO = 1;

    public static final String NOME_BANCO = "noticia.db";

    public NoticiaDBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE = "CREATE TABLE " + Noticia.NOME_TABELA + " (" +
                Noticia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Noticia.COLUNA_DATA + " INTEGER NOT NULL, " +
                Noticia.COLUNA_TITULO + " TEXT NOT NULL, " +
                Noticia.COLUNA_TEXTO + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContratoDB.Noticia.NOME_TABELA);
        onCreate(sqLiteDatabase);
    }
}
