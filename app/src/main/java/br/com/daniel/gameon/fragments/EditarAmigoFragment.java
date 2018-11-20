package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.DownloadImagemUtil;
import br.com.daniel.gameon.util.HorariosUtil;

public class EditarAmigoFragment extends Fragment {

    private View view;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String amigoId;
    private String idUsuarioAtual;
    private String idSessao;
    private Integer tipoProcedimento;

    private Usuario usuarioAtual;
    private Usuario usuarioAmigo;
    private Sessao sessao;
    private Horarios horariosUsuarioAmigo;

    private ImageView imagemUsuario;
    private TextView fraseUsuario;
    private TextView nomeUsuario;
    private TextView horarioDomingo;
    private TextView horarioSegunda;
    private TextView horarioTerca;
    private TextView horarioQuarta;
    private TextView horarioQuinta;
    private TextView horarioSexta;
    private TextView horarioSabado;
    private Button botaoVoltar;
    private Button botaoAdicionarRemover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        carregarParametros();
        carregarInterface(inflater,container);
        carregarUsuarioAtual();
        carregarUsuarioAmigo();
        if (tipoProcedimento == 4){
            carregarSessao();
        }

        return view;

    }

    public void carregarSessao(){


        databaseReference.child("sessoes").orderByChild("idSessao").equalTo(idSessao)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        sessao = children.iterator().next().getValue(Sessao.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }



    public void carregarUsuarioAmigo(){

        databaseReference.child("usuarios").orderByChild("idUsuario").equalTo(amigoId)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        usuarioAmigo = children.iterator().next().getValue(Usuario.class);
                        if (usuarioAmigo.getUrlImagem()!= null){
                            new DownloadImagemUtil(imagemUsuario).execute(usuarioAmigo.getUrlImagem());
                        }
                        nomeUsuario.setText(usuarioAmigo.getNomeUsuario());
                        fraseUsuario.setText(usuarioAmigo.getFrase());

                        carregarHorariosUsuarioAmigo();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

    public void carregarHorariosUsuarioAmigo(){

        databaseReference.child("horarios").orderByChild("idUsuario").startAt(usuarioAmigo.getIdUsuario()).endAt(usuarioAmigo.getIdHorarios()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                if (children.iterator().hasNext()){

                    horariosUsuarioAmigo = children.iterator().next().getValue(Horarios.class);

                    horarioDomingo.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(0)));
                    horarioSegunda.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(1)));
                    horarioTerca.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(2)));
                    horarioQuarta.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(3)));
                    horarioQuinta.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(4)));
                    horarioSexta.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(5)));
                    horarioSabado.setText(DataUtil.formatHorarioPerfil(horariosUsuarioAmigo.getHoariosInicio().get(6)));

                } else {

                    horariosUsuarioAmigo = HorariosUtil.horariosNaoPreenchidos();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void carregarInterface(LayoutInflater inflater,ViewGroup container){

        if (tipoProcedimento == 1){
            getActivity().setTitle("Amigos - Perfil");
        } else if (tipoProcedimento == 2){
            getActivity().setTitle("Amigos - Adicionar usuário");
        } else if (tipoProcedimento == 4){
            getActivity().setTitle("Sessão - Adicionar usuário");
        } else{

        }

        view = inflater.inflate(R.layout.amigo_editar_fragment,container,false);

        imagemUsuario = view.findViewById(R.id.imagem_usuario);
        fraseUsuario = view.findViewById(R.id.frase_usuario);
        nomeUsuario = view.findViewById(R.id.nome_usuario);
        horarioDomingo = view.findViewById(R.id.horario_domingo);
        horarioSegunda = view.findViewById(R.id.horario_segunda);
        horarioTerca = view.findViewById(R.id.horario_terca);
        horarioQuarta = view.findViewById(R.id.horario_quarta);
        horarioQuinta = view.findViewById(R.id.horario_quinta);
        horarioSexta = view.findViewById(R.id.horario_sexta);
        horarioSabado = view.findViewById(R.id.horario_sabado);
        botaoVoltar = view.findViewById(R.id.botao_voltar);
        botaoAdicionarRemover = view.findViewById(R.id.botao_remover);

        carregarBotaoVoltar();
        carregarBotaoSalvarRemover();

    }

    public void carregarBotaoVoltar(){

        if(tipoProcedimento == 1){

            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosFragment()).addToBackStack("AmigosFragment").commit();

                }

            });

        } else if(tipoProcedimento == 2) {

            botaoVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getFragmentManager().popBackStack();

                }
            });

        } else if(tipoProcedimento == 4) {

            botaoVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("idSessaoAtual", idSessao);

                    SessoesAdicionarAmigosFragment adicionarAmigosFragment = new SessoesAdicionarAmigosFragment();
                    adicionarAmigosFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, adicionarAmigosFragment).addToBackStack("sessoesListaAmigosFragment").commit();

                }
            });

        } else{

        }

    }

    public void carregarBotaoSalvarRemover(){

        if(tipoProcedimento == 1){

            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    List<String> listaTemp = usuarioAtual.getAmigos();
                    Iterator<String> i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(usuarioAmigo.getIdUsuario())){
                            i.remove();
                        }
                    }

                    usuarioAtual.setAmigos(listaTemp);

                    listaTemp = usuarioAmigo.getAmigos();
                    i = listaTemp.iterator();

                    while (i.hasNext()){
                        if (i.next().equals(usuarioAtual.getIdUsuario())){
                            i.remove();
                        }
                    }

                    usuarioAmigo.setAmigos(listaTemp);

                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    databaseReference.child("usuarios").child(usuarioAmigo.getIdUsuario()).setValue(usuarioAmigo);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosFragment()).addToBackStack("AmigosFragment").commit();

                    Toast.makeText(view.getContext(), "Usúario removido com sucesso!", Toast.LENGTH_LONG).show();

                }

            });

        } else if(tipoProcedimento == 2) {

            botaoAdicionarRemover.setText("Adicionar");
            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    usuarioAtual.getAmigos().add(usuarioAmigo.getIdUsuario());
                    databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                    usuarioAmigo.getAmigos().add(usuarioAtual.getIdUsuario());
                    databaseReference.child("usuarios").child(usuarioAmigo.getIdUsuario()).setValue(usuarioAmigo);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosFragment()).addToBackStack("AmigosFragment").commit();

                    Toast.makeText(view.getContext(), "Usúario adicionado com sucesso!", Toast.LENGTH_LONG).show();

                }

            });

        } else if(tipoProcedimento == 4) {

            botaoAdicionarRemover.setText("Adicionar a sessão");
            botaoAdicionarRemover.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    usuarioAmigo.getSessoes().add(sessao.getIdSessao());
                    sessao.getUsuarios().add(usuarioAmigo.getIdUsuario());

                    databaseReference.child("usuarios").child(usuarioAmigo.getIdUsuario()).setValue(usuarioAmigo);
                    databaseReference.child("sessoes").child(sessao.getIdSessao()).setValue(sessao);

                    Bundle bundle = new Bundle();
                    bundle.putString("idSessaoAtual", idSessao);

                    SessoesListaAmigosFragment sessoesListaAmigosFragment = new SessoesListaAmigosFragment();
                    sessoesListaAmigosFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, sessoesListaAmigosFragment).addToBackStack("sessoesListaAmigosFragment").commit();

                    Toast.makeText(view.getContext(), "Usuário adicionado a sessão com sucesso!", Toast.LENGTH_LONG).show();

                }

            });

        } else {

        }

    }

    public void carregarParametros(){
        idSessao = getArguments().getString("idSessao");
        amigoId = getArguments().getString("idAmigo");
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
