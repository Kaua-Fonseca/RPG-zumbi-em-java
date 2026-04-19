package jogo.Personagem;

import jogo.Mochila.Mochila;

import java.awt.*;

public class Jogador extends Entidade {
    private float dinheiro;
    private int exp;

    public Jogador(int x, int y, int hp, int hpMaximo, int nivel, float dinheiro, int exp){
        super('@', hp, hpMaximo, nivel, x, y, Color.GREEN, new Mochila("Mochila I", 20, 40));
    }

    public void adicionarHp(int hp){this.hp += hp;}
    public void adicionarHpMaximo(int hpMax){this.hpMaximo += hpMax;}

    public int getExp() {return exp;}
    public void setExp(int exp) {this.exp = exp;}
    public float getDinheiro() {return dinheiro;}
    public void setDinheiro(float dinheiro) {this.dinheiro = dinheiro;}
    public int getNivel() {return nivel;}
}


