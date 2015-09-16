package com.example.rodrigo.asynctask.data;

import android.provider.BaseColumns;

/**
 * Created by rodrigo on 9/9/15.
 */
public class ContratoDB {
    public static final class Noticia implements BaseColumns {

        public static final String NOME_TABELA = "noticia";

        public static final String COLUNA_DATA = "data";
        public static final String COLUNA_TITULO = "titulo";
        public static final String COLUNA_TEXTO = "texto";
    }
}
