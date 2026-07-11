package Itens;

import Personagens.Dinossauro;

public class Punhos extends Arma {

    public Punhos() {
        super("Punhos");
    }

    public boolean atacar(Dinossauro alvo, int dado) {
        if (dado <= 2) {
            System.out.println("Você errou!");
            return false;
        }
        if (dado <= 5) {
            if (alvo.receberAtaqueBasico(1))
                System.out.println("O soco acertou! Causou 1 de dano!");
        } else {
            if (alvo.receberAtaqueBasico(2))
                System.out.println("O soco acertou criticamente! Causou 2 de dano!");
        }

        return true;
    }
}