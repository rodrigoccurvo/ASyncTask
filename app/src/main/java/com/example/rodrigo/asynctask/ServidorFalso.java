package com.example.rodrigo.asynctask;

/**
 * Created by rodrigo on 9/2/15.
 */
public class ServidorFalso {
    public String[] pegaDados() {
        String[] dados1 = {
                "Um",
                "Dois",
                "Três indiozinhos"
        };

        String[] dados2 = {
                "Quatro",
                "Cinco",
                "Seis indiozinhos"
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
