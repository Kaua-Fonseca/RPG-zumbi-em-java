package jogo.Item;

public class Item {
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
