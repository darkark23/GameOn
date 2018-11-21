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
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public class SessoesListaAmigosFragment extends Fragment {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private View view;
    private String sessaoIdAtual;
    private Sessao sessaoAtual;
    private Usuario usuarioAtual;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        sessaoIdAtual = getArguments().getString("idSessaoAtual");

        verificaAutenticacao();
        getActivity().setTitle("Sessões - Usuários participantes");
        view = inflater.inflate(R.layout.amigos_fragment, container, false);

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
                        usuarioAtual = children.iterator().next().getValue(Usuario.class);
                        carregarSessao();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void carregarSessao(){

        databaseReference.child("sessoes").orderByChild("idSessao").equalTo(sessaoIdAtual)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        sessaoAtual = children.iterator().next().getValue(Sessao.class);
                        carregarUsuarios();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }


    public void carregarUsuarios(){


        databaseReference.child("usuarios").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<Usuario> listaAmigos = new ArrayList<Usuario>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {

                    Usuario usuario = child.getValue(Usuario.class);

                    for (String id:sessaoAtual.getUsuarios()){

                        if (id != null){
                            if (id.equals(usuario.getIdUsuario()) && !id.equals(usuarioAtual.getIdUsuario())){
                                listaAmigos.add(usuario);
                                break;
                            }
                        }

                    }

                }

                if (listaAmigos.isEmpty()){
                    TextView nenhum = view.findViewById(R.id.nenhum_text_view);
                    nenhum.setText("Não existe nenhum outro jogador registrado na sessão!");
                }

                initRecyclerView(listaAmigos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }




    public void initRecyclerView(List<Usuario> listaAmigos){

        RecyclerView recyclerView = view.findViewById(R.id.amigo_recycler_view);
        RecyclerView.Adapter adapter = new AmigoAdapter(listaAmigos,view.getContext(),getFragmentManager(),3);
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

        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.adicionarAmigosButton);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("idSessaoAtual",sessaoAtual.getIdSessao());

                SessoesAdicionarAmigosFragment sessoesAdicionarAmigosFragment = new SessoesAdicionarAmigosFragment();
                sessoesAdicionarAmigosFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, sessoesAdicionarAmigosFragment).addToBackStack("SessoesAdicionarAmigosFragment").commit();

            }

        });

    }

    public void carregarBotaoVoltar(){

        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.botao_voltar);

        btn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("idSessao",sessaoIdAtual);
                bundle.putInt("tipo",1);

                EditarSessoesFragment editarSessoesFragment = new EditarSessoesFragment();
                editarSessoesFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, editarSessoesFragment).addToBackStack("EditarSessoesFragment").commit();

            }

        });

    }

}
