package jogo.Tile;

import jogo.Interacao.Interacao;

import java.awt.*;

public class Tile {
    private char simbolo;
    private boolean pisavel;
    private Color color;
    private String destinoMapa;
    private Interacao ObejetoInteracao;
    private int spawnX;
    private int spawnY;

    public Tile(char simbolo, boolean pisavel, Color color) {
        this.simbolo = simbolo;
        this.pisavel = pisavel;
        this.color = color;
    }

    public void setSpawnX(int spawnX) {this.spawnX = spawnX;}
    public int getSpawnX() {return this.spawnX;}
    public int getSpawnY() {return this.spawnY;}
    public void setSpawnY(int spawnY) {this.spawnY = spawnY;}
    public void setDestinoMapa(String destinoMapa) {this.destinoMapa = destinoMapa;}
    public String getDestinoMapa() {return destinoMapa;}
    public char getSimbolo() {return simbolo;}
    public boolean isPisavel() {return pisavel;}
    public Color getColor() {return color;}
    public Interacao getObejetoInteracao() {return ObejetoInteracao;}
    public void setObejetoInteracao(Interacao ObejetoInteracao) {this.ObejetoInteracao = ObejetoInteracao;}


}
