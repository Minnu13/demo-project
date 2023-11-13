package com.minhaj.androidfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FloatingActionButton fabAdd;
    private ArrayList<MyNote> arrayList = new ArrayList<>();
    private DatabaseReference myRef;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        listener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromFirebase();
    }

    private void initViews() {
        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
    }

    private void listener() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddNotedActivity.class);
                startActivity(intent);

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadList() {
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, arrayList);
        rv.setAdapter(adapter);
    }

    public void getListFromFirebase() {
        myRef.child("my_data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        MyNote aa = (MyNote) ds.getValue(MyNote.class);
                        arrayList.add(aa);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                loadList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}










