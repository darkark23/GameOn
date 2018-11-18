package br.com.daniel.gameon.util;

import com.google.firebase.database.DatabaseReference;

import br.com.daniel.gameon.entity.Usuario;

public final class FormatUtil {

    public static String publicoFormat(boolean publicoBoolean) {
        if (publicoBoolean){
            return "Sim";
        } else {
            return "Não";
        }
    }

    public static String finalizadoFormat(String finalizadoString) {
        if (finalizadoString.equals("S")){
            return "Sim";
        } else {
            return "Não";
        }
    }

}
