package jogo.Mochila;

import java.util.ArrayList;
import java.util.List;
import jogo.Item.Item;

public class Mochila {
    private String nomeMochila;
    private List<Item> inventario;
    private double capacidadeMaximaKg;
    private double capacidadeMaximaLitros;

    public Mochila(String nome, double litros, double kilos) {
        this.nomeMochila = nome;
        this.capacidadeMaximaKg = kilos;
        this.capacidadeMaximaLitros = litros;
        this.inventario = new ArrayList<>();

    }

    public String getNomeMochila() {
        return nomeMochila;
    }

    public double calcularVolumeAtual() {
        double total = 0;
        for (Item item : inventario) {
            total += item.getVolumeLitros();
        }
        return total;
    }

    public double calcularPesoAtual() {
        double total = 0;
        for (Item item : inventario) {
            total += item.getPesoKg();
        }
        return total;
    }

    public void quardarItem(Item item) {
        boolean temEspaco = (calcularVolumeAtual() + item.getVolumeLitros() <= capacidadeMaximaLitros);
        boolean aguentaPeso = (calcularPesoAtual() + item.getPesoKg() <= capacidadeMaximaKg);

        if (!temEspaco && !aguentaPeso) {
            System.out.println(item.getNome() + " não cabe na mochila e é muito pesado");
        } else if (!temEspaco) {
            System.out.println("Sem espaço para " + item.getNome());
        } else if (!aguentaPeso) {
            System.out.println("Muito pesado para aguentar " + item.getNome());
        } else {
            inventario.add(item);
            System.out.println(item.getNome() + " add... mochila");
        }
    }

    public void exibirInventario() {

        double totalPeso = 0;
        double totalVolume = 0;

        System.out.println("\n==== Inventário da " + getNomeMochila() + " ====\n");

        for (Item info : inventario) {
            System.out.println("-" + info.getNome() + " | " + info.getVolumeLitros() + "L | " + info.getPesoKg() + "Kg");
            totalVolume += info.getVolumeLitros();
            totalPeso += info.getPesoKg();
        }

        double percentualOcupado = (totalVolume / capacidadeMaximaLitros) * 100;

        System.out.println("\nPeso total: " + totalPeso);
        System.out.println("Volume total: " + totalVolume);
        System.out.println("Sua mochila está com " + percentualOcupado + "% do volume ocupado\n");
    }

    public void espacoRestante() {
        double espacoSobrando = (capacidadeMaximaLitros - calcularVolumeAtual());
        System.out.println("cabe mais " + espacoSobrando + "L");
    }

    public void pesoRestante() {
        double pesoFaltando = (capacidadeMaximaLitros - calcularVolumeAtual());
        System.out.println("aguenta mais " + pesoFaltando + "kg");
    }

    public void removerItemPorNome(String nome){
        for (int posicao = 0; posicao < inventario.size(); posicao++ ){
            if(inventario.get(posicao).getNome().equals(nome)){
                System.out.println("\nRemovendo..." + inventario.get(posicao).getNome() + " da mochila");
                inventario.remove(posicao);
                break;
            }
        }
    }
}