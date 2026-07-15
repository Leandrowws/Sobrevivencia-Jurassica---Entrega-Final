package Itens;

import java.util.ArrayList;

public class Inventario {

    private ArrayList<Item> itens;

    public Inventario() {
        itens = new ArrayList<>();
    }

    public void adicionar(Item item) {
        itens.add(item);
    }

    public void remover(Item item) {
        itens.remove(item);
    }

    public <T extends Item> T buscar(Class<T> tipo) {
        for (Item item : itens) {
            if (tipo.isInstance(item)) {
                return tipo.cast(item);
            }
        }
        return null;
    }

    public int quantidadeArmas() {

        int quantidade = 1;

        for (Item item : itens) {
            if (item instanceof Arma) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public Arma getArmaCorpoACorpo() {

        BastaoEletrico bastao = buscar(BastaoEletrico.class);

        if (bastao != null)
            return bastao;

        return new Punhos();
    }

    public ArmaDardos getArmaDardos() {
        return buscar(ArmaDardos.class);
    }

    public KitMedico getKitMedico() {
        return buscar(KitMedico.class);
    }

    public boolean temKitMedico() {
        return getKitMedico() != null;
    }

    public boolean temArmaDardos() {
        return getArmaDardos() != null;
    }

    public boolean temBastao() {
        return buscar(BastaoEletrico.class) != null;
    }
}