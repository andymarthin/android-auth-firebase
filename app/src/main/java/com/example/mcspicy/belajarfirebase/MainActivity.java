package com.example.mcspicy.belajarfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase fireData = FirebaseDatabase.getInstance();
    @BindView(R.id.displayName)
    TextView displayName;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.logoutButton)
    Button logutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(fireAuth.getCurrentUser() == null){
            backToLogin();
        }
        logutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireAuth.signOut();
                backToLogin();
            }
        });

        getData();
    }
    private void backToLogin(){
        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(logout);
    }
    private void getData(){

        String uid = fireAuth.getCurrentUser().getUid();
        System.out.println(uid);
        fireData.getReference().child("Users").child(uid)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Person person = dataSnapshot.getValue(Person.class);
                    displayName.setText(person.getName());
                    status.setText(person.getStatus());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
