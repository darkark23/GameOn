package br.com.daniel.gameon.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public final class HorariosUtil {

    public static Horarios horariosNaoPreenchidos(){


        List<String> horariosIniciais =  new ArrayList<>();

        horariosIniciais.add("000000");
        horariosIniciais.add("000000");
        horariosIniciais.add("000000");
        horariosIniciais.add("000000");
        horariosIniciais.add("000000");
        horariosIniciais.add("000000");
        horariosIniciais.add("000000");

        List<String> horariosFinais =  new ArrayList<>();

        horariosFinais.add("000000");
        horariosFinais.add("000000");
        horariosFinais.add("000000");
        horariosFinais.add("000000");
        horariosFinais.add("000000");
        horariosFinais.add("000000");
        horariosFinais.add("000000");

        return new Horarios(horariosIniciais,horariosFinais,"Horários não informados!");

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
