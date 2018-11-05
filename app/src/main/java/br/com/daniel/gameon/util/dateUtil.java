package br.com.daniel.gameon.util;

public final class dateUtil {

    public static String getHorario(String horario) {
        String horarioFormatado = horario.substring(0, 2) + ":" + horario.substring(2, 4) + ":" + horario.substring(4,6);
        return horario;
    }

    public static String setHorario(String horas, String minutos, String segundos) {
        return horas.toString() + minutos.toString() + segundos.toString();
    }

    public static String getData(String Data, Integer tipo){
        String dataFormatada;
        if ( tipo == 1){
            dataFormatada = Data.substring(0,2) + "/" + Data.substring(2,4) + "/" + Data.substring(4,8);//(dd/mm/aaaa)
        } else {
            dataFormatada = Data.substring(0,2) + "/" + Data.substring(2,4) + "/" + Data.substring(4,6);//(dd/mm/aa)
        }
        return dataFormatada;
    }

    public static String setData(String dia, String mes, String ano) {
        return dia.toString() + mes.toString() + ano.toString();
    }

    public static String getDataHora(String DataHora, Integer tipo){
        String dataHoraFormatada;
        switch (tipo){
            case 1:
                dataHoraFormatada = DataHora.substring(0,2) + "/" + DataHora.substring(2,4) + "/" +
                        DataHora.substring(4,8) + " " + DataHora.substring(8,10)  + ":" +
                         DataHora.substring(10,12) + ":" + DataHora.substring(12,14); //(dd/mm/aaaa hh:mm:ss)
                break;
            case 2:
                dataHoraFormatada = DataHora.substring(0,2) + "/" + DataHora.substring(2,4) + "/" +
                        DataHora.substring(4,6) + " " + DataHora.substring(8,10)
                        + ":" + DataHora.substring(10,12) + ":" + DataHora.substring(12,14); //(dd/mm/aa hh:mm:ss)
                break;
            case 3:
                dataHoraFormatada = DataHora.substring(0,2) + "/" + DataHora.substring(2,4) + "/" +
                        DataHora.substring(4,8); //(dd/mm/aaaa)
                break;
            case 4:
                dataHoraFormatada = DataHora.substring(0,2) + "/" + DataHora.substring(2,4) + "/" +
                        DataHora.substring(4,8); //(dd/mm/aa)
                break;
            case 5:
                dataHoraFormatada = DataHora.substring(8,10) + ":" + DataHora.substring(10,12) + ":" +
                        DataHora.substring(12,14); //(hh:mm:ss)
                break;
            case 6:
                dataHoraFormatada = DataHora.substring(0,2) + "/" + DataHora.substring(2,4) + " " +
                        DataHora.substring(8,10)  + ":" + DataHora.substring(10,12) + ":"
                        + DataHora.substring(12,14); //(dd/mm/aaaa hh:mm:ss)
                break;
            default:
                dataHoraFormatada = "opção invalida";
        }
        return dataHoraFormatada;
    }

    public static String setDataHora(String dia, String mes, String ano, String horas, String minutos, String segundos) {
        return dia.toString() + mes.toString() + ano.toString() + horas.toString() + minutos.toString() + segundos.toString();
    }

}
