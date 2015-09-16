package com.example.rodrigo.asynctask;

/**
 * Created by rodrigo on 9/2/15.
 */
public class ServidorFalso {
    public String[][] pegaDados() {
        String[][] dados1 = {
                {"1442361600", "Vovó campeã de kickbox", "Uma avozinha ganhou o campeonato de kickbox"},
                {"1442448000", "Uma notícia", "Um texto bem interessante"},
                {"1442534400", "Outra notícia", "Outro texto não tão interessante"}
        };

        String[][] dados2 = {
                {"1442620800", "Mais uma notícia", "Mais um texto"},
                {"1442707200", "Última notícia", "Último texto"}
        };

        // Para não pegar sempre o mesmo...
        long time = System.currentTimeMillis() / 1000;
        if (time % 2 == 0) {
            return dados1;
        } else {
            return dados2;
        }
    }
}
