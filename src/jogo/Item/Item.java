package jogo.Item;
import jogo.Interacao.Interacao;

public class Item implements Interacao {
    private String nome;
    private double volumeLitros;
    private double pesoKg;
    private String descricao;

    public Item(String item, double volume, double kilos, String descricao){
        this.nome = item;
        this.volumeLitros = volume;
        this.pesoKg = kilos;
        this.descricao = descricao;

    }

    @Override
    public String interagir() {
        return String.format("Item: %s | %s (Peso: %.2fkg | Vol: %.2fL", nome, descricao, volumeLitros);
    }

    public String getNome() {
        return nome;
    }
    public double getVolumeLitros(){
        return volumeLitros;
    }
    public double getPesoKg(){
        return pesoKg;
    }
    public String getDescricao(){return descricao;}

}
