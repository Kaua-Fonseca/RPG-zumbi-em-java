package jogo.Personagem;

import jogo.Interacao.Interacao;
import jogo.Mochila.Mochila;
import java.awt.*;

public class Npc extends Entidade implements Interacao {
    private final String dialogoPadrao; // Caso a IA esteja offline
    private final String nome;
    private final String papel; // Ex: "Médico", "Sobrevivente", "Guarda"
    private String historicoConversa = "";
    private int nivel;

    public Npc(char simbolo, int x, int y, int hp, int hpMaximo, int nivel, String dialogo, String nome, String papel) {
        super(simbolo, hp, hpMaximo, nivel, x, y, Color.RED, new Mochila("Mochila I", 10, 20));
        this.dialogoPadrao = dialogo;
        this.nome = nome;
        this.nivel = nivel;
        this.papel = papel;
    }

    // Getters para o GeminiServices usar no prompt
    public String getNome() { return nome; }
    public String getPapel() { return papel; }
    public String getHistoricoConversa() { return historicoConversa; }
    public void adicioanarHistoricoConversa(String fala) {
        this.historicoConversa += fala + "\n";
    }
    public int getNivel() { return nivel; }

    @Override
    public String interagir() {

        return "["  + this.nome + "] " + "[ HP:" + this.hp+ " ] " + "[ LV: " + this.nivel +" ] \n" + this.dialogoPadrao;
    }
}