package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.daniel.gameon.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userLogin;
    private EditText passwordLogin;

    public FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View view = null;
        if(checkLogUser()){
            openMenu(view);
        }

    }

    public void openRegisterUser(View view){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity( intent );
    }

    public void signIn(final View view){

        EditText userLogin = (EditText) findViewById(R.id.idUserLogin);
        EditText passwordLogin = (EditText) findViewById(R.id.idPasswordLogin);

        this.mAuth.signInWithEmailAndPassword(userLogin.getText().toString(), passwordLogin.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            openMenu(view);
                        }
                    }
                });
    }

    public void openMenu(View view){
        Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
        startActivity( intent );
    }

    public boolean checkLogUser(){
        return this.mAuth.getCurrentUser() != null ? true : false;
    }
}
