package com.example.mcspicy.belajarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    @BindView(R.id.emailTextField)
    EditText emailText;
    @BindView(R.id.passwordField)
    EditText passwordText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        isLogin();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(emailText.getText().toString(), passwordText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private void login(String email, String password){
        if (!email.isEmpty() && !password.isEmpty()){

            this.fireAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void isLogin(){
        if(fireAuth.getCurrentUser() != null){
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
        }
    }
}
