package jogo.Game;

import jogo.Mapa.MapLoader;
import jogo.Mapa.Mapa;
import jogo.Personagem.Jogador;
import jogo.Personagem.Npc;
import jogo.Tile.Tile;
import java.util.List;

public class Game {
    private Mapa mapa;
    private Jogador player;
    private List<Npc> npcs;

    public Game() {
        MapLoader.MapResult res = MapLoader.carregarDeArquivo("assets/mapa_inicial.txt");
        this.mapa = res.mapa;
        this.player = res.player;
        this.npcs = res.npcs;
    }
/// /////////////////////////////// trocar de mapa
    public void trocarMapa(String caminho, int x, int y) {
        MapLoader.MapResult res = MapLoader.carregarDeArquivo("assets/" + caminho);

        this.mapa = res.mapa;
        this.player = res.player;
        this.npcs = res.npcs;

        player.setX(x);
        player.setY(y);
    }

    public void verificarTransicao() {
        Tile tile = mapa.getTile(player.getX(), player.getY());

        if (tile.getDestinoMapa() != null) {
            trocarMapa(tile.getDestinoMapa(), tile.getSpawnX(), tile.getSpawnY());
        }
    }
    ////////////////////////////////
    public Mapa getMapa() { return mapa; }
    public Jogador getPlayer() { return player; }
    public List<Npc> getNpcs() { return npcs; }

    public void moverPlayer(char tecla) {
        int novoX = player.getX();
        int novoY = player.getY();

        verificarTransicao();

        switch (Character.toUpperCase(tecla)) {
            case 'W' -> novoY--;
            case 'S' -> novoY++;
            case 'A' -> novoX--;
            case 'D' -> novoX++;
        }

        // Validação de limites e colisão com parede
        if (mapa.isPisavel(novoX, novoY)) {
            player.setX(novoX);
            player.setY(novoY);
        }
    }


    public Npc getNpcNaPosicao() {
        for (Npc npc : npcs) {
            if (npc.getX() == player.getX() && npc.getY() == player.getY()) {
                return npc;
            }
        }
        return null;
    }

    public Tile getTileAtual() {
        return mapa.getTile(player.getX(), player.getY());
    }


    /**
     * Gera uma representação em String (ASCII) do estado atual do mapa.
     * Útil para logs ou exibições simples sem cores.
     */
    public String renderizarMapa() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < mapa.getAltura(); y++) {
            for (int x = 0; x < mapa.getLargura(); x++) {
                sb.append(getSimboloNaPosicao(x, y)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Determina qual caractere exibir em uma coordenada específica.
     * Prioridade: Player > NPCs > Terreno (Tile).
     */
    public char getSimboloNaPosicao(int x, int y) {
        if (player.getX() == x && player.getY() == y) return player.getSimbolo();

        for (Npc npc : npcs) {
            if (npc.getX() == x && npc.getY() == y) return npc.getSimbolo();
        }

        Tile t = mapa.getTile(x, y);
        return (t != null) ? t.getSimbolo() : ' ';
    }

    public List<Npc> getNpcs(int x, int y) {
        return npcs;
    }

}
