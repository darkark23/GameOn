package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;
import br.com.daniel.gameon.adapter.AmigoAdapter;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;

public class AmigosResultadoPesquisaFragment extends Fragment {

    private String nomePesquisa;
    private String idGame;
    private Jogo jogo;
    private List<Usuario> listaAmigos;
    private List<String> listaAmigosUsuario;
    private List<Usuario> listaAmigosGames;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        getActivity().setTitle("Amigos - Resutado usuários");
        view = inflater.inflate(R.layout.amigos_resultado_pesquisa_fragment, container, false);
        nomePesquisa = getArguments().getString("nomePesquisa");
        idGame = getArguments().getString("idGame");
        carregarJogo();
        carregarUsuario();
        carregarAmigos();
        carregarBotaoVoltar();


        return view;

    }

    public void carregarUsuario(){

        String idAutenticacaoUsuario = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("usuarios").orderByChild("idAutenticacao").equalTo(idAutenticacaoUsuario)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        Usuario usuario = children.iterator().next().getValue(Usuario.class);
                        listaAmigosUsuario = usuario.getAmigos();
                        listaAmigosUsuario.add(usuario.getIdUsuario());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void carregarJogo(){

        if (idGame != null){

        databaseReference.child("jogos").orderByChild("idJogo").equalTo(idGame).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                jogo = children.iterator().next().getValue(Jogo.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        }
    }


    public void carregarAmigos(){


        databaseReference.child("usuarios").orderByChild("nomeUsuario").startAt(nomePesquisa).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaAmigos = new ArrayList<Usuario>();
                listaAmigosGames = new ArrayList<Usuario>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    boolean repetido = false;
                    Usuario usuario = child.getValue(Usuario.class);

                    for (String id:listaAmigosUsuario){
                        if (id != null){
                            if (id.equals(usuario.getIdUsuario())){
                                repetido = true;
                                break;
                            }
                        }
                    }

                    if (!repetido){
                        listaAmigos.add(usuario);
                    }

                }

                if (idGame != null){

                    for (String idJogadores: jogo.getJogadores()) {
                        for (Usuario usuario:listaAmigos){
                            if (usuario.getIdUsuario().equals(idJogadores)){
                                listaAmigosGames.add(usuario);
                            }
                        }
                    }

                    listaAmigos = listaAmigosGames;
                }



                if (listaAmigos.isEmpty()){
                    TextView nenhum = view.findViewById(R.id.nenhum_text_view);
                    nenhum.setText("Não existe nenhum jogoador que atenda o filtro infotmado!");
                }

                initRecyclerView(listaAmigos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void initRecyclerView(List<Usuario> listaAmigos){

        RecyclerView recyclerView = view.findViewById(R.id.amigo_resutado_pesquisa_recycler_view);
        RecyclerView.Adapter adapter = new AmigoAdapter(listaAmigos,view.getContext(),getFragmentManager(),2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }

    public void carregarBotaoVoltar(){

        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.botao_voltar);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new AmigoProcuraFragment()).addToBackStack("AmigoProcuraFragment").commit();

            }

        });

    }

    public void verificaAutenticacao(){

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    startActivity( new Intent(view.getContext(), MenuPrincipalActivity.class));
                }

            }

        };

    }

}
