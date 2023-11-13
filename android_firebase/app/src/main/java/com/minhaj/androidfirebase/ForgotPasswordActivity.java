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

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEt;
    private Button forgotPasswordBtn;
    private ProgressBar pb;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initViews();

        listener();
    }

    private void initViews() {

        pb = findViewById(R.id.pb);
        emailEt = findViewById(R.id.emailEt);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
    }

    private void listener() {
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEt.getText().toString();

                if (email.length() == 0) {
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                    return;
                }


                pb.setVisibility(View.VISIBLE);
                forgotPassword(email);

            }
        });
    }

    private void forgotPassword(String email) {

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Forgot password changed linked has been send to your mail.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
