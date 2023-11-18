package com.example.ac2_mobile.model;

public class ItemModel {
    private int id;
    private String nome;
    private String descricao;

    public ItemModel() {}

    public ItemModel(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() { return id; }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}