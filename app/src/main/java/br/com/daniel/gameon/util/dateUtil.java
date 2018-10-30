package br.com.daniel.gameon.util;

import java.util.Calendar;

public final class dateUtil {

    public static Calendar setHorario(Integer hora, Integer minuto){
        Calendar horario = null;
        horario.set(0,0,0,hora,minuto,0);
        return horario;
    }

}
