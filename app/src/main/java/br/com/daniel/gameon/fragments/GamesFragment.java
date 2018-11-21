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
import br.com.daniel.gameon.adapter.JogoAdapter;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;

public class GamesFragment extends Fragment {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        getActivity().setTitle("Games");
        view = inflater.inflate(R.layout.games_fragment,container,false);

        carregarBotaoAdicionar();
        carregarBotaoVoltar();
        carregarUsuario();

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
                        getAmigos(usuario.getJogos());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void getAmigos(final List<String> listaIdJogos){


        databaseReference.child("jogos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<Jogo> listaJogos = new ArrayList<Jogo>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Jogo jogo = child.getValue(Jogo.class);

                    for (String id:listaIdJogos){

                        if (id != null){
                            if (id.equals(jogo.getIdJogo())){
                                listaJogos.add(jogo);
                                break;
                            }
                        }

                    }

                }

                if (listaJogos.isEmpty()){
                    TextView nenhum = view.findViewById(R.id.nenhum_text_view);
                    nenhum.setText("Você não tem nenhum Game na usa lista!");
                }

                initRecyclerView(listaJogos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void initRecyclerView(List<Jogo> listaAmigos){

        RecyclerView recyclerView = view.findViewById(R.id.game_recycler_view);
        RecyclerView.Adapter adapter = new JogoAdapter(listaAmigos,view.getContext(),getFragmentManager(),1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

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

    public void carregarBotaoAdicionar(){
        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.game_botao_adicionar);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new GamesProcuraFragment()).addToBackStack("GamesProcuraFragment").commit();

            }

        });
    }

    public void carregarBotaoVoltar(){

        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.botao_voltar);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new PerfilFragment()).addToBackStack("PerfilFragment").commit();

            }

        });

    }

}
