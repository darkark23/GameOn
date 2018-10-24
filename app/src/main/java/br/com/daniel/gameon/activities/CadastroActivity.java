package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.daniel.gameon.R;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void backToLogin(View view){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity( intent );
    }
}
