package jogo.Mapa;

import jogo.Personagem.Jogador;
import jogo.Personagem.Npc;
import jogo.Personagem.Npcs;
import jogo.Tile.Tile;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLoader {

    public static class MapResult {
        public Mapa mapa;
        public Jogador player;
        public final List<Npc> npcs = new ArrayList<>();
    }

    public static MapResult carregarDeArquivo(String caminho) {
        MapResult result = new MapResult();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("NOME:")) {
                    String nome = linha.substring(5).trim();
                    linha = br.readLine();
                    String[] dim = linha.substring(10).trim().split("x");
                    int larg = Integer.parseInt(dim[0]);
                    int alt = Integer.parseInt(dim[1]);
                    result.mapa = new Mapa(nome, larg, alt);
                } else if (linha.startsWith("MAPA:")) {
                    for (int y = 0; y < result.mapa.getAltura(); y++) {
                        String[] celulas = br.readLine().trim().split("\\s+");
                        for (int x = 0; x < result.mapa.getLargura(); x++) {
                            processarCelula(celulas[x], x, y, result);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o mapa: " + e.getMessage());
        }
        return result;
    }

    private static void processarCelula(String celula, int x, int y, MapResult res) {
        String[] partes = celula.split("\\$");
        char caractere = partes[0].charAt(0);
        int codigoCor = Integer.parseInt(partes[1]);
        Color cor = mapearCor(codigoCor);

        if (caractere == '@') {
            res.player = new Jogador(x, y, 100, 1, 1, 20, 200);
            res.mapa.setTile(x, y, new Tile('.', true, mapearCor(0)));
        }
        else if (caractere == 'N' || caractere == 'M') {
            Npcs tipoSorteado = Npcs.sortearPorTipo(caractere);
            if (tipoSorteado != null) {
                res.npcs.add(tipoSorteado.criar(x,y, res.mapa.getNome()));
                res.mapa.setTile(x, y, new Tile('.', true, mapearCor(0)));
            }
        } else if ( caractere == 'B') {
            res.mapa.setTile(x, y, new Tile('B', true, cor));
        }
        else if (caractere == 'S') {
            String destino = (partes.length > 2) ? partes[2] : null;

            int spawnX = 0;
            int spawnY = 0;

            if (partes.length > 3) {
                String[] coords = partes[3].split(",");
                spawnX = Integer.parseInt(coords[0]);
                spawnY = Integer.parseInt(coords[1]);
            }

            Tile t = new Tile('S', true, cor);
            t.setDestinoMapa(destino);
            t.setSpawnX(spawnX);
            t.setSpawnY(spawnY);

            res.mapa.setTile(x, y, t);
        }
        else {
            res.mapa.setTile(x, y, new Tile(caractere, caractere != '#', cor));
        }
    }

    private static Color mapearCor ( int codigo){
        return switch (codigo) {
            case 0 -> new Color(50, 50, 50);
            case 1 -> Color.CYAN;
            case 3 -> Color.GREEN;
            case 4 -> new Color(212, 148, 0);
            case 5 -> Color.GRAY;
            case 6 -> new Color(130, 32, 149);
            default -> Color.WHITE;
        };
    }
}