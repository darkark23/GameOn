package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.adapter.AmigoAdapter;
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.dateUtil;

public class AmigosFragment extends Fragment {

    final List<Usuario> listaAmigos = new ArrayList<>();
    DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.amigos_fragment, container, false);
        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.adicionarAmigosButton);
        btn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
                cargaInicial();
            }
        });
        dtRef.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    Usuario amigo = child.getValue(Usuario.class);
                    listaAmigos.add(amigo);
                }
                initRecyclerView(listaAmigos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void cargaInicial(){
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53",
                "Usuario01", "NomeUsuario01", "Eu sou o Jogador01!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53",
                "Usuario02", "NomeUsuario02", "Eu sou o Jogador02!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53",
                "Usuario03", "NomeUsuario03", "Eu sou o Jogador03!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53",
                "Usuario04", "NomeUsuario04", "Eu sou o Jogador04!"));
        cadastrarUsuario(new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53",
                "Usuario05", "NomeUsuario05", "Eu sou o Jogador05!"));
        cadastrarJogo(new Jogo("Jogo01","Jogo número 01"));
        cadastrarJogo(new Jogo("Jogo02","Jogo número 02"));
        cadastrarJogo(new Jogo("Jogo03","Jogo número 03"));
        cadastrarJogo(new Jogo("Jogo04","Jogo número 04"));
        cadastrarJogo(new Jogo("Jogo05","Jogo número 05"));
        cadastrarSessao(new Sessao("Sessão01","-LQS1LrVuatHh9e3xNci",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true));
        cadastrarSessao(new Sessao("Sessão02","LQS1LrX5YEy31Ng3zn4",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true));
        cadastrarSessao(new Sessao("Sessão03","-LQS1LrZWHkOFYXW8Tfx",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true));
        cadastrarSessao(new Sessao("Sessão04","-LQS1LraEzjR85vQVno2",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true));
        cadastrarSessao(new Sessao("Sessão05","-LQS1LraEzjR85vQVno3",
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                dateUtil.setDataHora("01","01","2018","22","00","00"),
                true));
        Map<String,String> horarios =  new HashMap<>();
        horarios.put("1","200000");
        horarios.put("2","210000");
        horarios.put("3","210000");
        horarios.put("4","200000");
        horarios.put("5","230000");
        horarios.put("6","220000");
        horarios.put("7","200000");
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
        cadastrarHorarios(new Horarios(horarios,"Não jogo nos feriados"));
    }

    public void initRecyclerView(List<Usuario> listaAmigos){
        RecyclerView recyclerView = view.findViewById(R.id.amigo_recycler_view);
        RecyclerView.Adapter adapter = new AmigoAdapter( listaAmigos,view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
