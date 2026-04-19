package jogo.Personagem;
import jogo.Mochila.Mochila;
import java.awt.*;

public class Entidade {
    protected int x,y;
    protected int hp;
    protected int hpMaximo;
    protected Mochila mochila;
    protected int nivel;
    protected char simbolo;
    protected Color cor;

    public Entidade(char simbolo, int hp, int hpMaximo, int nivel, int x, int y, Color cor, Mochila mochila){
        this.simbolo = simbolo;
        this.hp = hp;
        this.hpMaximo = hpMaximo;
        this.nivel = nivel;
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.mochila = mochila;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getHp() {return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getHpMaximo() { return hpMaximo; }
    public void setHpMaximo(int hpMaximo) {this.hpMaximo = hpMaximo;}
    public char getSimbolo() {return simbolo; }
    public Color getCor() { return cor; }
    public Mochila getMochila(){return mochila;}
    public void setMochila(Mochila mochila) {this.mochila = mochila;}

}
