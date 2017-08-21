package com.example.mcspicy.belajarfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase fireData = FirebaseDatabase.getInstance();

    @BindView(R.id.register)
    Button registerButton;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.passwordTextField)
    EditText password;
    @BindView(R.id.name)
    EditText displayName;
    @BindView(R.id.statusMessage)
    EditText statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(displayName.getText().toString(),
                        statusMessage.getText().toString(),
                        emailAddress.getText().toString(),
                        password.getText().toString());
            }
        });
    }

    private void register(final String name, final String status, final String email, String password){
        fireAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Person person = new Person();
                            person.setName(name);
                            person.setStatus(status);
                            person.setEmail(email);
                            String uid = fireAuth.getCurrentUser().getUid();
                            fireData.getReference().child("Users").child(uid).setValue(person)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(loginIntent);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
