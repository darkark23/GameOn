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

import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.HorariosUtil;

public class EditarAmigoFragment extends Fragment {

    private String amigoId;
    private Integer tipo;

    private View view;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        view = inflater.inflate(R.layout.amigo_editar_fragment,container,false);
        amigoId = getArguments().getString("idAmigo");
        tipo = getArguments().getInt("tipo");
        carregarPerfil();
        adicionarBotaoRemover();
        adicionarBotaoVoltar();
        return view;

    }

    public void carregarPerfil(){

        String idUsuario = firebaseAuth.getCurrentUser().getUid();

        final TextView fraseUsuario = view.findViewById(R.id.frase_usuario);
        final TextView nomeUsuario = view.findViewById(R.id.nome_usuario);

        databaseReference.child("usuarios").orderByChild("idUsuario").equalTo(amigoId)
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Usuario usuario = children.iterator().next().getValue(Usuario.class);

                nomeUsuario.setText(usuario.getNomeUsuario());
                fraseUsuario.setText(usuario.getFrase());

                carregarHorario(usuario.getIdHorarios(),usuario.getIdUsuario());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                nomeUsuario.setText(databaseError.getMessage());
                fraseUsuario.setText(databaseError.getMessage());

            }
        });

    }

    public void carregarHorario(String idHorario,String idUsuario){

        final TextView horarioDomingo = view.findViewById(R.id.horario_domingo);
        final TextView horarioSegunda = view.findViewById(R.id.horario_segunda);
        final TextView horarioTerca = view.findViewById(R.id.horario_terca);
        final TextView horarioQuarta = view.findViewById(R.id.horario_quarta);
        final TextView horarioQuinta = view.findViewById(R.id.horario_quinta);
        final TextView horarioSexta = view.findViewById(R.id.horario_sexta);
        final TextView horarioSabado = view.findViewById(R.id.horario_sabado);

        databaseReference.child("horarios").orderByChild("idUsuario").startAt(idUsuario).endAt(idUsuario)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Horarios horarios;
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        if (children.iterator().hasNext()){
                            horarios = children.iterator().next().getValue(Horarios.class);
                        } else {
                            horarios = HorariosUtil.horariosNaoPreenchidos();
                        }

                        horarioDomingo.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(0)));
                        horarioSegunda.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(1)));
                        horarioTerca.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(2)));
                        horarioQuarta.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(3)));
                        horarioQuinta.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(4)));
                        horarioSexta.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(5)));
                        horarioSabado.setText(DataUtil.formatHorarioPerfil(horarios.getHoariosInicio().get(6)));

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

    public void adicionarBotaoVoltar(){

        Button botaoVoltar = view.findViewById(R.id.botao_voltar);

        if(tipo == 1){
            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosFragment()).commit();

                }

            });
        } else {
            botaoVoltar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    getFragmentManager().popBackStack();
                    /*getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosResultadoPesquisaFragment()).commit();*/

                }

            });
        }

    }

    public void adicionarBotaoRemover(){

            Button botaoRemover = view.findViewById(R.id.botao_remover);
            if(tipo == 1){
                botaoRemover.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        getFragmentManager().beginTransaction().
                                replace(R.id.content_frame, new AmigosFragment()).commit();

                    }

                });
            } else {
                botaoRemover.setText("Adicionar");
                botaoRemover.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        carregarUsuario();
                        getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, new AmigosFragment()).commit();

                    }

                });
            }
    }

    public void carregarUsuario(){

        String idAutenticacaoUsuario = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("usuarios").orderByChild("idAutenticacao").equalTo(idAutenticacaoUsuario)
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Usuario usuario = children.iterator().next().getValue(Usuario.class);
                List<String> listaAmigos = usuario.getAmigos();
                listaAmigos.add(usuario.getIdUsuario());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }



}