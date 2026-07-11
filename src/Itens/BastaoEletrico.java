package Itens;

import Personagens.Dinossauro;

public class BastaoEletrico extends Arma {

    public BastaoEletrico() {
        super("Bastão Elétrico");
    }

    public boolean atacar(Dinossauro alvo, int dado) {
        if (dado <= 1) {
            System.out.println("Você errou!");
            return false;
        }
        if (dado <= 4) {
            System.out.println("O bastão acertou! Causou 1 de dano!");
            alvo.receberBastaoEletrico(1);
        } else {
            System.out.println("O bastão acertou criticamente! Causou 2 de dano!");
            alvo.receberBastaoEletrico(2);
        }

        return true;
    }
}