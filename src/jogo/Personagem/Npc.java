package jogo.Personagem;

import jogo.Mochila.Mochila;
import java.awt.*;

public class Npc extends Entidade {

    private final String nome;
    private final String papel;

    private String historicoConversa = "";
    private final String nomeMapa;

    public Npc(char simbolo, int x, int y, int hp, int hpMaximo, int nivel, String nome, String papel, String nomeMapa) {
        super(simbolo, hp, hpMaximo, nivel, x, y, Color.RED, new Mochila("Mochila I", 10, 20));
        this.nome = nome;
        this.papel = papel;
        this.nomeMapa = nomeMapa;
    }

    public String getNome() { return nome; }
    public String getPapel() { return papel; }
    public String getNomeMapa() { return nomeMapa; }
    public String getHistoricoConversa() { return historicoConversa; }
    public void adicionarHistoricoConversa(String fala) {this.historicoConversa += fala + "\n";}
    public int getNivel() { return nivel; }
}