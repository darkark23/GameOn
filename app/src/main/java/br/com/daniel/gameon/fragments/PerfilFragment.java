package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.LoginActivity;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;

public class PerfilFragment extends Fragment {

    private View myView;

    private TextView nomeUsuario;
    private TextView fraseUsuario;
    private TextView horarioDomingo;
    private TextView horarioSegunda;
    private TextView horarioTerca;
    private TextView horarioQuarta;
    private TextView horarioQuinta;
    private TextView horarioSexta;
    private TextView horarioSabado;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.perfil_fragment,container,false);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){

                    startActivity( new Intent(myView.getContext(), MenuPrincipalActivity.class));

                }
            }
        };
        carregarPerfil();
        return myView;
    }

    public void carregarPerfil(){
        String idUsuario = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("usuarios").orderByChild("idAutenticacao").startAt(idUsuario).endAt(idUsuario)
                .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Usuario usuario = children.iterator().next().getValue(Usuario.class);

                nomeUsuario = myView.findViewById(R.id.nome_usuario);
                nomeUsuario.setText(usuario.getNomeUsuario());
                fraseUsuario = myView.findViewById(R.id.frase_usuario);
                fraseUsuario.setText(usuario.getFrase());
                horarioDomingo = myView.findViewById(R.id.horario_domingo);
                horarioSegunda = myView.findViewById(R.id.horario_segunda);
                horarioTerca = myView.findViewById(R.id.horario_terca);
                horarioQuarta = myView.findViewById(R.id.horario_quarta);
                horarioQuinta = myView.findViewById(R.id.horario_quinta);
                horarioSexta = myView.findViewById(R.id.horario_sexta);
                horarioSabado = myView.findViewById(R.id.horario_sabado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
