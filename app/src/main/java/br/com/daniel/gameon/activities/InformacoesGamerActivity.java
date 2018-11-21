package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Usuario;

public class InformacoesGamerActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private ArrayList<String> gameNames = new ArrayList<String>();
    private ArrayList<String> selectedGames = new ArrayList<String>();
    private Map games;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_gamer);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.get("user");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("jogos");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        games = (Map<String,Object>) dataSnapshot.getValue();
                        collectGameNames((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }

    private void collectGameNames(Map<String,Object> games) {
        gameNames = new ArrayList<>();

        for (Entry<String, Object> entry : games.entrySet()){
            Map game = (Map) entry.getValue();
            gameNames.add((String) game.get("nome"));
        }

        onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
        ListView chl = (ListView) findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, gameNames);
        chl.setAdapter(aa);

        chl.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView aux = (CheckedTextView) view.findViewById(R.id.txt_title);
                String selectedNameGame = ((TextView) view.findViewById(R.id.txt_title)).getText().toString();
                String selectedGame = null;
                for (Object entry : games.values()){
                    Map game = (Map) entry;
                    if(selectedNameGame.equals(game.get("nome"))){
                        selectedGame = (String) game.get("idJogo");
                    }
                }
                if (selectedGames.contains(selectedGame)) {
                    selectedGames.remove(selectedGame);
                    aux.setChecked(false);
                } else {
                    selectedGames.add(selectedGame);
                    aux.setChecked(true);
                }

            }
        });
    }

    public void save(View view){
        usuario.setJogos(selectedGames);
        databaseReference.child("usuarios").child(usuario.getIdUsuario()).setValue(usuario);
        Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        openMenu(view);
    }

    public void openMenu(View view){
        Intent intent = new Intent(InformacoesGamerActivity.this, MenuPrincipalActivity.class);
        startActivity( intent );
    }
}
