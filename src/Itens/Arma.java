package Itens;

import Personagens.Dinossauro;
import Personagens.Jogador;

public abstract class Arma extends Item {

    public Arma(String nome) {
        super(nome);
    }

    public abstract boolean atacar(Dinossauro alvo, int dado, Jogador jogador);
}