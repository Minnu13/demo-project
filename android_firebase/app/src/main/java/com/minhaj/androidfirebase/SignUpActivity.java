package com.minhaj.androidfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEt;
    private EditText passEt;
    private Button signupBtn;
    private TextView gotoLoginTv;
    private ProgressBar pb;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initViews();

        listener();
    }

    private void initViews() {
        pb = findViewById(R.id.pb);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        signupBtn = findViewById(R.id.signpBtn);
        gotoLoginTv = findViewById(R.id.gotoLoginTv);
    }

    private void listener() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String pass = passEt.getText().toString();

                if (email.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb.setVisibility(View.VISIBLE);
                signup(email, pass);
            }
        });

        gotoLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signup(String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // createUserWithEmail:success
                    // now goto login

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
