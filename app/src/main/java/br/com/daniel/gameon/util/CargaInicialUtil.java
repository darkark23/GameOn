package br.com.daniel.gameon.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public final class CargaInicialUtil {

    public static void cargaInicial(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        cadastrarInicialUsuario(new Usuario("YH5mcu1tetbKq0ZA3sLw6zG0u4s2",
                "Usuario01", "NomeUsuario01", "Eu sou o Jogador01!"),databaseReference);
        cadastrarInicialUsuario(new Usuario("t6eKJrCDLbUw1euZuujduORkEh42",
                "Usuario02", "NomeUsuario02", "Eu sou o Jogador02!"),databaseReference);
        cadastrarInicialUsuario(new Usuario("GXQl3gCsQTNIH4tJqRUgGWXbiYt2",
                "Usuario03", "NomeUsuario03", "Eu sou o Jogador03!"),databaseReference);
        cadastrarInicialUsuario(new Usuario("zrpZADpVUqcfCRR9R6Ql7FWEnbu1",
                "Usuario04", "NomeUsuario04", "Eu sou o Jogador04!"),databaseReference);
        cadastrarInicialUsuario(new Usuario("R2JT5bBsXPQ5vtbawNqGTsj40ns1",
                "Usuario05", "NomeUsuario05", "Eu sou o Jogador05!"),databaseReference);

        cadastrarInicialJogo(new Jogo("Jogo01","Jogo número 01"),databaseReference);
        cadastrarInicialJogo(new Jogo("Jogo02","Jogo número 02"),databaseReference);
        cadastrarInicialJogo(new Jogo("Jogo03","Jogo número 03"),databaseReference);
        cadastrarInicialJogo(new Jogo("Jogo04","Jogo número 04"),databaseReference);
        cadastrarInicialJogo(new Jogo("Jogo05","Jogo número 05"),databaseReference);

        cadastrarInicialSessao(new Sessao("Sessão01","-LQS1LrVuatHh9e3xNci",
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                true),databaseReference);
        cadastrarInicialSessao(new Sessao("Sessão02","LQS1LrX5YEy31Ng3zn4",
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                true),databaseReference);
        cadastrarInicialSessao(new Sessao("Sessão03","-LQS1LrZWHkOFYXW8Tfx",
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                true),databaseReference);
        cadastrarInicialSessao(new Sessao("Sessão04","-LQS1LraEzjR85vQVno2",
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                true),databaseReference);
        cadastrarInicialSessao(new Sessao("Sessão05","-LQS1LraEzjR85vQVno3",
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                DataUtil.setDataHora("01","01","2018","22","00","00"),
                true),databaseReference);

        List<String> horariosIniciais =  new ArrayList<>();

        horariosIniciais.add("180000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("210000");
        horariosIniciais.add("180000");

        List<String> horariosFinais =  new ArrayList<>();

        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");
        horariosFinais.add("230000");

        cadastrarInicialHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),databaseReference);
        cadastrarInicialHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),databaseReference);
        cadastrarInicialHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),databaseReference);
        cadastrarInicialHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),databaseReference);
        cadastrarInicialHorarios(new Horarios(horariosIniciais,horariosFinais,"Não jogo nos feriados"),databaseReference);

    }

    public static void cadastrarInicialUsuario(Usuario usuario, DatabaseReference databaseReference) {

        String usuarioId = databaseReference.child("usuarios").push().getKey();
        usuario.setIdUsuario(usuarioId);
        databaseReference.child("usuarios").child(usuarioId).setValue(usuario);

    }

    public static void cadastrarInicialJogo(Jogo jogo, DatabaseReference databaseReference) {

        String jogoId = databaseReference.child("jogos").push().getKey();
        jogo.setIdJogo(jogoId);
        databaseReference.child("jogos").child(jogoId).setValue(jogo);

    }

    public static void cadastrarInicialSessao(Sessao sessao, DatabaseReference databaseReference) {

        String sessaoId = databaseReference.child("sessoes").push().getKey();
        sessao.setIdSessao(sessaoId);
        databaseReference.child("sessoes").child(sessaoId).setValue(sessao);

    }

    public static void cadastrarInicialHorarios(Horarios horarios, DatabaseReference databaseReference) {

        String horariosId = databaseReference.child("horarios").push().getKey();
        horarios.setIdHorarios(horariosId);
        databaseReference.child("horarios").child(horariosId).setValue(horarios);

    }

}
