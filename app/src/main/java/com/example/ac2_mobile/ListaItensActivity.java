package com.example.ac2_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobile.adapters.ItemAdapter;
import com.example.ac2_mobile.model.ItemModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ListaItensActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    List<ItemModel> itemList;
    private Button btnSair;
    private FirebaseAuth auth;

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
        itemList.add(new ItemModel("Item 1", "Continuar o curso de Angular"));
        itemList.add(new ItemModel("Item 2", "Pagar IPVA do Mitsubichi Lancer EVO X"));
        itemList.add(new ItemModel("Item 3", "Comprar pelo menos 10 frações de MXRF11 e VGHF11"));

        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }

    private void sair() {
        auth.signOut();
        startActivity(new Intent(ListaItensActivity.this, MainActivity.class));
    }

}