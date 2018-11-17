package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.util.CargaInicialUtil;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    startActivity( new Intent(LoginActivity.this, MenuPrincipalActivity.class));
                } else {
                    setContentView(R.layout.activity_login);
                }

            }
        };



    }

    @Override
    protected void onStart() {

        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);

    }

    public void openRegisterUser(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);

        startActivity( intent );

    }

    public void openMenuPrincipal(View view){

        EditText usuarioEmail = findViewById(R.id.usuario_email);
        EditText usuarioSenha = findViewById(R.id.usuario_senha);

        String email = usuarioEmail.getText().toString();
        String senha = usuarioSenha.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){
            Toast.makeText(LoginActivity.this, "Informe a senha e o email!", Toast.LENGTH_LONG).show();
        } else {

            firebaseAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Erro desconhecido tente novamente!", Toast.LENGTH_LONG).show();
                    }

                }

            });
        }
    }

    public void cargaInicial(View view){
        CargaInicialUtil.cargaInicial();
    }

}
