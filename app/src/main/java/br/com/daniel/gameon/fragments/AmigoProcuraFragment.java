package br.com.daniel.gameon.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.HorariosUtil;

public class AmigoProcuraFragment extends Fragment {

    private View view;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText campoNome;
    private List<Jogo> listaGames;
    private List<String> listaGamesNome;
    private AutoCompleteTextView gamesAutoCompleteTextView;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        getActivity().setTitle("Amigos - Buscar usuários");
        view = inflater.inflate(R.layout.amigo_procurar_fragment,container,false);
        campoNome = view.findViewById(R.id.campo_nome);
        adicionarBotaoPesquisar();
        adicionarBotaoVoltar();
        carregarListaGames();
        return view;

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
        botaoVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new AmigosFragment()).addToBackStack("AmigosFragment").commit();

            }

        });

    }

    public void adicionarBotaoPesquisar(){


        Button botaoRemover = view.findViewById(R.id.botao_pesquisar);
        botaoRemover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Boolean existeJogo = false;
                String nomeJogo = gamesAutoCompleteTextView.getText().toString();

                for (String jogo: listaGamesNome ){
                    if (jogo.equals(nomeJogo)){
                        existeJogo = true;
                        break;
                    }
                }

                if (!nomeJogo.equals("") && !existeJogo){
                    Toast.makeText(view.getContext(),"O game escolhido não existe!",Toast.LENGTH_LONG).show();
                } else {
                    InputMethodManager imm =(InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);

                    Bundle bundle = new Bundle();
                    bundle.putString("nomePesquisa",campoNome.getText().toString());

                    if (existeJogo){
                        Integer index = listaGamesNome.indexOf(nomeJogo);
                        bundle.putString("idGame",listaGames.get(index).getIdJogo());
                    }

                    AmigosResultadoPesquisaFragment amigosResultadoPesquisaFragment = new AmigosResultadoPesquisaFragment();
                    amigosResultadoPesquisaFragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().
                            replace(R.id.content_frame, amigosResultadoPesquisaFragment).addToBackStack("AmigosResultadoPesquisaFragment").commit();
                }

            }

        });

    }

    public void carregarListaGames(){

        gamesAutoCompleteTextView = view.findViewById(R.id.jogo_text_view);

        databaseReference.child("jogos").orderByChild("nome").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaGames = new ArrayList<Jogo>();
                listaGamesNome = new ArrayList<String>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    Jogo jogo = child.getValue(Jogo.class);
                    listaGames.add(jogo);
                    listaGamesNome.add(jogo.getNome());
                }

                ArrayAdapter<String> arrayAdapterjogos = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line,listaGamesNome);
                gamesAutoCompleteTextView.setAdapter(arrayAdapterjogos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

}
