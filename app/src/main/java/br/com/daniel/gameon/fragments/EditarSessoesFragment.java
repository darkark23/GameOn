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
import android.widget.Toast;

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
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.FormatUtil;

public class EditarSessoesFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String idSessao;
    private String idUsuarioAtual;
    private Integer tipoProcedimento;

    private Usuario usuarioAtual;
    private Sessao sessao;

    private TextView nomeSessao;
    private TextView jogoSessao;
    private TextView inicioSessao;
    private TextView fimSessao;
    private TextView publicoSessao;
    private TextView ativoSessao;

    private Button botaoVoltar;
    private Button botaoAdicionarRemover;
    private Button botaoJogadores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        carregarParametros();
        carregarInterface(inflater,container);
        carregarUsuarioAtual();
        carregarSessao();
        return view;

    }

    public void carregarSessao(){

        databaseReference.child("sessoes").orderByChild("idSessao").equalTo(idSessao)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        sessao = children.iterator().next().getValue(Sessao.class);
                        nomeSessao.setText(sessao.getNomeSessao());
                        inicioSessao.setText(DataUtil.formatDataHora(sessao.getDataInicio(),6));
                        fimSessao.setText(DataUtil.formatDataHora(sessao.getDataFim(),6));
                        publicoSessao.setText(FormatUtil.publicoFormat(sessao.getPublico()));
                        ativoSessao.setText(FormatUtil.finalizadoFormat(sessao.getAtivo()));
                        carregarJogoSessao();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void carregarJogoSessao(){

        databaseReference.child("jogos").orderByChild("idJogo").equalTo(sessao.getIdJogo())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        Jogo jogo = children.iterator().next().getValue(Jogo.class);
                        jogoSessao.setText(jogo.getNome());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }


    public void carregarInterface(LayoutInflater inflater,ViewGroup container){

        if(tipoProcedimento == 1){
            getActivity().setTitle("Sessões - Informações");
        }else {
            getActivity().setTitle("Sessões - Participar da Sessão");
        }

        view = inflater.inflate(R.layout.sessoes_editar_fragment,container,false);

        nomeSessao = view.findViewById(R.id.nome_text_view);
        jogoSessao = view.findViewById(R.id.jogo_text_view);
        inicioSessao = view.findViewById(R.id.inicio_text_view);
        fimSessao = view.findViewById(R.id.fim_text_view);
        publicoSessao = view.findViewById(R.id.publico_text_view);
        ativoSessao = view.findViewById(R.id.finalizado_text_view);
        botaoVoltar = view.findViewById(R.id.botao_voltar);
        botaoAdicionarRemover = view.findViewById(R.id.botao_salvar_remover);

        carregarBotaoVoltar();
        carregarBotaoSalvarRemover();

        botaoJogadores = view.findViewById(R.id.botao_jogadores);
        carregarBotaoAdicionar();


    }

    public void carregarBotaoVoltar(){

        if(tipoProcedimento == 1){

            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new SessoesFragment()).addToBackStack("SessoesFragment").commit();
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

                    List<String> listaTemp = usuarioAtual.getSessoes();
                    Iterator<String> i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(sessao.getIdSessao())){
                            i.remove();
                        }
                    }

                    usuarioAtual.setSessoes(listaTemp);

                    listaTemp = sessao.getUsuarios();
                    i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(usuarioAtual.getIdUsuario())){
                            i.remove();
                        }
                    }

                    sessao.setUsuarios(listaTemp);

                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    databaseReference.child("sessoes").child(sessao.getIdSessao()).setValue(sessao);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new SessoesFragment()).addToBackStack("SessoesFragment").commit();

                    Toast.makeText(view.getContext(), "Você saiu da sessão!", Toast.LENGTH_LONG).show();

                }

            });

        } else {

            botaoAdicionarRemover.setText("Adicionar");
            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    usuarioAtual.getSessoes().add(sessao.getIdSessao());
                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    sessao.getUsuarios().add(usuarioAtual.getIdUsuario());
                    databaseReference.child("sessoes").child(sessao.getIdSessao()).setValue(sessao);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new SessoesFragment()).addToBackStack("SessoesFragment").commit();;

                    Toast.makeText(view.getContext(), "Você esta participando da sessão escolhida!", Toast.LENGTH_LONG).show();
                }

            });

        }

    }

    public void carregarBotaoAdicionar(){

        botaoJogadores.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("idSessaoAtual",sessao.getIdSessao());

                SessoesListaAmigosFragment sessoesListaAmigosFragment = new SessoesListaAmigosFragment();
                sessoesListaAmigosFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, sessoesListaAmigosFragment).commit();

            }

        });


    }

    public void carregarParametros(){

        idSessao = getArguments().getString("idSessao");
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
