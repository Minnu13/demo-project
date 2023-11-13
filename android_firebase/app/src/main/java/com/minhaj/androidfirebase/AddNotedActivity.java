package com.minhaj.androidfirebase;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNotedActivity extends AppCompatActivity {
    private Button submitBtn;
    private EditText addNoteEt;
    private ImageView backIv;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_note);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        listener();
    }

    private void initViews() {
        submitBtn = (Button) findViewById(R.id.submitBtn);
        addNoteEt = (EditText) findViewById(R.id.addNoteEt);
        backIv = (ImageView) findViewById(R.id.backIv);
    }

    private void listener() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = addNoteEt.getText().toString();

                String id = myRef.child("my_data").push().getKey();

                MyNote note = new MyNote(id, data);

                writeToFirebase(note);

            }
        });
    }

    private void writeToFirebase(MyNote note) {

        myRef
                .child("my_data")
                .child(note.getId())
                .setValue(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddNotedActivity.this, "success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddNotedActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}











