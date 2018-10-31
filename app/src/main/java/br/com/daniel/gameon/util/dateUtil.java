package br.com.daniel.gameon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class dateUtil {

    public static Calendar setHorario(Integer hora, Integer minuto){
        Calendar horario = Calendar.getInstance();
        horario.set(99,12,29,hora,minuto,00);
        return horario;
    }

    public static String getStringDate(Date data){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String strDate = dateFormat.format(data).toString();
        return strDate;
    };


}
