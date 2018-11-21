package br.com.daniel.gameon.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.fragments.AmigosFragment;
import br.com.daniel.gameon.fragments.GamesFragment;
import br.com.daniel.gameon.fragments.PerfilFragment;
import br.com.daniel.gameon.R;
import br.com.daniel.gameon.fragments.SessoesFragment;
import br.com.daniel.gameon.util.DownloadImagemUtil;

public class MenuPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FragmentManager fragmentManager = getFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        carregarUsuario(hView);

        fragmentManager.beginTransaction().
                replace(R.id.content_frame,new PerfilFragment()).addToBackStack("MenuPrincipal").commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.item_amigo) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new AmigosFragment()).addToBackStack("AmigosFragment").commit();
        } else if (id == R.id.item_games) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new GamesFragment()).addToBackStack("GamesFragment").commit();
        } else if (id == R.id.item_sessoes_jogos) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new SessoesFragment()).addToBackStack("SessoesFragment").commit();
        } else if (id == R.id.item_perfil) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new PerfilFragment()).addToBackStack("PerfilFragment").commit();
        } else if (id == R.id.item_sair) {
            onSignOut();
        } else if (id == R.id.item_configuracaoes) {

        } else if (id == R.id.item_avalie) {
            startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/")) );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSignOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(MenuPrincipalActivity.this, LoginActivity.class);
        startActivity( intent );
    }

    public void carregarUsuario(View hView){

        String idAutenticacaoUsuario = firebaseAuth.getCurrentUser().getUid();

        final TextView fraseUsuario = hView.findViewById(R.id.frase_usuario);
        final ImageView imagemUsuario = hView.findViewById(R.id.imagem_usuario);
        final TextView nomeUsuario = hView.findViewById(R.id.nome_usuario);

        databaseReference.child("usuarios").orderByChild("idAutenticacao").equalTo(idAutenticacaoUsuario)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        Usuario usuario = children.iterator().next().getValue(Usuario.class);

                        fraseUsuario.setText(usuario.getFrase());
                        nomeUsuario.setText(usuario.getNomeUsuario());
                        if (usuario.getUrlImagem()!= null){
                            new DownloadImagemUtil(imagemUsuario).execute(usuario.getUrlImagem());
                        }else{
                            StorageReference ref = storage.getReference().child(usuario.getIdImagem());
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    new DownloadImagemUtil(imagemUsuario).execute(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

    }

}
