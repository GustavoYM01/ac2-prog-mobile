package com.example.ac2_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class ListaItensActivity extends AppCompatActivity {

    private ListView lvItens;
    private Button btnSair;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(view -> sair());

        lvItens = findViewById(R.id.lvItens);

        List<String> itens = Arrays.asList("- Fazer compras do mês", "- Pagar IPTU e IPVA",
                "- Continuar o curso de Angular", "- Fazer a revisão de 85000km do Toyota AE86");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListaItensActivity.this, android.R.layout.simple_list_item_1, itens);
        lvItens.setAdapter(adapter);
    }

    private void sair() {
        auth.signOut();
        startActivity(new Intent(ListaItensActivity.this, MainActivity.class));
    }
}