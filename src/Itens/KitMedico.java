package Itens;

import Personagens.Jogador;

public class KitMedico extends Item {

    private int cura;

    public KitMedico() {
        super("Kit Médico");
        this.cura = 2;
    }

    public void usar(Jogador jogador) {
        jogador.recuperarVida(cura);
    }
}