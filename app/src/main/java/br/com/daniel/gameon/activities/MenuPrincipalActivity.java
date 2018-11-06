package br.com.daniel.gameon.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import br.com.daniel.gameon.fragments.AmigosFragment;
import br.com.daniel.gameon.fragments.GamesFragment;
import br.com.daniel.gameon.fragments.PerfilFragment;
import br.com.daniel.gameon.R;
import br.com.daniel.gameon.fragments.SessoesFragment;

public class MenuPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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

        fragmentManager.beginTransaction().
                replace(R.id.content_frame,new PerfilFragment()).commit();

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
                    replace(R.id.content_frame,new AmigosFragment()).commit();
        } else if (id == R.id.item_games) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new GamesFragment()).commit();
        } else if (id == R.id.item_sessoes_jogos) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new SessoesFragment()).commit();
        } else if (id == R.id.item_perfil) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame,new PerfilFragment()).commit();
        } else if (id == R.id.item_sair) {
            Intent intent = new Intent(MenuPrincipalActivity.this, LoginActivity.class);
            startActivity( intent );
            firebaseAuth.signOut();
        } else if (id == R.id.item_configuracaoes) {

        } else if (id == R.id.item_avalie) {
            startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/")) );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
