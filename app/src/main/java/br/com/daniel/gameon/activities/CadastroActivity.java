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
import com.google.firebase.auth.FirebaseAuthException;

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

    public void registerUser(final View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        EditText userName = (EditText) findViewById(R.id.userNameRegister);
        EditText userEmail = (EditText) findViewById(R.id.userEmailRegister);
        EditText userPassword = (EditText) findViewById(R.id.userPasswordRegister);

        mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            backToLogin(view);
                        }
                    }
                });

    }

}
