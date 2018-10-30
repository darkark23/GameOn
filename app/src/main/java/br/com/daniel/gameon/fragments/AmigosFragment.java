package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.dateUtil;

public class AmigosFragment extends Fragment {

    DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference();


    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.amigos_fragment, container, false);
        FloatingActionButton btn =(FloatingActionButton) myView.findViewById(R.id.adicionarAmigosButton);
        btn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
                cargaInicial();
            }
        });
        return myView;
    }

    public void cargaInicial(){
        Calendar dataInicio = null;
        Calendar dataFinal = null;
        dataInicio.set(2018,01,01,20,00,00);
        dataFinal.set(2018,01,01,22,00,00);
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "Usuario01", "NomeUsuario01", "Eu sou o Jogador01!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "Usuario02", "NomeUsuario02", "Eu sou o Jogador02!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "Usuario03", "NomeUsuario03", "Eu sou o Jogador03!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "Usuario04", "NomeUsuario04", "Eu sou o Jogador04!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "Usuario05", "NomeUsuario05", "Eu sou o Jogador05!"));
        cadastrarJogo(new Jogo("Jogo01","Jogo número 01"));
        cadastrarJogo(new Jogo("Jogo02","Jogo número 02"));
        cadastrarJogo(new Jogo("Jogo03","Jogo número 03"));
        cadastrarJogo(new Jogo("Jogo04","Jogo número 04"));
        cadastrarJogo(new Jogo("Jogo05","Jogo número 05"));
        cadastrarSessao(new Sessao("Sessão01",dataInicio,dataFinal,true));
        cadastrarSessao(new Sessao("Sessão02",dataInicio,dataFinal,true));
        cadastrarSessao(new Sessao("Sessão03",dataInicio,dataFinal,true));
        cadastrarSessao(new Sessao("Sessão04",dataInicio,dataFinal,true));
        cadastrarSessao(new Sessao("Sessão05",dataInicio,dataFinal,true));
        Calendar[] horarios = {dateUtil.setHorario(20,00)
                ,dateUtil.setHorario(21,00)
                ,dateUtil.setHorario(22,00)
                ,dateUtil.setHorario(20,00)
                ,dateUtil.setHorario(21,00)
                ,dateUtil.setHorario(22,00)
                ,dateUtil.setHorario(01,00)};
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
    }

    public void cadastrarUsuario(Usuario usuario) {
        String usuarioId = dtRef.child("usuarios").push().getKey();
        usuario.setIdUsuario(usuarioId);
        dtRef.child("usuarios").child(usuarioId).setValue(usuario);
    }

    public void cadastrarJogo(Jogo jogo) {
        String jogoId = dtRef.child("jogos").push().getKey();
        jogo.setIdJogo(jogoId);
        dtRef.child("jogos").child(jogoId).setValue(jogo);
    }

    public void cadastrarSessao(Sessao sessao) {
        String sessaoId = dtRef.child("sessoes").push().getKey();
        sessao.setIdSessao(sessaoId);
        dtRef.child("sessoes").child(sessaoId).setValue(sessao);
    }

    public void cadastrarHorarios(Horarios horarios) {
        String horariosId = dtRef.child("horarios").push().getKey();
        horarios.setIdHorarios(horariosId);
        dtRef.child("horarios").child(horariosId).setValue(horarios);
    }

}
