package br.com.daniel.gameon.util;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public final class CargaInicialUtil {

    public static void cadastrarUsuario(Usuario usuario, DatabaseReference dtRef) {
        String usuarioId = dtRef.child("usuarios").push().getKey();
        usuario.setIdUsuario(usuarioId);
        dtRef.child("usuarios").child(usuarioId).setValue(usuario);
    }

    public static void cadastrarJogo(Jogo jogo, DatabaseReference dtRef) {
        String jogoId = dtRef.child("jogos").push().getKey();
        jogo.setIdJogo(jogoId);
        dtRef.child("jogos").child(jogoId).setValue(jogo);
    }

    public static void cadastrarSessao(Sessao sessao, DatabaseReference dtRef) {
        String sessaoId = dtRef.child("sessoes").push().getKey();
        sessao.setIdSessao(sessaoId);
        dtRef.child("sessoes").child(sessaoId).setValue(sessao);
    }

    public static void cadastrarHorarios(Horarios horarios, DatabaseReference dtRef) {
        String horariosId = dtRef.child("horarios").push().getKey();
        horarios.setIdHorarios(horariosId);
        dtRef.child("horarios").child(horariosId).setValue(horarios);
    }

    public static void cargaInicial(DatabaseReference dtRef){
        cadastrarUsuario(new Usuario("YH5mcu1tetbKq0ZA3sLw6zG0u4s2",
                "Usuario01", "NomeUsuario01", "Eu sou o Jogador01!"),dtRef);
        cadastrarUsuario(new Usuario("t6eKJrCDLbUw1euZuujduORkEh42",
                "Usuario02", "NomeUsuario02", "Eu sou o Jogador02!"),dtRef);
        cadastrarUsuario(new Usuario("GXQl3gCsQTNIH4tJqRUgGWXbiYt2",
                "Usuario03", "NomeUsuario03", "Eu sou o Jogador03!"),dtRef);
        cadastrarUsuario(new Usuario("zrpZADpVUqcfCRR9R6Ql7FWEnbu1",
                "Usuario04", "NomeUsuario04", "Eu sou o Jogador04!"),dtRef);
        cadastrarUsuario(new Usuario("R2JT5bBsXPQ5vtbawNqGTsj40ns1",
                "Usuario05", "NomeUsuario05", "Eu sou o Jogador05!"),dtRef);
        cadastrarJogo(new Jogo("Jogo01","Jogo número 01"),dtRef);
        cadastrarJogo(new Jogo("Jogo02","Jogo número 02"),dtRef);
        cadastrarJogo(new Jogo("Jogo03","Jogo número 03"),dtRef);
        cadastrarJogo(new Jogo("Jogo04","Jogo número 04"),dtRef);
        cadastrarJogo(new Jogo("Jogo05","Jogo número 05"),dtRef);
        cadastrarSessao(new Sessao("Sessão01","-LQS1LrVuatHh9e3xNci",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true),dtRef);
        cadastrarSessao(new Sessao("Sessão02","LQS1LrX5YEy31Ng3zn4",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true),dtRef);
        cadastrarSessao(new Sessao("Sessão03","-LQS1LrZWHkOFYXW8Tfx",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true),dtRef);
        cadastrarSessao(new Sessao("Sessão04","-LQS1LraEzjR85vQVno2",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true),dtRef);
        cadastrarSessao(new Sessao("Sessão05","-LQS1LraEzjR85vQVno3",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true),dtRef);
        List<String> horariosIniciais =  new ArrayList<>();
        List<String> horariosFinais =  new ArrayList<>();
        horariosIniciais.add("180000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("180000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");

        cadastrarHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),dtRef);
        cadastrarHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),dtRef);
        cadastrarHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),dtRef);
        cadastrarHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),dtRef);
        cadastrarHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),dtRef);
    }
}
