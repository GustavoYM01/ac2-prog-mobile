package com.example.ac2_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobile.adapters.ItemAdapter;
import com.example.ac2_mobile.model.ItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaItensActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    List<ItemModel> itemList;
    private Button btnSair;
    private FirebaseAuth auth;

    // Salvar cada item no itemList no RealtimeDatabase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference itensRef = database.getReference("itens");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        btnSair = findViewById(R.id.btnLogout);
        btnSair.setOnClickListener(view -> sair());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList.add(new ItemModel(1, "Item 1", "Continuar o curso de Angular"));
        itemList.add(new ItemModel(2, "Item 2", "Pagar IPVA do Mitsubichi Lancer EVO X"));
        itemList.add(new ItemModel(3, "Item 3", "Comprar pelo menos 10 frações de MXRF11 e VGHF11"));

        Task<DataSnapshot> snapshot = itensRef.get();

        snapshot.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot ds = task.getResult();
                if (!ds.exists()) {
                    for (ItemModel item : itemList) {
                        itensRef.push().setValue(item);
                    }
                }
            } else {
                Toast.makeText(ListaItensActivity.this,  "Erro, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
            }
        });

        itensRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListaItensActivity.this,  "ERRO: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }

    private void sair() {
        auth.signOut();
        startActivity(new Intent(ListaItensActivity.this, MainActivity.class));
    }

}