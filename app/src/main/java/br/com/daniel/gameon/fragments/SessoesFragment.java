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
import br.com.daniel.gameon.adapter.SessaoAdapter;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public class SessoesFragment extends Fragment {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();

        view = inflater.inflate(R.layout.sessoes_fragment,container,false);

        carregarBotaoAdicionar();
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
                        getAmigos(usuario.getSessoes());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void getAmigos(final List<String> listaIdSessoes){


        databaseReference.child("sessoes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<Sessao> listaSessoes = new ArrayList<Sessao>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Sessao sessao = child.getValue(Sessao.class);

                    for (String id:listaIdSessoes){

                        if (id != null){
                            if (id.equals(sessao.getIdSessao())){
                                listaSessoes.add(sessao);
                                break;
                            }
                        }

                    }

                }


                if (listaSessoes.isEmpty()){
                    TextView nenhum = view.findViewById(R.id.nenhum_text_view);
                    nenhum.setText("Você não faz parte de nenhuma sessão!");
                }

                initRecyclerView(listaSessoes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void initRecyclerView(List<Sessao> listaSessao){
        RecyclerView recyclerView = view.findViewById(R.id.sessao_recycler_view);
        RecyclerView.Adapter adapter = new SessaoAdapter(listaSessao,view.getContext(),getFragmentManager(),1);
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
        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.sessao_botao_adicionar);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new SessoesProcuraFragment()).commit();

            }

        });
    }
}
