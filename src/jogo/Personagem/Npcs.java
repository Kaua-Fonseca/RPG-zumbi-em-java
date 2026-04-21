package jogo.Personagem;

import java.util.ArrayList;
import java.util.List;

public enum Npcs {

    MEDICO('N', 80, 800, 1, "Dr. Albert", "Médico cirurgião", "remédios..."),
    SOLDADO('N', 140, 150, 2, "Sgt. Rocha", "Soldado", "aponto minha arma..."),
    MERCADOR('N', 100, 100, 9, "Ronald", "Mercador do mercado negro", "ola viajente do que precisa..."),
    ENGENHEIRO('N', 90, 90, 4, "Carlos", "Engenheiro de armas", "você teria algumas peças de armaas.."),
    SOBREVIVENTE('N', 50, 60, 1, "Marina", "uma mulher a porcura de sua familia", "você viu meu irmão..."),
    LIDER('N', 120, 130, 10, "Jeferson", "um lider construindo seu imperio no apocalipse", "ola meu jovem, voce se juntaria a nos..."),
    FERREIRO('N', 100, 100, 7, "Victor", "um ferreiro abilidoso e desconfiado", "posso melhorar seus equipamentos meu rapaz?..."),

    //Zumbis
    ZUMBI1('M', 50, 50, 1,  "Zumbi Comum", "Zumbi Lento", "Grrr!"),
    ZUMBI2('M', 200, 200, 5, "Tanker", "Zumbi Gigante e Furioso" , "ROAAR!"),
    ZUMBI3('M', 70, 70, 2, "Zumbi Rápido", "Zumbi Ágil", "Hsss..."),
    ZUMBI4('M', 300, 300, 8, "Brutamontes", "Zumbi Mutante", "GROOOOAAAR!"),
    ZUMBI5('M', 60, 60, 1, "Zumbi Ferido", "Zumbi Fraco", "Urrr...");



    public final char simbolo;
    public final int hp;
    public final int hpMax;
    public final int nivel;
    public final String nome;
    public final String papel;
    public final String falaIncial;

    Npcs(char simbolo, int hp, int hpMax, int nivel, String nome, String papel,  String falaIncial) {
        this.simbolo = simbolo;
        this.hp = hp;
        this.hpMax = hpMax;
        this.nivel = nivel;
        this.nome = nome;
        this.papel = papel;
        this.falaIncial = falaIncial;
    }

    public String getFalaIncial() { return falaIncial;}

    public Npc criar(int x, int y, String nomeMapa) {

        Npc npc = new Npc(
                simbolo, x, y, hp, hpMax, nivel, nome, papel, nomeMapa
        );
        npc.adicionarHistoricoConversa(getFalaIncial());
        return npc;
    }

    public static Npcs sortearPorTipo(char tipoBuscado){
        List<Npcs> filtrados = new ArrayList<>();

        for (Npcs npc : Npcs.values()){
            if (npc.simbolo == tipoBuscado){
                filtrados.add(npc);
            }
        }
        if (filtrados.isEmpty()) return null;

        //sorteia apenas a lista filtrada
        return filtrados.get((int) (Math.random() * filtrados.size()));
    }
}


