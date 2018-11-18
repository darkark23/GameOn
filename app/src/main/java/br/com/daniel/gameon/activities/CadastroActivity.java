package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.daniel.gameon.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private EditText userConfirmPassword;

    private TextView errorEmail;
    private TextView errorPassword;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        errorEmail = (TextView) findViewById(R.id.errorEmail);
        errorPassword = (TextView) findViewById(R.id.errorPassword);

        errorEmail.setVisibility(View.INVISIBLE);
        errorPassword.setVisibility(View.INVISIBLE);
    }

    public void backToLogin(View view){
        personalInformation(view);
//        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
//        startActivity( intent );
    }

    public void personalInformation(View view){
        Intent intent = new Intent(CadastroActivity.this, InformacoesPessoaisActivity.class);
        startActivity( intent );
    }

    public void registerUser(final View view){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.userNameRegister);
        userEmail = (EditText) findViewById(R.id.userEmailRegister);
        userPassword = (EditText) findViewById(R.id.userPasswordRegister);
        userConfirmPassword = (EditText) findViewById(R.id.userConfirmPasswordRegister);

        errorEmail.setVisibility(View.INVISIBLE);
        errorPassword.setVisibility(View.INVISIBLE);

        if(validEmail(userEmail.getText().toString())){
            if(userPassword.getText().toString().equals(userConfirmPassword.getText().toString())){
                mAuth.createUserWithEmailAndPassword(userEmail.getText().toString(), userPassword.getText().toString())
                        .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    personalInformation(view);
                                }
                            }
                        });
            }else{
                errorPassword.setVisibility(View.VISIBLE);
            }
        }else{
            errorEmail.setVisibility(View.VISIBLE);
        }

    }

    public static boolean validEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
