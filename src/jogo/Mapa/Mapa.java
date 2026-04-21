package jogo.Mapa;

import jogo.Personagem.Npc;
import jogo.Tile.Tile;

public class Mapa {
    private String nome;
    private int largura;
    private int altura;
    private Tile[][] tiles;

    public Mapa(String nome, int largura, int altura) {
        this.nome = nome;
        this.largura = largura;
        this.altura = altura;
        this.tiles = new Tile[altura][largura];
    }

    public void setTile(int x, int y, Tile tile) {
        if(posicaoValida(x, y)){
            tiles[y][x] = tile;
        }
    }

    public Tile getTile(int x, int y) {
        if(posicaoValida(x, y)){
            return tiles[y][x];
        }
        return null;
    }

    public boolean isPisavel(int x, int y) {
        Tile tile = getTile(x, y);
        return tile != null && tile.isPisavel();
    }

    public boolean posicaoValida(int x, int y){ return x >= 0 && x < largura && y >= 0 && y < altura; }

    public String getNome() {return nome;}
    public int getLargura() {return largura;}
    public int getAltura() {return altura;}
}
