package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.Iterator;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.HorariosUtil;

public class EditarGamesFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String idGame;
    private String idUsuarioAtual;
    private Integer tipoProcedimento;

    private Usuario usuarioAtual;
    private Jogo game;

    private TextView nomeGame;
    private TextView descricaoGame;
    private Button botaoVoltar;
    private Button botaoAdicionarRemover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        carregarParametros();
        carregarInterface(inflater,container);
        carregarUsuarioAtual();
        carregarGame();
        return view;

    }

    public void carregarGame(){

        databaseReference.child("jogos").orderByChild("idJogo").equalTo(idGame)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        game = children.iterator().next().getValue(Jogo.class);
                        nomeGame.setText(game.getNome());
                        descricaoGame.setText(game.getDescricao());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }


    public void carregarInterface(LayoutInflater inflater,ViewGroup container){

        view = inflater.inflate(R.layout.games_editar_fragment,container,false);

        nomeGame = view.findViewById(R.id.nome_game);
        descricaoGame = view.findViewById(R.id.descricao_game);
        botaoVoltar = view.findViewById(R.id.botao_voltar);
        botaoAdicionarRemover = view.findViewById(R.id.botao_salvar_remover);

        carregarBotaoVoltar();
        carregarBotaoSalvarRemover();

    }

    public void carregarBotaoVoltar(){

        if(tipoProcedimento == 1){

            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new GamesFragment()).commit();
                }

            });

        } else {

            botaoVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });

        }

    }

    public void carregarBotaoSalvarRemover(){

        if(tipoProcedimento == 1){

            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    List<String> listaTemp = usuarioAtual.getJogos();
                    Iterator<String> i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(game.getIdJogo())){
                            i.remove();
                        }
                    }

                    usuarioAtual.setJogos(listaTemp);

                    listaTemp = game.getJogadores();
                    i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(usuarioAtual.getIdUsuario())){
                            i.remove();
                        }
                    }

                    game.setJogadores(listaTemp);

                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    databaseReference.child("jogos").child(game.getIdJogo()).setValue(game);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new GamesFragment()).commit();

                }

            });

        } else {

            botaoAdicionarRemover.setText("Adicionar");
            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    usuarioAtual.getJogos().add(game.getIdJogo());
                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    game.getJogadores().add(usuarioAtual.getIdUsuario());
                    databaseReference.child("jogos").child(game.getIdJogo()).setValue(game);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new GamesFragment()).commit();
                }

            });

        }

    }

    public void carregarParametros(){

        idGame = getArguments().getString("idGame");
        tipoProcedimento = getArguments().getInt("tipo");

    }

    public void carregarUsuarioAtual(){

        idUsuarioAtual = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("usuarios").orderByChild("idAutenticacao").equalTo(idUsuarioAtual).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                usuarioAtual = children.iterator().next().getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
