package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.daniel.gameon.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void openRegisterUser(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( intent );
    }

    public void openMenuPrincipal(View view){
        Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
        startActivity( intent );
    }
}
